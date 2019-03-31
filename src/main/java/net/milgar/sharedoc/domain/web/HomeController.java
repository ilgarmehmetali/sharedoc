package net.milgar.sharedoc.domain.web;

import net.milgar.sharedoc.domain.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    SecurityService securityService;

    @GetMapping("/")
    public String index(Model model, Principal principal) {

        model.addAttribute("name", "your name211");
        model.addAttribute("user", securityService.findLoggedInUserDetails());
        return "index";
    }

}