package edu.upc.eetac.dsa.groupTalk.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Marti on 25/02/2016.
 */
public class Group {
    @InjectLinks({})
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
