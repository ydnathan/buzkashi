package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.entities.core.Company;
import com.example.helloworld.dao.CompanyDAO;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by vaidyanathan.s on 10/05/15.
 */
@Path("/company")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyResource {
    private CompanyDAO dao;

    public CompanyResource(CompanyDAO dao) {
        this.dao = dao;
    }

    @POST
    @Timed
    @Path("add")
    @UnitOfWork
    public Long addCompany(@FormParam("company_name") Optional<String> companyName,
                           @FormParam("address_code") Optional<String> addressCode,
                           @FormParam("email_domain") Optional<String> emailDomain,
                           @FormParam("address_text") Optional<String> addressText,
                           @FormParam("address_lat_lon") Optional<String> addressLatLon,
                           @FormParam("city") Optional<String> city,
                           @FormParam("state") Optional<String> state) {
        return dao.create(new Company(companyName.get(), addressCode.get(), emailDomain.get(), addressText.get(), addressLatLon.get(), city.get(), state.get()));
    }

    @GET
    @Timed
    @Path("find")
    @UnitOfWork
    public Company findCompany(@QueryParam("id") Optional<Long> company_id) {
        return dao.findById(company_id.get());
    }

    @GET
    @Timed
    @Path("all")
    @UnitOfWork
    public List<Company> allCompanies() {
        return dao.findAll();
    }
}
