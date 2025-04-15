package com.library.view.utils;

public class InputValidator {

    public static boolean emptyInputChecker(String... fields) {

        for (String field : fields) {
            if (field.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
