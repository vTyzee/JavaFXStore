package com.example.GrocceryShop.service;

import java.util.List;
import java.util.Optional;

public interface AppService<T> {
    T create(T entity);
    Optional<T> getById(Long id);
    List<T> getAll();
    void delete(Long id);
}
