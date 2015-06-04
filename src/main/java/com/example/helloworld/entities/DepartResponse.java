package com.example.helloworld.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vaidyanathan.s on 16/05/15.
 */
public class DepartResponse {
    @JsonProperty("ride_id")
    private long rideId;

    @JsonProperty("state")
    private String state;

    public DepartResponse() {
        // Jackson deserialization
    }

    @JsonCreator
    public DepartResponse(@JsonProperty("ride_id") long rideId, @JsonProperty("state") String state) {
        this.rideId = rideId;
        this.state = state;
    }
}
