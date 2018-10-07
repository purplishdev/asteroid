package com.asteroid.net.exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException() {
        super();
    }

    public ConnectionException(String s) {
        super(s);
    }

    public ConnectionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ConnectionException(Throwable throwable) {
        super(throwable);
    }
}
