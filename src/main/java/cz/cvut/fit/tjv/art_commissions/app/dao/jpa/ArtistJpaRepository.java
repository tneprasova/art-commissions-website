package cz.cvut.fit.tjv.art_commissions.app.dao.jpa;

import cz.cvut.fit.tjv.art_commissions.app.dao.ArtistRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
@Primary
public interface ArtistJpaRepository extends ArtistRepository, JpaRepository<Artist, Long> {
    @Override
    @Query(value =
            "SELECT a FROM Artist a " +
            "LEFT JOIN a.commissions c " +
            "GROUP BY a.id " +
            "ORDER BY SUM(CASE WHEN c.estimatedEndDate >= :date AND c.issuingDate <= :date THEN 1 ELSE 0 END ) ASC, a.id ASC ")
    Collection<Artist> readAllByActiveCommissionsToDate(@Param("date") LocalDate date);

    @Override
    @Query (value = "SELECT a FROM Artist a ORDER BY a.pricePerHour ASC")
    Collection<Artist> readAllOrderByPricePerHour();

    @Override
    Collection<Artist> readAllByArtType(ArtType artType);

    @Override
    Collection<Artist> readAllByName(String name);
}
