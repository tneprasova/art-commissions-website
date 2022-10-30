package cz.cvut.fit.tjv.art_commissions.app.dao.in_memory;

import cz.cvut.fit.tjv.art_commissions.app.dao.CommissionRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CommissionInMemRepository extends InMemoryRepository<Commission, Long> implements CommissionRepository {
    @Override
    public Collection<Commission> findCommissionsByCreatorId(long id) {
        return null;
    }
}
