package com.example.helloworld.entities;

import com.example.helloworld.entities.core.Ride;
import com.example.helloworld.entities.core.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Created by vaidyanathan.s on 16/05/15.
 */
public class SearchRidesResponse {
    @JsonProperty("ride_id")
    private long rideId;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("vehicle_capacity")
    private int vehicleCapacity;

    @JsonProperty("available")
    private boolean available;

    @JsonProperty("leaving_at")
    private Timestamp leavingAt;

    public SearchRidesResponse() {
        // Jackson deserialization
    }

    @JsonCreator
    public SearchRidesResponse(Ride ride) {
        this.rideId = ride.getId();
        User rideGiver = ride.getUser();
        this.userId = rideGiver.getId();
        this.userName = rideGiver.getName();
        this.available = ride.isAvailable();
        this.leavingAt = ride.getLeavingAt();
    }
}
