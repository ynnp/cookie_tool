package com.sandbox.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.sandbox.utils.DateUtils.isValidDate;
import static com.sandbox.utils.DateUtils.toLocalDate;
import static com.sandbox.utils.DateUtils.toLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Tests for DateUtils class.
 */
public class DateUtilsTest {
    @ParameterizedTest
    @ValueSource(strings = {"2018-12-09"})
    void isValidate_validDate_returnsTrue(String date) {
        assertTrue(isValidDate(date));
    }

    @ParameterizedTest
    @ValueSource(strings = {"09-12-2018", "abcd", "0000-00-00", "2018-13-01", "2018-12-66", "2018-02-31"})
    void isValidate_invalidDate_returnsFalse(String date) {
        assertFalse(isValidDate(date));
    }

    @ParameterizedTest
    @MethodSource("validDateAndLocalDateProvider")
    void toLocalDate_validDate_returnsLocalDate(String date, LocalDate expectedLocalDate) {
        assertEquals(0, toLocalDate(date).compareTo(expectedLocalDate));
    }

    static Stream<Arguments> validDateAndLocalDateProvider() {
        return Stream.of(
                arguments("2018-12-09", LocalDate.of(2018, 12, 9))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"09-12-2018", "abcd", "0000-00-00", "2018-13-01", "2018-12-66", "2018-02-31"})
    void toLocalDate_invalidDate_throwsDateTimeParseException(String date) {
        assertThrows(DateTimeException.class, () -> toLocalDate(date));
    }

    @ParameterizedTest
    @MethodSource("validDateAndLocalDateTimeProvider")
    void toLocalDateTime_validDate_returnsLocalDateTime(String date, LocalDateTime expectedLocalDateTime) {
        assertEquals(0, toLocalDateTime(date).compareTo(expectedLocalDateTime));
    }

    static Stream<Arguments> validDateAndLocalDateTimeProvider() {
        return Stream.of(
                arguments("2018-12-09T14:19:00+00:00", LocalDateTime.of(2018, 12, 9, 14, 19, 0))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"09-12-2018", "abcd", "0000-00-00", "2018-13-01", "2018-12-66", "2018-02-31"})
    void toLocalDateTime_invalidDate_throwsDateTimeParseException(String date) {
        assertThrows(DateTimeException.class, () -> toLocalDateTime(date));
    }
}
