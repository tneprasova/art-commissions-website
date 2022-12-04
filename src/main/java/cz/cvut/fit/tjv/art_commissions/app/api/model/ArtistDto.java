package cz.cvut.fit.tjv.art_commissions.app.api.model;

import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;

import java.util.Collection;

public class ArtistDto {
    private Long id;
    private String name;
    private int pricePerHour;
    private ArtType artType;
    private Collection<Long> commissions;
    private Collection<Long> coworkers;

    public ArtistDto(Long id, String name, int pricePerHour, ArtType artType, Collection<Long> commissions, Collection<Long> coworkers) {
        this.id = id;
        this.name = name;
        this.pricePerHour = pricePerHour;
        this.artType = artType;
        this.commissions = commissions;
        this.coworkers = coworkers;
    }

    // Getters and setters --------------------------------------------------------------------------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Collection<Long> getCommissions() {
        return commissions;
    }

    public void setCommissions(Collection<Long> commissions) {
        this.commissions = commissions;
    }

    public Collection<Long> getCoworkers() {
        return coworkers;
    }

    public void setCoworkers(Collection<Long> coworkers) {
        this.coworkers = coworkers;
    }
}
