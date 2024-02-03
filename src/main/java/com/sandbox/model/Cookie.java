package com.sandbox.model;

import java.util.Objects;

/**
 * Represents cookie.
 */
public class Cookie {
    private final String value;

    public Cookie(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(value, cookie.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
