package cz.cvut.fit.tjv.art_commissions.app.dao;

import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;

import java.time.LocalDate;
import java.util.Collection;

public interface CommissionRepository extends CrudRepository<Commission, Long> {

    Collection<Commission> findCommissionsByCreatorId(long id);

    Collection<Commission> findActive(LocalDate date);
}
