package com.example.librarymanagementsystem.utils;

import java.time.LocalDate;
import java.util.List;

public final class Util {
    private Util() {
        throw new UnsupportedOperationException("Util class cannot be instantiated");
    }

    public static LocalDate generateDefaultDate() {
        return LocalDate.of(9999, 12, 31);
    }

    public static String generateCommaSeparatedString(List<String> list) {
        if(list == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if(i != (list.size() - 1)) {
                result.append(",");
            }
        }

        return result.toString();
    }
}
