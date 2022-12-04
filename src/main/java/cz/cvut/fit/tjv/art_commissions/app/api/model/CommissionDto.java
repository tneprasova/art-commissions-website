package cz.cvut.fit.tjv.art_commissions.app.api.model;

import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;

import java.time.LocalDate;
import java.util.Collection;

public class CommissionDto {
    private Long id;
    private ArtType artType;
    private String description;
    private int estimatedHours;
    private int price;
    private LocalDate issuingDate;
    private LocalDate estimatedEndDate;
    private Long creator;
    private Collection<Long> commissioners;

    public CommissionDto(Long id, ArtType artType, String description, int estimatedHours, int price,
                         LocalDate issuingDate, LocalDate estimatedEndDate, Long creator,
                         Collection<Long> commissioners) {
        this.id = id;
        this.artType = artType;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.price = price;
        this.issuingDate = issuingDate;
        this.estimatedEndDate = estimatedEndDate;
        this.creator = creator;
        this.commissioners = commissioners;
    }

    // Getters and setters --------------------------------------------------------------------------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(LocalDate issuingDate) {
        this.issuingDate = issuingDate;
    }

    public LocalDate getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(LocalDate estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Collection<Long> getCommissioners() {
        return commissioners;
    }

    public void setCommissioners(Collection<Long> commissioners) {
        this.commissioners = commissioners;
    }
}
