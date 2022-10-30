package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.CommissionRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommissionService extends AbstractCrudService<Commission, Long> {
    public CommissionService(CommissionRepository repository) {
        super(repository);
    }

    public Collection<Commission> findByCustomerId(long id) {
        return ((CommissionRepository) repository).findCommissionsByCreatorId(id);
    }
}
