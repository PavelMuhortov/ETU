package com.example.market.consoleview.model;

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

    public Product(String name, String description, int amount) {
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public Product() {
    }

}
