package cz.cvut.fit.tjv.art_commissions.app.dao.in_memory;



import cz.cvut.fit.tjv.art_commissions.app.dao.CrudRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.DomainEntity;
import java.util.*;

public class InMemoryRepository<T extends DomainEntity<ID>, ID> implements CrudRepository<T, ID> {
    private Map<ID, T> database = new HashMap<>();
    @Override
    public T save(T e) {
        database.put(e.getId(), e);
        return e;
    }

    @Override
    public boolean existsById(ID id) {
        return database.containsKey(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Collection<T> findAll() {
        return database.values();
    }

    @Override
    public void deleteById(ID id) {
        database.remove(id);
    }
}
