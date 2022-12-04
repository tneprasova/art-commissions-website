package cz.cvut.fit.tjv.art_commissions.app.api.model.converter;

import cz.cvut.fit.tjv.art_commissions.app.api.model.ArtistDto;
import cz.cvut.fit.tjv.art_commissions.app.business.ArtistService;
import cz.cvut.fit.tjv.art_commissions.app.business.CommissionService;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ArtistConverter extends AbstractConverter<Artist, ArtistDto, Long> {
    private final ArtistService artistService;
    private final CommissionService commissionService;

    public ArtistConverter(ArtistService artistService, CommissionService commissionService) {
        this.artistService = artistService;
        this.commissionService = commissionService;
    }

    @Override
    public ArtistDto toDto(Artist artist) {
        Collection<Long> coworkersIds = new ArrayList<>();
        artist.getCoworkers().forEach(c -> coworkersIds.add(c.getId()));

        Collection<Long> commissionsIds = new ArrayList<>();
        artist.getCommissions().forEach(c -> commissionsIds.add(c.getId()));

        return new ArtistDto(artist.getId(), artist.getName(), artist.getPricePerHour(), artist.getArtType(), commissionsIds, coworkersIds);
    }

    @Override
    public Artist toEntity(ArtistDto dto) {
        return new Artist(dto.getId(), dto.getName(), dto.getPricePerHour(), dto.getArtType(),
                artistService.readById(dto.getCoworkers()), commissionService.readById(dto.getCommissions()));
    }
}
