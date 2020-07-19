package com.radha.railwayrest.dao;

import java.util.List;

public interface Dao<T> {
    int save(T entity);
    T get(int key);
    List<T> getAll();
}
