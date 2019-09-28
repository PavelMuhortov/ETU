package com.example.market.core.model;

import java.util.Objects;

public class PropDef {

    private final String propertyName;

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
