package net.milgar.sharedoc.domain.web;

import net.milgar.sharedoc.domain.model.User;
import net.milgar.sharedoc.domain.service.SecurityService;
import net.milgar.sharedoc.domain.service.UserService;
import net.milgar.sharedoc.domain.validator.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RegistrationValidator registrationValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//        model.addAttribute("role", "");
        model.addAttribute("registrationRequest", new RegistrationRequest());

        String[] tmp = new String[] {"student", "teacher"};

        return "registration";
    }

    @PostMapping("/registration")
//    public String registration(Model model, BindingResult bindingResult) {
    public String registration(@ModelAttribute("registrationRequest") RegistrationRequest registrationRequest, Map<String, Object> model, BindingResult bindingResult) {
        User userForm = registrationRequest.getUser();
        String role = registrationRequest.getRole();
        registrationValidator.validate(registrationRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm, new String[]{role});

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
}
