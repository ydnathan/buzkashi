package com.example.helloworld.dao;

import com.example.helloworld.entities.core.Destination;
import com.example.helloworld.entities.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
public class DestinationDAO extends AbstractDAO<Destination> {
    public DestinationDAO(SessionFactory factory) {
        super(factory);
    }

    public Destination findById(Long id) { return get(id); }

    public List<Destination> findById(List<Long> idList) {
        Criteria criteria = currentSession().createCriteria(Destination.class).add(Restrictions.in("id", idList));
        List<Destination> destinationList = criteria.list();
        return destinationList;
    }

    public long create(Destination destination) {
        return persist(destination).getId();
    }

    public List<Destination> findAll() {
        return currentSession().createCriteria(Destination.class).list();
    }

    public List<Destination> searchDestinations(String city, String state) {
        Criteria criteria = currentSession().createCriteria(Destination.class).add(Restrictions.eq("city", city)).add(Restrictions.eq("state", state));
        List<Destination> destinationList = criteria.list();
        return destinationList;
    }
    
    public List<Destination> searchDestinationsByCity(String city) {
        Criteria criteria = currentSession().createCriteria(Destination.class).add(Restrictions.eq("city", city));
        List<Destination> destinationList = criteria.list();
        return destinationList;
    }
}
