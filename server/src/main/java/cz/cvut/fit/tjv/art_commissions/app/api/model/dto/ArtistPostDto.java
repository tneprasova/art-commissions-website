package cz.cvut.fit.tjv.art_commissions.app.api.model.dto;

import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class ArtistPostDto {
    private String name;
    private int pricePerHour;
    private ArtType artType;
    private Collection<Long> commissions;
    private Long teacher;
    private Collection<Long> apprentices;
}