package cz.cvut.fit.tjv.art_commissions.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class ArtistDto {
    private Long id;
    private String name;
    private int pricePerHour;
    private String artType;
    private Collection<Long> commissions;
    private Long teacher;
    private Collection<Long> apprentices;

    public String getArtTypeFormated() {
        var leadingChar = String.valueOf(artType.charAt(0));
        return artType.toLowerCase().replace("_", " ").replaceFirst("^.", leadingChar);
    }
}