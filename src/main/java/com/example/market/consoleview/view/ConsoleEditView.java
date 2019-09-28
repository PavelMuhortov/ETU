package com.example.market.consoleview.view;

import com.example.market.core.model.Model;
import com.example.market.core.model.PropDef;
import com.example.market.core.view.EditView;

import java.util.List;

public class ConsoleEditView<M extends Model<M>>
        extends ConsoleView
        implements EditView<M> {

    private static final int SIZE = 20;

    @Override
    public M edit(M model) {
        final List<PropDef> propDefs = model.getPropDefs();
        while (true) {
            clearScreen();
            for (int i = 0; i < propDefs.size(); i++) {
                final String propertyName = propDefs.get(i).getPropertyName();
                final String propertyDisplayedName = propDefs.get(i).getPropertyDisplayedName();
                final String propertyValue = model.getPropertyValue(propertyName);
                System.out.println(toSize(String.valueOf(i), 5, ' ') + "| " + toSize(propertyDisplayedName, SIZE, ' ') + "|" + toSize(propertyValue, SIZE, ' '));
            }
            if (askForConfirm()) {
                break;
            }
            final int number = askForNumber(propDefs.size());
            final String value = getValue();
            model.setPropertyValue(propDefs.get(number).getPropertyName(), value);
        }
        return model;
    }


}
