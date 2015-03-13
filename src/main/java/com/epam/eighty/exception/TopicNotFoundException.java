package com.epam.eighty.exception;

import javax.servlet.http.HttpServletResponse;

public class TopicNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    private HttpServletResponse response;

    public TopicNotFoundException(final Long id, final HttpServletResponse response) {
        super("Topic with id=" + id + " is not found");
        this.response = response;
    }

    public TopicNotFoundException(final HttpServletResponse response) {
        super("Root topic is not found");
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

}