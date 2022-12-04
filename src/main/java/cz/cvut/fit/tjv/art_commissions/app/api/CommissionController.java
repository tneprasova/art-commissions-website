package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.api.model.CommissionDto;
import cz.cvut.fit.tjv.art_commissions.app.api.model.converter.CommissionConverter;
import cz.cvut.fit.tjv.art_commissions.app.business.CommissionService;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v1/commissions")
public class CommissionController extends AbstractCrudController<Commission, CommissionDto, Long>{
    public CommissionController(CommissionService service, CommissionConverter converter) {
        super(service, converter);
    }

    @GetMapping
    public Collection<CommissionDto> readAll() {
        return service.readAll().stream().map(entity -> converter.toDto(entity)).toList();
    }

    @GetMapping("/customers/{id}")
    public Collection<CommissionDto> myCommissions(@PathVariable Long id, @RequestParam Optional<String> filter_by) {
        if (filter_by.isPresent() && filter_by.get().equals("active"))
            return ((CommissionService) service).findMyActiveCommissions(id).stream().map(entity -> converter.toDto(entity)).toList();

        return ((CommissionService) service).findByCustomerId(id).stream().map(entity -> converter.toDto(entity)).toList();
    }
}
