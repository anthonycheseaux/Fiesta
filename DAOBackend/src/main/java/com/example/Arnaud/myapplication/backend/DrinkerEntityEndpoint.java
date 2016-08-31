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
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "drinkerEntityApi",
        version = "v1",
        resource = "drinkerEntity",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Arnaud.example.com",
                ownerName = "backend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
public class DrinkerEntityEndpoint {

    private static final Logger logger = Logger.getLogger(DrinkerEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;
/*
    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(DrinkerEntity.class);
    }
*/
    /**
     * Returns the {@link DrinkerEntity} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code DrinkerEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "drinkerEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public DrinkerEntity get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting DrinkerEntity with ID: " + id);
        DrinkerEntity drinkerEntity = ofy().load().type(DrinkerEntity.class).id(id).now();
        if (drinkerEntity == null) {
            throw new NotFoundException("Could not find DrinkerEntity with ID: " + id);
        }
        return drinkerEntity;
    }

    /**
     * Inserts a new {@code DrinkerEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "drinkerEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public DrinkerEntity insert(DrinkerEntity drinkerEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that drinkerEntity.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(drinkerEntity).now();
        logger.info("Created DrinkerEntity with ID: " + drinkerEntity.getId());

        return ofy().load().entity(drinkerEntity).now();
    }

    /**
     * Updates an existing {@code DrinkerEntity}.
     *
     * @param id            the ID of the entity to be updated
     * @param drinkerEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code DrinkerEntity}
     */
    @ApiMethod(
            name = "update",
            path = "drinkerEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public DrinkerEntity update(@Named("id") Long id, DrinkerEntity drinkerEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(drinkerEntity).now();
        logger.info("Updated DrinkerEntity: " + drinkerEntity);
        return ofy().load().entity(drinkerEntity).now();
    }

    /**
     * Deletes the specified {@code DrinkerEntity}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code DrinkerEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "drinkerEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(DrinkerEntity.class).id(id).now();
        logger.info("Deleted DrinkerEntity with ID: " + id);
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
            path = "drinkerEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<DrinkerEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<DrinkerEntity> query = ofy().load().type(DrinkerEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<DrinkerEntity> queryIterator = query.iterator();
        List<DrinkerEntity> drinkerEntityList = new ArrayList<DrinkerEntity>(limit);
        while (queryIterator.hasNext()) {
            drinkerEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<DrinkerEntity>builder().setItems(drinkerEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(DrinkerEntity.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find DrinkerEntity with ID: " + id);
        }
    }
}