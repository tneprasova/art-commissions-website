package cz.cvut.fit.tjv.art_commissions.app.api.model.converter;

import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.ArtistDto;
import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.ArtistPostDto;
import cz.cvut.fit.tjv.art_commissions.app.business.ArtistService;
import cz.cvut.fit.tjv.art_commissions.app.business.CommissionService;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.ArtistException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ArtistConverter extends AbstractConverter<Artist, ArtistDto, ArtistPostDto, Long> {
    private final ArtistService artistService;
    private final CommissionService commissionService;

    public ArtistConverter(ArtistService artistService, CommissionService commissionService) {
        this.artistService = artistService;
        this.commissionService = commissionService;
    }

    @Override
    public ArtistDto fromEntityToDto(Artist artist) {
        Collection<Long> apprenticesIds = new ArrayList<>();
        artist.getApprentices().forEach(c -> apprenticesIds.add(c.getId()));

        Collection<Long> commissionsIds = new ArrayList<>();
        artist.getCommissions().forEach(c -> commissionsIds.add(c.getId()));

        return new ArtistDto(artist.getId(), artist.getName(), artist.getPricePerHour(), artist.getArtType(),
                commissionsIds, (artist.getTeacher() != null ? artist.getTeacher().getId() : null), apprenticesIds);
    }

    @Override
    public Artist fromPostDtoToEntity(ArtistPostDto dto) {
        if (artistService.readById(dto.getTeacher()).isEmpty() && dto.getTeacher() != null)
            throw new ArtistException("An artist cannot have a teacher with a nonexistent ID");

        return new Artist(dto.getName(), dto.getPricePerHour(), dto.getArtType(),
                artistService.readById(dto.getTeacher()).orElse(null),
                artistService.readById(dto.getApprentices()), commissionService.readById(dto.getCommissions()));
    }
}
