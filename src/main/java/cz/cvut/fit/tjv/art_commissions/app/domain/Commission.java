package cz.cvut.fit.tjv.art_commissions.app.domain;

import cz.cvut.fit.tjv.art_commissions.app.exceptions.CommissionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Commission implements DomainEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commission")
    private long id;
    @Column(name = "art_type")
    private ArtType artType;
    private String description;
    @Column(name = "estimated_hours")
    private int estimatedHours;
    @Column(name = "issuing_date")
    private LocalDate issuingDate;
    @Setter(AccessLevel.NONE)
    @Column(name = "estimated_end_date")
    private LocalDate estimatedEndDate;
    @Setter(AccessLevel.NONE)
    private int price;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "commission_artist",
            joinColumns = @JoinColumn(name = "id_commission"),
            inverseJoinColumns = @JoinColumn(name = "id_artist")
    )
    private Set<Artist> commissioners = new HashSet<>();

    public Commission(ArtType artType, String description, int hours, LocalDate issuingDate,
                      Customer creator, Collection<Artist> commissioners) {
        this.artType = artType;
        this.description = description;
        this.estimatedHours = hours;
        this.issuingDate = issuingDate;
        this.creator = Objects.requireNonNull(creator);
        this.commissioners.addAll(Objects.requireNonNull(commissioners));
        updatePrice();
        updateEstimatedEndDate();

        if (commissioners.stream().anyMatch(c -> c.getArtType() != this.artType))
            throw new CommissionException("Cannot add commissioners who specialize on a different art type " +
                                          "than the commission requires");
    }

    public void updatePrice() {
        price = 0;
        commissioners.forEach(artist -> price += artist.getPricePerHour() * estimatedHours);
    }

    public void updateEstimatedEndDate() {
        int hoursPerWorkDay = 8;
        int daysSpentWorking = estimatedHours / hoursPerWorkDay + ((estimatedHours % hoursPerWorkDay == 0) ? 0 : 1);
        estimatedEndDate = issuingDate.plusDays(daysSpentWorking);
    }

    public void addCommissioner(Artist commissioner) {
        if (commissioners.contains(commissioner))
            throw new CommissionException("The artist with ID " + commissioner.getId() + " is already assigned to this commission");

        if (commissioner.getArtType() != artType)
            throw new CommissionException("The artist with ID " + commissioner.getId() + " does not specialize in " + artType);

        this.commissioners.add(commissioner);
        updatePrice();
    }

    public void setEstimatedHours(int hours) {
        this.estimatedHours = hours;
        updatePrice();
        updateEstimatedEndDate();
    }

    public void setIssuingDate(LocalDate issuingDate) {
        this.issuingDate = issuingDate;
        updatePrice();
        updateEstimatedEndDate();
    }

    public void setCommissioners(Set<Artist> commissioners) {
        this.commissioners = commissioners;
        updatePrice();
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

        Commission commission = (Commission) obj;

        return getId() != null ? getId().equals(commission.getId()) : commission.getId() == null;
    }

    @Override
    public String toString() {
        return "Commission {" +
                "id=" + id +
                ", artType=" + artType +
                ", description='" + description + '\'' +
                ", estimatedDays=" + estimatedHours +
                ", price=" + price +
                ", issuingDate=" + issuingDate +
                ", estimatedEndDate=" + estimatedEndDate +
                ", creator=" + creator +
                ", commissioners=" + commissioners +
                '}';
    }
}
