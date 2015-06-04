package com.example.helloworld.dao;

import com.example.helloworld.entities.core.Route;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
public class RouteDAO extends AbstractDAO<Route> {
    public RouteDAO(SessionFactory factory) {
        super(factory);
    }

    public Route findById(Long id) {
        return get(id);
    }

//    public long create(Route route) {
//        return persist(route).getId();
//    }

    public Route create(Route route) {
        persist(route);
        return route;
    }

    public List<Route> findAll() {
        return currentSession().createCriteria(Route.class).list();
    }

//    public List<Route> searchRoute(Long destination_id) {
//        return null;
//    }
}
