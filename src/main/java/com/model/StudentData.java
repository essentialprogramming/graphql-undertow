package com.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class StudentData {

    static Student student1 = new Student(
            "1",
            "Ion",
            "Pop"
    );

    static Student student2 = new Student(
            "2",
            "Maria",
            "Popescu"
    );

    static Map<String, Student> students = new LinkedHashMap<>();

    static {
        students.put("1", student1);
        students.put("2", student2);
    }

    public static Object getStudentData(String id) {
        if (students.get(id) != null) {
            return students.get(id);
        }
        return null;
    }


}
