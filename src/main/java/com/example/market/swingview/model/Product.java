package com.example.market.swingview.model;

import com.example.market.core.model.BaseModel;
import com.example.market.core.model.Named;

/**
 * Пример реализации модели данных
 */
@Named("Продукт")
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

    @Named("Цена")
    private int price;

}
