package com.example.Arnaud.myapplication.backend.Chat;

import com.example.Arnaud.myapplication.backend.UserEntity;
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
        name = "messageEntityApi",
        version = "v1",
        resource = "messageEntity",
        namespace = @ApiNamespace(
                ownerDomain = "Chat.backend.myapplication.Arnaud.example.com",
                ownerName = "Chat.backend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
public class MessageEntityEndpoint {

    private static final Logger logger = Logger.getLogger(MessageEntityEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;


    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(MessageEntity.class);
        ObjectifyService.register(MailBoxEntity.class);
        ObjectifyService.register(UserEntity.class);
    }


    /**
     * Inserts a new {@code MessageEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "messageEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insert(MessageEntity messageEntity) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that messageEntity.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(messageEntity).now();
        Comparator<String> comparator = new StringComparator();
        String discutionId[] = new String[2];
        if (0 < comparator.compare(messageEntity.getSender(), messageEntity.getReceiver())){
            discutionId[0] = messageEntity.getSender();
            discutionId[1] = messageEntity.getReceiver();
        }

        else{
            discutionId[1] = messageEntity.getSender();
            discutionId[0] = messageEntity.getReceiver();
        }


        MailBoxEntity mb=null;
        String concat = discutionId[0]+discutionId[1];
        UserEntity[] users = new UserEntity[0];
        try {
            mb = ofy().load().type(MailBoxEntity.class).id(concat).safe();
        } catch (com.googlecode.objectify.NotFoundException e) { }
        if (mb == null){
            mb= new MailBoxEntity(concat);
            users = new UserEntity[2];
            users[0]=ofy().load().type(UserEntity.class).id(discutionId[0]).now();
            users[1]=ofy().load().type(UserEntity.class).id(discutionId[1]).now();
        }


        mb.addMessage(messageEntity);
         ofy().save().entity(mb);

        for (int cpt = 0; cpt< users.length;++ cpt){
            users[cpt].addMailBox(mb);
            ofy().save().entity(users[cpt]);
        }


    }



    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(MessageEntity.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find MessageEntity with ID: " + id);
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
}