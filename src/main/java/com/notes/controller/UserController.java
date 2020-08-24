package com.notes.controller;

import com.notes.model.User;
import com.notes.service.UserService;
import com.notes.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 *
 * User Controller, i.e. {contextPath}/login & {contextPath}/ registration Controller
 *
 * Provides access to the above-mentioned pages and populates them with error / messages,
 * should any be present.
 *
 * */

@Controller
public final class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @ModelAttribute("userForm")
    public User getUserForm() {
        return new User();
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") final User userForm,
                               final BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
            return "registration";

        userService.save(userForm);

        return "redirect:/login";
    }

    @PostMapping("/login/error")
    public String errorLogin(final Model model, final String error) {
        System.out.println("hello there");
        model.addAttribute("error", "The provided username and password combination is invalid.");

        return "login";
    }

    @GetMapping("/login")
    public String login(final Model model, final String error, final String logout) {
//        if (Objects.nonNull(error))
//            if (!error.isBlank())
//                model.addAttribute("error", "The provided username and password combination is invalid.");

        if (Objects.nonNull(logout))
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
}
