package com.example.helloworld.entities;

import com.example.helloworld.entities.core.Request;
import com.example.helloworld.entities.core.Ride;
import com.example.helloworld.entities.core.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Created by vaidyanathan.s on 16/05/15.
 */
public class RequestRideResponse {
    @JsonProperty("request_id")
    private long requestId;

    @JsonProperty("state")
    private String state;

    public RequestRideResponse() {
        // Jackson deserialization
    }

    @JsonCreator
    public RequestRideResponse(Request request) {
        this.requestId = request.getId();
        this.state = request.getStatus().name();
    }
}
