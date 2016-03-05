package edu.upc.eetac.dsa.groupTalk.dao;

/**
 * Created by Marti on 05/03/2016.
 */
public interface ThemeDAOQuery {
    public final static String CREATE_THEME = "insert into themes (id, userid, groupid, title, content) " +
            "values (UNHEX(?), UNHEX(?), UNHEX(?), ?, ?)";
    public final static String GET_THEME_BY_ID = "select hex(t.id) as tid, hex(t.userid) as tuserid, t.title as ttitle, " +
            "t.content as tcontent, t.last_modified as tlast, t.creation_timestamp as tcreation, hex(c.id) as cid, " +
            "hex(c.userid) as cuserid, c.answer as canswer, c.last_modified as clast, c.creation_timestamp as ccreation " +
            "from themes as t join comments as c on t.id = c.themeid where t.id = unhex(?)";
    public final static String GET_THEMES = "select hex(id) as id, hex(userid) as userid, title, content, last_modified, creation_timestamp from themes";
    public final static String UPDATE_THEME = "update themes set title=?, content=? where id=unhex(?) ";
    public final static String DELETE_THEME = "delete from themes where id=unhex(?)";
}
