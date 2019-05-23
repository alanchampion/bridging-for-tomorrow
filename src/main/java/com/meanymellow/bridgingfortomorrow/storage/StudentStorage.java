package com.meanymellow.bridgingfortomorrow.storage;

import com.meanymellow.bridgingfortomorrow.Util;
import com.meanymellow.bridgingfortomorrow.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentStorage {

    final private List<Student> students = new ArrayList<>();

    public void saveAll(List<Student> students) {
        for(Student student : students) {
            this.students.add(Util.cleanUp(student));
        }
        // this.students.addAll(students);
    }

    public List<Student> getAll() {
        return new ArrayList<Student>(this.students);
    }

    public Map<String, List<Student>> getAllGrades() {
        Map<String, List<Student>> grades = new HashMap<>();
        for(Student student : students) {
            // System.out.println(student.getFirstName());
            grades.computeIfAbsent(student.getGrade().toLowerCase(), k -> new ArrayList<>()).add(student);
        }
        return grades;
    }

    public List<Student> getGrade(String g) {
        List <Student> grade = new ArrayList<>();
        for(Student student : students) {
            // System.out.println(student.getFirstName());
            if(student.getGrade().toLowerCase().equals(g.toLowerCase()))
                grade.add(student);
        }
        return grade;
    }

    public Map<String, List<Student>> getAllGenders() {
        Map<String, List<Student>> genders = new HashMap<>();
        for(Student student : students) {
            // System.out.println(student.getFirstName());
            genders.computeIfAbsent(student.getGender().toLowerCase(), k -> new ArrayList<>()).add(student);
        }
        return genders;
    }

    public List<Student> getGender(String g) {
        List <Student> gender = new ArrayList<>();
        for(Student student : students) {
            // System.out.println(student.getFirstName());
            if(student.getGender().toLowerCase().equals(g.toLowerCase()))
                gender.add(student);
        }
        return gender;
    }

    public Map<String, List<Student>> getAllSchools() {
        Map<String, List<Student>> schools = new HashMap<>();
        for(Student student : students) {
            // System.out.println(student.getFirstName());
            schools.computeIfAbsent(student.getSchool().toLowerCase(), k -> new ArrayList<>()).add(student);
        }
        return schools;
    }

    public List<Student> getSchool(String s) {
        List <Student> school = new ArrayList<>();
        for(Student student : students) {
            // System.out.println(student.getFirstName());
            if(student.getSchool().toLowerCase().equals(s.toLowerCase()))
                school.add(student);
        }
        return school;
    }

    public void removeAll() {
        this.students.clear();
    }
}