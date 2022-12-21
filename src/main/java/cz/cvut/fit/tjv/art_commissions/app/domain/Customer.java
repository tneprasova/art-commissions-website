package cz.cvut.fit.tjv.art_commissions.app.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer implements DomainEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    @Setter(AccessLevel.NONE)
    private long id;
    private String name;

    @OneToMany(mappedBy = "creator")
    Collection<Commission> myCommissions;

    public Customer(String name, Collection<Commission> myCommissions) {
        this.name = name;
        this.myCommissions = myCommissions;
    }

    @Override
    public Long getId() {
        return id;
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
