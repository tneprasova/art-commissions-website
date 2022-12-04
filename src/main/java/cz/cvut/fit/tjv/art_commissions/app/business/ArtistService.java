package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.jpa.ArtistJpaRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;

@Service
@Transactional
public class ArtistService extends AbstractCrudService<Artist, Long> {
    protected ArtistService(ArtistJpaRepository repository) {
        super(repository);
    }

    public Collection<Artist> orderByActiveCommissionsToDate(LocalDate date) {
        return ((ArtistJpaRepository) repository).readAllByActiveCommissionsToDate(date);
    }

    public Collection<Artist> orderByPrice() {
        return ((ArtistJpaRepository) repository).readAllOrderByPricePerHour();
    }

    public Collection<Artist> filterByArtType(ArtType artType) {
        return ((ArtistJpaRepository) repository).readAllByArtType(artType);
    }

    public Collection<Artist> filterByName(String name) {
        return ((ArtistJpaRepository) repository).readAllByName(name);
    }
}
