package com.example.helloworld;

import com.example.helloworld.dao.*;
import com.example.helloworld.entities.core.*;
import com.example.helloworld.resources.*;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.hibernate.SessionFactory;

/**
 * Created by vaidyanathan.s on 02/05/15.
 */
public class HibernateApplication extends Application<HibernateConfiguration> {

    public static void main(String[] args) throws Exception {
        new HibernateApplication().run(args);
    }

    private final HibernateBundle<HibernateConfiguration> hibernate = new HibernateBundle<HibernateConfiguration>(Person.class, Company.class, Destination.class, Request.class, Ride.class, Route.class, RouteDestinationMap.class, User.class) {
        public DataSourceFactory getDataSourceFactory(HibernateConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void run(HibernateConfiguration config, Environment environment) {
        SessionFactory sessionFactory = hibernate.getSessionFactory();

        final CompanyDAO companyDAO = new CompanyDAO(sessionFactory);
        final UserDAO userDAO = new UserDAO(sessionFactory);
        final DestinationDAO destinationDAO = new DestinationDAO(sessionFactory);
        final RouteDAO routeDAO = new RouteDAO(sessionFactory);
        final RideDAO rideDAO = new RideDAO(sessionFactory);
        final RequestDAO requestDAO = new RequestDAO(sessionFactory);
        final RouteDestinationMapDAO routeDestinationMapDAO = new RouteDestinationMapDAO(sessionFactory);
        final PublishedRideDAO publishedRideDAO = new PublishedRideDAO(sessionFactory);

        environment.jersey().register(new DestinationResource(destinationDAO));
        environment.jersey().register(new CompanyResource(companyDAO));
        environment.jersey().register(new UserResource(userDAO, companyDAO, destinationDAO, routeDAO, rideDAO, publishedRideDAO));
        environment.jersey().register(new RouteResource(routeDAO, userDAO, companyDAO, destinationDAO, routeDestinationMapDAO));
        environment.jersey().register(new RideResource(rideDAO, userDAO, requestDAO, destinationDAO,companyDAO,routeDAO,routeDestinationMapDAO,publishedRideDAO));
        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(new PersonResource(new PersonDAO(sessionFactory)));
    }

    @Override
    public void initialize(Bootstrap<HibernateConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }
}
