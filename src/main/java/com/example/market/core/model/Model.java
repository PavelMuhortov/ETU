package com.example.market.core.model;

import java.util.List;

/**
 * Интерфейс модели данных приложения
 * @param <M> конкретный тип данных
 */
public interface Model<M> {

    /**
     * @return Уникальный идентификатор модели
     */
    long getId();

    /**
     * @param id Уникальный идентификатор модели
     */
    void setId(long id);

    /**
     * @return Список определений полей модели. {@link PropDef}
     */
    List<PropDef> getPropDefs();

    /**
     * Возвращает строковое представление поля модели по имени поля.
     * @param propertyName имя поля модели {@link PropDef#propertyName}
     * @return значение поля.
     */
    String getPropertyValue(String propertyName);

    /**
     * Устанавливает новое значение поля модели из строкового представления.
     * @param propertyName имя поля модели {@link PropDef#propertyName}
     * @param value новое значение.
     */
    void setPropertyValue(String propertyName, String value);

}
