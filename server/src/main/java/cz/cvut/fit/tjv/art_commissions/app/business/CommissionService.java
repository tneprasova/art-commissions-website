package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.ArtistRepository;
import cz.cvut.fit.tjv.art_commissions.app.dao.CommissionRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityDoesNotExistException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommissionService extends AbstractCrudService<Commission, Long> {
    private ArtistRepository artistRepository;
    private EntityManager entityManager;
    public CommissionService(CommissionRepository repository, ArtistRepository artistRepository, EntityManager entityManager) {
        super(repository);
        this.artistRepository = artistRepository;
        this.entityManager = entityManager;
    }

    public Collection<Commission> findByCustomerId(long id) {
        return ((CommissionRepository) repository).findCommissionsByCreatorId(id);
    }

    public Collection<Commission> findMyActiveCommissions(long id) {
        var active = ((CommissionRepository) repository).findActive(LocalDate.now());
        var mine = findByCustomerId(id);

        return mine.stream().filter(active::contains).collect(Collectors.toList());
    }

    public void addCommissioner(long idCommission, long idArtist) {
        var optionalCommission = readById(idCommission);
        var optionalArtist = artistRepository.findById(idArtist);

        Commission commission = optionalCommission.orElseThrow(() ->
                new EntityDoesNotExistException("Attempt to add a commissioner to a nonexistent commission"));
        Artist artist = optionalArtist.orElseThrow(() ->
                new EntityDoesNotExistException("Attempt to add a nonexistent commissioner to a commission"));

        commission.addCommissioner(artist);
    }

    public void removeCommissioner(long idCommission, long idArtist) {
        var optionalCommission = readById(idCommission);
        var optionalArtist = artistRepository.findById(idArtist);

        Commission commission = optionalCommission.orElseThrow(() ->
                new EntityDoesNotExistException("Attempt to remove a commissioner from a nonexistent commission"));
        Artist artist = optionalArtist.orElseThrow(() ->
                new EntityDoesNotExistException("Attempt to remove a nonexistent commissioner from a commission"));
        if (!commission.getCommissioners().contains(artist))
            throw new EntityDoesNotExistException("Attempt to remove a commissioner from a commission who is not assigned to it");

        commission.removeCommissioner(artist);

        entityManager.flush();
        entityManager.clear();
        if (commission.getCommissioners().isEmpty())
            deleteById(idCommission);
    }
}
