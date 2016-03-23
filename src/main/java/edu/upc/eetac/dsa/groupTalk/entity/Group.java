package edu.upc.eetac.dsa.groupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.groupTalk.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Marti on 25/02/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {
    @InjectLinks({
            @InjectLink(resource = GroupTalkRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "GroupTalk Root API"),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-group", title = "Current group"),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-group", title = "Create group", type = MediaType.APPLICATION_FORM_URLENCODED), //ONLY ADMIN
            @InjectLink(resource = GroupResource.class, method = "getGroupById", style = InjectLink.Style.ABSOLUTE, rel = "self group", title = "Group", bindings = @Binding(name = "id", value = "${instance.id}")),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "subscribe-group", title = "Subscribe group", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = ThemeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-theme", title = "Current theme"),
            @InjectLink(resource = ThemeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-theme", title = "Create theme", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })
    private List<Link> links;
    private List<User> users;
    private ThemeCollection collectionThemes;
    private String id;
    private String name;
    private long creationTimestamp;
    private long lastModified;

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<User> getUsers() {
        return users;
    }

    public ThemeCollection getCollectionThemes() {
        return collectionThemes;
    }

    public void setCollectionThemes(ThemeCollection collectionThemes) {
        this.collectionThemes = collectionThemes;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
