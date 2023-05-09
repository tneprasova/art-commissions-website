package cz.cvut.fit.tjv.art_commissions.client.service;

import cz.cvut.fit.tjv.art_commissions.client.api_client.ArtistClient;
import cz.cvut.fit.tjv.art_commissions.client.dto.ArtistDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ArtistService {
    private final ArtistClient artistClient;

    public ArtistService(ArtistClient artistClient) {
        this.artistClient = artistClient;
    }

    public Collection<ArtistDto> readAll(Optional<String> name, Optional<String> artType, Optional<String> orderBy) {
        return artistClient.readAll(name, artType, orderBy);
    }

    public Optional<ArtistDto> readOneById(long id) {
        return artistClient.readOneById(id);
    }
}
