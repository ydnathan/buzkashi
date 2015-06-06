package com.example.helloworld.dao;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import ch.qos.logback.classic.Logger;

import com.example.helloworld.entities.core.Request;
import com.example.helloworld.entities.core.Route;

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

//	public List<Request> findRequestsByDestinationId(long destinationId) {
//		// TODO Auto-generated method stub
//		return currentSession().createCriteria(Request.class).add(Restrictions.eq("destination_id", destinationId)).list();
//	}

//	public List<Request> findRequestsByDestinationIdAndSourceId(long destinationId, Long sourceId) {
//		System.out.println("destination id being received =" +destinationId);
//		return currentSession().createCriteria(Request.class).add(Restrictions.eq("destination_id", destinationId)).add(Restrictions.eq("source_id", sourceId)).list();
//	}

//    public List<Route> searchRoute(Long destination_id) {
//        return null;
//    }
}
