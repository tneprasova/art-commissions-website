package cz.cvut.fit.tjv.art_commissions.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String name;
    private Collection<Long> myCommissions;
}