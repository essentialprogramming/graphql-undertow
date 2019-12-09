package com.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class StudentData {

    private static Student student1 = new Student(
            "1",
            "Ion",
            "Pop"
    );

    private static Student student2 = new Student(
            "2",
            "Maria",
            "Popescu"
    );

    private static Map<String, Student> students = new LinkedHashMap<>();

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
