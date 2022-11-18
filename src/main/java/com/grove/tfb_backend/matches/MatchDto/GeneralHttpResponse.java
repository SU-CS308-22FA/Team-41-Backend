package com.grove.tfb_backend.matches.MatchDto;

public class GeneralHttpResponse<T> {
    private String status;
    private T returnObject;  // for GET calls.

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(T returnObject) {
        this.returnObject = returnObject;
    }

    public GeneralHttpResponse(String status, T returnObject) {
        this.status = status;
        this.returnObject = returnObject;
    }
}

