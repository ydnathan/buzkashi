package com.example.helloworld.dao;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.example.helloworld.entities.core.PublishedRide;
import com.example.helloworld.entities.core.RouteDestinationMap;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
public class PublishedRideDAO extends AbstractDAO<PublishedRide> {
    public PublishedRideDAO(SessionFactory factory) {
        super(factory);
    }

    public PublishedRide findById(Long id) {
        return get(id);
    }

    public PublishedRide create(PublishedRide ride) {
        persist(ride);
        return ride;
    }
    
    public PublishedRide createPublishedRide(PublishedRide ride) {
        persist(ride);
        return ride;
    }

    public List<PublishedRide> findAll() {
        return currentSession().createCriteria(PublishedRide.class).list();
    }

    public List<PublishedRide> searchRides(long destinationId) {
        // find out all rides that have this destination
        Criteria routeDestinationMapCriteria = currentSession().createCriteria(RouteDestinationMap.class).add(Restrictions.eq("destination_id", destinationId));
        List<RouteDestinationMap> routeDestinationMapList = routeDestinationMapCriteria.list();
        Set<Long> rideIdList = new HashSet<Long>();
        for(RouteDestinationMap routeDestinationMap : routeDestinationMapList) {
            rideIdList.add(routeDestinationMap.getRoute_id());
        }

        // get all ride objects
        if(rideIdList.isEmpty()) {
            //nothing is available
            return new ArrayList<PublishedRide>();
        } else {
            Criteria criteria = currentSession().createCriteria(PublishedRide.class).add(Restrictions.in("id", rideIdList)).add(Restrictions.eq("available", true));
            List<PublishedRide> rideList = criteria.list();
            return rideList;
        }
    }
}
