package com.example.helloworld.entities.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by vaidyanathan.s on 02/05/15.
 */
@Entity
@Table(name="Destination")
public class Destination {
    public Destination() {
        // Jackson deserialization
    }

    @Id
    @Column(name="id")
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator", strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(name="area_code")
    @JsonProperty("area_code")
    private String areaCode;

    @NotEmpty
    @Column(name="area_text")
    @JsonProperty("area_text")
    private String areaText;

    @NotEmpty
    @Column(name="city")
    @JsonProperty("city")
    private String city;

    @NotEmpty
    @Column(name="state")
    @JsonProperty("state")
    private String state;

    @OneToMany(mappedBy = "destination", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonBackReference
    private Set<Request> requests;


//    @ManyToMany
//    @JoinTable(name="destination_route_destination_map", joinColumns= { @JoinColumn(name = "destination_id", nullable = false, updatable = false) }, inverseJoinColumns=@JoinColumn(name = "route_destination_map_id"))
//    @JsonManagedReference
//    private Set<RouteDestinationMap> routeDestinationMaps;

    @JsonCreator
    public Destination(@JsonProperty("area_code") String areaCode,
                       @JsonProperty("area_text") String areaText,
                       @JsonProperty("city") String city,
                       @JsonProperty("state") String state) {
        this.areaCode = areaCode;
        this.areaText = areaText;
        this.city = city;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaText() {
        return areaText;
    }

    public void setAreaText(String areaText) {
        this.areaText = areaText;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    //    public Set<RouteDestinationMap> getRouteDestinationMaps() {
//        return routeDestinationMaps;
//    }
//
//    public void setRouteDestinationMaps(Set<RouteDestinationMap> routeDestinationMaps) {
//        this.routeDestinationMaps = routeDestinationMaps;
//    }
}
