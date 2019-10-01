package com.example.market.core.data;

import com.example.market.core.model.Model;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Предоставляет CRUD методы к данным
 *
 * @param <M>
 */
public interface Repository<M extends Model<M>> {

    void delete(long index);

    /**
     * Сохраняет модель. Метод должен устанавливать модели уникальный идентификатор. {@link Model#setId(long)}
     *
     * @param model
     */
    void save(M model);

    default M find(long index) {
        return getAll().stream()
                .filter(model -> model.getId() == index)
                .findFirst()
                .orElse(null);
    }

    /**
     * Прочитать все данные
     *
     * @return
     */
    Collection<M> getAll();

    /**
     * Прочитать все данные со значением
     *
     * @param propertyName  имя поля для поиска
     * @param propertyValue значение поля для поиска
     * @return
     */
    default Collection<M> findBy(String propertyName, String propertyValue) {
        return getAll().stream()
                .filter(model -> model.getPropertyValue(propertyName).equals(propertyValue))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Прочитать все данные, отсортировать
     *
     * @param sortBy имя поля для сортировки
     * @return
     */
    default Collection<M> sortBy(String sortBy) {
        return getAll().stream()
                .sorted(Comparator.comparing(m -> m.getPropertyValue(sortBy)))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Прочитать все данные со значением, отсортировать
     *
     * @param propertyName  имя поля для поиска
     * @param propertyValue значение поля для поиска
     * @param sortBy        имя поля для сортировки
     * @return
     */
    default Collection<M> findBySortedBy(String propertyName, String propertyValue, String sortBy) {
        return getAll().stream()
                .filter(model -> model.getPropertyValue(propertyName).equals(propertyValue))
                .sorted(Comparator.comparing(m -> m.getPropertyValue(sortBy)))
                .collect(Collectors.toUnmodifiableList());
    }
}
