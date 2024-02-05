package com.sandbox;

import com.sandbox.cookie.CookieStatsAnalyser;
import com.sandbox.model.Cookie;
import com.sandbox.model.CookieLog;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;
import picocli.CommandLine.ParameterException;

import java.io.File;
import java.util.List;

import static com.sandbox.cookie.CookieLogFileParser.parseFileOfCookies;
import static com.sandbox.utils.DateUtils.isValidDate;
import static com.sandbox.utils.DateUtils.toLocalDate;

/**
 * This application is CLI that allows to get the most active cookie for a specific day.
 *
 * It requires two arguments to start up:
 * 1. Path to CSV file with cookie logs.
 * 2. Date for which most active cookies are requested.
 *
 * Then, it will process cookie log file and return list of available cookies.
 */
@Command(
        name = "cookie",
        description = "Gets most active cookie from input file by input date."
)
public class CookieCli implements Runnable {
    @Option(names = {"-f", "--file"}, description = "Filename should be in CSV format.", required = true)
    private static String fileName;
    @Option(names = {"-d", "--date"}, description = "Date should be in yyyy-MM-dd format.", required = true)
    private static String date;
    @Spec
    CommandSpec spec;

    public static void main(String[] args) {
        new CommandLine(new CookieCli()).execute(args);
    }

    @Override
    public void run() {
        if (!fileName.endsWith(".csv")) {
            throw new ParameterException(spec.commandLine(), "Enter valid filename in CSV format.");
        }
        File cookieLogFile = new File(fileName);
        if (!cookieLogFile.exists() || !cookieLogFile.isFile()) {
            throw new ParameterException(spec.commandLine(), "Enter valid filename in CSV format.");
        }
        if (!isValidDate(date)) {
            throw new ParameterException(spec.commandLine(), "Enter valid date in yyyy-MM-dd format.");
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
