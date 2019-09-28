package com.example.market.swingview.model;

import com.example.market.core.model.BaseModel;
import com.example.market.core.model.Named;

public class Product
        extends BaseModel<Product> {

    @Named("Имя")
    private String name;

    @Named("Описание")
    private String description;

    @Named("Количество")
    private int amount;

    @Named("Ед. изм.")
    private String units;

    public Product(String name, String description, int amount, String units) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.units = units;
    }

    public Product() {
    }

}
