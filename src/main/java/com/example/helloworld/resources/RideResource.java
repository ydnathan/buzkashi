package com.example.helloworld.resources;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.dao.CompanyDAO;
import com.example.helloworld.dao.DestinationDAO;
import com.example.helloworld.dao.PublishedRideDAO;
import com.example.helloworld.dao.RequestDAO;
import com.example.helloworld.dao.RideDAO;
import com.example.helloworld.dao.RouteDAO;
import com.example.helloworld.dao.RouteDestinationMapDAO;
import com.example.helloworld.dao.UserDAO;
import com.example.helloworld.entities.RequestRideResponse;
import com.example.helloworld.entities.SearchRidesResponse;
import com.example.helloworld.entities.core.Company;
import com.example.helloworld.entities.core.Destination;
import com.example.helloworld.entities.core.PublishedRide;
import com.example.helloworld.entities.core.PublishedRide.RideStatus;
import com.example.helloworld.entities.core.Request;
import com.example.helloworld.entities.core.Request.RequestStatus;
import com.example.helloworld.entities.core.Ride;
import com.example.helloworld.entities.core.Route;
import com.example.helloworld.entities.core.RouteDestinationMap;
import com.example.helloworld.entities.core.User;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

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
    private CompanyDAO companyDAO;
    private RouteDAO routeDAO;
    private RouteDestinationMapDAO routeDestinationMapDAO;
    private PublishedRideDAO publishedRideDAO;

    final static Logger logger = LoggerFactory.getLogger(RideResource.class);

    public RideResource(RideDAO rideDAO, UserDAO userDAO, RequestDAO requestDAO, DestinationDAO destinationDAO, 
    		CompanyDAO companyDAO, RouteDAO routeDAO, RouteDestinationMapDAO routeDestinationMapDAO,PublishedRideDAO publishedRideDAO) {
        this.rideDAO = rideDAO;
        this.userDAO = userDAO;
        this.requestDAO = requestDAO;
        this.destinationDAO = destinationDAO;
        this.companyDAO = companyDAO;
        this.routeDAO = routeDAO;
        this.routeDestinationMapDAO = routeDestinationMapDAO;
        this.publishedRideDAO = publishedRideDAO;
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

    /*
     * [POST] Approve a ride-seeker
	REQUEST
	user_id
	[user_ids] (ride seekers)
	no_more
	RESPONSE
	user_id
	user_name
	profile_image_url
	contact_number
**/

    @POST
    @Timed
    @Path("approveRideSeekers")
    @UnitOfWork
    public List<Request> approveRideSeekers(@QueryParam("user_id") Optional<Long> userId, @QueryParam("requestIds") Optional<String> requestIdsStr) {
    	String[] requestIds = requestIdsStr.get().split("\\s*,\\s*");
        List<Long> requestIdList = new ArrayList<Long>();
        for(String requestId : requestIds) {
        	requestIdList.add(Long.parseLong(requestId));
        }
        List<Request> requestList = requestDAO.findById(requestIdList);
        List<Request> approvedList = new ArrayList<Request>();
        for(Request request : requestList){
        	if(request.getStatus() != RequestStatus.REJECTED_LEFT_ALREADY){
	        	request.setRequestUpdatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
	        	request.setStatus(RequestStatus.ACCEPTED);
	        	approvedList.add(requestDAO.save(request));
        	}
        }
        return approvedList;
    }
   
    
    @POST
    @Timed
    @Path("createPublishedRide")
    @UnitOfWork
    public PublishedRide createPublishedRide(@FormParam("user_id") Optional<Long> userId, @FormParam("company_id") Optional<Long> companyId, 
    		@FormParam("destination_ids") Optional<String> destinationIdStr,@QueryParam("leaving_at") Optional<String> leavingAt) {
    	User createdBy = userDAO.findById(userId.get());
        Route route = createRoute(createdBy, companyId, destinationIdStr);
        Company company = companyDAO.findById(companyId.get());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date leavingByDate = Calendar.getInstance().getTime();
        try {
            leavingByDate = format.parse(leavingAt.get());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return publishedRideDAO.create(new PublishedRide(createdBy, route, leavingByDate, company));
    }

	private Route createRoute(User createdBy, Optional<Long> companyId,
			Optional<String> destinationIdStr) {
        Company company = companyDAO.findById(companyId.get());
        String companyAddressCode = company.getAddressCode();
        String[] destinationIds = destinationIdStr.get().split("\\s*,\\s*");
        List<Long> destinationIdList = new ArrayList<Long>();
        for(String destinationId : destinationIds) {
            destinationIdList.add(Long.parseLong(destinationId));
        }
        List<Destination> destinationList = destinationDAO.findById(destinationIdList);
        String routeName = companyAddressCode.concat("_").concat(Iterables.getLast(destinationList).getAreaCode());
        Route route = routeDAO.create(new Route(routeName, createdBy));

        int sequence = 0;
        for(Destination destination : destinationList) {
            routeDestinationMapDAO.create(new RouteDestinationMap(route, destination, sequence));
            sequence++;
        }

        return route;
	}

    
}
