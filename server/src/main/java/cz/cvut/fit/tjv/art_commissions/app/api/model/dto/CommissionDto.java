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
}
