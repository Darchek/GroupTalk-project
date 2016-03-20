package edu.upc.eetac.dsa.groupTalk.dao;

/**
 * Created by Marti on 05/03/2016.
 */
public interface ThemeDAOQuery {
    public final static String CREATE_THEME = "insert into themes (id, userid, groupid, title, content) " +
            "values (UNHEX(?), UNHEX(?), UNHEX(?), ?, ?)";
    public final static String GET_THEME_BY_ID = "select hex(id) as id, hex(userid) as userid, title, " +
            "content, last_modified, creation_timestamp from themes where id = unhex(?)";
    public final static String GET_COMMENTS_BY_THEME_ID = "select hex(id) as id, hex(userid) as userid, answer, " +
            "last_modified, creation_timestamp from comments where themeid = unhex(?)";
    public final static String GET_THEMES_BY_GROUP_ID = "select hex(id) as id, hex(userid) as userid, title, content, last_modified, creation_timestamp from themes where groupid = unhex(?)";
    public final static String UPDATE_THEME = "update themes set title=?, content=? where id=unhex(?) ";
    public final static String DELETE_THEME = "delete from themes where id=unhex(?)";
}