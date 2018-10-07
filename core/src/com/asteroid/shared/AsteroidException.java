package com.asteroid.shared;

public class AsteroidException extends RuntimeException {

    public AsteroidException() {
    }

    public AsteroidException(String s) {
        super(s);
    }

    public AsteroidException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AsteroidException(Throwable throwable) {
        super(throwable);
    }
}