package services.ServiceBackend;

import com.example.Arnaud.myapplication.backend.DAL_facade;
import com.example.Arnaud.myapplication.backend.DrinkerEntity;
import com.example.Arnaud.myapplication.backend.DriverEntity;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.example.Arnaud.myapplication.backend.LiftEntity;
import com.example.Arnaud.myapplication.backend.UserEntity;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;

import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by darle on 22.07.2016.
 */
class DAL_access implements DAL_facade {

    private static final Logger logger = Logger.getLogger(DAL_access.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;
    @Override
    public DrinkerEntity getDrinker(@Named("id") Long id) throws NotFoundException {
        return null;
    }

    @Override
    public DrinkerEntity insertDrinker(DrinkerEntity drinkerEntity) {
        return null;
    }

    @Override
    public DrinkerEntity updateDrinker(@Named("id") Long id, DrinkerEntity drinkerEntity) throws NotFoundException {
        return null;
    }

    @Override
    public void removeDrinker(@Named("id") Long id) throws NotFoundException {

    }

    @Override
    public CollectionResponse<DrinkerEntity> listDrinker(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        return null;
    }

    @Override
    public DriverEntity getDriver(@Named("id") Long id) throws NotFoundException {
        return null;
    }

    @Override
    public DriverEntity insertDriver(DriverEntity driverEntity) {
        return null;
    }

    @Override
    public DriverEntity updateDriver(@Named("id") Long id, DriverEntity driverEntity) throws NotFoundException {
        return null;
    }

    @Override
    public void removeDriver(@Named("id") Long id) throws NotFoundException {

    }

    @Override
    public CollectionResponse<DriverEntity> listDriver(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        return null;
    }

    @Override
    public EventEntity getEvent(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting EventEntity with ID: " + id);
        EventEntity eventEntity = ofy().load().type(EventEntity.class).id(id).now();
        if (eventEntity == null) {
            throw new NotFoundException("Could not find EventEntity with ID: " + id);
        }
        return eventEntity;
    }

    @Override
    public EventEntity insertEvent(EventEntity eventEntity) {
        return null;
    }

    @Override
    public EventEntity updateEvent(@Named("id") Long id, EventEntity eventEntity) throws NotFoundException {
        return null;
    }

    @Override
    public void removeEvent(@Named("id") Long id) throws NotFoundException {

    }

    @Override
    public CollectionResponse<EventEntity> listEvent(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        return null;
    }

    @Override
    public LiftEntity getLift(@Named("id") Long id) throws NotFoundException {
        return null;
    }

    @Override
    public LiftEntity insertLift(LiftEntity liftEntity) {
        return null;
    }

    @Override
    public LiftEntity updateLift(@Named("id") Long id, LiftEntity liftEntity) throws NotFoundException {
        return null;
    }

    @Override
    public void removeLift(@Named("id") Long id) throws NotFoundException {

    }

    @Override
    public CollectionResponse<LiftEntity> listLift(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        return null;
    }

    @Override
    public UserEntity getUser(@Named("id") Long id) throws NotFoundException {
        return null;
    }

    @Override
    public UserEntity insertUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity updateUser(@Named("id") Long id, UserEntity userEntity) throws NotFoundException {
        return null;
    }

    @Override
    public void removeUser(@Named("id") Long id) throws NotFoundException {

    }

    @Override
    public CollectionResponse<UserEntity> listUser(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        return null;
    }
}
