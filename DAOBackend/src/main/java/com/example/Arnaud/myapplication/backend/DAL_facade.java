package com.example.Arnaud.myapplication.backend;


import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;

import javax.annotation.Nullable;
import javax.inject.Named;

/**
 * Created by Arnaud on 22.07.2016.
 */
public interface DAL_facade {
    public void 								removeDrinker	(@Named("id") Long id) throws NotFoundException;
    public void 							    removeDriver    (@Named("id") Long id) throws NotFoundException;

    public EventEntity						    getEvent	    (@Named("id") Long id) throws NotFoundException;
    public EventEntity						    insertEvent	    (EventEntity eventEntity);
    public EventEntity						    updateEvent	    (@Named("id") Long id, EventEntity eventEntity) throws NotFoundException;
    public void								    removeEvent	    (@Named("id") Long id) throws NotFoundException;
    public CollectionResponse<EventEntity>	    listEvent	    (@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit);


    public LiftEntity 						    getLift		    (@Named("id") Long id) throws NotFoundException;
    public LiftEntity 						    insertLift	    (LiftEntity liftEntity);
    public LiftEntity 						    updateLift	    (@Named("id") Long id, LiftEntity liftEntity) throws NotFoundException;
    public void 							    removeLift	    (@Named("id") Long id) throws NotFoundException;
    public CollectionResponse<LiftEntity>	    listLift	    (@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit);


    public UserEntity 						    getUser		    (@Named("id") Long id) throws NotFoundException;
    public UserEntity 						    insertUser	    (UserEntity userEntity);
    public UserEntity 						    updateUser	    (@Named("id") Long id, UserEntity userEntity) throws NotFoundException;
    public void 							    removeUser	    (@Named("id") Long id) throws NotFoundException;
    public CollectionResponse<UserEntity>	    listUser	    (@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit);
}
