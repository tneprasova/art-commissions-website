package cz.cvut.fit.tjv.art_commissions.client.service;

import cz.cvut.fit.tjv.art_commissions.client.api_client.CustomerClient;
import cz.cvut.fit.tjv.art_commissions.client.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerClient customerClient;

    public CustomerService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public Collection<CustomerDto> readAll() {
        return customerClient.readAll();
    }

    public Optional<CustomerDto> readOneById(long id) {
        return customerClient.readOneById(id);
    }
}
