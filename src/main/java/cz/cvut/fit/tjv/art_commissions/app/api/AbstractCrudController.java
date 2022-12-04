package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.api.model.converter.AbstractConverter;
import cz.cvut.fit.tjv.art_commissions.app.business.AbstractCrudService;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.art_commissions.app.domain.DomainEntity;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractCrudController<Entity extends DomainEntity<ID>, DTO, ID> {
    protected AbstractCrudService<Entity, ID> service;
    protected AbstractConverter<Entity, DTO, ID> converter;

    public AbstractCrudController(AbstractCrudService<Entity, ID> service, AbstractConverter<Entity, DTO, ID> converter) {
        this.service = service;
        this.converter = converter;
    }

    @PostMapping
    public DTO create(@RequestBody DTO dto) {
        return converter.toDto(service.create(converter.toEntity(dto)));
    }

    /*@GetMapping
    public Collection<DTO> readAll() {
        return service.readAll().stream().map(entity -> converter.toDto(entity)).toList();
    }*/

    @GetMapping("/{id}")
    public DTO readById(@PathVariable ID id) {
        return service.readById(id).map(entity -> converter.toDto(entity)).orElseThrow(EntityDoesNotExistException::new);
    }

    // TODO This implementation is most probably wrong - it does not use the id at all, needs to be tested
    @PutMapping("/{id}")
    public void update(@RequestBody DTO dto, @PathVariable ID id) {
        service.update(converter.toEntity(dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        service.deleteById(id);
    }
}
