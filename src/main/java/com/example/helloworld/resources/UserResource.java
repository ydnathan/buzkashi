package com.example.helloworld.resources;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.dao.*;
import com.example.helloworld.entities.*;
import com.example.helloworld.entities.core.*;
import com.google.common.base.Optional;
import com.google.common.io.Files;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import com.example.helloworld.dao.RideDAO;
import com.example.helloworld.dao.RouteDAO;
import com.example.helloworld.dao.RouteDestinationMapDAO;
import com.example.helloworld.dao.UserDAO;
import com.example.helloworld.entities.DepartResponse;
import com.example.helloworld.entities.RideSeekersResponse;
import com.example.helloworld.entities.core.Company;
import com.example.helloworld.entities.core.PublishedRide;
import com.example.helloworld.entities.core.Request;
import com.example.helloworld.entities.core.Ride;
import com.example.helloworld.entities.core.Route;
import com.example.helloworld.entities.core.RouteDestinationMap;
import com.example.helloworld.entities.core.User;
import com.google.common.base.Optional;

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
    private RouteDestinationMapDAO routeDestinationMapDAO;
    private RideDAO rideDAO;
    private PublishedRideDAO publishedRideDAO;
    private RequestDAO requestDAO;

    final static Logger logger = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserDAO userDAO, CompanyDAO companyDAO, DestinationDAO destinationDAO, RouteDAO routeDAO, 
    		RideDAO rideDAO, PublishedRideDAO publishedRideDAO, RouteDestinationMapDAO routeDestinationMapDAO, RequestDAO requestDAO) {
        this.companyDAO = companyDAO;
        this.userDAO = userDAO;
        this.destinationDAO = destinationDAO;
        this.routeDAO = routeDAO;
        this.rideDAO = rideDAO;
        this.publishedRideDAO = publishedRideDAO;
        this.routeDestinationMapDAO = routeDestinationMapDAO;
        this.requestDAO = requestDAO;
    }

    @POST
    @Timed
    @Path("add")
    @UnitOfWork
    //@Consumes(MediaType.MULTIPART_FORM_DATA)
    public AddUserResponse addUser(@FormParam("company_id") Optional<Long> companyId,
                              @FormParam("name") Optional<String> name,
                              @FormParam("gender") Optional<String> gender,
                              @FormParam("company_email") Optional<String> companyEmail,
                              @FormParam("contact_number") Optional<String> contactNumber,
                              @FormParam("verified") Optional<Integer> verified
//                        @FormDataParam("file") final InputStream fileInputStream,
//                        @FormDataParam("file") final FormDataContentDisposition contentDispositionHeader
    ) throws IOException {

        //String filePath = "~/images/" + contentDispositionHeader.getFileName();
        //saveFile(fileInputStream, filePath);
    	logger.info(" >>>>>>>>>>>>>Company id "+companyId+"   get value "+companyId.get());
        Company company = companyDAO.findById(companyId.get());
        //String profileImageURL = AWSResource.uploadFile(contentDispositionHeader.getFileName(), filePath);
        String profileImageURL = "https://s3-ap-southeast-1.amazonaws.com/buzkashi/images/profile1.jpeg";
        Long userID = userDAO.create(new User(company, name.get(), gender.get(), companyEmail.get(), contactNumber.get(), profileImageURL));
        //return userDAO.create(new User(company, name.get(), gender.get(), companyEmail.get(), contactNumber.get(), profileImageURL.orNull()));
        String emailToken = sendVerificationEmail(name.get(), companyEmail.get());
        User user = userDAO.findById(userID);
        userDAO.updateEmailToken(user, emailToken);
        //HashMap<String, Object> returnValue = new HashMap<String, Object>();

//        JSONObject obj = new JSONObject();
//
//        try {
//            obj.put("userID", userID);
//            obj.put("verificationCode", emailToken);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return (obj);

        return new AddUserResponse(emailToken, userID+"");
    }

    private void saveFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
        int read;
        final int BUFFER_LENGTH = 1024;
        final byte[] buffer = new byte[BUFFER_LENGTH];
        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
        while ((read = uploadedInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();
    }

    private String sendVerificationEmail(String name, String email) throws IOException {
        String randomString = generateRandomString();


        StringBuffer output = new StringBuffer();

        String command = "mkdir check_folder";

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            //p = Runtime.getRuntime().exec("echo \"Hello "+name+"! Here's your verification code - "+randomString+". Have a nice time!\" | mutt -s \"Demo Subject Line\" "+email+" >> ~/mail_error.log");
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }








//        Runtime rt = Runtime.getRuntime();
//        System.out.println("email id = " +email);
//        Process pr = rt.exec("echo \"Hello "+name+"! Here's your verification code - "+randomString+". Have a nice time!\" | mutt -s \"Demo Subject Line\" "+email+" >> ~/mail_error.log");
//        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        return randomString;
    }

    public String generateRandomString() {
        return RandomStringUtils.random(10, true, false);
    }

    @GET
    @Timed
    @Path("find")
    @UnitOfWork
    public User findUser(@QueryParam("id") Optional<Long> user_id) {
        return userDAO.findById(user_id.get());
    }


    @PUT
    @Timed
    @Path("verify")
    public void verify(@FormParam("verified") String verified,
                       @FormParam("user_id") Optional<Long> userId) {
        User user = userDAO.findById(userId.get());
        userDAO.updateUserVerification(User.VerificationStatus.valueOf(verified), user);
    }

    @POST
    @Timed
    @Path("depart")
    @UnitOfWork
    public DepartResponse depart(@FormParam("user_id") Optional<Long> userId,
                                /*@FormParam("company_id") Optional<Long> companyId,*/
                                /*@FormParam("destination_id") Optional<Long> destinationId,*/
                                 @FormParam("route_id") Optional<Long> routeId,
                                 @FormParam("leaving_at") Optional<String> leavingAt /*2015-05-17T18:34:56*/,
                                 @FormParam("request_id") Optional<Long> requestId) {

        //TODO: Tomorrow if a user posts a ride from a different company
        /*Company company = companyDAO.findById(companyId.get());*/

        User user = userDAO.findById(userId.get());
        Request request = requestDAO.findById(requestId.get());
        Route route = routeDAO.findById(routeId.get());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date leavingByDate = Calendar.getInstance().getTime();
        try {
            leavingByDate = format.parse(leavingAt.get());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Ride ride = rideDAO.create(new Ride(user, route, leavingByDate, Ride.RideStatus.OPEN, request));
        return new DepartResponse(ride.getId(), ride.getStatus().name());
    }

//    @GET
//    @Timed
//    @Path("ride_seekers")
//    @UnitOfWork
//    public List<RideSeekersResponse> getRideSeekers(@QueryParam("user_id") Optional<Long> userId, @QueryParam("ride_id") Optional<Long> rideId) {
//        // TODO : basic level of authentication..
//        User user = userDAO.findById(userId.get());
//        Ride ride = rideDAO.findById(rideId.get());
//        if(user == null || ride == null) {
//            return new ArrayList<RideSeekersResponse>();
//        }
//
//        List<RideSeekersResponse> rideSeekersResponseList = new ArrayList<RideSeekersResponse>();
//        Request requests = ride.getRequest();
//        for(Request request : requests) {
//            rideSeekersResponseList.add(new RideSeekersResponse(request));
//        }
//        return rideSeekersResponseList;
//    }
    
//    @GET
//    @Timed
//    @Path("published_ride_seekers")
//    @UnitOfWork
//    public List<RideSeekersResponse> getPublishedRideSedekers(@QueryParam("user_id") Optional<Long> userId, @QueryParam("route_id") Optional<Long> routeId, @QueryParam("source_id") Optional<Long> sourceId) {
//        User user = userDAO.findById(userId.get());
//        //PublishedRide ride = publishedRideDAO.findById(id);
//        
//        if(user == null || ride == null) {
//            return new ArrayList<RideSeekersResponse>();
//        }
//
//        List<RideSeekersResponse> rideSeekersResponseList = new ArrayList<RideSeekersResponse>();
//        Set<Request> requests = getRequests(ride);
//        for(Request request : requests) {
//            rideSeekersResponseList.add(new RideSeekersResponse(request));
//        }
//        return rideSeekersResponseList;
//    }
    
    @GET
    @Timed
    @Path("published_ride_seekers")
    @UnitOfWork
    public List<RideSeekersResponse> getPublishedRideSeekers(@QueryParam("user_id") Optional<Long> userId, @QueryParam("route_id") Optional<Long> routeId, @QueryParam("source_id") Optional<Long> sourceId) {
    	User user = userDAO.findById(userId.get());
    	Set<Request> requests = getRequests(routeId.get(),sourceId.get());
    	List<RideSeekersResponse> rideSeekersResponseList = new ArrayList<RideSeekersResponse>();
    	for(Request request : requests){
    		rideSeekersResponseList.add(new RideSeekersResponse(request));
    	}
    	return rideSeekersResponseList;
    }
    
    private Set<Request> getRequests(Long routeId, Long sourceId){
    	Set<Request> requests =  new HashSet<Request>();
    	List<RouteDestinationMap> destinationMaps = routeDestinationMapDAO.findAllDestinationMapsByRouteId(routeId);
    	for(RouteDestinationMap map : destinationMaps){
    		//System.out.println("in get request destination map = " +map.getDestinationId());
    		Destination destination = destinationDAO.findById(map.getDestination_id());
    		Company source = companyDAO.findById(sourceId);
    		requests.addAll(requestDAO.findRequestsByDestinationIdAndSourceId(destination,source));
    	}
    	return requests;
    	
    }

}
