package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.CustomerDto;
import cz.cvut.fit.tjv.art_commissions.app.api.model.converter.CustomerConverter;
import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.CustomerPostDto;
import cz.cvut.fit.tjv.art_commissions.app.business.CustomerService;
import cz.cvut.fit.tjv.art_commissions.app.domain.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController extends AbstractCrudController<Customer, CustomerDto, CustomerPostDto, Long> {
    public CustomerController(CustomerService service, CustomerConverter converter) {
        super(service, converter);
    }

    @GetMapping
    public Collection<CustomerDto> readAll() {
        return service.readAll().stream().map(entity -> converter.fromEntityToDto(entity)).toList();
    }
}
