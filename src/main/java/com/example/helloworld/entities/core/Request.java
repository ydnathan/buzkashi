package com.example.helloworld.entities.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

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
    private Company source;

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

    @Column(name="request_created_at")
    @JsonProperty("request_created_at")
    private Timestamp requestCreatedAt;

    @Column(name="request_updated_at")
    @JsonProperty("request_updated_at")
    private Timestamp requestUpdatedAt;

//user_id, request_created_at, status (cancelled, confirmed, pending, expired, deleted), acked_giver, source_id, destination_ids(CSV)
    @JsonCreator
    public Request(@JsonProperty("user") User user,
                @JsonProperty("source") Company company,
                @JsonProperty("destination") Destination destination
    ) {
        this.user = user;
        this.source = company;
        this.destination = destination;
        this.requestCreatedAt = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.requestUpdatedAt = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.status = RequestStatus.REQUESTED;
    }

    public long getId() {
        return id;
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

    public Long getDestinationId() {
        return destination.getId();
    }

    public Long getDestination_id() {
        return destination.getId();
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Company getSource() { return source; }

    public void setSource(Company source) { this.source = source; }

    public Timestamp getRequestCreatedAt() {
        return requestCreatedAt;
    }

    public void setRequestCreatedAt(Timestamp requestCreatedAt) {
        this.requestCreatedAt = requestCreatedAt;
    }

    public Timestamp getRequestUpdatedAt() { return requestUpdatedAt; }

    public void setRequestUpdatedAt(Timestamp requestUpdatedAt) {
        this.requestUpdatedAt = requestUpdatedAt;
    }
}
