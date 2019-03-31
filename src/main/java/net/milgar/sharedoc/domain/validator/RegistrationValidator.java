package net.milgar.sharedoc.domain.validator;

import net.milgar.sharedoc.domain.model.User;
import net.milgar.sharedoc.domain.service.UserService;
import net.milgar.sharedoc.domain.web.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationRequest registrationRequest = (RegistrationRequest) o;
        User user = registrationRequest.getUser();
        String role = registrationRequest.getRole();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("user.username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("user.username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("user.password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("user.passwordConfirm", "Diff.userForm.passwordConfirm");
        }

        if(!role.equals("teacher") && !role.equals("student")){
            errors.rejectValue("role", "WrongValue.userForm.role");
        }
    }
}