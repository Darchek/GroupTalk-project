package edu.upc.eetac.dsa.groupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.groupTalk.GroupTalkRootAPIResource;
import edu.upc.eetac.dsa.groupTalk.LoginResource;
import edu.upc.eetac.dsa.groupTalk.ThemeResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marti on 02/03/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThemeCollection {
    @InjectLinks({
            @InjectLink(resource = GroupTalkRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "GroupTalk Root API"),
            @InjectLink(resource = ThemeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-themes", title = "Current themes"),
            @InjectLink(resource = ThemeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-themes", title = "Current themes"),
            @InjectLink(resource = ThemeResource.class, method = "getThemes", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer themes", bindings = {@Binding(name = "groupid", value = "${instance.groupid}"), @Binding(name = "timestamp", value = "${instance.newestTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = ThemeResource.class, method = "getThemes", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older themes", bindings = {@Binding(name = "groupid", value = "${instance.groupid}"), @Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "true")}),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private String groupid;
    private List<Theme> themes = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }
}
