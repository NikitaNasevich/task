package ru.digitalchief.task.util.time;

import ru.digitalchief.task.exeption.ParseException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeFormatter {
    private static final String pattern = "dd-MM-yyyy HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

    public static LocalDateTime formatStringToDateTime(@NotNull String dateTime) {
        int dateLengthWithoutHoursAndMinutes = 10;
        LocalDateTime localDateTime;

        if (dateTime.length() == dateLengthWithoutHoursAndMinutes) {
            dateTime += " 00:00";
        }

        try {
            localDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException exception) {
            throw new ParseException("Date and time must match the pattern: " + pattern);
        }

        return localDateTime;
    }

    public static String formatLocalDateTimeToString(@NotNull LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }
}
