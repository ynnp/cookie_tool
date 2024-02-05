package com.sandbox.cookie;

import com.sandbox.model.Cookie;
import com.sandbox.model.CookieLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Analyser of cookie statistics.
 */
public class CookieStatsAnalyser {
    private final Map<LocalDate, List<CookieLog>> cookiesByDate = new HashMap<>();

    /**
     * Constructor to initialize analyser with list of CookieLog.
     *
     * @param cookieLogs list of cookie logs to work with.
     */
    public CookieStatsAnalyser(List<CookieLog> cookieLogs) {
        if (cookieLogs != null && !cookieLogs.isEmpty()) {
            for (CookieLog cookieLog: cookieLogs) {
                addCookieLog(cookieLog);
            }
        }
    }

    /**
     * Gets list of Cookie by specific date.
     *
     * @param date date for which to get cookies.
     * @return list of cookies filtered by input date.
     */
    public List<Cookie> getCookiesByDate(LocalDate date) {
        if (!cookiesByDate.containsKey(date)) return new ArrayList<>();

        return cookiesByDate.get(date).stream().map(CookieLog::getCookie).toList();
    }

    /**
     * Adds CookieLog entry for further use in calculating statistics.
     *
     * @param cookieLog CookieLog entry to be added.
     */
    public void addCookieLog(CookieLog cookieLog) {
        LocalDate date = cookieLog.getTimestamp().toLocalDate();
        cookiesByDate.computeIfAbsent(date, k -> new ArrayList<>());
        cookiesByDate.get(date).add(cookieLog);
    }

    /**
     * Gets most active cookies by specific date.
     *
     * @param date date for which to get most active cookies.
     * @return list of most active cookies.
     */
    public List<Cookie> getMostActiveCookies(LocalDate date) {
        List<Cookie> cookies = getCookiesByDate(date);
        if (cookies == null || cookies.isEmpty()) {
            return new ArrayList<>();
        }
        return getMostActiveCookies(getCookiesFrequency(cookies));
    }

    private static Map<Cookie, Integer> getCookiesFrequency(List<Cookie> cookies) {
        Map<Cookie, Integer> cookiesFrequency = new HashMap<>();
        for (Cookie cookie: cookies) {
            cookiesFrequency.put(cookie, cookiesFrequency.getOrDefault(cookie, 0) + 1);
        }
        return cookiesFrequency;
    }

    private static List<Cookie> getMostActiveCookies(Map<Cookie, Integer> cookiesFrequency) {
        PriorityQueue<Cookie> cookiesMaxHeap = buildCookiesMaxHeap(cookiesFrequency);
        List<Cookie> mostActiveCookies = new ArrayList<>();
        Integer maxFrequency = cookiesFrequency.get(cookiesMaxHeap.peek());
        while (!cookiesMaxHeap.isEmpty() && cookiesFrequency.get(cookiesMaxHeap.peek()).equals(maxFrequency)) {
            mostActiveCookies.add(cookiesMaxHeap.poll());
        }
        return mostActiveCookies;
    }

    private static PriorityQueue<Cookie> buildCookiesMaxHeap(Map<Cookie, Integer> cookiesFrequency) {
        PriorityQueue<Cookie> queue = new PriorityQueue<>((a, b) -> cookiesFrequency.get(b) - cookiesFrequency.get(a));
        for (Cookie cookie: cookiesFrequency.keySet()) {
            queue.offer(cookie);
        }
        return queue;
    }
}
