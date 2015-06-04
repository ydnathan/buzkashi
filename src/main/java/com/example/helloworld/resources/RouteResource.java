package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.dao.*;
import com.example.helloworld.entities.core.*;
import com.example.helloworld.dao.RouteDAO;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vaidyanathan.s on 02/05/15.
 */

@Path("/route")
@Produces(MediaType.APPLICATION_JSON)
public class RouteResource {

    private RouteDAO routeDAO;
    private UserDAO userDAO;
    private CompanyDAO companyDAO;
    private DestinationDAO destinationDAO;
    private RouteDestinationMapDAO routeDestinationMapDAO;

    public RouteResource(RouteDAO routeDAO, UserDAO userDAO, CompanyDAO companyDAO, DestinationDAO destinationDAO, RouteDestinationMapDAO routeDestinationMapDAO) {
        this.routeDAO = routeDAO;
        this.userDAO = userDAO;
        this.companyDAO = companyDAO;
        this.destinationDAO = destinationDAO;
        this.routeDestinationMapDAO = routeDestinationMapDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public Route findRoute(@QueryParam("id") LongParam id) {
        return routeDAO.findById(id.get());
    }

    @GET
    @Timed
    @Path("all")
    @UnitOfWork
    public List<Route> allRoutes() {
        return routeDAO.findAll();
    }

//    @GET
//    @Timed
//    @Path("search")
//    @UnitOfWork
//    public List<Route> searchRoutes(@QueryParam("destination_id") LongParam destination_id) {
//        return dao.searchRoute(destination_id.get());
//  }

    @POST
    @Timed
    @Path("add")
    @UnitOfWork
    public Long addRoute(@FormParam("user_id") Optional<Long> userId, @FormParam("company_id") Optional<Long> companyId, @FormParam("destination_ids") Optional<String> destinationIdStr) {
        User createdBy = userDAO.findById(userId.get());
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

        return route.getId();
    }

}
