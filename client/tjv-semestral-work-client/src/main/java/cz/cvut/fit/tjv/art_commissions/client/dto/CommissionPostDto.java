package cz.cvut.fit.tjv.art_commissions.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class CommissionPostDto {
    private String artType;
    private String description;
    private Integer estimatedHours;
    private LocalDate issuingDate;
    private Long creator;
    private Collection<Long> commissioners;
}
