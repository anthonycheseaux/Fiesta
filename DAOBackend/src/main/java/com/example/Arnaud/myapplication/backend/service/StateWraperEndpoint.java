package com.example.Arnaud.myapplication.backend.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "stateWraperApi",
        version = "v1",
        resource = "stateWraper",
        namespace = @ApiNamespace(
                ownerDomain = "service.backend.myapplication.Arnaud.example.com",
                ownerName = "service.backend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
public class StateWraperEndpoint {

    private static final Logger logger = Logger.getLogger(StateWraperEndpoint.class.getName());

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(StateWraper.class);
    }

    /**
     * Give the initial State
     * @return one initial state
     */
    @ApiMethod(
            name = "get",
            path = "stateWraper/",
            httpMethod = ApiMethod.HttpMethod.GET)
    public StateWraper get()  {
        logger.info("give a new initial state to client");
        return new StateWraper(new BackInsciptionState());
    }



    /**
     * Updates an existing {@code StateWraper}.
     *
     * @param stateWraper the desired state of the entity
     * @return the updated version of the entity
     */
    @ApiMethod(
            name = "update",
            path = "stateWraper/",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public StateWraper update(StateWraper stateWraper)  {
        StateWraper respons = null;

    return respons;
    }

    /**
     * Deletes the specified {@code StateWraper}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code StateWraper}
     */
    @ApiMethod(
            name = "remove",
            path = "stateWraper/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(StateWraper.class).id(id).now();
        logger.info("Deleted StateWraper with ID: " + id);
    }



    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(StateWraper.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find StateWraper with ID: " + id);
        }
    }
}