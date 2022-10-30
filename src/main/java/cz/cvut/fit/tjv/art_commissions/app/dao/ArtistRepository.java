package cz.cvut.fit.tjv.art_commissions.app.dao;

import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;

import java.time.LocalDate;
import java.util.Collection;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    Collection<Artist> readAllByActiveCommissionsToDate(LocalDate date);

    Collection<Artist> readAllOrderByPricePerHour();

    Collection<Artist> readAllByArtType(ArtType artType);

    Collection<Artist> readAllByName(String name);
}
