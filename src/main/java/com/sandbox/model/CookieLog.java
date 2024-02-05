package com.sandbox.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents log entry from cookie log file.
 */
public class CookieLog {
    private Cookie cookie;
    private LocalDateTime timestamp;

    public CookieLog(Cookie cookie, LocalDateTime timestamp) {
        this.cookie = cookie;
        this.timestamp = timestamp;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CookieLog cookieLog = (CookieLog) o;
        return Objects.equals(cookie, cookieLog.cookie) && Objects.equals(timestamp, cookieLog.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookie, timestamp);
    }
}
