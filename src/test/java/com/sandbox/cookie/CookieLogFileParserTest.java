package com.sandbox.cookie;

import com.sandbox.model.Cookie;
import com.sandbox.model.CookieLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.sandbox.cookie.CookieLogFileParser.COMMA_DELIMITER;
import static com.sandbox.cookie.CookieLogFileParser.parseFileOfCookies;
import static com.sandbox.utils.DateUtils.toLocalDateTime;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Tests for CookieLogFileParser class.
 */
public class CookieLogFileParserTest {
    private static final String VALID_COOKIE_LOG_FILENAME = "cookie_log_valid.csv";
    private static final String INVALID_COOKIE_LOG_FILENAME = "cookie_log_invalid.csv";
    private static final String NONEXISTENT_FILENAME = "abcd";
    private static final String VALID_COOKIE_LOG_ENTRY_1 = "cookie,timestamp";
    private static final String VALID_COOKIE_LOG_ENTRY_2 = "AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00";
    private static final String VALID_COOKIE_LOG_ENTRY_3 = "SAZuXPGUrfbcn5UA,2018-12-08T22:03:00+00:00";
    private static final String VALID_COOKIE_LOG_ENTRY_4 = "4sMM2LxV07bPJzwf,2018-12-07T23:30:00+00:00";
    private static final List<String> VALID_COOKIE_LOG_ENTRIES = Arrays.asList(VALID_COOKIE_LOG_ENTRY_1,
            VALID_COOKIE_LOG_ENTRY_2, VALID_COOKIE_LOG_ENTRY_3, VALID_COOKIE_LOG_ENTRY_4);
    private static final String INVALID_COOKIE_LOG_ENTRY_1 = "AtY0laUfhglK3lC7,09-12-2018";
    private static final List<String> INVALID_COOKIE_LOG_ENTRIES = Arrays.asList(VALID_COOKIE_LOG_ENTRY_1,
            INVALID_COOKIE_LOG_ENTRY_1);
    @TempDir
    private static File temporaryDirectory;

    @ParameterizedTest
    @MethodSource("validCookieLogsAndFileProvider")
    void parseFileOfCookies_validCookieLogFile_returnsCookieLogList(List<CookieLog> validCookieLogs, File validCookieLogFile) {
        assertThat(parseFileOfCookies(validCookieLogFile), containsInAnyOrder(validCookieLogs.toArray()));
    }

    static Stream<Arguments> validCookieLogsAndFileProvider() throws IOException {
        File validCookieLogFile = new File(temporaryDirectory, VALID_COOKIE_LOG_FILENAME);
        Files.write(validCookieLogFile.toPath(), VALID_COOKIE_LOG_ENTRIES);
        List<CookieLog> validCookieLogs = new ArrayList<>();
        for (int i = 1; i < VALID_COOKIE_LOG_ENTRIES.size(); i++) {
            String[] expectedValues = VALID_COOKIE_LOG_ENTRIES.get(i).split(COMMA_DELIMITER);
            validCookieLogs.add(new CookieLog(new Cookie(expectedValues[0]), toLocalDateTime(expectedValues[1])));
        }
        return Stream.of(
                arguments(validCookieLogs, validCookieLogFile)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCookieLogFileProvider")
    void parseFileOfCookies_cookieLogEntryWithInvalidTimestamp_throwsDateTimeException(File invalidCookieLogFile) {
        assertThrows(DateTimeException.class, () -> parseFileOfCookies(invalidCookieLogFile));
    }

    static Stream<Arguments> invalidCookieLogFileProvider() throws IOException {
        File invalidCookieLogFile = new File(temporaryDirectory, INVALID_COOKIE_LOG_FILENAME);
        Files.write(invalidCookieLogFile.toPath(), INVALID_COOKIE_LOG_ENTRIES);
        return Stream.of(
                arguments(invalidCookieLogFile)
        );
    }

    @Test
    void parseFileOfCookies_nullAsCookieLogFile_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parseFileOfCookies(null));
    }

    @ParameterizedTest
    @MethodSource("nonexistentCookieLogFileProvider")
    void parseFileOfCookies_nonexistentCookieLogFile_throwsIllegalArgumentException(File nonexistentCookieLogFile) {
        assertThrows(IllegalArgumentException.class, () -> parseFileOfCookies(nonexistentCookieLogFile));
    }

    static Stream<Arguments> nonexistentCookieLogFileProvider() {
        File nonexistentCookieLogFile = new File(temporaryDirectory, NONEXISTENT_FILENAME);
        return Stream.of(
                arguments(nonexistentCookieLogFile)
        );
    }

    @Test
    void parseFileOfCookies_directoryAsCookieLogFile_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> parseFileOfCookies(temporaryDirectory));
    }
}
