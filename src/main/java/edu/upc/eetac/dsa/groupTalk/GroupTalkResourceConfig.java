package edu.upc.eetac.dsa.groupTalk;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Marti on 25/02/2016.
 */
public class GroupTalkResourceConfig extends ResourceConfig {

    public GroupTalkResourceConfig() {
        packages("edu.upc.eetac.dsa.groupTalk");
    }
}
