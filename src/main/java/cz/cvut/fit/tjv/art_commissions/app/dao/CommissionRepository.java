package cz.cvut.fit.tjv.art_commissions.app.dao;

import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;

import java.util.Collection;

public interface CommissionRepository extends CrudRepository<Commission, Long> {

    Collection<Commission> findCommissionsByCreatorId(long id);
}
