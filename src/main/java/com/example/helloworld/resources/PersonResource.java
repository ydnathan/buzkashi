package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.entities.core.Person;
import com.example.helloworld.dao.PersonDAO;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by vaidyanathan.s on 02/05/15.
 */

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private PersonDAO dao;

    public PersonResource(PersonDAO personDAO) {
        this.dao = personDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public Person findPerson(@QueryParam("id") LongParam id) {
        return dao.findById(id.get());
    }

    @GET
    @Timed
    @Path("all")
    @UnitOfWork
    public List<Person> allPersons() {
        return dao.findAll();
    }

    @GET
    @Timed
    @Path("add")
    @UnitOfWork
    public Long addPerson(@QueryParam("name") Optional<String> name) {
        return dao.create(new Person(name.get()));
    }

}
