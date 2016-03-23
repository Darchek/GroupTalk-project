package edu.upc.eetac.dsa.groupTalk;

import edu.upc.eetac.dsa.groupTalk.auth.UserInfo;
import edu.upc.eetac.dsa.groupTalk.dao.AuthTokenDAO;
import edu.upc.eetac.dsa.groupTalk.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.groupTalk.dao.UserDAO;
import edu.upc.eetac.dsa.groupTalk.dao.UserDAOImpl;
import edu.upc.eetac.dsa.groupTalk.entity.AuthToken;
import edu.upc.eetac.dsa.groupTalk.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.sql.SQLException;

/**
 * Created by Marti on 21/03/2016.
 */
@Path("login")
public class LoginResource {

    @Context
    SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_AUTH_TOKEN)
    public AuthToken login(@FormParam("login") String loginid, @FormParam("password") String password) {
        if(loginid == null || password == null)
            throw new BadRequestException("all parameters are mandatory");

        User user = null;
        AuthToken authToken = null;
        try{
            UserDAO userDAO = new UserDAOImpl();
            user = userDAO.getUserByLoginid(loginid);
            if(user == null)
                throw new BadRequestException("loginid " + loginid + " not found.");
            if(!userDAO.checkPassword(user.getId(), password))
                throw new BadRequestException("incorrect password");

            AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
            authTokenDAO.deleteToken(user.getId());
            authToken = authTokenDAO.createAuthToken(user.getId());
            final UserInfo principal = (new AuthTokenDAOImpl()).getUserByAuthToken(authToken.getToken());
            authToken.setIsAdmin(principal);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        return authToken;
    }

    @DELETE
    public void logout(){
        String userid = securityContext.getUserPrincipal().getName();
        AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
        try {
            authTokenDAO.deleteToken(userid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
