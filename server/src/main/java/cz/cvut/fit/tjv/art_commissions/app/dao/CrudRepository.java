package cz.cvut.fit.tjv.art_commissions.app.dao;

import java.util.Collection;
import java.util.Optional;

public interface CrudRepository<T, ID>  {
    T save(T entity);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    Collection<T> findAll();

    void deleteById(ID id);

}