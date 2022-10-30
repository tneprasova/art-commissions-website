package cz.cvut.fit.tjv.art_commissions.app.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Customer implements DomainEntity<Long> {

    // Attributes -----------------------------------------------------------------------------------------------------
    @Id
    @Column(name = "customer_id")
    private long id;
    private String name;

    // Constructors ---------------------------------------------------------------------------------------------------
    public Customer() {}

    public Customer(Long id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
    }

    // Getters and setters --------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Overrides ------------------------------------------------------------------------------------------------------
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Customer customer = (Customer) obj;

        return getId() != null ? getId().equals(customer.getId()) : customer.getId() == null;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "id=" + id +
                ", name='" + name + '\'';
    }
}
