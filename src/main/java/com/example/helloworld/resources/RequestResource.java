package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.dao.*;
import com.example.helloworld.entities.core.Company;
import com.example.helloworld.entities.core.Destination;
import com.example.helloworld.entities.core.Request;
import com.example.helloworld.entities.core.User;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

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

    public RequestResource(UserDAO userDAO, CompanyDAO companyDAO, DestinationDAO destinationDAO, RouteDAO routeDAO) {
        this.companyDAO = companyDAO;
        this.userDAO = userDAO;
        this.destinationDAO = destinationDAO;
        this.routeDAO = routeDAO;
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


}
