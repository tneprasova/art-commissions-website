package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.ArtistRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityDoesNotExistException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;

@Service
@Transactional
public class ArtistService extends AbstractCrudService<Artist, Long> {

    private CommissionService commissionService;
    private EntityManager entityManager;
    protected ArtistService(ArtistRepository repository, CommissionService commissionService,
                            EntityManager entityManager) {
        super(repository);
        this.commissionService = commissionService;
        this.entityManager = entityManager;
    }

    public Collection<Artist> orderByActiveCommissionsToDate(LocalDate date) {
        return ((ArtistRepository) repository).readAllByActiveCommissionsToDateAsc(date);
    }

    public Collection<Artist> orderByPrice() {
        return ((ArtistRepository) repository).readAllOrderByPricePerHour();
    }

    public Collection<Artist> filterByArtType(ArtType artType) {
        return ((ArtistRepository) repository).readAllByArtType(artType);
    }

    public Collection<Artist> filterByName(String name) {
        return ((ArtistRepository) repository).readAllByName(name);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (repository.findById(id).isEmpty())
            throw new EntityDoesNotExistException("Cannot delete a nonexistent entity");

        Collection<Long> commissionsIds = repository.findById(id).get().getCommissions()
                .stream().map(Commission::getId).toList();
        repository.deleteById(id);

        // Flush the persistence context
        entityManager.flush();
        entityManager.clear();

        for (var comm : commissionService.readById(commissionsIds)) {
            comm.updatePrice();
            if (comm.getCommissioners().isEmpty())
                commissionService.deleteById(comm.getId());
        }
    }
}
