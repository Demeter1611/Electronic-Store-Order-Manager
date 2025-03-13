package com.example.proiectlab4.repository;

public class DuplicateEntityException extends RepositoryException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}
