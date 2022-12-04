package cz.cvut.fit.tjv.art_commissions.app.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Customer implements DomainEntity<Long> {

    // Attributes -----------------------------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private long id;
    private String name;

    // Relations ------------------------------------------------------------------------------------------------------
    @OneToMany(mappedBy = "creator")
    Collection<Commission> myCommissions;

    // Constructors ---------------------------------------------------------------------------------------------------
    public Customer() {}

    public Customer(Long id, String name, Collection<Commission> myCommissions) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.myCommissions = myCommissions;
    }

    // Getters and setters --------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Commission> getMyCommissions() {
        return myCommissions;
    }

    public void setMyCommissions(Collection<Commission> myCommissions) {
        this.myCommissions = myCommissions;
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
