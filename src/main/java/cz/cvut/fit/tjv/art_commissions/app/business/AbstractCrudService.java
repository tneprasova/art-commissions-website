package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.CrudRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.DomainEntity;

import javax.transaction.Transactional;
import java.util.Optional;


public abstract class AbstractCrudService<Entity extends DomainEntity<ID>, ID> {
    protected final CrudRepository<Entity, ID> repository;

    protected AbstractCrudService(CrudRepository<Entity, ID> repository) {
        this.repository = repository;
    }

    // CRUD -----------------------------------------------------------------------------------------------------------
    @Transactional
    public Entity create(Entity entity) throws EntityStateException {
        if (repository.existsById(entity.getId()))
            throw new EntityStateException(entity);
        return repository.save(entity); //delegate call to data layer
    }

    public Optional<Entity> readById(ID id) {
        return repository.findById(id);
    }

    public Iterable<Entity> readAll() {
        return repository.findAll();
    }

    @Transactional
    public Entity update(Entity entity) throws EntityStateException {
        if (repository.existsById(entity.getId()))
            return repository.save(entity);
        else
            throw new EntityStateException(entity);
    }

    @Transactional
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
