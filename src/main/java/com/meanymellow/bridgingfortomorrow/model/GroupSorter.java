package com.meanymellow.bridgingfortomorrow.model;

import com.meanymellow.bridgingfortomorrow.storage.StudentStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GroupSorter {
    private List<Student> students;
    private List<String> schools;
    private int roughNumGroups;

    private Group badGroup;

    private List<Group> okGroups;
    private List<Group> perfectGroups;
    private List<Group> incompleteGroups;

    public GroupSorter() {
        okGroups = new ArrayList<>();
        perfectGroups = new ArrayList<>();
        incompleteGroups = new ArrayList<>();
        badGroup = new Group();
    }

    public void addStudents(StudentStorage studentStorage) {
        this.students = studentStorage.getAll();
        this.schools = new ArrayList<>(studentStorage.getAllSchools().keySet());
    }

    public void sort() {
        int i = 0;
        do {
            trySort();
            students.clear();
            for (Group group : incompleteGroups) {
                students.addAll(group.getStudents());
            }
            incompleteGroups.clear();
            students.addAll(badGroup.getStudents());
            badGroup = new Group();
            i++;
        } while (i < students.size()+3);
        trySort();
    }

    public void trySort() {
        roughNumGroups = students.size()/6;
        for(int j = 0; j < roughNumGroups+1; j++) {
            Collections.shuffle(students);
            Iterator<Student> i = students.iterator();
            while (i.hasNext()) {
                Student student = i.next();

                if (tryAddStudent(student)) {
                    i.remove();
                    System.out.println("Added student: " + student);
                }
            }
        }

        Iterator<Student> i = students.iterator();
        while(i.hasNext()) {
            Student student = i.next();
            forceAddStudent(student);
            i.remove();
        }
    }

    private boolean tryAddStudent(Student student) {
        if(incompleteGroups.size() == 0) {
            System.out.println("Created new group with student: " + student);
            Group newGroup = new Group();
            newGroup.tryAddStudent(student);
            incompleteGroups.add(newGroup);
            return true;
        }

        Iterator<Group> i = incompleteGroups.iterator();
        while(i.hasNext()) {
            Group group = i.next();

            if(group.tryAddStudent(student)) {
                Quality groupQuality = group.checkQuality();
                if(groupQuality == Quality.GOOD || groupQuality == Quality.OK) {
                    okGroups.add(group);
                    i.remove();
                } else if(groupQuality == Quality.PERFECT) {
                    perfectGroups.add(group);
                    i.remove();
                }
                return true;
            }
        }

        if(incompleteGroups.size() + okGroups.size() + perfectGroups.size() < roughNumGroups) {
            System.out.println("Created new group with student: " + student);
            Group newGroup = new Group();
            newGroup.tryAddStudent(student);
            incompleteGroups.add(newGroup);
            return true;
        }

        return false;
    }

    private void forceAddStudent(Student student) {
        // Try to gently add student.
        if(tryAddStudent(student)) {
            return;
        }

        Iterator<Group> i = okGroups.iterator();
        while(i.hasNext()) {
            Group group = i.next();

            if(group.tryAddStudent(student)) {
                Quality groupQuality = group.checkQuality();
                if(groupQuality == Quality.PERFECT) {
                    perfectGroups.add(group);
                    i.remove();
                }
                return;
            }
        }

        i = perfectGroups.iterator();
        while(i.hasNext()) {
            Group group = i.next();

            if(group.tryAddStudent(student)) {
                Quality groupQuality = group.checkQuality();
                if(groupQuality == Quality.GOOD || groupQuality == Quality.OK) {
                    okGroups.add(group);
                    i.remove();
                }
                return;
            }
        }

        // Add student if possible
        i = incompleteGroups.iterator();
        while(i.hasNext()) {
            Group group = i.next();

            if(group.canAddStudent(student)) {
                group.forceAddStudent(student);
                Quality groupQuality = group.checkQuality();
                if(groupQuality == Quality.GOOD || groupQuality == Quality.OK) {
                    okGroups.add(group);
                    i.remove();
                } else if(groupQuality == Quality.PERFECT) {
                    perfectGroups.add(group);
                    i.remove();
                }
                return;
            }
        }

        i = okGroups.iterator();
        while(i.hasNext()) {
            Group group = i.next();

            if(group.canAddStudent(student)) {
                group.forceAddStudent(student);
                Quality groupQuality = group.checkQuality();
                if(groupQuality == Quality.PERFECT) {
                    perfectGroups.add(group);
                    i.remove();
                }
                return;
            }
        }

        i = perfectGroups.iterator();
        while(i.hasNext()) {
            Group group = i.next();

            if(group.canAddStudent(student)) {
                group.forceAddStudent(student);
                Quality groupQuality = group.checkQuality();
                if(groupQuality == Quality.GOOD || groupQuality == Quality.OK) {
                    okGroups.add(group);
                    i.remove();
                }
                return;
            }
        }

        System.out.println("Added student to bad group: " + student);
        badGroup.forceAddStudent(student);

        return;
    }

    public boolean isGood() {
        return incompleteGroups.isEmpty() && badGroup.size() == 0;
    }

    public List<Group> getGroups() {
        for(Group group : okGroups) {
            group.setName("Ok Group");
        }
        for(Group group : perfectGroups) {
            group.setName("Perfect Group");
        }
        for(Group group : incompleteGroups) {
            group.setName("Incomplete Group");
        }
        badGroup.setName("Bad Group");

        List<Group> groups = new ArrayList<>();
        groups.addAll(okGroups);
        groups.addAll(perfectGroups);
        groups.addAll(incompleteGroups);
        if(badGroup.size() > 0)
            groups.add(badGroup);

        return groups;
    }

    public List<Group> getOkGroups() {
        return okGroups;
    }

    public List<Group> getPerfectGroups() {
        return perfectGroups;
    }
}
