package com.ars.alpha.exception;

public class TestStudentStoredProcedureException extends Exception {

    public int errorCode;
    public String errorMessage;

    public TestStudentStoredProcedureException (int code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

}
