package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.dao.*;
import com.example.helloworld.entities.DepartResponse;
import com.example.helloworld.entities.RideSeekersResponse;
import com.example.helloworld.entities.core.*;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by vaidyanathan.s on 11/05/15.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO userDAO;
    private CompanyDAO companyDAO;
    private DestinationDAO destinationDAO;
    private RouteDAO routeDAO;
    private RideDAO rideDAO;

    final static Logger logger = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserDAO userDAO, CompanyDAO companyDAO, DestinationDAO destinationDAO, RouteDAO routeDAO, RideDAO rideDAO) {
        this.companyDAO = companyDAO;
        this.userDAO = userDAO;
        this.destinationDAO = destinationDAO;
        this.routeDAO = routeDAO;
        this.rideDAO = rideDAO;
    }

    @POST
    @Timed
    @Path("add")
    @UnitOfWork
    public Long addUser(@FormParam("company_id") Optional<Long> companyId,
                        @FormParam("name") Optional<String> name,
                        @FormParam("gender") Optional<String> gender,
                        @FormParam("company_email") Optional<String> companyEmail,
                        @FormParam("contact_number") Optional<String> contactNumber,
                        @FormParam("ride_giver") Optional<Boolean> rideGiver,
                        @FormParam("vehicle_capacity") Optional<Integer> vehicleCapacity,
                        @FormParam("vehicle_number") Optional<String> vehicleNumber,
                        @FormParam("profile_image_url") Optional<String> profileImageURL) {
        Company company = companyDAO.findById(companyId.get());
        return userDAO.create(new User(company, name.get(), gender.get(), companyEmail.get(), contactNumber.get(), rideGiver.get(), vehicleCapacity.or(-1), vehicleNumber.orNull(), profileImageURL.orNull()));
    }

    @GET
    @Timed
    @Path("find")
    @UnitOfWork
    public User findUser(@QueryParam("id") Optional<Long> user_id) {
        return userDAO.findById(user_id.get());
    }

    @POST
    @Timed
    @Path("depart")
    @UnitOfWork
    public DepartResponse depart(@FormParam("user_id") Optional<Long> userId,
                                /*@FormParam("company_id") Optional<Long> companyId,*/
                                /*@FormParam("destination_id") Optional<Long> destinationId,*/
                                 @FormParam("route_id") Optional<Long> routeId,
                                 @FormParam("leaving_at") Optional<String> leavingAt /*2015-05-17T18:34:56*/) {

        //TODO: Tomorrow if a user posts a ride from a different company
        /*Company company = companyDAO.findById(companyId.get());*/

        User user = userDAO.findById(userId.get());

        Route route = routeDAO.findById(routeId.get());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date leavingByDate = Calendar.getInstance().getTime();
        try {
            leavingByDate = format.parse(leavingAt.get());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Ride ride = rideDAO.create(new Ride(user, route, leavingByDate, Ride.RideStatus.OPEN));
        return new DepartResponse(ride.getId(), ride.getStatus().name());
    }

    @GET
    @Timed
    @Path("ride_seekers")
    @UnitOfWork
    public List<RideSeekersResponse> getRideSeekers(@QueryParam("user_id") Optional<Long> userId, @QueryParam("ride_id") Optional<Long> rideId) {
        // TODO : basic level of authentication..
        User user = userDAO.findById(userId.get());
        Ride ride = rideDAO.findById(rideId.get());
        if(user == null || ride == null) {
            return new ArrayList<RideSeekersResponse>();
        }

        List<RideSeekersResponse> rideSeekersResponseList = new ArrayList<RideSeekersResponse>();
        Set<Request> requests = ride.getRequests();
        for(Request request : requests) {
            rideSeekersResponseList.add(new RideSeekersResponse(request));
        }
        return rideSeekersResponseList;
    }
}
