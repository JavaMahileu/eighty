package com.epam.eighty.utility;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class TraceLogger {

    @Before("execution(public * com.epam.eighty.web.api.*.*(..))")
    public void log(JoinPoint point) {
        final Logger LOG = LoggerFactory.getLogger(point.getTarget().getClass());
        Object[] arguments = point.getArgs();
        StringBuilder message = new StringBuilder();

        message.append(point.getSignature().getName()).
        append(" called");
        if(arguments.length > 1) {
            message.append(" with arguments: ");
        }
        else if(arguments.length == 1) {
            message.append(" with argument: ");
        }
        for (int i = 0; i < arguments.length; i++) {
            message.append(arguments[i].toString());
            if(i != arguments.length - 1) {
                message.append(", ");
            }
        }
        LOG.trace(message.toString());
    }

}