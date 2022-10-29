package cz.cvut.fit.tjv.art_commissions.app.domain;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Artist implements DomainEntity<Long> {

    // Attributes -----------------------------------------------------------------------------------------------------
    @Id
    private long id;
    @NotNull
    private String name;
    @NotNull
    private int pricePerHour;
    @NotNull
    private ArtType artType;

    // Relations ------------------------------------------------------------------------------------------------------
    @ManyToMany(mappedBy = "commissioners")
    private Set<Commission> commissions = new HashSet<>();

    @OneToMany
    private Set<Artist> coworkers = new HashSet<>();

    // Constructors ---------------------------------------------------------------------------------------------------
    public Artist() {}

    public Artist(long id, String name, int pricePerHour, ArtType artType, Collection<Artist> coworkers) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.artType = artType;
        this.coworkers.addAll(coworkers);

        if (pricePerHour < 0)
            throw new IllegalArgumentException("Artist's price per hour cannot be a negative number");
        else
            this.pricePerHour = pricePerHour;
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

    public Collection<Artist> getCoworkers() {
        return coworkers;
    }

    public void setCoworkers(Collection<Artist> coworkers) {
        this.coworkers.clear();
        this.coworkers.addAll(coworkers);
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

    @Override
    public String toString() {
        return "Artist {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", artType=" + artType +
                ", commissions=" + commissions +
                '}';
    }
}
