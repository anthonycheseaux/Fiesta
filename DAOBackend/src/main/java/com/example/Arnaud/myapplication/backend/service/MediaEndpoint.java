package com.example.Arnaud.myapplication.backend.service;

import com.example.Arnaud.myapplication.backend.Chat.MailBoxEntity;
import com.example.Arnaud.myapplication.backend.Chat.MessageEntity;
import com.example.Arnaud.myapplication.backend.DrinkerEntity;
import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.LiftMapperEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.example.Arnaud.myapplication.backend.requestManager.Facade;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.ObjectifyService;

import java.util.logging.Logger;

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
        name = "mediaApi",
        version = "v1",
        resource = "media",
        namespace = @ApiNamespace(
                ownerDomain = "service.backend.myapplication.Arnaud.example.com",
                ownerName = "service.backend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
public class MediaEndpoint {

    private final static Manager manager = new Facade();

    private static final Logger logger = Logger.getLogger(MediaEndpoint.class.getName());

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Media.class);
        ObjectifyService.register(UserEntity.class);
        ObjectifyService.register(LiftEntity.class);
        ObjectifyService.register(EventEntity.class);
        ObjectifyService.register(MailBoxEntity.class);
    }

    /**
     * Returns the {@link Media} with the corresponding ID.
     *
     * @return the entity with the corresponding ID
     */
    @ApiMethod(
            name = "get",
            path = "media/",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Media get() {
        logger.info("Getting Media");
        return manager.getInitalState();
    }


    /**
     * Updates an existing {@code Media}.
     *
     * @param media the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Media}
     */
    @ApiMethod(
            name = "update",
            path = "media/",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Media update(Media media) throws NotFoundException {
        return manager.manage(media);
    }

}