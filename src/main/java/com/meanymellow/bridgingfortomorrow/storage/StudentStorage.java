package com.meanymellow.bridgingfortomorrow.storage;

import com.meanymellow.bridgingfortomorrow.Util;
import com.meanymellow.bridgingfortomorrow.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StudentStorage {

    final private List<Student> students = new ArrayList<>();

    public Page<Student> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Student> list;

        if (students.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, students.size());
            list = students.subList(startItem, toIndex);
        }

        Page<Student> bookPage
                = new PageImpl<Student>(list, PageRequest.of(currentPage, pageSize), students.size());

        return bookPage;
    }

    public void saveAll(List<Student> students) {
        for(Student student : students) {
            this.students.add(Util.cleanUp(student));
        }
        // this.students.addAll(students);
    }

    public List<Student> findAll() {
        return this.students;
    }

    public void removeAll() {
        this.students.clear();
    }
}