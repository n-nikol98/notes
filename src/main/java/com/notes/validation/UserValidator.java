package com.notes.validation;

import com.notes.model.User;
import com.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * Validates incoming front-end User Entities.
 *
 * The following restrictions are imposed:
 *  (1) Not-Null / Blank: 'username' field
 *  (2) Not-Null / Blank: 'password' field
 *  (3) >= 6 && <= 20 Characters: 'username' field
 *  (4) >=6 && <= 32 Characters: 'password' field
 *
 * */

@Component
public final class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(final Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final User user = (User) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        final String userName = user.getUsername();

        if (!userName.isBlank() && (userName.length() < 6 || userName.length() > 20)) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(userName).isPresent())
            errors.rejectValue("username", "Duplicate.userForm.username");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        final String password = user.getPassword();

        if (!password.isBlank() && (password.length() < 6 || password.length() > 32)) {
            errors.rejectValue("password", "Size.userForm.password");
        }
    }
}
