package com.example.Arnaud.myapplication.backend;

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
        name = "driverEntityApi",
        version = "v1",
        resource = "driverEntity",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Arnaud.example.com",
                ownerName = "backend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
public class DriverEntityEndpoint {

    private static final Logger logger = Logger.getLogger(DriverEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(DriverEntity.class);
    }

    /**
     * Returns the {@link DriverEntity} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code DriverEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "driverEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public DriverEntity get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting DriverEntity with ID: " + id);
        DriverEntity driverEntity = ofy().load().type(DriverEntity.class).id(id).now();
        if (driverEntity == null) {
            throw new NotFoundException("Could not find DriverEntity with ID: " + id);
        }
        return driverEntity;
    }

    /**
     * Inserts a new {@code DriverEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "driverEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public DriverEntity insert(DriverEntity driverEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that driverEntity.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(driverEntity).now();
        logger.info("Created DriverEntity with ID: " + driverEntity.getId());

        return ofy().load().entity(driverEntity).now();
    }

    /**
     * Updates an existing {@code DriverEntity}.
     *
     * @param id           the ID of the entity to be updated
     * @param driverEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code DriverEntity}
     */
    @ApiMethod(
            name = "update",
            path = "driverEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public DriverEntity update(@Named("id") Long id, DriverEntity driverEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(driverEntity).now();
        logger.info("Updated DriverEntity: " + driverEntity);
        return ofy().load().entity(driverEntity).now();
    }

    /**
     * Deletes the specified {@code DriverEntity}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code DriverEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "driverEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(DriverEntity.class).id(id).now();
        logger.info("Deleted DriverEntity with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "driverEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<DriverEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<DriverEntity> query = ofy().load().type(DriverEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<DriverEntity> queryIterator = query.iterator();
        List<DriverEntity> driverEntityList = new ArrayList<DriverEntity>(limit);
        while (queryIterator.hasNext()) {
            driverEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<DriverEntity>builder().setItems(driverEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(DriverEntity.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find DriverEntity with ID: " + id);
        }
    }
}