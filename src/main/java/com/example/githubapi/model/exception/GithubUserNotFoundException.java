package com.example.githubapi.model.exception;

public class GithubUserNotFoundException extends RuntimeException {
    public GithubUserNotFoundException(String message) {
        super(message);
    }
}
