package com.notes.component;

import com.notes.model.User;
import com.notes.service.SecurityService;
import com.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * Bean for the quick provision of a Persistence Context attached User Entity
 * of the current logged in User.
 *
 * */

@Component
public final class CurrentUserEntityProviderImpl implements CurrentUserEntityProvider {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Override
    public User getCurrentUserEntity() {
        final String loggedInUsername = securityService.findLoggedInUsername();
        return userService.findByUsername(
                loggedInUsername)
                .orElseThrow(() ->
                        new UsernameNotFoundException(loggedInUsername));
    }
}
