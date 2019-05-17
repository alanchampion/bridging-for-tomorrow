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
public class DataManipulationController {

    private final StorageService storageService;

    @Autowired
    public DataManipulationController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/groups")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "showGroups";
    }
}
