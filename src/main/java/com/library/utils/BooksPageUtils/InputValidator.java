package com.library.utils.BooksPageUtils;

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
