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
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "mailMapperEntityApi",
        version = "v1",
        resource = "mailMapperEntity",
        namespace = @ApiNamespace(
                ownerDomain = "Chat.backend.myapplication.Arnaud.example.com",
                ownerName = "Chat.backend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
@Deprecated
public class MailMapperEntityEndpoint {

    private static final Logger logger = Logger.getLogger(MailMapperEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(MailMapperEntity.class);
    }

    /**
     * Returns the {@link MailMapperEntity} with the corresponding ID.
     *
     * @param userMail the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code MailMapperEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "mailMapperEntity/{userMail}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public MailMapperEntity get(@Named("userMail") String userMail) throws NotFoundException {
        logger.info("Getting MailMapperEntity with ID: " + userMail);
        MailMapperEntity mailMapperEntity = ofy().load().type(MailMapperEntity.class).id(userMail).now();
        if (mailMapperEntity == null) {
            throw new NotFoundException("Could not find MailMapperEntity with ID: " + userMail);
        }
        return mailMapperEntity;
    }

    /**
     * Inserts a new {@code MailMapperEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "mailMapperEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public MailMapperEntity insert(MailMapperEntity mailMapperEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that mailMapperEntity.userMail has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(mailMapperEntity).now();
        logger.info("Created MailMapperEntity.");

        return ofy().load().entity(mailMapperEntity).now();
    }

    /**
     * Updates an existing {@code MailMapperEntity}.
     *
     * @param userMail         the ID of the entity to be updated
     * @param mailMapperEntity the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code userMail} does not correspond to an existing
     *                           {@code MailMapperEntity}
     */
    @ApiMethod(
            name = "update",
            path = "mailMapperEntity/{userMail}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public MailMapperEntity update(@Named("userMail") String userMail, MailMapperEntity mailMapperEntity) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(userMail);
        ofy().save().entity(mailMapperEntity).now();
        logger.info("Updated MailMapperEntity: " + mailMapperEntity);
        return ofy().load().entity(mailMapperEntity).now();
    }

    /**
     * Deletes the specified {@code MailMapperEntity}.
     *
     * @param userMail the ID of the entity to delete
     * @throws NotFoundException if the {@code userMail} does not correspond to an existing
     *                           {@code MailMapperEntity}
     */
    @ApiMethod(
            name = "remove",
            path = "mailMapperEntity/{userMail}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("userMail") String userMail) throws NotFoundException {
        checkExists(userMail);
        ofy().delete().type(MailMapperEntity.class).id(userMail).now();
        logger.info("Deleted MailMapperEntity with ID: " + userMail);
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
            path = "mailMapperEntity",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<MailMapperEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<MailMapperEntity> query = ofy().load().type(MailMapperEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<MailMapperEntity> queryIterator = query.iterator();
        List<MailMapperEntity> mailMapperEntityList = new ArrayList<MailMapperEntity>(limit);
        while (queryIterator.hasNext()) {
            mailMapperEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<MailMapperEntity>builder().setItems(mailMapperEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String userMail) throws NotFoundException {
        try {
            ofy().load().type(MailMapperEntity.class).id(userMail).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find MailMapperEntity with ID: " + userMail);
        }
    }
}