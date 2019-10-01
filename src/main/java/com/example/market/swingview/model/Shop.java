package com.example.market.swingview.model;

import com.example.market.core.model.BaseModel;
import com.example.market.core.model.Named;

@Named("Магазин")
public class Shop extends BaseModel<Shop> {

    @Named("Название")
    private String name;

    @Named("Специализация")
    private String specialization;

    @Named("Директор")
    private String ceo;

    @Named("Адрес")
    private String address;
}
