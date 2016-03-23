package edu.upc.eetac.dsa.groupTalk;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by Marti on 25/02/2016.
 */
public class GroupTalkResourceConfig extends ResourceConfig {

    public GroupTalkResourceConfig() {
        packages("edu.upc.eetac.dsa.groupTalk");
        packages("edu.upc.eetac.dsa.groupTalk.auth");
        packages("edu.upc.eetac.dsa.beeter.cors");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
    }
}
