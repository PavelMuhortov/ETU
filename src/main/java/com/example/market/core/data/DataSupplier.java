package com.example.market.core.data;

import com.example.market.core.model.Model;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public interface DataSupplier<M extends Model<M>> {

    Collection<M> getAll();

    default Collection<M> findBy(String propertyName, String propertyValue) {
        return getAll().stream()
                .filter(model -> model.getPropertyValue(propertyName).equals(propertyValue))
                .collect(Collectors.toUnmodifiableList());
    }

    default Collection<M> sortBy(String sortBy) {
        return getAll().stream()
                .sorted(Comparator.comparing(m -> m.getPropertyValue(sortBy)))
                .collect(Collectors.toUnmodifiableList());
    }

    default Collection<M> findBySortedBy(String propertyName, String propertyValue, String sortBy) {
        return getAll().stream()
                .filter(model -> model.getPropertyValue(propertyName).equals(propertyValue))
                .sorted(Comparator.comparing(m -> m.getPropertyValue(sortBy)))
                .collect(Collectors.toUnmodifiableList());
    }
}
