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
        UserDAO userDAO = new UserDAOImpl();
        ThemeDAO themeDAO = new ThemeDAOImpl();
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
                group.setUsers(userDAO.getUsersByGroupId(id));
                group.setCollectionThemes(themeDAO.getThemesByGroupId(id));
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
    public GroupCollection getGroupsByUserId(String id) throws SQLException {
        GroupCollection groups = new GroupCollection();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.GET_GROUPS_BY_USER_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getString("id"));
                group.setName(rs.getString("name"));
                group.setLastModified(rs.getTimestamp("last_modified").getTime());
                group.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                if (first) {
                    groups.setNewestTimestamp(group.getLastModified());
                    first = false;
                }
                groups.setOldestTimestamp(group.getLastModified());
                groups.getGroups().add(group);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return groups;
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

    @Override
    public boolean isUserSubscribe(String userid, String groupid) throws SQLException {
        boolean isSubscribe = false;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GroupDAOQuery.CHECK_USER_SUBSCRIBE);
            stmt.setString(1, userid);
            stmt.setString(2, groupid);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                isSubscribe = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return isSubscribe;
    }

}
