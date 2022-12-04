package cz.cvut.fit.tjv.art_commissions.app.dao.in_memory;

import cz.cvut.fit.tjv.art_commissions.app.dao.CustomerRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import org.springframework.stereotype.Component;

//@Component
public class CustomerInMemRepository extends InMemoryRepository<Customer, Long> implements CustomerRepository {
}
