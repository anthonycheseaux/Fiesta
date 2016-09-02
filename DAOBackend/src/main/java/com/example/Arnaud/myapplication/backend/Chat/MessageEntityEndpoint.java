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
        ObjectifyService.register(MailBoxEntity.class);
        ObjectifyService.register(MailMapperEntity.class);
    }


    /**
     * Inserts a new {@code MessageEntity}.
     */
    @ApiMethod(
            name = "insert",
            path = "messageEntity",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insert(MessageEntity messageEntity) {

        Comparator<String> comparator = new StringComparator();
        String[] interlocutors = new String[2];
        try{
            interlocutors[0]= messageEntity.getSender();
            interlocutors[1]=  messageEntity.getReceiver();
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


        MailBoxEntity mailBox=null;
        try {
            mailBox = ofy().load().type(MailBoxEntity.class).id(mailBoxID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) { }
        if (mailBox == null)
            mailBox= new MailBoxEntity(mailBoxID);

        mailBox.addMessage(messageEntity);
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