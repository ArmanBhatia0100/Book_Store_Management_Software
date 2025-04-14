package com.library.view.utils;

import com.library.database.BookDAOImplementation;

public class BookFindUtil {

    public static void searchBook(String bookInfo) {
        BookDAOImplementation.findBook(bookInfo);
    }

    private static void findBook(String bookInfo) {
    }

    private static void findByTitle(String bookInfo) {
    }

    private static void findByISBN(String bookInfo) {
    }
}
