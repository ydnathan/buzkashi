package com.example.helloworld.entities.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by vaidyanathan.s on 02/05/15.
 */
@Entity
@Table(name="Person")
public class Person {
    public Person() {
        // Jackson deserialization
    }

    @Id
    @Column(name="id")
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private long id;

    @NotEmpty // ensure that name isn't null or blank
    @Column(name="name")
    @JsonProperty("name")
    private String name;

    @JsonCreator
    public Person(@JsonProperty("name") String name) {
        this.name = name;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }
}
