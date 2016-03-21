package edu.upc.eetac.dsa.groupTalk.dao;

/**
 * Created by Marti on 02/03/2016.
 */
public interface UserDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_USER = "insert into users (id, loginid, password, email, fullname) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?);";
    public final static String UPDATE_USER = "update users set email=?, fullname=? where id=unhex(?)";
    public final static String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), 'registered')";
    public final static String GET_USER_BY_ID = "select hex(u.id) as id, u.loginid, u.email, u.fullname from users u where id=unhex(?)";
    public final static String GET_USERS_BY_GROUP_ID = "select hex(id) as id, loginid, email, fullname from users join group_user on users.id = group_user.userid where group_user.groupid = unhex(?)";
    public final static String GET_USER_BY_USERNAME = "select hex(u.id) as id, u.loginid, u.email, u.fullname from users u where u.loginid=?";
    public final static String DELETE_USER = "delete from users where id=unhex(?)";
    public final static String GET_PASSWORD =  "select hex(password) as password from users where id=unhex(?)";
}