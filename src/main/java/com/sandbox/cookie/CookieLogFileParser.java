package com.sandbox.cookie;

import com.sandbox.model.Cookie;
import com.sandbox.model.CookieLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sandbox.utils.DateUtils.toLocalDateTime;

/**
 * Parser of file with cookie logs.
 */
public class CookieLogFileParser {
    public static final String COMMA_DELIMITER = ",";

    /**
     * Parses file with cookie logs.
     *
     * @param cookieLogFile file with cookie logs to parse.
     * @return list of CookieLog entries.
     * @throws IllegalArgumentException - if input file doesn't exist.
     */
    public static List<CookieLog> parseFileOfCookies(File cookieLogFile) {
        if (cookieLogFile == null || !cookieLogFile.exists() || !cookieLogFile.isFile()) {
            throw new IllegalArgumentException("Provide valid file.");
        }
        List<CookieLog> cookieLogs = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(cookieLogFile))) {
            parseFileOfCookies(bufferedReader, cookieLogs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cookieLogs;
    }

    private static void parseFileOfCookies(BufferedReader bufferedReader, List<CookieLog> cookieLogs) throws IOException {
        bufferedReader.readLine(); // skip header
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] cookieLog = line.split(COMMA_DELIMITER);
            cookieLogs.add(new CookieLog(new Cookie(cookieLog[0]), toLocalDateTime(cookieLog[1])));
        }
    }
}
