package com.example.helloworld.dao;

import com.example.helloworld.entities.core.RouteDestinationMap;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

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
}
