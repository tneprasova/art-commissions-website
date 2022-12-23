package cz.cvut.fit.tjv.art_commissions.app.dao;

import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>, JpaRepository<Customer, Long> {
}
