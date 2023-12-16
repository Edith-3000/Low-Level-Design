package org.example.builderpattern;

public class Director {
    private final StudentBuilder studentBuilder;

    public Director(StudentBuilder studentBuilder) {
        this.studentBuilder = studentBuilder;
    }

    public Student createStudent() {
        if (studentBuilder instanceof EngineeringStudentBuilder) {
            return createEngineeringStudent();
        } else if (studentBuilder instanceof MBAStudentBuilder) {
            return createMBAStudent();
        }

        return null;
    }

    private Student createEngineeringStudent() {
        return studentBuilder.setRollNumber(1).setAge(23).setName("Kapil").setSubjects().build();
    }

    private Student createMBAStudent() {
        return studentBuilder.setRollNumber(2).setAge(24).setName("Ashutosh").setFatherName("Venkat").setMotherName("Shakuntla").setSubjects().build();
    }
}
