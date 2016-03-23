package edu.upc.eetac.dsa.groupTalk;

import edu.upc.eetac.dsa.groupTalk.dao.GroupDAO;
import edu.upc.eetac.dsa.groupTalk.dao.GroupDAOImpl;
import edu.upc.eetac.dsa.groupTalk.dao.ThemeDAO;
import edu.upc.eetac.dsa.groupTalk.dao.ThemeDAOImpl;
import edu.upc.eetac.dsa.groupTalk.entity.AuthToken;
import edu.upc.eetac.dsa.groupTalk.entity.Theme;
import edu.upc.eetac.dsa.groupTalk.entity.ThemeCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Marti on 06/03/2016.
 */
@Path("themes")
public class ThemeResource {

    @Context
    private SecurityContext securityContext;


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_THEME)
    public Response createTheme(@FormParam("groupid") String groupid, @FormParam("title") String title, @FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if (groupid == null || title == null || content == null)
            throw new BadRequestException("all parameters are mandatory");

        GroupDAO group = new GroupDAOImpl();
        ThemeDAO themeDAO = new ThemeDAOImpl();
        Theme theme = null;
        try {
            if (!isUserSubscribeOrAdmin(groupid))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
            theme = themeDAO.createTheme(securityContext.getUserPrincipal().getName(), groupid, title, content);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + theme.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_THEME).entity(theme).build();
    }

    @Path("group/{groupid}")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_THEME_COLLECTION)
    public ThemeCollection getThemesByGroupId(@PathParam("groupid") String groupid, @QueryParam("timestamp") long timestamp, @DefaultValue("true") @QueryParam("before") boolean before) {
        ThemeCollection themeCollection = null;
        ThemeDAO themeDAO = new ThemeDAOImpl();
        try {
            if (!isUserSubscribeOrAdmin(groupid))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
            if (before && timestamp == 0) timestamp = System.currentTimeMillis();
            themeCollection = themeDAO.getThemesByGroupId(groupid, timestamp, before);
            themeCollection.setGroupid(groupid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return themeCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_THEME)
    public Response getThemeById(@PathParam("id") String id, @Context Request request) {
        CacheControl cacheControl = new CacheControl();
        Theme theme = null;
        ThemeDAO themeDAO = new ThemeDAOImpl();
        try {
            theme = themeDAO.getThemeById(id);
            if(theme == null)
                throw new NotFoundException("Theme with id = " + id +" doesn't exist");
            if (!isUserSubscribeOrAdmin(theme.getGroupid()))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
            EntityTag eTag = new EntityTag(Long.toString(theme.getLastModified()));
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }
            rb = Response.ok(theme).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(GroupTalkMediaType.GROUPTALK_THEME)
    @Produces(GroupTalkMediaType.GROUPTALK_THEME)
    public Theme updateTheme(@PathParam("id") String id, Theme theme) {
        GroupDAO groupDAO = new GroupDAOImpl();
        ThemeDAO themeDAO = new ThemeDAOImpl();
        if(theme == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(theme.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");
        try {
            if (!isUserSubscribeOrAdmin(theme.getGroupid()))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
            theme = themeDAO.updateTheme(id, theme.getTitle(), theme.getContent());
            if(theme == null)
                throw new NotFoundException("Theme with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return theme;
    }

    @Path("/{id}")
    @DELETE
    public void deleteTheme(@PathParam("id") String id) {
        ThemeDAO themeDAO = new ThemeDAOImpl();
        GroupDAO groupDAO = new GroupDAOImpl();
        Theme theme = null;
        try {
            theme = themeDAO.getThemeById(id);
            if (!isUserSubscribeOrAdmin(theme.getGroupid()))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
            if(!themeDAO.deleteTheme(id))
                throw new NotFoundException("Theme with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


    public boolean isUserSubscribeOrAdmin(String groupid) {
        if(securityContext.isUserInRole("administrator"))
            return true;
        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            return groupDAO.isUserSubscribe(securityContext.getUserPrincipal().getName(), groupid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

}
