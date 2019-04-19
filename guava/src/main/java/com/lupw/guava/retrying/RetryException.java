package com.lupw.guava.retrying;

/**
 * @author v_pwlu 2019/3/8
 */
public class RetryException extends RuntimeException {

    public RetryException(String message) {
        super(message);
    }
}
