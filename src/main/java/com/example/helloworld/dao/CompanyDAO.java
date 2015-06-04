package com.example.helloworld.dao;

import com.example.helloworld.entities.core.Company;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
public class CompanyDAO extends AbstractDAO<Company> {
    public CompanyDAO(SessionFactory factory) {
        super(factory);
    }

    public Company findById(Long id) {
        return get(id);
    }

    public long create(Company company) {
        return persist(company).getId();
    }

    public List<Company> findAll() {
        return currentSession().createCriteria(Company.class).list();
    }
}
