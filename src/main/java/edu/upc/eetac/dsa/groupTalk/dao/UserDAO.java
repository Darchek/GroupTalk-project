package edu.upc.eetac.dsa.groupTalk.dao;

import edu.upc.eetac.dsa.groupTalk.entity.Group;
import edu.upc.eetac.dsa.groupTalk.entity.GroupCollection;
import edu.upc.eetac.dsa.groupTalk.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marti on 02/03/2016.
 */
public interface UserDAO {
    public User createUser(String loginid, String password, String email, String fullname) throws SQLException, UserAlreadyExistsException;

    public User updateProfile(String id, String email, String fullname) throws SQLException;

    public User getUserById(String id) throws SQLException;

    public List<User> getUsersByGroupId(String id) throws SQLException;

    public User getUserByLoginid(String loginid) throws SQLException;

    public boolean deleteUser(String id) throws SQLException;

    public boolean checkPassword(String id, String password) throws SQLException;

}
