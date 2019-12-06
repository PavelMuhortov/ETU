package com.example.market.swingview.model;

import com.example.market.core.model.BaseModel;
import com.example.market.core.model.Named;

@Named("Марки")
public class Stamp extends BaseModel<Stamp> {

    @Named("Название")
    private String stampName;

    @Named("Коллекция")
    private String collectionName;

    @Named("Страна")
    private String country;

    @Named("Позиция")
    private int position;

    public String getStampName() {
        return stampName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCountry() {
        return country;
    }

    public String getPosition() {
        return String.valueOf(position);
    }
}
