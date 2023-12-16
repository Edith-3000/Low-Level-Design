package org.example.builderpattern;

import java.util.ArrayList;
import java.util.List;

public class EngineeringStudentBuilder extends StudentBuilder {
    @Override
    public StudentBuilder setSubjects() {
        List<String> subjects = new ArrayList<>();
        subjects.add("DSA");
        subjects.add("OS");
        subjects.add("Computer Architecture");
        subjects.add("Compiler Design");

        this.subjects = subjects;
        return this;
    }
}
