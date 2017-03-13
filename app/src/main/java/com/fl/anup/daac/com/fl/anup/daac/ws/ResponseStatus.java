package com.fl.anup.daac.com.fl.anup.daac.ws;

/**
 * Created by Anup on 3/14/2017.
 */

public enum ResponseStatus{

    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    UPDATEPASSWORD("UPDATEPASSWORD");


    private String status;
    ResponseStatus(String status) {
        this.status = status;
    }
}
