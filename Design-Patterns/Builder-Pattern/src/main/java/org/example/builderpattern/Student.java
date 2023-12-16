package org.example.builderpattern;

import java.util.List;

public class Student {
    private final int rollNumber;
    private final int age;
    private final String name;
    private final String motherName;
    private final String fatherName;
    private final List<String> subjects;

    public Student(StudentBuilder studentBuilder) {
        this.rollNumber = studentBuilder.rollNumber;
        this.age = studentBuilder.age;
        this.name = studentBuilder.name;
        this.motherName = studentBuilder.motherName;
        this.fatherName = studentBuilder.fatherName;
        this.subjects = studentBuilder.subjects;
    }

    @Override
    public String toString() {
        return "Roll Number: " + this.rollNumber + " " +
                "Age: " + this.age + " " +
                "Name: " + this.name + " " +
                "Mother's Name: " + this.motherName + " " +
                "Father's Name: " + this.fatherName + " " +
                "Subjects: " + subjects.get(0) + ", " + subjects.get(1) + ", " + subjects.get(2) + ", " + subjects.get(3);
    }
}
