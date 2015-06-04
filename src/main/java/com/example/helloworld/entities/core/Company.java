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
@Table(name="Company")
public class Company {
    public Company() {
        // Jackson deserialization
    }

    @Id
    @Column(name="id")
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private long id;

    @NotEmpty
    @Column(name="company_name")
    @JsonProperty("company_name")
    private String companyName;

    @NotEmpty
    @Column(name="address_code")
    @JsonProperty("address_code")
    private String addressCode;

    @NotEmpty
    @Column(name="email_domain")
    @JsonProperty("email_domain")
    private String emailDomain;

    @NotEmpty
    @Column(name="address_text")
    @JsonProperty("address_text")
    private String addressText;

    @NotEmpty
    @Column(name="address_lat_lon")
    @JsonProperty("address_lat_lon")
    private String addressLatLon;

    @NotEmpty
    @Column(name="city")
    @JsonProperty("city")
    private String city;

    @NotEmpty
    @Column(name="state")
    @JsonProperty("state")
    private String state;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonBackReference
    private Set<User> users;

    @JsonCreator
    public Company(@JsonProperty("company_name") String companyName,
                   @JsonProperty("address_code") String addressCode,
                   @JsonProperty("email_domain") String emailDomain,
                   @JsonProperty("address_text") String addressText,
                   @JsonProperty("address_lat_lon") String addressLatLon,
                   @JsonProperty("city") String city,
                   @JsonProperty("state") String state) {
        this.companyName = companyName;
        this.addressCode = addressCode;
        this.emailDomain = emailDomain;
        this.addressText = addressText;
        this.addressLatLon = addressLatLon;
        this.city = city;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getAddressLatLon() {
        return addressLatLon;
    }

    public void setAddressLatLon(String addressLatLon) {
        this.addressLatLon = addressLatLon;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
