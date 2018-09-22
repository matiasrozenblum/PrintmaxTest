package com.example.matirozen.printmaxtest.model;

public class CheckUserResponse {
    private boolean exists;

    public CheckUserResponse(){

    }

    public CheckUserResponse(boolean exists) {
        this.exists = exists;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    @Override
    public String toString() {
        return "CheckUserResponse{" +
                "exists=" + exists +
                '}';
    }
}
