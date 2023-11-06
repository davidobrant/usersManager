package org.example;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

public class Utilities {
    public final String REGEX_CHARS = "[a-zA-Z_åäöÅÄÖ]+";
    public final String REGEX_DIGITS = "\\d+";
    public final String REGEX_EMAIL = "^(.+)@(.+)$";

    /**
     * Converts util.Date to sql.Date from String(yyyyMMdd)
     * @param dateString Date in format (yyyyMMdd)
     * @return Date sql.Date-object
     */
    public Date createSqlDate(String dateString) {
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyyMMdd").parse(dateString);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates input by format to be correctly given date
     * @param input - Input from user
     * @param format - Format of input given to user
     * @throws InputMismatchException Throws specified error message up one level
     */
    public void validateDate(String input, String format) throws InputMismatchException, ParseException {
        if ((input.length() != format.length()) || !input.matches(REGEX_DIGITS)) throw new InputMismatchException();
        if (!isYear(Integer.parseInt(input.substring(0,4)))) throw new InputMismatchException("Year must be withing range of 0 AD to " + LocalDateTime.now().getYear());
        if (!isMonth(Integer.parseInt(input.substring(4,6)))) throw new InputMismatchException("Month must be within range of 1 to 12");
        if (!isDay(Integer.parseInt(input.substring(6,8)))) throw new InputMismatchException("Day must be within range of 1 to 31");
        if (isFuture(input)) throw new InputMismatchException("Holy S#1t!? You're from the future? Wow!!!");
    }
    private boolean isYear(int n) {
        return n <= LocalDateTime.now().getYear();
    }
    private boolean isMonth(int n) {
        return 0 < n && n <= 12;
    }
    private boolean isDay(int n) {
        return 0 < n && n <=31;
    }
    private boolean isFuture(String dateString) throws ParseException {
        java.util.Date present = new java.util.Date();
        java.util.Date date = new SimpleDateFormat("yyyyMMdd").parse(dateString);
        return present.compareTo(date) < 0;
    }

    /**
     * Method for extracting MMdd of Date object for comparing dates
     * @param date java.sql.Date object
     * @return string of "MMdd"
     */
    public String getMMddString(Date date) {
        return date.toString().replace("-", "").substring(4, 8);
    }
}
