package cz.cvut.fit.tjv.art_commissions.app.domain;

import java.io.Serializable;

public interface DomainEntity<ID> extends Serializable {
    public ID getId();

    public void setId(ID id);
}
