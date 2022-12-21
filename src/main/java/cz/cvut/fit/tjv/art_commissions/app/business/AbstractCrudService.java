package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.CrudRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.DomainEntity;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityAlreadyExistsException;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityDoesNotExistException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


public abstract class AbstractCrudService<Entity extends DomainEntity<ID>, ID> {
    protected final CrudRepository<Entity, ID> repository;

    protected AbstractCrudService(CrudRepository<Entity, ID> repository) {
        this.repository = repository;
    }

    @Transactional
    public Entity create(Entity entity) throws EntityDoesNotExistException {
        if (repository.existsById(entity.getId()))
            throw new EntityAlreadyExistsException("Cannot create an entity which already exists");

        return repository.save(entity);
    }

    public Optional<Entity> readById(ID id) {
        if (id == null)
            return Optional.empty();

        return repository.findById(id);
    }

    public Collection<Entity> readById(Collection<ID> ids) {
        return repository.findAll().stream().filter(e -> ids.contains(e.getId())).collect(Collectors.toList());
    }

    public Collection<Entity> readAll() {
        return repository.findAll();
    }

    @Transactional
    public void update(ID id, Entity entity) throws EntityDoesNotExistException {
        if (!repository.existsById(id))
            throw new EntityDoesNotExistException("Cannot update a nonexistent entity");

        repository.save(entity);
    }

    @Transactional
    public void deleteById(ID id) {
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new EntityDoesNotExistException("Cannot delete a nonexistent entity");
    }
}
