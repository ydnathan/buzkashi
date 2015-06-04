package com.example.helloworld.entities.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */

@Entity
@Table(name="Request")
public class Request {
    public Request() {
        // Jackson deserialization
    }

    public enum RequestStatus {
        REQUESTED, ACCEPTED, REJECTED_LEFT_ALREADY, REJECTED_RIDE_FULL
    }

    @Id
    @Column(name="id")
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private long id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Ride ride;

//    @JsonProperty("destination")
//    @Column(name="destination")
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Destination destination;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @Column(name="status")
    @JsonProperty("status")
    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;

    @Column(name="created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    @JsonProperty("updated_at")
    private Timestamp updatedAt;

    @JsonCreator
    public Request(@JsonProperty("ride") Ride ride, @JsonProperty("user") User user, @JsonProperty("destination_id") Destination destination) {
        this.ride = ride;
        this.user = user;
        this.status = RequestStatus.REQUESTED;
        this.destination = destination;
        this.createdAt = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.updatedAt = new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public long getId() {
        return id;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
