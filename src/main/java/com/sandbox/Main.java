package com.sandbox;

import com.sandbox.cookie.CookieStatsAnalyser;
import com.sandbox.model.Cookie;
import com.sandbox.model.CookieLog;

import java.io.File;
import java.util.List;

import static com.sandbox.cookie.CookieLogFileParser.parseFileOfCookies;
import static com.sandbox.utils.DateUtils.isValidDate;
import static com.sandbox.utils.DateUtils.toLocalDate;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            throw new IllegalArgumentException("Provide path to CSV file and date for which to get most recent " +
                    "cookies.\nValid format: <filename> <date>");
        }

        String fileName = args[0];
        if (!fileName.endsWith(".csv")) {
            throw new IllegalArgumentException("Enter valid filename in CSV format.");
        }
        File cookieLogFile = new File(fileName);
        if (!cookieLogFile.exists() || !cookieLogFile.isFile()) {
            throw new IllegalArgumentException("Enter valid filename in CSV format.");
        }

        String date = args[1];
        if (!isValidDate(date)) {
            throw new IllegalArgumentException("Enter valid date in yyyy-MM-dd format.");
        }

        List<CookieLog> cookieLogs = parseFileOfCookies(cookieLogFile);
        CookieStatsAnalyser cookieStatsAnalyser = new CookieStatsAnalyser(cookieLogs);
        List<Cookie> mostActiveCookies = cookieStatsAnalyser.getMostActiveCookies(toLocalDate(date));
        if (!mostActiveCookies.isEmpty()) {
            for (Cookie cookie: mostActiveCookies) {
                System.out.println(cookie.getValue());
            }
        } else {
            System.out.println("No cookies available for " + date + " date.");
        }
    }
}
