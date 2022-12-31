package cz.cvut.fit.tjv.art_commissions.client.api_client;

import cz.cvut.fit.tjv.art_commissions.client.dto.ArtistDto;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Component
public class ArtistClient {
    private final WebTarget allArtistsEndpoint;

    public ArtistClient(@Value("${server.url}") String apiUrl) {
        var client = ClientBuilder.newClient();
        allArtistsEndpoint = client.target(apiUrl + "/v1/artists");
    }

    public Collection<ArtistDto> readAll(Optional<String> name, Optional<String > artType, Optional<String> orderBy) {
        var target = allArtistsEndpoint;
        if (name.isPresent())
            target = target.queryParam("name", name.get());
        if (artType.isPresent())
            target = target.queryParam("art_type", artType.get());
        if (orderBy.isPresent())
            target = target.queryParam("order_by", orderBy.get());

        return Arrays.stream(target.request(MediaType.APPLICATION_JSON_TYPE)
                .get(ArtistDto[].class)).toList();
    }

    public Optional<ArtistDto> readOneById (long id) {
        var response = allArtistsEndpoint.path("/" + id).request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == 200)
            return Optional.of(response.readEntity(ArtistDto.class));
        else if (response.getStatus() == 404)
            return Optional.empty();
        else
            throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }
}
