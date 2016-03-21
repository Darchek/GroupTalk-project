package edu.upc.eetac.dsa.groupTalk.dao;

/**
 * Created by Marti on 04/03/2016.
 */
public interface GroupDAOQuery {
    public final static String CREATE_GROUP = "insert into groups (id, name) values (UNHEX(?), ?)";
    public final static String SUBSCRIBE_GROUP = "insert into group_user (groupid, userid) values (UNHEX(?), UNHEX(?))";
    public final static String GET_GROUP_BY_ID = "select hex(g.id) as id, g.name as name, g.last_modified as last, " +
            "g.creation_timestamp as creation from groups as g where g.id = unhex(?)";
    public final static String GET_GROUPS_BY_USER_ID = "select hex(id) as id, name, last_modified, creation_timestamp from groups JOIN group_user ON groups.id = group_user.groupid WHERE group_user.userid = unhex(?)";
    public final static String GET_GROUPS = "select hex(id) as id, name, creation_timestamp, last_modified from groups";
    public final static String UPDATE_GROUP = "update groups set name=? where id=unhex(?) ";
    public final static String DELETE_GROUP = "delete from groups where id=unhex(?)";
    public final static String CHECK_USER_SUBSCRIBE = "select hex(userid) as userid, hex(groupid) as groupid from group_user where userid = unhex(?) and groupid = unhex(?)";
}