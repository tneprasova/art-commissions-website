package cz.cvut.fit.tjv.art_commissions.app.business;

import cz.cvut.fit.tjv.art_commissions.app.dao.CustomerRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractCrudService<Customer, Long> {
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }
}
