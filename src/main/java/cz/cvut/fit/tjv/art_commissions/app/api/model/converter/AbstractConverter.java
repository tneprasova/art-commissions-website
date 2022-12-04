package cz.cvut.fit.tjv.art_commissions.app.api.model.converter;

import cz.cvut.fit.tjv.art_commissions.app.domain.DomainEntity;

public abstract class AbstractConverter<Entity extends DomainEntity<ID>, DTO, ID> {
    public abstract DTO toDto(Entity entity);

    public abstract  Entity toEntity(DTO dto);
}
