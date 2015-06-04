package com.example.helloworld.entities;

import com.example.helloworld.entities.core.Destination;
import com.example.helloworld.entities.core.Request;
import com.example.helloworld.entities.core.Ride;
import com.example.helloworld.entities.core.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Created by vaidyanathan.s on 16/05/15.
 */
public class RideSeekersResponse {
    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("destination_area")
    private String destinationArea;

    @JsonProperty("profile_image_url")
    private String profileImageURL;

    @JsonProperty("request_status")
    private String requestStatus;

    public RideSeekersResponse() {
        // Jackson deserialization
    }

    @JsonCreator
    public RideSeekersResponse(Request request) {
        User user = request.getUser();
        this.userId = user.getId();
        this.userName = user.getName();
        this.profileImageURL = user.getProfileImageURL();
        this.destinationArea = request.getDestination().getAreaText();
        this.requestStatus = request.getStatus().name();
    }
}
