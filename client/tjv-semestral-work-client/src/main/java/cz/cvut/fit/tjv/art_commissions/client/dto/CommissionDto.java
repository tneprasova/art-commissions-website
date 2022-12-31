package cz.cvut.fit.tjv.art_commissions.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class CommissionDto {
    private Long id;
    private String artType;
    private String description;
    private int estimatedHours;
    private int price;
    private LocalDate issuingDate;
    private LocalDate estimatedEndDate;
    private Long creator;
    private Collection<Long> commissioners;

    public String getArtTypeFormated() {
        var leadingChar = String.valueOf(artType.charAt(0));
        return artType.toLowerCase().replace("_", " ").replaceFirst("^.", leadingChar);
    }

    public void setArtType(String artType) {
        this.artType = artType.toUpperCase().replace(" ", "_");
    }
}