package edu.upc.eetac.dsa.groupTalk.dao;

import edu.upc.eetac.dsa.groupTalk.auth.UserInfo;
import edu.upc.eetac.dsa.groupTalk.entity.AuthToken;

import java.sql.SQLException;

/**
 * Created by Marti on 03/03/2016.
 */
public interface AuthTokenDAO {
    public UserInfo getUserByAuthToken(String token) throws SQLException;
    public AuthToken createAuthToken(String userid) throws SQLException;
    public void deleteToken(String userid) throws  SQLException;
}