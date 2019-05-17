package com.meanymellow.bridgingfortomorrow.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import com.meanymellow.bridgingfortomorrow.storage.StudentStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.meanymellow.bridgingfortomorrow.storage.StorageService;

@Controller
public class LoginController {
    private final StudentStorage studentStorage;

    @Autowired
    public LoginController(StudentStorage studentStorage) {
        this.studentStorage = studentStorage;
    }

    @GetMapping("/login")
    public String login(Model model) throws IOException {
        return "login";
    }

    @GetMapping("/")
    public String homepage(Model model) throws IOException{
        model.addAttribute("students", studentStorage.findAll());

        return "homepage";
    }
}
