package edu.upc.eetac.dsa.groupTalk.dao;

import edu.upc.eetac.dsa.groupTalk.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marti on 04/03/2016.
 */
public class GroupDAOImpl implements GroupDAO {

    @Override
    public Group createGroup(String userid, String name) throws SQLException {
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

            stmt = connection.prepareStatement(GroupDAOQuery.CREATE_GROUP);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.executeUpdate();
            subscribeGroup(id, userid);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getGroupById(id);
    }

    @Override
    public boolean subscribeGroup(String groupid, String userid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GroupDAOQuery.SUBSCRIBE_GROUP);
            stmt.setString(1, groupid);
            stmt.setString(2, userid);
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
        return true;
    }

    @Override
    public Group getGroupById(String id) throws SQLException {
        Group group = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.GET_GROUP_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                group = new Group();
                group.setId(rs.getString("id"));
                group.setName(rs.getString("name"));
                group.setLastModified(rs.getTimestamp("last").getTime());
                group.setCreationTimestamp(rs.getTimestamp("creation").getTime());
                group.setUsers(getUsersByGroupId(id));
                group.setCollectionThemes(getThemesByGroupId(id));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return group;
    }

    @Override
    public ThemeCollection getThemesByGroupId(String id) throws SQLException {
        ThemeCollection collectionThemes = new ThemeCollection();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.GET_THEMES_BY_GROUP_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Theme theme = new Theme();
                theme.setId(rs.getString("id"));
                theme.setUserid(rs.getString("userid"));
                theme.setTitle(rs.getString("title"));
                theme.setContent(rs.getString("content"));
                theme.setLastModified(rs.getTimestamp("last_modified").getTime());
                theme.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                if(first) {
                    collectionThemes.setNewestTimestamp((rs.getTimestamp("last_modified").getTime()));
                    first = false;
                }
                collectionThemes.setOldestTimestamp(rs.getTimestamp("last_modified").getTime());
                collectionThemes.getThemes().add(theme);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return collectionThemes;
    }

    @Override
    public List<User> getUsersByGroupId(String id) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.GET_USERS_BY_GROUP_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setLoginid(rs.getString("loginid"));
                user.setEmail(rs.getString("email"));
                user.setFullname(rs.getString("fullname"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return users;
    }

    @Override
    public GroupCollection getGroups() throws SQLException {
        GroupCollection groupCollection = new GroupCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GroupDAOQuery.GET_GROUPS);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getString("id"));
                group.setName(rs.getString("name"));
                group.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                group.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    groupCollection.setNewestTimestamp(group.getLastModified());
                    first = false;
                }
                groupCollection.setOldestTimestamp(group.getLastModified());
                groupCollection.getGroups().add(group);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return groupCollection;
    }

    @Override
    public Group updateGroup(String id, String name) throws SQLException {
        Group group = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.UPDATE_GROUP);
            stmt.setString(1, name);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                group = getGroupById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return group;
    }

    @Override
    public boolean deleteGroup(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.DELETE_GROUP);
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
