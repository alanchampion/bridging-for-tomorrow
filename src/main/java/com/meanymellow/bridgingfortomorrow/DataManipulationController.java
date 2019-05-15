package com.meanymellow.bridgingfortomorrow;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meanymellow.bridgingfortomorrow.storage.StorageFileNotFoundException;
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
        return "showGroups";
    }
}
