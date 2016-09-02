package com.example.Arnaud.myapplication.backend.Chat;

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
        name = "mailBoxEntityApi",
        version = "v1",
        resource = "mailBoxEntity",
        namespace = @ApiNamespace(
                ownerDomain = "Chat.backend.myapplication.Arnaud.example.com",
                ownerName = "Chat.backend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
public class MailBoxEntityEndpoint {

    private static final Logger logger = Logger.getLogger(MailBoxEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(MailBoxEntity.class);
        ObjectifyService.register(MessageEntity.class);
    }

    /**
     * Returns the {@link MailBoxEntity} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code MailBoxEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "mailBoxEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public MailBoxEntity get(@Named("id") String id) throws NotFoundException {
        logger.info("Getting MailBoxEntity with ID: " + id);
        MailBoxEntity mailBoxEntity = ofy().load().type(MailBoxEntity.class).id(id).now();
        if (mailBoxEntity == null) {
            throw new NotFoundException("Could not find MailBoxEntity with ID: " + id);
        }
        return mailBoxEntity;
    }

    /**
     * Inserts a new {@code MailBoxEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "mailBoxEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public MailBoxEntity insert(MailBoxEntity mailBoxEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that mailBoxEntity.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(mailBoxEntity).now();
        logger.info("Created MailBoxEntity.");

        return ofy().load().entity(mailBoxEntity).now();
    }

    /**
     * Updates an existing {@code MailBoxEntity}.
     *
     * @param id            the ID of the entity to be updated
     * @param mailBoxEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code MailBoxEntity}
     */
    @ApiMethod(
            name = "update",
            path = "mailBoxEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public MailBoxEntity update(@Named("id") String id, MailBoxEntity mailBoxEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(mailBoxEntity).now();
        logger.info("Updated MailBoxEntity: " + mailBoxEntity);
        return ofy().load().entity(mailBoxEntity).now();
    }

    /**
     * Deletes the specified {@code MailBoxEntity}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code MailBoxEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "mailBoxEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") String id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(MailBoxEntity.class).id(id).now();
        logger.info("Deleted MailBoxEntity with ID: " + id);
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
            path = "mailBoxEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<MailBoxEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<MailBoxEntity> query = ofy().load().type(MailBoxEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<MailBoxEntity> queryIterator = query.iterator();
        List<MailBoxEntity> mailBoxEntityList = new ArrayList<MailBoxEntity>(limit);
        while (queryIterator.hasNext()) {
            mailBoxEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<MailBoxEntity>builder().setItems(mailBoxEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String id) throws NotFoundException {
        try {
            ofy().load().type(MailBoxEntity.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find MailBoxEntity with ID: " + id);
        }
    }
}