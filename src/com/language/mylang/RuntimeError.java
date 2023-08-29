package com.language.mylang;

public class RuntimeError extends RuntimeException {
    final Token token;
    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }
}
