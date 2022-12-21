package cz.cvut.fit.tjv.art_commissions.app.api;

import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.ArtistDto;
import cz.cvut.fit.tjv.art_commissions.app.api.model.converter.ArtistConverter;
import cz.cvut.fit.tjv.art_commissions.app.api.model.dto.ArtistPostDto;
import cz.cvut.fit.tjv.art_commissions.app.business.ArtistService;
import cz.cvut.fit.tjv.art_commissions.app.business.CommissionService;
import cz.cvut.fit.tjv.art_commissions.app.domain.ArtType;
import cz.cvut.fit.tjv.art_commissions.app.domain.Artist;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v1/artists")
public class ArtistController extends AbstractCrudController<Artist, ArtistDto, ArtistPostDto, Long> {
    CommissionService commissionService;
    public ArtistController(ArtistService service, ArtistConverter converter, CommissionService commissionService) {
        super(service, converter);
        this.commissionService = commissionService;
    }

    @GetMapping
    public Collection<ArtistDto> readAll(
            @Parameter(description = "Name of the artist")
            @RequestParam Optional<String> name,
            @Parameter(description = "The type of art an artist specializes on")
            @RequestParam Optional<String> art_type,
            @Parameter(description = "What to order the artists by", examples = {
                    @ExampleObject(name = "price", description = "An artist's price per hour", value = "order_by=price"),
                    @ExampleObject(name = "activeCount", description = "The number of artist's active commissions", value = "order_by=activeCount")
            })
            @RequestParam Optional<String> order_by) {

        Collection<Artist> ordered;

        // Filtering
        Collection<Long> filteredByName = name.map(s -> ((ArtistService) service)
                        .filterByName(s).stream().map(Artist::getId))
                .orElseGet(() -> service.readAll().stream().map(Artist::getId)).toList();

        Collection<Long> filteredByArtType = art_type.map(s -> ((ArtistService) service)
                        .filterByArtType(ArtType.valueOf(s.toUpperCase())).stream().map(Artist::getId))
                .orElseGet(() -> service.readAll().stream().map(Artist::getId)).toList();

        // Ordering
        if (order_by.isPresent() && order_by.get().equals("activeCount"))
            ordered = ((ArtistService) service).orderByActiveCommissionsToDate(LocalDate.now());

        else if (order_by.isPresent() && order_by.get().equals("price"))
            ordered = ((ArtistService) service).orderByPrice();
        else
            ordered = service.readAll();

        return ordered.stream().filter(a -> filteredByName.contains(a.getId()) && filteredByArtType.contains(a.getId()))
                .map(entity -> converter.fromEntityToDto(entity)).toList();
    }
}
