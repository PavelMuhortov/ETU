package com.example.market.core.data;

import com.example.market.core.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class FileRepository<M extends Model<M>> implements Repository<M> {

    public static final String SEPARATOR = ",";
    private static final Logger LOGGER = LoggerFactory.getLogger(FileRepository.class);
    private final File file;
    private final Supplier<M> modelFactory;
    private Map<Long, M> data;
    private final AtomicLong indexGenerator;

    public FileRepository(File file, Supplier<M> modelFactory) {
        this.file = file;
        this.modelFactory = modelFactory;
        if (!Files.exists(file.toPath())) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                LOGGER.error("Can't create file [{}]", file, e);
                throw new RuntimeException("Can't create file", e);
            }
        }
        initData();
        long maxIndex = data.keySet().stream()
                .mapToLong(l -> l)
                .max().orElse(0);
        indexGenerator = new AtomicLong(maxIndex);
    }

    private void initData() {
        data = readFile().stream()
                .collect(toMap(M::getId, m -> m));
    }


    @Override
    public void delete(long index) {
        data.remove(index);
        writeFile();
    }

    @Override
    public void save(M model) {
        List<String> propertyNames = model.getPropertyNames();
        if (model.getId() == 0) {
            model.setId(indexGenerator.incrementAndGet());
        }
        for (String propertyName : propertyNames) {
            String propertyValue = model.getPropertyValue(propertyName);
            model.setPropertyValue(propertyName, propertyValue);
        }
        data.put(model.getId(), model);
        writeFile();
    }

    @Override
    public Collection<M> getAll() {
        initData();
        return data.values();
    }

    private List<M> readFile() {
        try (var lines = Files.lines(file.toPath())) {
            return lines.map(s -> s.split(SEPARATOR))
                    .map(params -> {
                        M model = modelFactory.get();
                        model.setId(Long.parseLong(params[0]));
                        List<String> propertyNames = model.getPropertyNames();
                        IntStream.range(0, propertyNames.size())
                                .forEach(index -> {
                                    model.setPropertyValue(propertyNames.get(index), params[index + 1]);
                                });
                        return model;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Can't read file [{}]", file, e);
            throw new IllegalStateException("Can't read file", e);
        }
    }

    private void writeFile() {
        try (var writer = new PrintWriter(Files.newBufferedWriter(file.toPath()))) {
            data.values().stream()
                    .map(model -> Stream.concat(
                            Stream.of(String.valueOf(model.getId())),
                            model.getPropertyNames().stream().map(model::getPropertyValue)
                            ).collect(Collectors.joining(SEPARATOR))
                    ).forEach(writer::println);
        } catch (Exception e) {
            LOGGER.error("Can't write file [{}]", file, e);
            throw new IllegalStateException("Can't write file", e);
        }
    }
}
