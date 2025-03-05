package com.luv2code.springsecurity.demo.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    // Converts a date string from "yyyy-MM-dd" to "MM-dd-yyyy"
    public static String convertDate(String date) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            // Parse the input string and format it to the desired output format
            LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
            return parsedDate.format(outputFormatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

