package org.example.builderpattern;

import java.util.ArrayList;
import java.util.List;

public class MBAStudentBuilder extends StudentBuilder {
    @Override
    public StudentBuilder setSubjects() {
        List<String> subjects = new ArrayList<>();
        subjects.add("Micro Economics");
        subjects.add("Business Studies");
        subjects.add("Operations Management");
        subjects.add("Accounting");

        this.subjects = subjects;
        return this;

    }
}
