package edu.upc.eetac.dsa.groupTalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Marti on 02/03/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupTalkError {
    private int status;
    private String reason;

    public GroupTalkError() {

    }

    public GroupTalkError(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
