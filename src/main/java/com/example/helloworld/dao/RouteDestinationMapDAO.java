package com.example.helloworld.dao;

import java.util.List;

import com.example.helloworld.entities.core.RouteDestinationMap;

import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
public class RouteDestinationMapDAO extends AbstractDAO<RouteDestinationMap> {
    public RouteDestinationMapDAO(SessionFactory factory) {
        super(factory);
    }

    public RouteDestinationMap findById(Long id) {
        return get(id);
    }

    public long create(RouteDestinationMap routeDestinationMap) {
        return persist(routeDestinationMap).getId();
    }
    
    public List<RouteDestinationMap> findAllDestinationMapsByRouteId(Long routeId) {
        return currentSession().createCriteria(RouteDestinationMap.class).add(Restrictions.eq("route_id", routeId)).list();
    }

}
