package cz.cvut.fit.tjv.art_commissions.app.domain;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Customer implements DomainEntity<Long> {

    // Attributes -----------------------------------------------------------------------------------------------------
    @Id
    private long id;
    @NotNull
    private String name;

    // Relations ------------------------------------------------------------------------------------------------------
    @OneToMany(mappedBy = "creator")
    private Set<Commission> commissions = new HashSet<>();

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

    public Collection<Commission> getCommissions() {
        return commissions;
    }

    public void setCommissions(Collection<Commission> commissions) {
        this.commissions.clear();
        this.commissions.addAll(commissions);
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
                ", name='" + name + '\'' +
                ", commissions=" + commissions +
                '}';
    }
}
