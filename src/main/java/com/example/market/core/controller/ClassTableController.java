package com.example.market.core.controller;

import com.example.market.core.model.Model;

import java.lang.reflect.InvocationTargetException;

public class ClassTableController<M extends Model<M>> extends BaseTableController<M> {

    private final Class<M> modelClass;

    public ClassTableController(Class<M> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public M newOne() {
        try {
            return modelClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("Can't instantiate " + modelClass, e);
        }
    }
}
