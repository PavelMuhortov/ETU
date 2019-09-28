package com.example.market.core.data;

import com.example.market.core.model.Model;

public interface Repository<M extends Model<M>>
        extends Storage<M>, DataSupplier<M> {
}
