package com.example.helloworld.entities.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
@Entity
@Table(name="RouteDestinationMap")
public class RouteDestinationMap {
    public RouteDestinationMap() {
        // Jackson deserialization
    }

    @Id
    @Column(name="id", unique = true, nullable = false)
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator", strategy = GenerationType.IDENTITY)
    private long id;

    //    @OneToOne(fetch = FetchType.LAZY, mappedBy = "routeDestinationMap", cascade = CascadeType.ALL)
    @JsonProperty("route_id")
    @Column(name="route_id")
    private long route_id;

//    @ManyToMany(mappedBy="routeDestinationMaps")
//    @JsonBackReference
//    private Set<Destination> destinations;


    @JsonProperty("destination_id")
    @Column(name="destination_id")
    private long destination_id;

    @Column(name="sequence_number")
    @JsonProperty("sequence_number")
    private int sequenceNumber;

    public long getRoute_id() {
        return route_id;
    }

    public void setRoute_id(long route_id) {
        this.route_id = route_id;
    }

    public long getDestination_id() {
        return destination_id;
    }

    public long getDestinationId() {
        return destination_id;
    }

    public void setDestination_id(long destination_id) {
        this.destination_id = destination_id;
    }

    @JsonCreator
    public RouteDestinationMap(@JsonProperty("route") Route route, @JsonProperty("destination") Destination destination, @JsonProperty("sequence_number") int sequenceNumber) {
        this.route_id = route.getId();
//        if(this.destinations == null) {
//            destinations = new HashSet<Destination>();
//        }
//        this.destinations.add(destination);
        this.destination_id = destination.getId();

        this.sequenceNumber = sequenceNumber;
    }

    public long getId() {
        return id;
    }

//    public Route getRoute() {
//        return route;
//    }
//
//    public void setRoute(Route route) {
//        this.route = route;
//    }
//
//    public Set<Destination> getDestinations() {
//        return destinations;
//    }
//
//    public void setDestinations(Set<Destination> destinations) {
//        this.destinations = destinations;
//    }


    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
