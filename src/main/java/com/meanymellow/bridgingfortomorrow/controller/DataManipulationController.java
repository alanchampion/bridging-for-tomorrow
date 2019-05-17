package com.meanymellow.bridgingfortomorrow.controller;

import java.io.*;
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
    public String createGroups(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        storageService.loadAll().forEach(path -> {
            BufferedReader br = null;
            try {
                String current = new java.io.File( "." ).getCanonicalPath();
                System.out.println("Current dir: "+current);
                String currentDir = System.getProperty("user.dir");
                System.out.println("Current dir using System: " +currentDir);
                System.out.println("Get File Name: " + path.getFileName().toString());
                System.out.println("To File Absolute: " + path.toFile().getAbsolutePath());
                System.out.println("To File Canonical: " + path.toFile().getCanonicalPath());
                System.out.println("To File Path: " + path.toFile().getPath());
                System.out.println("To File Name: " + path.toFile().getName());
                br = new BufferedReader(new FileReader(storageService.loadAsResource(path.getFileName().toString()).getFile()));
                System.out.println(br.readLine());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        return "showGroups";
    }
}
