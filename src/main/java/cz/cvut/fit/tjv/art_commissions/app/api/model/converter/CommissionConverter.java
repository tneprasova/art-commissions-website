package cz.cvut.fit.tjv.art_commissions.app.api.model.converter;

import cz.cvut.fit.tjv.art_commissions.app.api.model.CommissionDto;
import cz.cvut.fit.tjv.art_commissions.app.business.ArtistService;
import cz.cvut.fit.tjv.art_commissions.app.business.CustomerService;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import cz.cvut.fit.tjv.art_commissions.app.domain.Difficulty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class CommissionConverter extends AbstractConverter<Commission, CommissionDto, Long> {
    public final ArtistService artistService;
    public final CustomerService customerService;

    public CommissionConverter(ArtistService artistService, CustomerService customerService) {
        this.artistService = artistService;
        this.customerService = customerService;
    }

    @Override
    public CommissionDto toDto(Commission commission) {
        Collection<Long> commissionersIds = new ArrayList<>();
        commission.getCommissioners().forEach(c -> commissionersIds.add(c.getId()));

        return new CommissionDto(commission.getId(), commission.getArtType(), commission.getDescription(),
                commission.getEstimatedHours(), commission.getPrice(), commission.getIssuingDate(),
                commission.getEstimatedEndDate(), commission.getCreator().getId(), commissionersIds);
    }

    @Override
    public Commission toEntity(CommissionDto dto) {
        Optional<Customer> customer = customerService.readById(dto.getCreator());
        if (customer.isEmpty())
            throw new EntityDoesNotExistException("Nonexistent customer assigned to a commission");

        return new Commission(dto.getId(), dto.getArtType(), dto.getDescription(), Difficulty.EASY, // TODO difficulty
                dto.getIssuingDate(), customer.get(), artistService.readById(dto.getCommissioners()));
    }
}
