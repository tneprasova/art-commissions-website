package cz.cvut.fit.tjv.art_commissions.client.ui;

import cz.cvut.fit.tjv.art_commissions.client.service.ArtistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/Artist")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public String listArtist(
            @RequestParam(value = "artistName", required = false) String name,
            @RequestParam(value = "art_type", required = false) String art_type,
            @RequestParam(value = "order_by", required = false) String order_by,
            Model model) {
        Optional<String> nameParam;
        Optional<String> artTypeParam;
        Optional<String> orderByParam;

        if (name == null || name.isEmpty())
            nameParam = Optional.empty();
        else
            nameParam = Optional.of(name);

        if (art_type == null || art_type.equalsIgnoreCase("any"))
            artTypeParam = Optional.empty();
        else
            artTypeParam = Optional.of(art_type);

        if (art_type == null || order_by.equalsIgnoreCase("none"))
            orderByParam = Optional.empty();
        else
            orderByParam = Optional.of(order_by);

        model.addAttribute("allArtists", artistService.readAll(nameParam, artTypeParam, orderByParam));
        return "artists";
    }
}
