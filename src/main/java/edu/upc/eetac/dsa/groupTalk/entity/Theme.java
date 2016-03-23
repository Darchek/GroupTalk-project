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
public class Theme {
    @InjectLinks({
            @InjectLink(resource = GroupTalkRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "GroupTalk Root API"),
            @InjectLink(resource = ThemeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-theme", title = "Current theme"),
            @InjectLink(resource = ThemeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-theme", title = "Create theme", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = ThemeResource.class, method = "getThemeById", style = InjectLink.Style.ABSOLUTE, rel = "self theme", title = "Theme", bindings = @Binding(name = "id", value = "${instance.id}")),
            @InjectLink(resource = ThemeResource.class, method = "getThemesByGroupId", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer themes", bindings = {@Binding(name = "groupid", value = "${instance.groupid}"), @Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = ThemeResource.class, method = "getThemesByGroupId", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older themes", bindings = {@Binding(name = "groupid", value = "${instance.groupid}"), @Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "true")}),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.userid}")),
            @InjectLink(resource = CommentResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-comment", title = "Current comment"),
            @InjectLink(resource = CommentResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-comment", title = "Create comment", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })
    private List<Link> links;
    private List<Comment> comments;
    private String id;
    private String userid;
    private String groupid;
    private String title;
    private String content;
    private long creationTimestamp;
    private long lastModified;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
