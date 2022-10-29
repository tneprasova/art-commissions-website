package cz.cvut.fit.tjv.art_commissions.app.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Commission implements DomainEntity<Long> {

    // Attributes -----------------------------------------------------------------------------------------------------
    @Id
    private long id;
    @NotNull
    private ArtType artType;
    private String description;
    @NotNull
    private int estimatedHours;
    @NotNull
    private int price;
    @NotNull
    private LocalDate issuingDate;
    @NotNull
    private LocalDate estimatedEndDate;

    // Relations ------------------------------------------------------------------------------------------------------
    @ManyToOne
    private Customer creator;

    @ManyToMany
    @JoinTable(
            name = "commission_artist",
            joinColumns = @JoinColumn(name = "commission_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> commissioners = new HashSet<>();

    // Constructors ---------------------------------------------------------------------------------------------------
    public Commission() {}

    public Commission(long id, ArtType artType, String description, Difficulty difficulty, LocalDate issuingDate,
                      Customer creator, Collection<Artist> commissioners) {
        this.id = id;
        this.artType = artType;
        this.description = description;
        this.estimatedHours = difficulty.hours;
        this.issuingDate = issuingDate;
        this.creator = Objects.requireNonNull(creator);
        this.commissioners.addAll(Objects.requireNonNull(commissioners));
        updatePrice();
        updateEstimatedEndDate();
    }

    // Custom methods -------------------------------------------------------------------------------------------------
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

        for (var artist : this.commissioners) {
            if (artist.getCoworkers().contains(commissioner)) {
                this.commissioners.add(commissioner);
                updatePrice();
                return;
            }
        }
        throw new CommissionException(
                "The artist with ID " + commissioner.getId() +
                " does not collaborate with any of the commissioners assigned to this commission"
        );
    }

    // Getters and setters --------------------------------------------------------------------------------------------
    public ArtType getArtType() {
        return artType;
    }

    public void setArtType(ArtType artType) {
        this.artType = artType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Difficulty difficulty) {
        this.estimatedHours = difficulty.hours;
        updatePrice();
        updateEstimatedEndDate();
    }

    public int getPrice() {
        return price;
    }

    public LocalDate getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(LocalDate issuingDate) {
        this.issuingDate = issuingDate;
        updatePrice();
        updateEstimatedEndDate();
    }

    public LocalDate getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public Customer getCreator() {
        return creator;
    }

    public void setCreator(Customer creator) {
        this.creator = creator;
    }

    public Collection<Artist> getCommissioners() {
        return commissioners;
    }

    public void setCommissioners(Collection<Artist> commissioners) {
        this.commissioners.clear();
        this.commissioners.addAll(commissioners);
        updatePrice();
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

        Commission commission = (Commission) obj;

        return getId() != null ? getId().equals(commission.getId()) : commission.getId() == null;
    }

    @Override
    public String toString() {
        return "Commission {" +
                "id=" + id +
                ", artType=" + artType +
                ", description='" + description + '\'' +
                ", estimatedHours=" + estimatedHours +
                ", price=" + price +
                ", issuingDate=" + issuingDate +
                ", estimatedEndDate=" + estimatedEndDate +
                ", creator=" + creator +
                ", commissioners=" + commissioners +
                '}';
    }
}
