package cz.cvut.fit.tjv.art_commissions.app.dao;

import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long>, JpaRepository<Artist, Long> {
    @Query(value =
            "SELECT a.id FROM Artist a LEFT JOIN a.commissions c GROUP BY a.id ORDER BY SUM(CASE WHEN c.estimatedEndDate >= :date AND c.issuingDate <= :date THEN 1 ELSE 0 END ) ASC, a.id ASC")
    Collection<Long> readAllByActiveCommissionsToDateAsc(@Param("date") LocalDate date);

    @Query (value = "SELECT a FROM Artist a ORDER BY a.pricePerHour ASC")
    Collection<Artist> readAllOrderByPricePerHour();

    Collection<Artist> readAllByArtType(ArtType artType);

    Collection<Artist> readAllByName(String name);
}
