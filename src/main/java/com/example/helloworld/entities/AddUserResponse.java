package com.example.helloworld.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vaidyanathan.s on 16/05/15.
 */
public class AddUserResponse {
    @JsonProperty("verificationCode")
    private String verificationCode;

    @JsonProperty("userID")
    private String userID;

    public AddUserResponse() {
        // Jackson deserialization
    }

    @JsonCreator
    public AddUserResponse(@JsonProperty("verificationCode") String verificationCode, @JsonProperty("userID") String userID) {
        this.userID = userID;
        this.verificationCode = verificationCode;
    }
}
