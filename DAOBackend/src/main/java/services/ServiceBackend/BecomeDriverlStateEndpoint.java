package services.ServiceBackend;

import com.example.Arnaud.myapplication.backend.DAL_facade;
import com.example.Arnaud.myapplication.backend.EventEntity;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "becomeDriverlStateApi",
        version = "v1",
        resource = "becomeDriverlState",
        namespace = @ApiNamespace(
                ownerDomain = "ServiceBackend.myapplication.Arnaud.example.com",
                ownerName = "ServiceBackend.myapplication.Arnaud.example.com",
                packagePath = ""
        )
)
public class BecomeDriverlStateEndpoint {

    private static final Logger logger = Logger.getLogger(BecomeDriverlStateEndpoint.class.getName());
    private static DAL_facade facade = new DAL_access();
    /**
     * This method gets the <code>BecomeDriverlState</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>BecomeDriverlState</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getBecomeDriverlState")
    public BecomeDriverlState getBecomeDriverlState(@Named("id") Long id) throws NotFoundException {
        logger.info("Calling getBecomeDriverlState method");

        EventEntity event = facade.getEvent(id);

        BecomeDriverlState becomeDriverlState = new BecomeDriverlState();
        becomeDriverlState.event_id = event.getId();
        becomeDriverlState.event_name = event.getName();

        logger.info("Send "+ becomeDriverlState);
        return becomeDriverlState;
    }

    /**
     * This inserts a new <code>BecomeDriverlState</code> object.
     *
     * @param becomeDriverlState The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertBecomeDriverlState")
    public BecomeDriverlState insertBecomeDriverlState(BecomeDriverlState becomeDriverlState) {

        logger.info("Calling insertBecomeDriverlState method");
        return becomeDriverlState;
    }
}