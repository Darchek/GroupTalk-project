package edu.upc.eetac.dsa.groupTalk.dao;

/**
 * Created by Marti on 05/03/2016.
 */
public interface CommentDAOQuery {
    public final static String CREATE_COMMENT = "insert into comments (id, userid, themeid, answer) values (UNHEX(?), UNHEX(?), UNHEX(?), ?)";
    public final static String GET_COMMENT_BY_ID = "select hex(id) as id, hex(userid) as userid, answer, " +
            "last_modified, creation_timestamp from comments where id=unhex(?)";
    public final static String UPDATE_COMMENT = "update comments set answer=? where id=unhex(?) ";
    public final static String DELETE_COMMENT = "delete from comments where id=unhex(?)";
}
