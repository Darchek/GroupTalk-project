package edu.upc.eetac.dsa.groupTalk.dao;

import edu.upc.eetac.dsa.groupTalk.entity.Comment;
import edu.upc.eetac.dsa.groupTalk.entity.Theme;
import edu.upc.eetac.dsa.groupTalk.entity.ThemeCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Marti on 05/03/2016.
 */
public class ThemeDAOImpl implements ThemeDAO {
    @Override
    public Theme createTheme(String userid, String groupid, String title, String content) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(ThemeDAOQuery.CREATE_THEME);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, groupid);
            stmt.setString(4, title);
            stmt.setString(5, content);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getThemeById(id);
    }

    @Override
    public Theme getThemeById(String id) throws SQLException {
        Theme theme = new Theme();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ThemeDAOQuery.GET_THEME_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                if(first) {
                    theme.setId(rs.getString("tid"));
                    theme.setUserid(rs.getString("tuserid"));
                    theme.setTitle(rs.getString("ttitle"));
                    theme.setContent(rs.getString("tcontent"));
                    theme.setLastModified(rs.getTimestamp("tlast").getTime());
                    theme.setCreationTimestamp(rs.getTimestamp("tcreation").getTime());
                    first = false;
                }
                Comment comment = new Comment();
                comment.setId(rs.getString("cid"));
                comment.setUserid(rs.getString("cuserid"));
                comment.setAnswer(rs.getString("canswer"));
                comment.setLastModified(rs.getTimestamp("clast").getTime());
                comment.setCreationTimestamp(rs.getTimestamp("ccreation").getTime());
                theme.getComments().add(comment);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return theme;
    }

    @Override
    public ThemeCollection getThemes() throws SQLException {
        ThemeCollection themeCollection = new ThemeCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(ThemeDAOQuery.GET_THEMES);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Theme theme = new Theme();
                theme.setId(rs.getString("id"));
                theme.setUserid(rs.getString("userid"));
                theme.setTitle(rs.getString("title"));
                theme.setContent(rs.getString("content"));
                theme.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                theme.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    themeCollection.setNewestTimestamp(theme.getLastModified());
                    first = false;
                }
                themeCollection.setOldestTimestamp(theme.getLastModified());
                themeCollection.getThemes().add(theme);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return themeCollection;
    }

    @Override
    public Theme updateTheme(String id, String title, String content) throws SQLException {
        Theme theme = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ThemeDAOQuery.UPDATE_THEME);
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                theme = getThemeById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return theme;
    }

    @Override
    public boolean deleteTheme(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ThemeDAOQuery.DELETE_THEME);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
