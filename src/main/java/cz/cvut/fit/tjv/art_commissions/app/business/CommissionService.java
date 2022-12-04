package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.jpa.CommissionJpaRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommissionService extends AbstractCrudService<Commission, Long> {
    public CommissionService(CommissionJpaRepository repository) {
        super(repository);
    }

    public Collection<Commission> findByCustomerId(long id) {
        return ((CommissionJpaRepository) repository).findCommissionsByCreatorId(id);
    }

    public Collection<Commission> findMyActiveCommissions(long id) {
        var active = ((CommissionJpaRepository) repository).findActive(LocalDate.now());
        var mine = findByCustomerId(id);

        return mine.stream().filter(active::contains).collect(Collectors.toList());
    }
}
