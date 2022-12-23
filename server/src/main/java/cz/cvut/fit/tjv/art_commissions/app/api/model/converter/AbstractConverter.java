package cz.cvut.fit.tjv.art_commissions.app.api.model.converter;

import cz.cvut.fit.tjv.art_commissions.app.domain.DomainEntity;

public abstract class AbstractConverter<Entity extends DomainEntity<ID>, DTO, POSTDTO, ID> {
    public abstract DTO fromEntityToDto(Entity entity);

    public abstract  Entity fromPostDtoToEntity(POSTDTO dto);
}
