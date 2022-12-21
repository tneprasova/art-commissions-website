package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.CommissionDto;
import cz.cvut.fit.tjv.art_commissions.app.api.model.converter.CommissionConverter;
import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.CommissionPostDto;
import cz.cvut.fit.tjv.art_commissions.app.business.CommissionService;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v1/commissions")
public class CommissionController extends AbstractCrudController<Commission, CommissionDto, CommissionPostDto, Long>{
    public CommissionController(CommissionService service, CommissionConverter converter) {
        super(service, converter);
    }

    @GetMapping
    public Collection<CommissionDto> readAll() {
        return service.readAll().stream().map(entity -> converter.fromEntityToDto(entity)).toList();
    }

    @GetMapping("/customers/{id}")
    public Collection<CommissionDto> myCommissions(
            @PathVariable Long id,
            @Parameter(description = "What to filter the commissions by", examples =
                @ExampleObject(name = "active", description = "Returns only currently active commissions", value = "filter_by=active"))
            @RequestParam Optional<String> filter_by) {

        if (filter_by.isPresent() && filter_by.get().equals("active"))
            return ((CommissionService) service).findMyActiveCommissions(id).stream().map(entity -> converter.fromEntityToDto(entity)).toList();

        return ((CommissionService) service).findByCustomerId(id).stream().map(entity -> converter.fromEntityToDto(entity)).toList();
    }
}
