package edu.upc.eetac.dsa.groupTalk;

import edu.upc.eetac.dsa.groupTalk.entity.GroupTalkRootAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by Marti on 21/03/2016.
 */
@Path("/")
public class GroupTalkRootAPIResource {
    @Context
    private SecurityContext securityContext;

    private String userid;
    private boolean isAdmin;

    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_ROOT)
    public GroupTalkRootAPI getRootAPI(){
        if(securityContext.getUserPrincipal() != null) {
            userid = securityContext.getUserPrincipal().getName();
            isAdmin = securityContext.isUserInRole("administrator");
        }
        GroupTalkRootAPI groupTalkRootAPI = new GroupTalkRootAPI();
        return groupTalkRootAPI;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean getIsAdmin() { return isAdmin; }

    public void setIsAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

}