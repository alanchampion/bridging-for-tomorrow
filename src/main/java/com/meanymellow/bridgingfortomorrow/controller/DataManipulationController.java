package com.meanymellow.bridgingfortomorrow.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.meanymellow.bridgingfortomorrow.model.Student;
import com.meanymellow.bridgingfortomorrow.storage.StudentStorage;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.meanymellow.bridgingfortomorrow.storage.StorageService;

@Controller
public class DataManipulationController {

    private final StorageService storageService;
    private final StudentStorage studentStorage;;

    @Autowired
    public DataManipulationController(StorageService storageService, StudentStorage studentStorage) {
        this.storageService = storageService;
        this.studentStorage = studentStorage;
    }

@PostMapping("/addupload")
    public String createGroups(Model model) throws IOException {
        List<Student> students = new ArrayList<Student>();

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        model.addAttribute("students", students);

        storageService.loadAll().forEach(path -> {
            try {
                File file = storageService.loadAsResource(path.getFileName().toString()).getFile();
                BufferedReader br = new BufferedReader(new FileReader(file));
                System.out.println(br.readLine());

                try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
                    String[] values = null;
                    int grade = -1, firstName = -1, lastName = -1, school = -1, gender = -1;
                    if((values = csvReader.readNext()) != null) {
                        for(int i = 0; i < values.length; i++) {
                            if(values[i].toLowerCase().equals("grade")) {
                                grade = i;
                            }
                            else if(values[i].toLowerCase().equals("first name")) {
                                firstName = i;
                            }
                            else if(values[i].toLowerCase().equals("last name")) {
                                lastName = i;
                            }
                            else if(values[i].toLowerCase().equals("school")) {
                                school = i;
                            }
                            else if(values[i].toLowerCase().equals("gender")) {
                                gender = i;
                            }
                        }
                    }
                    if(grade == -1 || firstName == -1 || lastName == -1 || school == -1 || gender == -1) {
                        // TODO Print something out for the user.
                        System.out.println("Error. CSV is not formatted correctly");
                    }
                    while ((values = csvReader.readNext()) != null) {
                        students.add(new Student(values[grade], values[firstName], values[lastName], values[school], values[gender]));
                        System.out.println(Arrays.toString(values));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        studentStorage.saveAll(students);

        return "redirect:/";
    }

    @GetMapping("/deleteall")
    public String deleteAll(Model model) throws IOException {
        studentStorage.removeAll();
        return "redirect:/";
    }
}
