package com.example.Arnaud.myapplication.backend.requestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Arnaud on 02.09.2016.
 */
final class _NavigationsRules {
    public final static String SN_INSCRIPTION_STATE = "inscription_state";
    public final static String SN_CREATE_TRANSPORT_STATE = "create_transport_state";
    public final static String SN_SEARCH_TRANSPORT_STATE = "search_transport_state";
    public final static String SN_IN_LIFT_STATE = "in_lift_sate";
    public final static String SN_MANAGE_LIFT = "manage_lift";

    final static String CONNECTION_TO=">>";

    private static HashMap<String ,List<String>> paths;

    static {
        paths = new HashMap<String ,List<String>>(8);

        List<String> from_inscription = new ArrayList(3);
        from_inscription.add(SN_INSCRIPTION_STATE);
        from_inscription.add(SN_CREATE_TRANSPORT_STATE);
        from_inscription.add(SN_SEARCH_TRANSPORT_STATE);

        List<String> from_createTrsp = new ArrayList(2);
        from_createTrsp.add(SN_CREATE_TRANSPORT_STATE);
        from_createTrsp.add(SN_MANAGE_LIFT);

        List<String> from_searchTransp = new ArrayList(2);
        from_searchTransp.add(SN_SEARCH_TRANSPORT_STATE);
        from_searchTransp.add(SN_IN_LIFT_STATE);

        List<String> from_manageLift = new ArrayList(1);
        from_manageLift.add(SN_MANAGE_LIFT);
        from_manageLift.add(SN_INSCRIPTION_STATE);

        List<String> from_inLift = new ArrayList(1);
        from_inLift.add(SN_IN_LIFT_STATE);

        paths.put(SN_INSCRIPTION_STATE,from_inscription);
        paths.put(SN_CREATE_TRANSPORT_STATE, from_createTrsp);
        paths.put(SN_SEARCH_TRANSPORT_STATE, from_searchTransp);
        paths.put(SN_MANAGE_LIFT,from_manageLift);
        paths.put(SN_IN_LIFT_STATE, from_inLift);
    }
    public static List<String> getAvailablePathFor(String state){
        return paths.get(state);
    }

}
