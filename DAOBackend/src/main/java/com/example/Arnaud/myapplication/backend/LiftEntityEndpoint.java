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
        name = "liftEntityApi",
        version = "v1",
        resource = "liftEntity",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Arnaud.example.com",
                ownerName = "backend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
public class LiftEntityEndpoint {

    private static final Logger logger = Logger.getLogger(LiftEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(LiftEntity.class);
    }

    /**
     * Returns the {@link LiftEntity} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code LiftEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "liftEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public LiftEntity get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting LiftEntity with ID: " + id);
        LiftEntity liftEntity = ofy().load().type(LiftEntity.class).id(id).now();
        if (liftEntity == null) {
            throw new NotFoundException("Could not find LiftEntity with ID: " + id);
        }


        logger.info("Getting EventEntity with ID: " + id);
        EventEntity eventEntity = ofy().load().type(EventEntity.class).id(liftEntity.getEvent().getId()).now();
        if (eventEntity == null) {
            throw new NotFoundException("Could not find EventEntity with ID: " + id);
        }

        return liftEntity;
    }

    /**
     * Inserts a new {@code LiftEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "liftEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public LiftEntity insert(LiftEntity liftEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that liftEntity.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(liftEntity).now();
        logger.info("Created LiftEntity with ID: " + liftEntity.getId());

        return ofy().load().entity(liftEntity).now();
    }

    /**
     * Updates an existing {@code LiftEntity}.
     *
     * @param id         the ID of the entity to be updated
     * @param liftEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code LiftEntity}
     */
    @ApiMethod(
            name = "update",
            path = "liftEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public LiftEntity update(@Named("id") Long id, LiftEntity liftEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(liftEntity).now();
        logger.info("Updated LiftEntity: " + liftEntity);
        return ofy().load().entity(liftEntity).now();
    }

    /**
     * Deletes the specified {@code LiftEntity}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code LiftEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "liftEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(LiftEntity.class).id(id).now();
        logger.info("Deleted LiftEntity with ID: " + id);
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
            path = "liftEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<LiftEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<LiftEntity> query = ofy().load().type(LiftEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<LiftEntity> queryIterator = query.iterator();
        List<LiftEntity> liftEntityList = new ArrayList<LiftEntity>(limit);
        while (queryIterator.hasNext()) {
            liftEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<LiftEntity>builder().setItems(liftEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(LiftEntity.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find LiftEntity with ID: " + id);
        }
    }
}