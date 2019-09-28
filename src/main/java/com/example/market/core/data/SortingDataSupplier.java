package com.example.market.core.data;

import com.example.market.core.model.Model;

import java.util.Collection;

public class SortingDataSupplier<M extends Model<M>> implements DataSupplier<M> {

    private final DataSupplier<M> origin;
    private String sortBy = null;
    private String propertyName = null;
    private String propertyValue = null;

    public SortingDataSupplier(DataSupplier<M> origin) {
        this.origin = origin;
    }

    public void sortedBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void filteredBy(String propertyName, String propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    @Override
    public Collection<M> getAll() {
        if (sortBy != null)
            if (propertyName != null && propertyValue != null)
                return findBySortedBy(propertyName, propertyValue, sortBy);
            else
                return sortBy(sortBy);
        else if (propertyName != null && propertyValue != null)
            return findBy(propertyName, propertyValue);
        else
            return origin.getAll();
    }

    @Override
    public Collection<M> findBy(String propertyName, String propertyValue) {
        return origin.getAll();
    }

    @Override
    public Collection<M> sortBy(String sortBy) {
        return origin.getAll();
    }

    @Override
    public Collection<M> findBySortedBy(String propertyName, String propertyValue, String sortBy) {
        return origin.getAll();
    }
}
