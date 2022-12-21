package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.api.model.converter.AbstractConverter;
import cz.cvut.fit.tjv.art_commissions.app.business.AbstractCrudService;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityAlreadyExistsException;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.art_commissions.app.domain.DomainEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractCrudController<Entity extends DomainEntity<ID>, DTO, POSTDTO, ID> {
    protected AbstractCrudService<Entity, ID> service;
    protected AbstractConverter<Entity, DTO, POSTDTO, ID> converter;

    public AbstractCrudController(AbstractCrudService<Entity, ID> service, AbstractConverter<Entity, DTO, POSTDTO, ID> converter) {
        this.service = service;
        this.converter = converter;
    }

    @PostMapping
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "409", description = "Attempt to create an entity with existing id")}
    )
    public DTO create(@RequestBody POSTDTO dto) {
        try {
            return converter.fromEntityToDto(service.create(converter.fromPostDtoToEntity(dto)));
        } catch (EntityAlreadyExistsException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public DTO readById(@PathVariable ID id) {
        return service.readById(id).map(entity -> converter.fromEntityToDto(entity)).orElseThrow(EntityDoesNotExistException::new);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success with no content provided"),
            @ApiResponse(responseCode = "404", description = "Attempt to update a nonexistent entity")}
    )
    public void update(@RequestBody POSTDTO dto, @PathVariable ID id) {
        try {
            service.update(id, converter.fromPostDtoToEntity(dto));
        } catch (EntityDoesNotExistException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success with no content provided"),
            @ApiResponse(responseCode = "404", description = "Attempt to delete a nonexistent entity")}
    )
    public void delete(@PathVariable ID id) {
        try {
            service.deleteById(id);
        } catch (EntityDoesNotExistException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
