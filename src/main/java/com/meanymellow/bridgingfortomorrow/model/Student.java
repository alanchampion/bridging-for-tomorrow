package com.meanymellow.bridgingfortomorrow.model;

public class Student {
    private String firstName, lastName, school, gender, grade;

    public Student(String grade, String firstName, String lastName, String school, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
        this.school = school;
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
