package com.tinder.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<A> {
    List<A> getAll();

    List<A> getBy(String SQL);

    Optional<A> get(int id);

    void insert(A a);

    void update(A a);
}
