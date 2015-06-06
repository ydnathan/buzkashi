package com.example.helloworld.resources;

import io.dropwizard.hibernate.UnitOfWork;

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
import com.example.helloworld.dao.RequestDAO;
import com.example.helloworld.dao.RouteDAO;
import com.example.helloworld.dao.UserDAO;
import com.example.helloworld.entities.core.Company;
import com.example.helloworld.entities.core.Destination;
import com.example.helloworld.entities.core.Request;
import com.example.helloworld.entities.core.User;
import com.google.common.base.Optional;

/**
 * Created by prashanth.yv on 6/5/15.
 */
@Path("/request")
@Produces(MediaType.APPLICATION_JSON)
public class RequestResource {
    private UserDAO userDAO;
    private CompanyDAO companyDAO;
    private DestinationDAO destinationDAO;
    private RouteDAO routeDAO;
    private RequestDAO requestDAO;

    final static Logger logger = LoggerFactory.getLogger(UserResource.class);

    public RequestResource(UserDAO userDAO, CompanyDAO companyDAO, DestinationDAO destinationDAO, RouteDAO routeDAO,RequestDAO requestDAO) {
        this.companyDAO = companyDAO;
        this.userDAO = userDAO;
        this.destinationDAO = destinationDAO;
        this.routeDAO = routeDAO;
        this.requestDAO = requestDAO;
    }

    @POST
    @Timed
    @Path("create")
    @UnitOfWork
    public Request createRequest(@FormParam("company_id") Optional<Long> companyId,
                                                 @FormParam("destination_id") Optional<Long> destinationId,
                                                 @FormParam("user_id") Optional<Long> userId
                                                 ) {
        Company source = companyDAO.findById(companyId.get());
        User user = userDAO.findById(userId.get());
        Destination destination = destinationDAO.findById(destinationId.get());
        return requestDAO.create(new Request(user, source, destination));
        //return dao.create(new Destination(areaCode.get(), areaText.get(), city.get(), state.get()));

    }
    
    @GET
    @Timed
    @Path("get")
    @UnitOfWork
    public List<Request> findRequest(@QueryParam("destination_id") Optional<Long> destinationId) {
        Destination destination = destinationDAO.findById(destinationId.get());
    	return requestDAO.findRequestsByDestinationId(destination);
        //return dao.create(new Destination(areaCode.get(), areaText.get(), city.get(), state.get()));

    }
    
    @GET
    @Timed
    @Path("get2")
    @UnitOfWork
    public List<Request> findRequest(@QueryParam("destination_id") Optional<Long> destinationId,
    		@QueryParam("source_id") Optional<Long> sourceId) {
        Destination destination = destinationDAO.findById(destinationId.get());
        Company source = companyDAO.findById(sourceId.get());
    	return requestDAO.findRequestsByDestinationIdAndSourceId(destination, source);

    }


}
