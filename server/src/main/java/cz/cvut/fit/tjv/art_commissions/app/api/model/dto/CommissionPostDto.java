package cz.cvut.fit.tjv.art_commissions.app.api.model.dto;

import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class CommissionPostDto {
    private ArtType artType;
    private String description;
    private int estimatedHours;
    private LocalDate issuingDate;
    private Long creator;
    private Collection<Long> commissioners;
}
