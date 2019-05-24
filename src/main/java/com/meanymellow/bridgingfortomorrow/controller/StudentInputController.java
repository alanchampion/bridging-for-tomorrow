package com.meanymellow.bridgingfortomorrow.controller;

import com.meanymellow.bridgingfortomorrow.Util;
import com.meanymellow.bridgingfortomorrow.model.Student;
import com.meanymellow.bridgingfortomorrow.model.StudentCreation;
import com.meanymellow.bridgingfortomorrow.storage.StudentStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        model.addAttribute("students", studentStorage.getAll());
        return "redirect:/input";
    }

    @GetMapping("/groups")
    public String showAll(Model model) {
        model.addAttribute("students", studentStorage.getAll());
        model.addAttribute("groups", Util.createGroups(studentStorage));
        return "showGroups";
    }

    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("student", studentStorage.getStudent(id));
        return "student";
    }

    @GetMapping("/student/{id}/delete")
    public String deleteStudent(@PathVariable int id, RedirectAttributes redirectAttributes) {
        boolean success = studentStorage.delete(id);
        if(success)
            redirectAttributes.addFlashAttribute("message", "You successfully deleted the student!");
        else
            redirectAttributes.addFlashAttribute("message", "Unable to find student to delete!");
        return "redirect:/";
    }

    // TODO have a way to remove a student.
}
