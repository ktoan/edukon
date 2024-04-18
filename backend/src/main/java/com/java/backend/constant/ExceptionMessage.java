package com.java.backend.constant;

/**
 * @author Toan Nguyen Khanh
 * @version 1.0
 */
public class ExceptionMessage {
    /**
     * Bad request message
     */
    public static final String REQUIRED_EMAIL = "Please fill out your email!";
    public static final String VALID_EMAIL = "Email is invalid!";
    public static final String REQUIRED_NAME = "Please fill out your name!";
    public static final String REQUIRED_PASSWORD = "Please fill out your name!";
    public static final String MIN_PASSWORD = "Password must be greater than 6 characters!";
    public static final String REQUIRED_GENDER = "Please fill out your gender!";
    public static final String VALID_GENDER = "Gender is invalid!";

    /**
     * Service exception message
     */
    public static final String EMAIL_EXISTS = "Email has been taken!";
    public static final String EMAIL_NOT_FOUND = "Email isn't existed in out system!";
    public static final String LOGIN_FAILED = "Username/Password is invalid!";
    public static final String USER_BLOCKED = "User is blocked by admin, please contact admin+001@edukon.com.vn to open.";
    public static final String USER_UNABLE = "User is unable, please confirm your account.";
    public static final String EXECUTING_ERROR = "Operation occurs some problem";
}
