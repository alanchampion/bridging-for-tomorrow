package com.meanymellow.bridgingfortomorrow.model;

import com.meanymellow.bridgingfortomorrow.Util;

import java.util.ArrayList;
import java.util.List;

public class Group implements Comparable<Group>{
    private List<Student> students;
    private String name;
    private GroupType groupType;
    private Quality quality;
    private Quality tempQuality;

    public Group() {
        students = new ArrayList<>();
        name = "New Group";
        groupType = GroupType.NONE;
        quality = Quality.BAD;
        tempQuality = Quality.PERFECT;
    }

    public Group(List<Student> students) {
        this.students = students;
    }

    private void addStudent(Student student) {
        students.add(student);
        if(groupType == GroupType.NONE) {
            setGroupType(student.getGrade());
        }
    }

    public boolean tryAddStudent(Student student) {
        if(students.isEmpty()) {
            addStudent(student);
            setGroupType(student.getGrade());
            return true;
        }

        if(students.size() >= 8 || (students.size() >= 6 && groupType == GroupType.KFIRST)) {
            return false;
        }

        Quality studentQuality = checkStudent(student);
        if(studentQuality == Quality.PERFECT || studentQuality == Quality.GOOD) {
            addStudent(student);
            updateQuality(studentQuality);
            return true;
        }

        return false;
    }

    public void forceAddStudent(Student student) {
        updateQuality(checkStudent(student));
        addStudent(student);
    }

    private void updateQuality(Quality studentQuality) {
        tempQuality = Util.combineQualities(this.tempQuality, studentQuality);
        if((students.size() != 8 && groupType != GroupType.KFIRST) || (students.size() != 6 && groupType == GroupType.KFIRST)) {
            quality = Quality.BAD;
        } else {
            quality = tempQuality;
        }
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Quality checkStudent(Student student) {
        if(students.size() == 0) {
            return Quality.PERFECT;
        }

        if(students.size() >= 8 || (students.size() >= 6 && groupType == GroupType.KFIRST)) {
            return Quality.BAD;
        }

        Quality gradescore = scoreGrade(student);
        Quality friend = scoreFriend(student);

        if(gradescore == Quality.BAD) {
            return Quality.BAD;
        }

        if(friend == Quality.PERFECT) {
            return Util.combineQualities(gradescore, friend);
        } else if (friend == Quality.GOOD) {
            return Util.combineQualities(gradescore, friend);
        } else if(friend == Quality.OK) {
            return gradescore;
        }

        return gradescore;
    }

    public boolean canAddStudent(Student student) {
        if(students.size() >= 8 || (students.size() >= 6 && groupType == GroupType.KFIRST)) {
            return false;
        }

        if(scoreGrade(student) == Quality.BAD) {
            return false;
        }

        return true;
    }

    public Quality checkQuality() {
        return quality;
    }

    public int size() {
        return students.size();
    }

    private void setGroupType(String grade){
        if(grade.equals("-1")) {
            groupType = GroupType.NONE;
        }
        if(grade.equals("0") || grade.equals("1")) {
            groupType = GroupType.KFIRST;
        }
        if(grade.equals("2") || grade.equals("3")) {
            groupType = GroupType.SECONDTHIRD;
        }
        if(grade.equals("4") || grade.equals("5")) {
            groupType = GroupType.FOURTHFIFTH;
        }
    }

    public GroupType getGroupType(){
        return groupType;
    }

    private Quality scoreFriend(Student student) {
        Quality score = Quality.BAD;
        boolean alreadyHasFriend = false;
        for(int i = 0; i < students.size(); i++) {
            if(student.getSchool().equals(students.get(i).getSchool())){
                if(student.getGrade().equals(students.get(i).getGrade())) {
                    if(student.getGender().equals(students.get(i).getGender())) {
                        if(!alreadyHasFriend) {
                            score = Quality.PERFECT;
                            alreadyHasFriend = true;
                        } else {
                            score = Quality.OK;
                        }
                    }
                }
            }
        }

        return score;
    }

    private Quality scoreGrade(Student student) {
        Quality gradescore = Quality.BAD;
        if(groupType == GroupType.NONE) {
            gradescore = Quality.PERFECT;
        } else {
            switch(student.getGrade()) {
                case "0":
                    if(groupType == GroupType.KFIRST)
                        gradescore = Quality.PERFECT;
                    break;
                case "1":
                    if(groupType == GroupType.KFIRST)
                        gradescore = Quality.PERFECT;
                    else if(groupType == GroupType.SECONDTHIRD)
                        gradescore = Quality.OK;
                    break;
                case "2":
                    if(groupType == GroupType.SECONDTHIRD)
                        gradescore = Quality.PERFECT;
                    else if(groupType == GroupType.KFIRST)
                        gradescore = Quality.OK;
                    break;
                case "3":
                    if(groupType == GroupType.SECONDTHIRD)
                        gradescore = Quality.PERFECT;
                    break;
                case "4":
                    if(groupType == GroupType.FOURTHFIFTH)
                        gradescore = Quality.PERFECT;
                    else if(groupType == GroupType.SECONDTHIRD)
                        gradescore = Quality.OK;
                    break;
                case "5":
                    if(groupType == GroupType.FOURTHFIFTH)
                        gradescore = Quality.PERFECT;
                    break;
                default:
                    gradescore = Quality.BAD;
            }
        }
        return gradescore;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    @Override
    public int compareTo(Group g) {
        int compare = this.getGroupType().compareTo(g.getGroupType());
        if(compare == 0) {
            compare = this.getName().compareTo(g.getName());
        }
        return compare;
    }
}
