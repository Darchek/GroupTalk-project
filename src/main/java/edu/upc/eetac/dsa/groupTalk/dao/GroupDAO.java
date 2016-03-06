package edu.upc.eetac.dsa.groupTalk.dao;

import edu.upc.eetac.dsa.groupTalk.entity.Group;
import edu.upc.eetac.dsa.groupTalk.entity.GroupCollection;
import edu.upc.eetac.dsa.groupTalk.entity.ThemeCollection;
import edu.upc.eetac.dsa.groupTalk.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marti on 03/03/2016.
 */
public interface GroupDAO {
    public Group createGroup(String userid, String name) throws SQLException;

    public boolean subscribeGroup(String groupid, String userid) throws SQLException;

    public Group getGroupById(String id) throws SQLException;

    public ThemeCollection getThemesByGroupId(String id) throws SQLException;

    public List<User> getUsersByGroupId(String id) throws SQLException;

    public GroupCollection getGroups() throws SQLException;

    public Group updateGroup(String id, String name) throws SQLException;

    public boolean deleteGroup(String id) throws SQLException;
}
