package com.epam.eighty.exception;

public class QuestionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public QuestionNotFoundException(final Long id) {
        super("Question with id=" + id + " is not found");
    }

}
