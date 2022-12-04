package cz.cvut.fit.tjv.art_commissions.app.dao.jpa;

import cz.cvut.fit.tjv.art_commissions.app.dao.CommissionRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
@Primary
public interface CommissionJpaRepository extends CommissionRepository, JpaRepository<Commission, Long> {

    @Override
    Collection<Commission> findCommissionsByCreatorId(long id);

    @Override
    @Query(value = "SELECT c FROM Commission c WHERE c.issuingDate <= :date AND c.estimatedEndDate >= :date")
    Collection<Commission> findActive(@Param("date") LocalDate date);
}
