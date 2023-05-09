package cz.cvut.fit.tjv.art_commissions.client.ui;

import cz.cvut.fit.tjv.art_commissions.client.api_client.ApiException;
import cz.cvut.fit.tjv.art_commissions.client.dto.ArtistDto;
import cz.cvut.fit.tjv.art_commissions.client.dto.CommissionDto;
import cz.cvut.fit.tjv.art_commissions.client.dto.CommissionPostDto;
import cz.cvut.fit.tjv.art_commissions.client.service.ArtistService;
import cz.cvut.fit.tjv.art_commissions.client.service.CommissionService;
import cz.cvut.fit.tjv.art_commissions.client.service.CustomerService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/Commission")
public class CommissionController {
    private final CommissionService commissionService;
    private final CustomerService customerService;
    private final ArtistService artistService;
    private final Map<Long, String> creatorsNames = new HashMap<>();
    private final Map<Long, String> artistsNames = new HashMap<>();

    private final boolean allSet;

    public CommissionController(CommissionService commissionService, CustomerService customerService, ArtistService artistService) {
        this.commissionService = commissionService;
        this.customerService = customerService;
        this.artistService = artistService;
        this.allSet = false;
    }

    public void setup() {
        if (!allSet) {
            customerService.readAll().forEach(c -> creatorsNames.put(c.getId(), c.getName()));
            artistService.readAll(Optional.empty(), Optional.empty(), Optional.empty()).forEach(
                    a -> artistsNames.put(a.getId(), a.getName()));
        }
    }

    @GetMapping
    public String listCommissions(Model model) {
        setup();

        model.addAttribute("creatorsNames", creatorsNames);
        model.addAttribute("artistsNames", artistsNames);
        model.addAttribute("allCommissions", commissionService.readAll());

        return "commissions";
    }

    @GetMapping("/MyCommissions")
    public String listMyCommissions(@RequestParam Long id, Model model) {
        setup();
        mainPageSetUp(model);
        model.addAttribute("allCommissions", commissionService.readMyCommissions(id, Optional.empty()));
        model.addAttribute("customer", customerService.readOneById(id).orElseThrow());

        return "commissions";
    }

    @GetMapping("/create")
    public String createCommission(@RequestParam Long id, Model model) {
        CommissionPostDto dto = new CommissionPostDto(null, null, null, LocalDate.now(), id, null);

        model.addAttribute("allArtists", artistService.readAll(Optional.empty(), Optional.empty(), Optional.empty()));
        model.addAttribute("commission", dto);
//        model.addAttribute("customerId", id);

        return "createCommission";
    }

    @PostMapping("/create")
    public String createCommissionSubmit(@ModelAttribute CommissionPostDto dto, Model model) {
        dto.setIssuingDate(LocalDate.now());

        try {
            commissionService.create(dto);

        } catch (ApiException ex) {
            String errorMessage;
            if (dto.getArtType() == null || dto.getArtType().isEmpty())
                errorMessage = "An art type must be selected";
            else if (ex.response.getDebugMessage().equalsIgnoreCase("The estimated hours of a commission must be a positive number"))
                errorMessage = "The commission's duration must be a positive whole number";
            else if (dto.getCommissioners() == null || dto.getCommissioners().isEmpty())
                errorMessage = "A commissioner must be selected";
            else
                errorMessage = ex.response.getDebugMessage();

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("commission", dto);
            model.addAttribute("allArtists", artistService.readAll(Optional.empty(), Optional.empty(), Optional.empty()));
            return "createCommission";
        }

        mainPageSetUp(model);
        return "commissions";
    }

    @GetMapping("/edit")
    public String editCommission(@RequestParam Long id, Model model) {
        commissionService.setCurrentCommission(id);
        var commission = commissionService.readOne().orElseThrow();
        List<ArtistDto> commissioners = new ArrayList<>();
        commission.getCommissioners().forEach(c -> commissioners.add(artistService.readOneById(c).orElseThrow()));

        model.addAttribute("commission", commission);
        model.addAttribute("newCoworkerId", new long[0]);
        model.addAttribute("artist", artistService.readOneById(commission.getCommissioners().iterator().next()).orElseThrow());
        model.addAttribute("customer", customerService.readOneById(commission.getCreator()).orElseThrow());
        model.addAttribute("coworkers", commissionService.readCoworkers(commission.getId()));
        model.addAttribute("commissioners", commissioners);

        return "editCommission";
    }

    @PostMapping("/edit")
    public String editCommissionSubmit(@RequestParam Long id, @RequestParam(value = "newCoworkerId") Long newCoworkerId, @ModelAttribute CommissionDto dto, Model model) {
        commissionService.setCurrentCommission(dto.getId());
        dto.setArtType(dto.getArtTypeFormated());

        if (newCoworkerId != 0) {
            var newCoworkers = dto.getCommissioners();
            newCoworkers.add(newCoworkerId);
            dto.setCommissioners(newCoworkers);
        }

        try {
            commissionService.update(dto);

        } catch (ApiException ex) {
            String errorMessage;
            if (ex.response.getDebugMessage().equalsIgnoreCase("The estimated hours of a commission must be a positive number"))
                errorMessage = "The commission's duration must be a positive whole number";
            else
                errorMessage = ex.response.getDebugMessage();

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("commission", dto);
            model.addAttribute("coworkers", commissionService.readCoworkers(id));
            return editCommission(id, model);
        }

        mainPageSetUp(model);
        return "commissions";
    }

    public void mainPageSetUp(Model model) {
        setup();

        model.addAttribute("allArtists", artistService.readAll(Optional.empty(), Optional.empty(), Optional.empty()));
        model.addAttribute("creatorsNames", creatorsNames);
        model.addAttribute("artistsNames", artistsNames);
        model.addAttribute("allCommissions", commissionService.readAll());
    }
}
