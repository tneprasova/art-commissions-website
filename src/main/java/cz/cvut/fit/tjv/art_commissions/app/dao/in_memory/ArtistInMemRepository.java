package cz.cvut.fit.tjv.art_commissions.app.dao.in_memory;

import cz.cvut.fit.tjv.art_commissions.app.dao.ArtistRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class ArtistInMemRepository extends InMemoryRepository<Artist, Long> implements ArtistRepository {
    @Override
    public Collection<Commission> findByEstimatedEndDateGreaterThan(LocalDate date) {
        return null;  // TODO - return all artists with the number of their ongoing commissions
    }
}
