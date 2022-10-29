package cz.cvut.fit.tjv.art_commissions.app.dao;

import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;

import java.time.LocalDate;
import java.util.Collection;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    Collection<Commission> findByEstimatedEndDateGreaterThan(LocalDate date);
}
