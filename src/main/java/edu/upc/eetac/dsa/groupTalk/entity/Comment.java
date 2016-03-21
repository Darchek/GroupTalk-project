package edu.upc.eetac.dsa.groupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Marti on 25/02/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {

    @InjectLinks({})
    private List<Link> links;
    private String id;
    private String userid;
    private String themeid;
    private String answer;
    private long creationTimestamp;
    private long lastModified;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
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

    public String getThemeid() {
        return themeid;
    }

    public void setThemeid(String themeid) {
        this.themeid = themeid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
