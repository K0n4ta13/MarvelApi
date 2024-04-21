package com.api.marvel.security.validator;

import com.api.marvel.persistence.entity.UserInteractionLog;
import com.api.marvel.service.AuthenticationService;
import org.springframework.stereotype.Component;

@Component("interactionLogValidator")
public class UserInteractionLogValidator {

    private final AuthenticationService authenticationService;

    public UserInteractionLogValidator(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public boolean validate(String username) {
        String userLoggedIn = this.authenticationService.getUserLoggedIn().getUsername();

        return userLoggedIn.equals(username);
    }
}
