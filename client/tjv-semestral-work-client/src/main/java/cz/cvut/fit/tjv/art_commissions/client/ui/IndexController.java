package cz.cvut.fit.tjv.art_commissions.client.ui;

import cz.cvut.fit.tjv.art_commissions.client.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    CustomerService customerService;

    public IndexController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("allCustomers", customerService.readAll());

        return "index";
    }
}
