package com.example.market.core.data;

import com.example.market.core.model.Model;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

//TODO: реализовать сохранение модели в файл и чтение моделей из файла
public class FileRepository<M extends Model<M>> implements Repository<M> {

    private final File file;
    private final Supplier<M> modelFactory;

    public FileRepository(File file, Supplier<M> modelFactory) {
        this.file = file;
        this.modelFactory = modelFactory;
    }


    @Override
    public void delete(long index) {
        throw new UnsupportedOperationException("Delete when ready"); // todo
    }

    @Override
    public void save(M model) {
        List<String> propertyNames = model.getPropertyNames();
        for (int i = 0; i < propertyNames.size(); i++) {
            String propertyName = propertyNames.get(i);
            String propertyValue = model.getPropertyValue(propertyName);

        }
        throw new UnsupportedOperationException("Delete when ready"); // todo
    }

    @Override
    public Collection<M> getAll() {
//        TODO
/*
        M newModel = modelFactory.get();
        newModel.setId(id);
        for (int i = 0; i < newModel.getPropertyNames().size(); i++) {
            newModel.setPropertyValue(propertyName, value);
        }
*/
        throw new UnsupportedOperationException("Delete when ready"); // todo
    }
}
