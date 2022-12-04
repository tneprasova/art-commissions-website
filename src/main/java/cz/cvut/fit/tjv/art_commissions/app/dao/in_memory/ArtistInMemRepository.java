package cz.cvut.fit.tjv.art_commissions.app.dao.in_memory;

import cz.cvut.fit.tjv.art_commissions.app.dao.ArtistRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

//@Component
public class ArtistInMemRepository extends InMemoryRepository<Artist, Long> implements ArtistRepository {
    @Override
    public Collection<Artist> readAllByActiveCommissionsToDate(LocalDate date) {
        return null;
    }

    @Override
    public Collection<Artist> readAllOrderByPricePerHour() {
        return findAll().stream().sorted(Comparator.comparingInt(Artist::getPricePerHour)).toList();
    }

    @Override
    public Collection<Artist> readAllByArtType(ArtType artType) {
        return findAll().stream().filter(artist -> artist.getArtType().equals(artType)).toList();
    }

    @Override
    public Collection<Artist> readAllByName(String name) {
        return findAll().stream().filter(artist -> artist.getName().equals(name)).toList();
    }
}
