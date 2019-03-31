package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.CustomUserDetails;
import net.milgar.sharedoc.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }

        return null;
    }

    @Override
    public UserDetails findLoggedInUserDetails() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Object userDetails = authentication.getPrincipal();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails);
        }

        return null;
    }

    @Override
    public User findLoggedInUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Object userDetails = authentication.getPrincipal();
        if (userDetails instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails)userDetails;
            return userService.findByUsername(customUserDetails.getUser().getUsername());
        }

        return null;
    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
    }
}