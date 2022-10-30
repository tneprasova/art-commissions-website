package cz.cvut.fit.tjv.art_commissions.app.dao.jpa;

import cz.cvut.fit.tjv.art_commissions.app.dao.CustomerRepository;
import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface CustomerJpaRepository extends CustomerRepository, JpaRepository<Customer, Long> {
}
