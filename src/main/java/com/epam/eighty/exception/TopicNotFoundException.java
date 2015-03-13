package com.epam.eighty.exception;

public class TopicNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    public TopicNotFoundException(final Long id) {
        super("Topic with id=" + id + " is not found");
    }

    public TopicNotFoundException() {
        super("Root topic is not found");
    }

}
