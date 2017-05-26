package com.org.vg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.org.vg.domain.User;
import com.org.vg.domain.UserCreateForm;
import com.org.vg.service.UserService;
import com.org.vg.service.exception.UserAlreadyExistsException;


import javax.inject.Inject;
import javax.validation.Valid;

@Controller
public class UserCreateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateController.class);
    private final UserService userService;

    @Inject
    public UserCreateController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user_create.html", method = RequestMethod.GET)
    public ModelAndView getCreateUserView() {
        LOGGER.debug("Received request for user create view");
        return new ModelAndView("user_create", "form", new UserCreateForm());
    }

    @RequestMapping(value = "/user_create.html", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("form") @Valid UserCreateForm form, BindingResult result) {
        LOGGER.debug("Received request to create {}, with result={}", form, result);
        if (result.hasErrors()) {
            return "user_create";
        }
        try {
            userService.save(new User(form.getId(), form.getPassword2()));
        } catch (UserAlreadyExistsException e) {
            LOGGER.debug("Tried to create user with existing id", e);
            result.reject("user.error.exists");
            return "user_create";
        }
        return "redirect:/user_list.html";
    }

}
