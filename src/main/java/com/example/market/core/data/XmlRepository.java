package com.example.market.core.data;

import com.example.market.core.model.Model;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class XmlRepository<M extends Model<M>> implements Repository<M> {

    private static final String DATA = "data";
    private static final String ID = "id";
    private final File file;
    private final Supplier<M> modelFactory;
    private Document document;
    private long maxId = 0L;

    public XmlRepository(File file, Supplier<M> modelFactory) {
        this.file = file;
        this.modelFactory = modelFactory;
        initDocument();
        document.normalizeDocument();
        maxId = getData().stream()
                .mapToLong(this::getModelId)
                .max()
                .orElse(maxId);
    }

    private List<Node> getData() {
        NodeList elementsByTagName = document.getElementsByTagName(getTagName());
        List<Node> result = new ArrayList<>(elementsByTagName.getLength());
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            result.add(elementsByTagName.item(i));
        }
        return result;
    }

    @Override
    public void delete(long index) {
        deleteElement(index);
        writeFile();
    }

    @Override
    public void save(M model) {
        if (model.getId() == 0) {
            model.setId(++maxId);
            appendElement(model);
        } else {
            updateElement(model);
        }
        writeFile();
    }

    @Override
    public Collection<M> getAll() {
        List<Node> childNodes = getData();
        return childNodes.stream()
                .map(item -> {
                    NamedNodeMap attributes = item.getAttributes();
                    M model = modelFactory.get();
                    model.setId(getModelId(item));
                    model.getPropertyNames()
                            .forEach(propertyName -> {
                                String value = attributes.getNamedItem(propertyName).getNodeValue();
                                model.setPropertyValue(propertyName, value);
                            });
                    return model;
                })
                .collect(Collectors.toList());
    }

    private void deleteElement(long index) {
        Node elementById = getElementById(index);
        elementById.getParentNode().removeChild(elementById);
    }

    private Node getElementById(long index) {
        return getData().stream()
                .filter(node -> getModelId(node) == index)
                .findFirst()
                .orElse(null);
    }

    private void updateElement(M model) {
        Node elementById = getElementById(model.getId());
        NamedNodeMap attributes = elementById.getAttributes();
        model.getPropertyNames()
                .forEach(propertyName -> {
                    Node property = attributes.getNamedItem(propertyName);
                    property.setNodeValue(model.getPropertyValue(propertyName));
                });
    }

    private String getElementId(long id) {
        return String.valueOf(id);
    }

    private void appendElement(M model) {
        Element element = document.createElement(getTagName());
        element.setAttribute(ID, getElementId(model.getId()));
        model.getPropertyNames()
                .forEach(propertyName -> element.setAttribute(propertyName, model.getPropertyValue(propertyName)));
        document.getDocumentElement().appendChild(element);
    }

    private String getTagName() {
        return modelFactory.get().getName();
    }

    private long getModelId(Node item) {
        String idString = item.getAttributes().getNamedItem(ID).getNodeValue();
        return Long.parseLong(idString);
    }

    private void initDocument() {
        try {
            if (file.createNewFile() || file.length() == 0) {
                document = getDocumentBuilderFactory()
                        .newDocumentBuilder().newDocument();
                Element element = document.createElement(getRootElementName());
                document.appendChild(element);
                writeFile();
            }
            document = readFile();
        } catch (Exception e) {
            throw new IllegalStateException("Can't parse file: " + file, e);
        }
    }

    private DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        return documentBuilderFactory;
    }

    private void writeFile() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(fileWriter));
        } catch (Exception e) {
            throw new IllegalStateException("Can't write file: " + file, e);
        }
    }

    private Document readFile() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        Document parse = getDocumentBuilderFactory().newDocumentBuilder().parse(file);
        XPath xp = XPathFactory.newInstance().newXPath();
        // ugly hack to remove unnecessary blank lines
        NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", parse, XPathConstants.NODESET);
        for (int i = 0; i < nl.getLength(); ++i) {
            Node node = nl.item(i);
            node.getParentNode().removeChild(node);
        }
        return parse;
    }

    private String getRootElementName() {
        return DATA;
    }
}
