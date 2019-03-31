package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityService {
    String findLoggedInUsername();
    UserDetails findLoggedInUserDetails();
    User findLoggedInUser();

    void autoLogin(String username, String password);
}
