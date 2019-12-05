package com.example.market.swingview.model;

import com.example.market.core.model.BaseModel;
import com.example.market.core.model.Named;

@Named("Марки")
public class Stamp extends BaseModel<Stamp> {

    @Named("Название")
    private String name;

    @Named("Коллекция")
    private String collectionName;

    @Named("Страна")
    private String country;

    @Named("Позиция")
    private int position;

}
