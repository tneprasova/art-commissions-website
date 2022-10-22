package cz.cvut.fit.tjv.art_commissions.app.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Artist implements DomainEntity<Long> {

    // Attributes -----------------------------------------------------------------------------------------------------
    @Id
    private long id;
    private String name;
    private int pricePerHour;
    private ArtType artType;

    // Relations ------------------------------------------------------------------------------------------------------
    @ManyToMany(mappedBy = "commissioners")
    private Set<Commission> commissions = new HashSet<>();

    // Constructors ---------------------------------------------------------------------------------------------------
    public Artist() {}

    public Artist(long id, String name, int pricePerHour, ArtType artType) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.artType = artType;

        if (pricePerHour < 0)
            this.pricePerHour = pricePerHour;
        else
            throw new IllegalArgumentException("Artist's price per hour cannot be a negative number");
    }

    // Getters and setters --------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public ArtType getArtType() {
        return artType;
    }

    public void setArtType(ArtType artType) {
        this.artType = artType;
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

        Artist artist = (Artist) obj;

        return getId() != null ? getId().equals(artist.getId()) : artist.getId() == null;
    }
}
