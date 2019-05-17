package com.meanymellow.bridgingfortomorrow.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.meanymellow.bridgingfortomorrow.storage.StorageService;

@Controller
public class LoginController {

    @Autowired
    public LoginController() {}

    @GetMapping("/login")
    public String login(Model model) throws IOException {
        return "login";
    }
}
