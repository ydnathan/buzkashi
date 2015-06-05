package com.example.helloworld.entities.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
@Entity
@Table(name="Ride")
public class Ride {
    public Ride() {
        // Jackson deserialization
    }

    public enum RideStatus {
        OPEN, FULL, NO_MORE, EXPIRED
    }

    @Id
    @Column(name="id")
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private long id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @JsonProperty("route_id")
    private long route_id;

    @Column(name="available")
    @JsonProperty("available")
    private boolean available;

    @NotNull
    @Column(name="leaving_at")
    @JsonProperty("leaving_at")
    private Timestamp leavingAt;

    @Column(name="status")
    @JsonProperty("status")
    @Enumerated(EnumType.ORDINAL)
    private RideStatus status;

    @Column(name="created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    @JsonProperty("updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = true)
    private Request request;

    @JsonCreator
    public Ride(@JsonProperty("user") User user, @JsonProperty("route") Route route, @JsonProperty("leaving_at") Date leavingAt, @JsonProperty("status") RideStatus status, @JsonProperty("request") Request request) {
        this.user = user;
        this.route_id = route.getId();
        this.leavingAt = new Timestamp(leavingAt.getTime());
        this.status = status;
        this.available = true;
        this.createdAt = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.updatedAt = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.request = request;
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

    public long getRoute_id() {
        return route_id;
    }

    public void setRoute_id(long route_id) {
        this.route_id = route_id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Timestamp getLeavingAt() {
        return leavingAt;
    }

    public void setLeavingAt(Timestamp leavingAt) {
        this.leavingAt = leavingAt;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
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

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
