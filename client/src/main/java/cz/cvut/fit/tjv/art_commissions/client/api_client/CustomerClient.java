package cz.cvut.fit.tjv.art_commissions.client.api_client;

import cz.cvut.fit.tjv.art_commissions.client.dto.CustomerDto;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Component
public class CustomerClient {
    private final WebTarget allCustomersEndpoint;

    public CustomerClient(@Value("${server.url}") String apiUrl) {
        var client = ClientBuilder.newClient();
        allCustomersEndpoint = client.target(apiUrl + "/v1/customers");
    }

    public Collection<CustomerDto> readAll() {
        return Arrays.stream(allCustomersEndpoint.request(MediaType.APPLICATION_JSON_TYPE)
                .get(CustomerDto[].class)).toList();
    }

    public Optional<CustomerDto> readOneById (long id) {
        var response = allCustomersEndpoint.path("/" + id).request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == 200)
            return Optional.of(response.readEntity(CustomerDto.class));
        else if (response.getStatus() == 404)
            return Optional.empty();
        else
            throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }
}
