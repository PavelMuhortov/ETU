package com.example.market.core.view;

import com.example.market.core.model.Model;

public interface EditView<M extends Model<M>> {

    M edit(M model);
}
