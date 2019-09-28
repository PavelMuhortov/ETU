package com.example.market.core.model;

import java.util.List;

public interface Model<M> {

    long getId();

    void setId(long id);

    List<PropDef> getPropDefs();

    String getPropertyValue(String propertyName);

    void setPropertyValue(String propertyName, String value);

}
