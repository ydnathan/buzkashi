package com.example.helloworld.entities.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
@Entity
@Table(name="Route")
public class Route {
    public Route() {
        // Jackson deserialization
    }

    @Id
    @Column(name="id")
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private long id;

    @NotEmpty
    @Column(name="name")
    @JsonProperty("name")
    private String name;

    //@Column(name="created_by")
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User createdBy;

    @Column(name="created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    @JsonProperty("updated_at")
    private Timestamp updatedAt;


//    @OneToOne(fetch = FetchType.LAZY)
//    @PrimaryKeyJoinColumn
//    private RouteDestinationMap routeDestinationMap;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Ride ride;

    @JsonCreator
    public Route(@JsonProperty("name") String name, @JsonProperty("created_by") User user) {
        this.name = name;
        this.createdBy = user;
        this.createdAt = new Timestamp(Calendar.getInstance().getTime().getTime());
        this.updatedAt = new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public RouteDestinationMap getRouteDestinationMap() {
//        return routeDestinationMap;
//    }
//
//    public void setRouteDestinationMap(RouteDestinationMap routeDestinationMap) {
//        this.routeDestinationMap = routeDestinationMap;
//    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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

//    public Ride getRide() {
//        return ride;
//    }
//
//    public void setRide(Ride ride) {
//        this.ride = ride;
//    }
}
