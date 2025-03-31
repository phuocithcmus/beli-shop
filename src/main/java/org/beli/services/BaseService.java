package org.beli.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService<E, T> {

    private final JpaRepository<E, T> repository;

    public BaseService(JpaRepository<E, T> repository) {
        this.repository = repository;
    }

    public E save(E entity) {
        return repository.save(entity);
    }

    public E findById(T id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(T id) {
        repository.deleteById(id);
    }

    public List<E> findAll() {
        return repository.findAll();
    }
}
