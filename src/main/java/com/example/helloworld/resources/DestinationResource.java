package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.entities.core.Company;
import com.example.helloworld.entities.core.Destination;
import com.example.helloworld.dao.DestinationDAO;
import com.google.common.base.Optional;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
@Path("/destination")
@Produces(MediaType.APPLICATION_JSON)
public class DestinationResource {

    private DestinationDAO dao;

    public DestinationResource(DestinationDAO dao) {
        this.dao = dao;
    }

    @POST
    @Timed
    @Path("add")
    @UnitOfWork
    public Long addDestination(@FormParam("area_code") Optional<String> areaCode,
                               @FormParam("area_text") Optional<String> areaText,
                               @FormParam("city") Optional<String> city,
                               @FormParam("state") Optional<String> state
                               ) {
        return dao.create(new Destination(areaCode.get(), areaText.get(), city.get(), state.get()));
    }

    @GET
    @Timed
    @Path("all")
    @UnitOfWork
    public List<Destination> allDestinations() {
        return dao.findAll();
    }

    @GET
    @Timed
    @Path("search")
    @UnitOfWork
    public List<Destination> searchDestinations(@QueryParam("city") Optional<String> city, @QueryParam("state") Optional<String> state) {
        return dao.searchDestinations(city.get(), state.get());
    }
    
    @GET
    @Timed
    @Path("city")
    @UnitOfWork
    public List<Destination> searchDestinations(@QueryParam("city") String city) {
        return dao.searchDestinationsByCity(city);
    }

}
