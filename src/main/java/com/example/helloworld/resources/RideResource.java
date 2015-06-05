package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.dao.DestinationDAO;
import com.example.helloworld.dao.RequestDAO;
import com.example.helloworld.dao.UserDAO;
import com.example.helloworld.entities.RequestRideResponse;
import com.example.helloworld.entities.SearchRidesResponse;
import com.example.helloworld.entities.core.*;
import com.example.helloworld.dao.RideDAO;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaidyanathan.s on 02/05/15.
 */

@Path("/ride")
@Produces(MediaType.APPLICATION_JSON)
public class RideResource {

    private RideDAO rideDAO;
    private UserDAO userDAO;
    private RequestDAO requestDAO;
    private DestinationDAO destinationDAO;

    final static Logger logger = LoggerFactory.getLogger(RideResource.class);

    public RideResource(RideDAO rideDAO, UserDAO userDAO, RequestDAO requestDAO, DestinationDAO destinationDAO) {
        this.rideDAO = rideDAO;
        this.userDAO = userDAO;
        this.requestDAO = requestDAO;
        this.destinationDAO = destinationDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public Ride findRide(@QueryParam("id") LongParam id) {
        return rideDAO.findById(id.get());
    }

    @GET
    @Timed
    @Path("all")
    @UnitOfWork
    public List<Ride> allRides() {
        return rideDAO.findAll();
    }

    @GET
    @Timed
    @Path("search")
    @UnitOfWork
    public List<SearchRidesResponse> searchRides(@QueryParam("user_id") Optional<Long> userId, @QueryParam("destination_id") Optional<Long> destinationId) {
        // TODO : basic level of authentication..
        User user = userDAO.findById(userId.get());
        if(user == null) {
            return new ArrayList<SearchRidesResponse>();
        }

        List<Ride> rides = rideDAO.searchRides(destinationId.get());
        List<SearchRidesResponse> searchRidesResponses = new ArrayList<SearchRidesResponse>();
        for(Ride ride : rides) {
            searchRidesResponses.add(new SearchRidesResponse(ride));
        }
        return searchRidesResponses;
    }

//    @POST
//    @Timed
//    @Path("request")
//    @UnitOfWork
//    public RequestRideResponse requestRide(@FormParam("ride_taker_user_id") Optional<Long> rideTakerUserId, @FormParam("ride_giver_user_id") Optional<Long> rideGiverUserId, @FormParam("ride_id") Optional<Long> rideId, @FormParam("destination_id") Optional<Long> destinationId) {
//        User rideGiver = userDAO.findById(rideGiverUserId.get());
//        User rideTaker = userDAO.findById(rideTakerUserId.get());
//        Ride ride = rideDAO.findById(rideId.get());
//        Destination destination = destinationDAO.findById(destinationId.get());
//        if(rideGiver == null || rideTaker == null || ride == null || destination == null) {
//            return new RequestRideResponse();
//        }
//
//        List<Request> existingRequests = requestDAO.searchRequests(destination, ride, rideTaker);
//        if(existingRequests!=null && existingRequests.size() > 0) {
//            logger.debug("[WARN] already existing request found for the same ride/destination by same user, returning the same");
//            return new RequestRideResponse(existingRequests.get(0));
//        }
//
//        Request request = requestDAO.create(new Request(ride, rideTaker, destination));
//        return new RequestRideResponse(request);
//    }

}
