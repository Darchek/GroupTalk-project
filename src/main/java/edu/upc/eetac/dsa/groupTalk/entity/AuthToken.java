package edu.upc.eetac.dsa.groupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.groupTalk.*;
import edu.upc.eetac.dsa.groupTalk.auth.UserInfo;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Marti on 02/03/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthToken {
    @InjectLinks({
            @InjectLink(resource = GroupTalkRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "GroupTalk Root API"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self login", title = "Login", type= GroupTalkMediaType.GROUPTALK_AUTH_TOKEN),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-groups", title = "Current groups", type= GroupTalkMediaType.GROUPTALK_GROUP_COLLECTION),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-group", title = "Create group", condition="${instance.isAdmin}", type=GroupTalkMediaType.GROUPTALK_GROUP),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", type= GroupTalkMediaType.GROUPTALK_USER, bindings = @Binding(name = "id", value = "${instance.userid}"))
    })
    private List<Link> links;

    private String userid;
    private String token;
    private boolean isAdmin;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(UserInfo principal) {
        List<Role> roles = null;
        if (principal != null) roles = principal.getRoles();
        this.isAdmin = roles.contains(Role.valueOf("administrator"));
    }
}