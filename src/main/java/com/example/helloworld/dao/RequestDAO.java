package com.example.helloworld.dao;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.example.helloworld.entities.core.Company;
import com.example.helloworld.entities.core.Destination;
import com.example.helloworld.entities.core.Request;
import com.example.helloworld.entities.core.Ride;
import com.example.helloworld.entities.core.User;

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
    
    public List<Request> findById(List<Long> idList) {
        Criteria criteria = currentSession().createCriteria(Request.class).add(Restrictions.in("id", idList));
        List<Request> requestList = criteria.list();
        return requestList;
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

	public Request save(Request request) {
		return persist(request);
		
	}
	
	public List<Request> findRequestsByDestinationId(Destination destination) {
		// TODO Auto-generated method stub
		return currentSession().createCriteria(Request.class).add(Restrictions.eq("destination", destination)).list();
	}

	public List<Request> findRequestsByDestinationIdAndSourceId(Destination destination, Company source) {
		//System.out.println("destination id being received =" +destination);
		return currentSession().createCriteria(Request.class).add(Restrictions.eq("destination", destination)).add(Restrictions.eq("source", source)).list();
	}
}
