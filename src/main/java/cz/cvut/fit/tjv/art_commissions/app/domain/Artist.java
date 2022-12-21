package cz.cvut.fit.tjv.art_commissions.app.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Artist implements DomainEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artist")
    private long id;
    private String name;
    @Column(name = "price_per_hour")
    private int pricePerHour;
    @Column(name = "art_type")
    private ArtType artType;

    @ManyToOne
    @JoinColumn(name = "id_coworker")
    private Artist teacher;
    @OneToMany(mappedBy = "teacher")
    private Collection<Artist> apprentices = new HashSet<>();

    @ManyToMany(mappedBy = "commissioners")
    private Collection<Commission> commissions;

    public Artist(String name, int pricePerHour, ArtType artType,
                  Artist teacher, Collection<Artist> apprentices, Collection<Commission> commissions) {
        this.name = name;
        this.artType = artType;
        this.teacher = teacher;
        this.apprentices = apprentices;
        this.commissions = commissions;

        if (pricePerHour < 0)
            throw new IllegalArgumentException("Artist's price per hour cannot be a negative number");
        else
            this.pricePerHour = pricePerHour;
    }

    // Without this the foreign key of the teacher would stay in the apprentice instances
    @PreRemove
    public void deleteArtist() {
        apprentices.forEach(a -> a.setTeacher(null));
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

        Artist artist = (Artist) obj;

        return getId() != null ? getId().equals(artist.getId()) : artist.getId() == null;
    }

    @Override
    public String toString() {
        return "Artist {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", artType=" + artType;
    }
}
