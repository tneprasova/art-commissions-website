package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.ArtistRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class ArtistService extends AbstractCrudService<Artist, Long> {
    protected ArtistService(ArtistRepository repository) {
        super(repository);
    }

    public Collection<Artist> orderByActiveCommissionsToDate(LocalDate date) {
        return ((ArtistRepository) repository).readAllByActiveCommissionsToDate(date);
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
}
