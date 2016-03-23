package edu.upc.eetac.dsa.groupTalk.entity;

import edu.upc.eetac.dsa.groupTalk.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Marti on 21/03/2016.
 */
public class GroupTalkRootAPI {
    @InjectLinks({
            @InjectLink(resource = GroupTalkRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self bookmark home", title = "GroupTalk Root API"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "login", title = "Login",  type= GroupTalkMediaType.GROUPTALK_AUTH_TOKEN),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-groups", title = "Current groups", type= GroupTalkMediaType.GROUPTALK_GROUP_COLLECTION),
            @InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-user", title = "Register", type= GroupTalkMediaType.GROUPTALK_AUTH_TOKEN),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout", condition="${!empty resource.userid}"),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-group", title = "Create group", condition="${resource.isAdmin}", type=GroupTalkMediaType.GROUPTALK_GROUP),
            @InjectLink(resource = UserResource.class, method="getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", condition="${!empty resource.userid}", type=GroupTalkMediaType.GROUPTALK_USER, bindings = @Binding(name = "id", value = "${resource.userid}"))
    })
    private List<Link> links;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
