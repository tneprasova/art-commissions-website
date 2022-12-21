package cz.cvut.fit.tjv.art_commissions.app.domain;

import java.io.Serializable;

public interface DomainEntity<ID> extends Serializable {
    ID getId();

    void setId(ID id);
}
