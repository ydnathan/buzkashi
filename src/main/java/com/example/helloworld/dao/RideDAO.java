package com.example.helloworld.dao;

import com.example.helloworld.entities.core.Ride;
import com.example.helloworld.entities.core.RouteDestinationMap;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
public class RideDAO extends AbstractDAO<Ride> {
    public RideDAO(SessionFactory factory) {
        super(factory);
    }

    public Ride findById(Long id) {
        return get(id);
    }

    public Ride create(Ride ride) {
        persist(ride);
        return ride;
    }

    public List<Ride> findAll() {
        return currentSession().createCriteria(Ride.class).list();
    }

    public List<Ride> searchRides(long destinationId) {
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
            return new ArrayList<Ride>();
        } else {
            Criteria criteria = currentSession().createCriteria(Ride.class).add(Restrictions.in("id", rideIdList)).add(Restrictions.eq("available", true));
            List<Ride> rideList = criteria.list();
            return rideList;
        }
    }
}
