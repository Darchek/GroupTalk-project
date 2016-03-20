package edu.upc.eetac.dsa.groupTalk.dao;

import edu.upc.eetac.dsa.groupTalk.entity.Comment;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marti on 05/03/2016.
 */
public interface CommentDAO {

    public Comment createComment(String userid, String themeid, String answer) throws SQLException;

    public Comment getCommentById(String id) throws SQLException;

    public List<Comment> getCommentsByThemeId(String id) throws SQLException;

    public Comment updateComment(String id, String answer) throws SQLException;

    public boolean deleteComment(String id) throws SQLException;
}
