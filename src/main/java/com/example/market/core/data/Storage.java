package com.example.market.core.data;

import com.example.market.core.model.Model;

public interface Storage<M extends Model<M>> {

    void delete(long index);

    void save(M model);

    M find(long index);

}
