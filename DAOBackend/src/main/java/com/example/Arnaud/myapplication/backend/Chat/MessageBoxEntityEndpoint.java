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
import java.util.Comparator;
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
        name = "messageBoxEntityApi",
        version = "v1",
        resource = "mailBoxEntity",
        namespace = @ApiNamespace(
                ownerDomain = "Chat.backend.myapplication.Arnaud.example.com",
                ownerName = "Chat.backend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
public class MessageBoxEntityEndpoint {

    private static final Logger logger = Logger.getLogger(MessageBoxEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(MessageBoxEntity.class);
        ObjectifyService.register(Message.class);
        ObjectifyService.register(MailMapperEntity.class);
    }

    /**
     * Returns the {@link MessageBoxEntity} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code MessageBoxEntity} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "mailBoxEntity/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public MessageBoxEntity get(@Named("id") String id) throws NotFoundException {
        logger.info("Getting MessageBoxEntity with ID: " + id);
        MessageBoxEntity messageBoxEntity = ofy().load().type(MessageBoxEntity.class).id(id).now();
        if (messageBoxEntity == null) {
            throw new NotFoundException("Could not find MessageBoxEntity with ID: " + id);
        }
        return messageBoxEntity;
    }


    /**
     * Inserts a new {@code MessageBoxEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "mailBoxEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insert(Message message) {
        ofy().save().entity(message).now();
        message = ofy().load().entity(message).now();

        Comparator<String> comparator = new StringComparator();
        String[] interlocutors = new String[2];
        try{
            interlocutors[0]= message.getSender();
            interlocutors[1]=  message.getReceiver();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
            return;
        }
        String mailBoxID = null;
        if (0 < comparator.compare(interlocutors[0],interlocutors[1]))
            mailBoxID = interlocutors[0]+interlocutors[1];
        else
            mailBoxID = interlocutors[1]+interlocutors[0];


        MessageBoxEntity mailBox=null;
        try {
            mailBox = ofy().load().type(MessageBoxEntity.class).id(mailBoxID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) { }
        if (mailBox == null)
            mailBox= new MessageBoxEntity(mailBoxID);

        mailBox.addMessage(message);
        ofy().save().entity(mailBox).now();
        mailBox = ofy().load().entity(mailBox).now();

        for (int cpt =0 ; cpt< interlocutors.length; ++cpt){
            MailMapperEntity mailMapper = null;
            try {
                mailMapper = ofy().load().type(MailMapperEntity.class).id(interlocutors[cpt]).safe();
            } catch (com.googlecode.objectify.NotFoundException e) { }
            if (mailMapper == null)
                mailMapper= new MailMapperEntity(interlocutors[cpt]);
            mailMapper.addMailBox(mailBox);
            ofy().save().entity(mailMapper);


        }
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Message.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Message with ID: " + id);
        }
    }

    private class StringComparator implements Comparator<String>
    {

        @Override
        public int compare(String o1, String o2) {
            int rep;

            rep = o1.length()-o2.length();
            if(rep != 0)
                return rep;

            rep = o1.hashCode()-o2.hashCode();
            if (rep != 0)
                return rep;

            for ( int index = 0 ; index < o1.length();++index){
                rep = o1.charAt(index) - o2.charAt(index);
                if (rep != 0)
                    return rep;
            }

            return rep;
        }
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
    public CollectionResponse<MessageBoxEntity> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<MessageBoxEntity> query = ofy().load().type(MessageBoxEntity.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<MessageBoxEntity> queryIterator = query.iterator();
        List<MessageBoxEntity> messageBoxEntityList = new ArrayList<MessageBoxEntity>(limit);
        while (queryIterator.hasNext()) {
            messageBoxEntityList.add(queryIterator.next());
        }
        return CollectionResponse.<MessageBoxEntity>builder().setItems(messageBoxEntityList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(String id) throws NotFoundException {
        try {
            ofy().load().type(MessageBoxEntity.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find MessageBoxEntity with ID: " + id);
        }
    }
}