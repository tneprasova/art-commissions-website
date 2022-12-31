package cz.cvut.fit.tjv.art_commissions.client.api_client;

import cz.cvut.fit.tjv.art_commissions.client.dto.CommissionDto;
import cz.cvut.fit.tjv.art_commissions.client.dto.CommissionPostDto;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Component
public class CommissionClient {
    private final WebTarget allCommissionsEndpoint;
    private final WebTarget singleEndpointTemplate;
    private WebTarget singleCommissionEndpoint;

    public CommissionClient(@Value("${server.url}") String apiUrl) {
        var client = ClientBuilder.newClient();
        allCommissionsEndpoint = client.target(apiUrl + "/v1/commissions");
        singleEndpointTemplate = allCommissionsEndpoint.path("/{id}");
    }

    public CommissionDto create(CommissionPostDto commission) {
        var response = allCommissionsEndpoint.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(commission, MediaType.APPLICATION_JSON_TYPE));

        if (response.getStatus() == 400) {
            throw new ApiException(response.readEntity(ApiErrorResponse.class));
        }
        else if (response.getStatus() == 404) {
            throw new ApiException(response.readEntity(ApiErrorResponse.class));
        }

        return response.readEntity(CommissionDto.class);
    }

    public Collection<CommissionDto> readAll() {
        return Arrays.stream(allCommissionsEndpoint.request(MediaType.APPLICATION_JSON_TYPE)
                .get(CommissionDto[].class)).toList();
    }

    public void setCurrentEntity(long id) {
        singleCommissionEndpoint = singleEndpointTemplate.resolveTemplate("id", id);
    }

    public Optional<CommissionDto> readOne() {
        var response = singleCommissionEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();

        if (response.getStatus() == 404)
            throw new ApiException(response.readEntity(ApiErrorResponse.class));

        return Optional.of(response.readEntity(CommissionDto.class));
    }

    public void updateOne(CommissionDto commission) {
        CommissionPostDto dto = new CommissionPostDto(commission.getArtType(), commission.getDescription(),
                commission.getEstimatedHours(), commission.getIssuingDate(), commission.getCreator(), commission.getCommissioners());

        var response = singleCommissionEndpoint.request()
                .put(Entity.entity(dto, MediaType.APPLICATION_JSON_TYPE));

        if (response.getStatus() == 404 || response.getStatus() == 400)
            throw new ApiException(response.readEntity(ApiErrorResponse.class));
    }

    public void deleteOne() {
        singleCommissionEndpoint.request(MediaType.APPLICATION_JSON_TYPE).delete();
    }

    public Collection<CommissionDto> getMyCommissions(long customerId, Optional<String> filterBy) {
        var target = allCommissionsEndpoint
                .path("/customers")
                .path("/" + customerId);

        if (filterBy.isPresent())
            target = target.queryParam("filter_by", filterBy.get());

        return Arrays.stream(target.request(MediaType.APPLICATION_JSON_TYPE)
                .get(CommissionDto[].class)).toList();
    }

    public void addArtist(long artistId) {
        var target = singleCommissionEndpoint
                .path("/artists/" + artistId);

        target.request(MediaType.APPLICATION_JSON_TYPE).put(null);
    }
}
