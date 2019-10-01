package com.example.market.swingview.model;

import com.example.market.core.model.BaseModel;
import com.example.market.core.model.Named;

/**
 * Пример реализации модели данных
 */
@Named("Товар")
public class Product
        extends BaseModel<Product> {

    @Named("Название")
    private String name;

    @Named("Описание")
    private String description;

    @Named("Количество")
    private int amount;

    @Named("Ед. изм.")
    private String units;

    @Named("Цена")
    private double price;

}
