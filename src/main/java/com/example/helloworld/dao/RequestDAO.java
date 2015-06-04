package com.example.helloworld.dao;

import com.example.helloworld.entities.core.Destination;
import com.example.helloworld.entities.core.Request;
import com.example.helloworld.entities.core.Ride;
import com.example.helloworld.entities.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
public class RequestDAO extends AbstractDAO<Request> {
    public RequestDAO(SessionFactory factory) {
        super(factory);
    }

    public Request findById(Long id) {
        return get(id);
    }

    public List<Request> searchRequests(Destination destination, Ride ride, User user) {
        Criteria routeDestinationMapCriteria = currentSession().createCriteria(Request.class).add(Restrictions.eq("destination", destination)).add(Restrictions.eq("ride", ride)).add(Restrictions.eq("user", user));
        List<Request> routeDestinationMapList = routeDestinationMapCriteria.list();
        return routeDestinationMapList;
    }

    public Request create(Request request) {
        persist(request);
        return request;
    }
}
