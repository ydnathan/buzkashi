package com.example.helloworld.entities.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by vaidyanathan.s on 02/05/15.
 */
@Entity
@Table(name="User")
public class User {
    public User() {
        // Jackson deserialization
    }

    public enum VerificationStatus {
        UNVERIFIED, VERIFICATION_SENT, VERIFIED
    }

    @Id
    @Column(name="id")
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private long id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Company company;

    @NotEmpty
    @Column(name="name")
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @Column(name="gender")
    @JsonProperty("gender")
    private String gender;

    @NotEmpty
    @Column(name="company_email")
    @JsonProperty("company_email")
    private String companyEmail;

    @NotEmpty
    @Column(name="contact_number")
    @JsonProperty("contact_number")
    private String contactNumber;

    @Column(name="ride_giver")
    @JsonProperty("ride_giver")
    private boolean rideGiver;

    @Column(name="vehicle_capacity")
    @JsonProperty("vehicle_capacity")
    private int vehicleCapacity;

    @Column(name="vehicle_number")
    @JsonProperty("vehicle_number")
    private String vehicleNumber;

    @Column(name="profile_image_url")
    @JsonProperty("profile_image_url")
    private String profileImageURL;

    @Column(name="verified")
    @JsonProperty("verified")
    @Enumerated(EnumType.ORDINAL)
    private VerificationStatus verified;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonBackReference
    private Set<Request> requests;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonBackReference
    private Set<Ride> rides;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonBackReference
    private Set<Route> routes;

    @JsonCreator
    public User(@JsonProperty("company") Company company,
                @JsonProperty("name") String name,
                @JsonProperty("gender") String gender,
                @JsonProperty("company_email") String companyEmail,
                @JsonProperty("contact_number") String contactNumber,
                @JsonProperty("ride_giver") boolean rideGiver,
                @JsonProperty("vehicle_capacity") int vehicleCapacity,
                @JsonProperty("vehicle_number") String vehicleNumber,
                @JsonProperty("profile_image_url") String profileImageURL
                ) {
        this.company = company;
        this.name = name;
        this.gender = gender;
        this.companyEmail = companyEmail;
        this.contactNumber = contactNumber;
        this.rideGiver = rideGiver;
        this.vehicleCapacity = vehicleCapacity;
        this.vehicleNumber = vehicleNumber;
        this.profileImageURL = profileImageURL;
        this.verified = VerificationStatus.UNVERIFIED;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isRideGiver() {
        return rideGiver;
    }

    public void setRideGiver(boolean rideGiver) {
        this.rideGiver = rideGiver;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(int vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public VerificationStatus getVerified() {
        return verified;
    }

    public void setVerified(VerificationStatus verified) {
        this.verified = verified;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Set<Ride> getRides() {
        return rides;
    }

    public void setRides(Set<Ride> rides) {
        this.rides = rides;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
