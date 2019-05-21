package com.meanymellow.bridgingfortomorrow;

import com.meanymellow.bridgingfortomorrow.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static List<List<Student>> createGroups(List<Student> students) {
        Map<Integer, List<Student>> grades = new HashMap<>();

        for(Student student : students) {
            // System.out.println(student.getFirstName());
            student = cleanUp(student);
            grades.computeIfAbsent(Integer.parseInt(student.getGrade()), k -> new ArrayList<>()).add(student);
        }

        List<List<Student>> groups = new ArrayList<>(grades.values());

        return groups;
    }

    public static Student cleanUp(Student student) {
        student.getGrade();

        if(student.getGender().toLowerCase().equals("m") || student.getGender().toLowerCase().equals("male")) {
            student.setGender("M");
        }
        else if(student.getGender().toLowerCase().equals("f") || student.getGender().toLowerCase().equals("female")) {
            student.setGender("F");
        }

        if(student.getGrade().toLowerCase().equals("k") || student.getGrade().toLowerCase().equals("kinder") || student.getGrade().toLowerCase().equals("kindergarten")) {
            student.setGrade("0");
        }
        if(!isInteger(student.getGrade())) {
            student.setGrade("-1");
        }

        return student;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
