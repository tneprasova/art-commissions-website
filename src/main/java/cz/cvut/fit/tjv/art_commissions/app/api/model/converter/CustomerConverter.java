package cz.cvut.fit.tjv.art_commissions.app.api.model.converter;

import cz.cvut.fit.tjv.art_commissions.app.api.model.CustomerDto;
import cz.cvut.fit.tjv.art_commissions.app.business.CommissionService;
import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CustomerConverter extends AbstractConverter<Customer, CustomerDto, Long> {
    private final CommissionService commissionService;

    public CustomerConverter(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @Override
    public CustomerDto toDto(Customer customer) {
        Collection<Long> myCommissionsIds = new ArrayList<>();
        customer.getMyCommissions().forEach(commission -> myCommissionsIds.add(customer.getId()));

        return new CustomerDto(customer.getId(), customer.getName(), myCommissionsIds);
    }

    @Override
    public Customer toEntity(CustomerDto dto) {
        return new Customer(dto.getId(), dto.getName(), commissionService.readById(dto.getMyCommissions()));
    }
}
