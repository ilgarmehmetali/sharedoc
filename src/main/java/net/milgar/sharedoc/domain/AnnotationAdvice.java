package net.milgar.sharedoc.domain;

import net.milgar.sharedoc.domain.model.User;
import net.milgar.sharedoc.domain.service.SecurityServiceImpl;
import net.milgar.sharedoc.domain.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(annotations = Controller.class)
public class AnnotationAdvice {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    SecurityServiceImpl securityService;

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        System.out.println("currentUser called");
        return securityService.findLoggedInUser();
    }

}