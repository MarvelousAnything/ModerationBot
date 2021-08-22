package com.yoursole1.repository;

import org.slf4j.Logger;

import java.io.Serializable;
import java.util.List;

public interface Repository<T, ID extends Serializable> {
    void save(T object);
    void save(List<T> objects);
    void delete(T object);
    void delete(ID id);
    void deleteAll();
    T get(ID id);
    List<T> getAll();
    Logger getLogger();
}
