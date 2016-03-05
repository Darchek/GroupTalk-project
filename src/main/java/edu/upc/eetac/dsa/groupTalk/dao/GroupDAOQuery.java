package edu.upc.eetac.dsa.groupTalk.dao;

/**
 * Created by Marti on 04/03/2016.
 */
public interface GroupDAOQuery {
    public final static String CREATE_GROUP = "insert into groups (id, name) values (UNHEX(?), ?)";
    public final static String SUBSCRIBE_GROUP = "insert into group_user (groupid, userid) values (UNHEX(?), UNHEX(?))";
    public final static String GET_GROUP_BY_ID = "select hex(g.id) as gid, g.name as gname, g.last_modified as glast, " +
            "g.creation_timestamp as gcreation, hex(t.id) as tid, hex(t.userid) as tuserid, t.title as ttitle, " +
            "t.content as tcontent, t.last_modified as tlast, t.creation_timestamp as tcreation from groups as g " +
            "JOIN themes as t ON g.id = t.groupid where g.id = unhex(?)";
    public final static String GET_USERS_BY_GROUP_ID = "select hex(id) as id, loginid, email, fullname from users join group_user on users.id = group_user.userid where group_user.groupid = unhex(?)";
    public final static String GET_GROUPS = "select hex(id) as id, name, creation_timestamp, last_modified from groups";
    public final static String UPDATE_GROUP = "update groups set name=? where id=unhex(?) ";
    public final static String DELETE_GROUP = "delete from groups where id=unhex(?)";
}
