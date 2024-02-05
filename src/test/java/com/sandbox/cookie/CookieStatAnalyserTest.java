package com.sandbox.cookie;

import com.sandbox.model.Cookie;
import com.sandbox.model.CookieLog;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Tests for CookieStatAnalyser class.
 */
public class CookieStatAnalyserTest {
    private static final String COOKIE_VALUE_1 = "AtY0laUfhglK3lC7";
    private static final String COOKIE_VALUE_2 = "SAZuXPGUrfbcn5UA";
    private static final String COOKIE_VALUE_3 = "5UAVanZf6UtGyKVS";
    private static final LocalDateTime VALID_DATE_1 = LocalDateTime.of(2018, 12, 8, 13, 12, 13);
    private static final LocalDateTime VALID_DATE_2 = LocalDateTime.of(2018, 12, 7, 14, 56, 2);
    private static final LocalDateTime VALID_DATE_3 = LocalDateTime.of(2018, 12, 6, 18, 37, 57);
    private static final LocalDate INVALID_DATE = LocalDate.of(2019, 12, 1);
    private static CookieStatsAnalyser cookieStatsAnalyser;

    @BeforeAll
    static void initCookieStatAnalyser() {
        // date1 - 3/3 unique cookie, date2 - 2/3 unique cookie, date3 1/1 unique cookie
        List<CookieLog> cookieLogs = new ArrayList<>();
        cookieLogs.add(new CookieLog(new Cookie(COOKIE_VALUE_1), VALID_DATE_1));
        cookieLogs.add(new CookieLog(new Cookie(COOKIE_VALUE_2), VALID_DATE_1));
        cookieLogs.add(new CookieLog(new Cookie(COOKIE_VALUE_3), VALID_DATE_1));
        cookieLogs.add(new CookieLog(new Cookie(COOKIE_VALUE_1), VALID_DATE_2));
        cookieLogs.add(new CookieLog(new Cookie(COOKIE_VALUE_2), VALID_DATE_2));
        cookieLogs.add(new CookieLog(new Cookie(COOKIE_VALUE_2), VALID_DATE_2));
        cookieLogs.add(new CookieLog(new Cookie(COOKIE_VALUE_1), VALID_DATE_3));
        cookieStatsAnalyser = new CookieStatsAnalyser(cookieLogs);
    }

    @ParameterizedTest
    @MethodSource("cookiesAndDateProvider")
    void getCookiesByDate_validDate_returnsListOfCookiesFilteredByDate(List<Cookie> cookie, LocalDate date) {
        assertThat(cookieStatsAnalyser.getCookiesByDate(date), containsInAnyOrder(cookie.toArray()));
    }

    static Stream<Arguments> cookiesAndDateProvider() {
        return Stream.of(
                arguments(Arrays.asList(new Cookie(COOKIE_VALUE_1), new Cookie(COOKIE_VALUE_2), new Cookie(COOKIE_VALUE_3)), VALID_DATE_1.toLocalDate()),
                arguments(Arrays.asList(new Cookie(COOKIE_VALUE_1), new Cookie(COOKIE_VALUE_2), new Cookie(COOKIE_VALUE_2)), VALID_DATE_2.toLocalDate()),
                arguments(List.of(new Cookie(COOKIE_VALUE_1)), VALID_DATE_3.toLocalDate())
        );
    }

    @Test
    void getCookiesByDate_invalidDate_returnsEmptyList() {
        assertEquals(new ArrayList<>(), cookieStatsAnalyser.getCookiesByDate(INVALID_DATE));
    }

    @Test
    void getCookiesByDate_nullAsDate_returnsEmptyList() {
        assertEquals(new ArrayList<>(), cookieStatsAnalyser.getCookiesByDate(null));
    }

    @ParameterizedTest
    @MethodSource("mostActiveCookiesAndDateProvider")
    void getMostActiveCookies_validDate_returnsListOfMostActiveCookiesByDate(List<Cookie> cookies, LocalDate date) {
        assertThat(cookieStatsAnalyser.getMostActiveCookies(date), containsInAnyOrder(cookies.toArray()));
    }

    static Stream<Arguments> mostActiveCookiesAndDateProvider() {
        return Stream.of(
                arguments(Arrays.asList(new Cookie(COOKIE_VALUE_1), new Cookie(COOKIE_VALUE_2), new Cookie(COOKIE_VALUE_3)), VALID_DATE_1.toLocalDate()),
                arguments(List.of(new Cookie(COOKIE_VALUE_2)), VALID_DATE_2.toLocalDate()),
                arguments(List.of(new Cookie(COOKIE_VALUE_1)), VALID_DATE_3.toLocalDate())
        );
    }

    @Test
    void getMostActiveCookies_invalidDate_returnsEmptyList() {
        assertEquals(new ArrayList<>(), cookieStatsAnalyser.getMostActiveCookies(INVALID_DATE));
    }

    @Test
    void getMostActiveCookies_nullAsDate_returnsEmptyList() {
        assertEquals(new ArrayList<>(), cookieStatsAnalyser.getMostActiveCookies(null));
    }

    @Test
    void getMostActiveCookies_cookieStatsAnalyserWithEmptyLogs_returnsEmptyList() {
        CookieStatsAnalyser cookieStatsAnalyser1 = new CookieStatsAnalyser(new ArrayList<>());
        assertEquals(new ArrayList<>(), cookieStatsAnalyser1.getMostActiveCookies(VALID_DATE_1.toLocalDate()));
    }

    @Test
    void getMostActiveCookies_cookieStatsAnalyserWithNullAsLogs_returnsEmptyList() {
        CookieStatsAnalyser cookieStatsAnalyser1 = new CookieStatsAnalyser(null);
        assertEquals(new ArrayList<>(), cookieStatsAnalyser1.getMostActiveCookies(VALID_DATE_1.toLocalDate()));
    }
}
