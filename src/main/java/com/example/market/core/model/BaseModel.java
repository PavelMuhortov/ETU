package com.example.market.core.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseModel<M extends Model>
        implements Model<M> {

    private long id;

    private List<PropDef> propDefs;

    public BaseModel() {
        final Field[] fields = getClass().getDeclaredFields();
        propDefs = Arrays.stream(fields)
                .map(field -> new PropDef(field.getName(), getDisplayedName(field)))
                .collect(Collectors.toList());
    }

    private String getDisplayedName(Field field) {
        final Named annotation = field.getAnnotation(Named.class);
        if (annotation == null) {
            return field.getName();
        } else {
            return annotation.value();
        }
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public List<PropDef> getPropDefs() {
        return propDefs;
    }

    @Override
    public String getPropertyValue(String propertyName) {
        Field field = null;
        try {
            field = getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            return String.valueOf(field.get(this));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Can't read property: " + propertyName);
        } finally {
            if (null != field) {
                field.setAccessible(false);
            }
        }
    }

    @Override
    public void setPropertyValue(String propertyName, String value) {
        Field field = null;
        try {
            field = getClass().getDeclaredField(propertyName);
            final Class<?> type = field.getType();
            field.setAccessible(true);
            if (type == int.class) {
                field.setInt(this, Integer.parseInt(value));
            } else if (type == long.class) {
                field.setLong(this, Long.parseLong(value));
            } else if (type == boolean.class) {
                field.setBoolean(this, Boolean.parseBoolean(value));
            } else if (type == double.class) {
                field.setDouble(this, Double.parseDouble(value));
            } else if (type == byte.class) {
                field.setByte(this, Byte.parseByte(value));
            } else if (type == char.class) {
                field.setChar(this, value.charAt(0));
            } else if (type == float.class) {
                field.setFloat(this, Float.parseFloat(value));
            } else if (type == short.class) {
                field.setShort(this, Short.parseShort(value));
            } else if (type == String.class) {
                field.set(this, value);
            } else {
                throw new IllegalArgumentException("Only primitives or String types supported");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Can't set property: " + propertyName + ", value: " + value);
        } finally {
            if (null != field) {
                field.setAccessible(false);
            }
        }
    }

    @Override
    public String toString() {
        return "Id: " + id + ", " +
                propDefs.stream()
                        .map(PropDef::getPropertyName)
                        .map(propName -> propName + ": " + getPropertyValue(propName))
                        .collect(Collectors.joining(", "));
    }
}
