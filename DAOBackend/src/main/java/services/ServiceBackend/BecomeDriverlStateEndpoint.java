package services.ServiceBackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

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

    /**
     * This method gets the <code>BecomeDriverlState</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>BecomeDriverlState</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getBecomeDriverlState")
    public BecomeDriverlState getBecomeDriverlState(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getBecomeDriverlState method");
        return null;
    }

    /**
     * This inserts a new <code>BecomeDriverlState</code> object.
     *
     * @param becomeDriverlState The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertBecomeDriverlState")
    public BecomeDriverlState insertBecomeDriverlState(BecomeDriverlState becomeDriverlState) {
        // TODO: Implement this function
        logger.info("Calling insertBecomeDriverlState method");
        return becomeDriverlState;
    }
}