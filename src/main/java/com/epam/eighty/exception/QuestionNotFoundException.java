package com.epam.eighty.exception;

import javax.servlet.http.HttpServletResponse;


public class QuestionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;

    public QuestionNotFoundException(final Long id, final HttpServletResponse response) {
        super("Question with id=" + id + " is not found");
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return response;
    }


}
