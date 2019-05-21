package com.meanymellow.bridgingfortomorrow.controller;

import com.meanymellow.bridgingfortomorrow.Util;
import com.meanymellow.bridgingfortomorrow.model.Student;
import com.meanymellow.bridgingfortomorrow.model.StudentCreation;
import com.meanymellow.bridgingfortomorrow.storage.StorageService;
import com.meanymellow.bridgingfortomorrow.storage.StudentStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentInputController {
    private final StudentStorage studentStorage;

    @Autowired
    public StudentInputController(StudentStorage studentStorage) {
        this.studentStorage = studentStorage;
    }

    @GetMapping("/input")
    public String showCreateForm(Model model) {
        StudentCreation studentsForm = new StudentCreation();

        // for (int i = 1; i <= 20; i++) {
        studentsForm.addStudent(new Student());
        // }

        model.addAttribute("form", studentsForm);
        return "createStudentsForm";
    }

    @PostMapping("/save")
    public String saveStudents(@ModelAttribute StudentCreation form, Model model) {
        studentStorage.saveAll(form.getStudents());

        model.addAttribute("students", studentStorage.findAll());
        return "redirect:/input";
    }

    @GetMapping("/groups")
    public String showAll(Model model) {
        model.addAttribute("students", studentStorage.findAll());
        model.addAttribute("groups", Util.createGroups(studentStorage.findAll()));
        return "showGroups";
    }

    // TODO have a way to remove a student.
}
