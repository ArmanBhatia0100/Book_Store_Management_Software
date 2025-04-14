package com.library.view.utils;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUpdater {
    final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");

    public static String getDateAndTime() {

        String time = LocalTime.now().format(timeFormatter);
        String date = LocalDate.now().format(dateFormatter);
        String day = LocalDate.now().getDayOfWeek().toString().toLowerCase();
        String camelCaseDay = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();

        String dateTimeString = (time + " | " + camelCaseDay + " | " + date);

        return dateTimeString;
    }

    public static void startDateTimeUpdate(JLabel label) {
        new Timer(1000, e -> label.setText(getDateAndTime())).start();
    }
}
