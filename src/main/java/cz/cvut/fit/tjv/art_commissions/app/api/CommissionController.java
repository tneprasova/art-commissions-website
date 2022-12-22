package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.CommissionDto;
import cz.cvut.fit.tjv.art_commissions.app.api.model.converter.CommissionConverter;
import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.CommissionPostDto;
import cz.cvut.fit.tjv.art_commissions.app.business.CommissionService;
import cz.cvut.fit.tjv.art_commissions.app.business.CustomerService;
import cz.cvut.fit.tjv.art_commissions.app.domain.Commission;
import cz.cvut.fit.tjv.art_commissions.app.exceptions.EntityDoesNotExistException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v1/commissions")
public class CommissionController extends AbstractCrudController<Commission, CommissionDto, CommissionPostDto, Long>{
    private CustomerService customerService;

    public CommissionController(CommissionService service, CommissionConverter converter, CustomerService customerService) {
        super(service, converter);
        this.customerService = customerService;
    }

    @GetMapping
    public Collection<CommissionDto> readAll() {
        return service.readAll().stream().map(entity -> converter.fromEntityToDto(entity)).toList();
    }

    @GetMapping("/customers/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success with the customers commissions as return data"),
            @ApiResponse(responseCode = "404", description = "Attempt to read commissions of a nonexistent customer")}
    )
    public Collection<CommissionDto> myCommissions(
            @PathVariable Long id,
            @Parameter(description = "What to filter the commissions by", examples =
                @ExampleObject(name = "active", description = "Returns only currently active commissions", value = "filter_by=active"))
            @RequestParam Optional<String> filter_by) {

        if (customerService.readById(id).isEmpty())
            throw new EntityDoesNotExistException("Trying to get commissions of a nonexistent customer");

        if (filter_by.isPresent() && filter_by.get().equals("active"))
            return ((CommissionService) service).findMyActiveCommissions(id).stream().map(entity -> converter.fromEntityToDto(entity)).toList();

        return ((CommissionService) service).findByCustomerId(id).stream().map(entity -> converter.fromEntityToDto(entity)).toList();
    }

    @PutMapping("/{idCommission}/artists/{idArtist}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success with no content provided"),
            @ApiResponse(responseCode = "400", description = "Received invalid data"),
            @ApiResponse(responseCode = "404", description = "Attempt to update a nonexistent entity")}
    )
    public void addCommissioner(@PathVariable Long idCommission, @PathVariable Long idArtist) {
        ((CommissionService) service).addCommissioner(idCommission, idArtist);
    }

    @DeleteMapping("/{idCommission}/artists/{idArtist}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success with no content provided"),
            @ApiResponse(responseCode = "404", description = "Attempt to update a nonexistent entity")}
    )
    public void removeCommissioner(@PathVariable Long idCommission, @PathVariable Long idArtist) {
        ((CommissionService) service).removeCommissioner(idCommission, idArtist);
    }
}
