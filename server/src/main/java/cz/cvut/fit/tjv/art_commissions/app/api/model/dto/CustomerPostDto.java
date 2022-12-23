package cz.cvut.fit.tjv.art_commissions.app.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class CustomerPostDto {
    private String name;
    private Collection<Long> myCommissions;
}
