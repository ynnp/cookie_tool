package com.sandbox.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Utility class to work with dates.
 */
public class DateUtils {
    private static final String DATE_FORMAT = "uuuu-MM-dd";
    private static final String TIMESTAMP_FORMAT = "uuuu-MM-dd'T'HH:mm:ssXXX";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT).withResolverStyle(ResolverStyle.STRICT);

    /**
     * Verifies whether input date is in uuuu-MM-dd format.
     *
     * @param date date to validate.
     * @return true if date is in uuuu-MM-dd format, false otherwise.
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDate unused = toLocalDate(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Converts date from String to LocalDate.
     *
     * @param date date in uuuu-MM-dd format to be converted.
     * @return date in LocalDate format.
     * @throws DateTimeParseException - if input date can't be parsed.
     */
    public static LocalDate toLocalDate(String date) throws DateTimeParseException {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * Converts date from String to LocalDateTime.
     *
     * @param timestamp timestamp in uuuu-MM-dd'T'HH:mm:ssXXX format to be converted.
     * @return timestamp in LocalDateTime format.
     * @throws DateTimeParseException - if input date can't be parsed.
     */
    public static LocalDateTime toLocalDateTime(String timestamp) throws DateTimeParseException {
        return LocalDateTime.parse(timestamp, TIMESTAMP_FORMATTER);
    }
}
