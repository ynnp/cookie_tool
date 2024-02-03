package com.sandbox.model;

import java.time.LocalDateTime;

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
}
