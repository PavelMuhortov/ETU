package com.example.market.core.model;

import java.util.Objects;

/**
 * Определение поля модели
 */
public class PropDef {

    /**
     * Имя поля модели напр. {@code name}
     */
    private final String propertyName;

    /**
     * Имя поля для отображения напр. {@code Имя}
     */
    private final String propertyDisplayedName;

    public PropDef(String propertyName, String propertyDisplayedName) {
        this.propertyName = Objects.requireNonNull(propertyName);
        this.propertyDisplayedName = propertyDisplayedName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyDisplayedName() {
        if (propertyDisplayedName != null) {
            return propertyDisplayedName;
        } else {
            return propertyName;
        }
    }
}
