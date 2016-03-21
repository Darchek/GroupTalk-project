package edu.upc.eetac.dsa.groupTalk;


import edu.upc.eetac.dsa.groupTalk.dao.*;
import edu.upc.eetac.dsa.groupTalk.entity.Comment;
import edu.upc.eetac.dsa.groupTalk.entity.Theme;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marti on 20/03/2016.
 */
@Path("comments")
public class CommentResource {

    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_COMMENT)
    public Response createComment(@FormParam("themeid") String themeid, @FormParam("answer") String answer, @Context UriInfo uriInfo) throws URISyntaxException {
        if (themeid == null || answer == null)
            throw new BadRequestException("all parameters are mandatory");

        GroupDAO group = new GroupDAOImpl();
        ThemeDAO themeDAO = new ThemeDAOImpl();
        CommentDAO commentDAO = new CommentDAOImpl();
        Theme theme = null;
        Comment comment = null;
        try {
            theme = themeDAO.getThemeById(themeid);
            if(theme == null)
                throw new NotFoundException("Theme with id = "+ themeid +" doesn't exist");
            if (!isUserSubscribeOrAdmin(theme.getGroupid()))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
            comment = commentDAO.createComment(securityContext.getUserPrincipal().getName(), themeid, answer);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + comment.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_THEME).entity(comment).build();
    }

    @Path("/{id}")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_COMMENT)
    public Comment getCommentById(@PathParam("id") String id) {
        GroupDAO groupDAO = new GroupDAOImpl();
        ThemeDAO themeDAO = new ThemeDAOImpl();
        CommentDAO commentDAO = new CommentDAOImpl();
        Theme theme = null;
        Comment comment = null;
        try {
            comment = commentDAO.getCommentById(id);
            if(comment == null)
                throw new NotFoundException("Comment with id = " + id +" doesn't exist");
            theme = themeDAO.getThemeById(comment.getThemeid());
            if(theme == null)
                throw new NotFoundException("Theme with id = " + id +" doesn't exist");
            if (!isUserSubscribeOrAdmin(theme.getGroupid()))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return comment;
    }

    @Path("theme/{themeid}")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_COMMENTS)
    public List<Comment> getCommentsByThemeId(@PathParam("themeid") String themeid) {
        List<Comment> comments = new ArrayList<>();
        GroupDAO groupDAO = new GroupDAOImpl();
        ThemeDAO themeDAO = new ThemeDAOImpl();
        CommentDAO commentDAO = new CommentDAOImpl();
        Theme theme = null;
        try {
            theme = themeDAO.getThemeById(themeid);
            if(theme == null)
                throw new NotFoundException("Theme with id = " + themeid +" doesn't exist");
            if (!isUserSubscribeOrAdmin(theme.getGroupid()))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
            comments = commentDAO.getCommentsByThemeId(themeid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return comments;
    }

    @Path("/{id}")
    @PUT
    @Consumes(GroupTalkMediaType.GROUPTALK_COMMENT)
    @Produces(GroupTalkMediaType.GROUPTALK_COMMENT)
    public Comment updateComment(@PathParam("id") String id, Comment comment) {
        GroupDAO groupDAO = new GroupDAOImpl();
        ThemeDAO themeDAO = new ThemeDAOImpl();
        CommentDAO commentDAO = new CommentDAOImpl();
        Theme theme = new Theme();
        if(comment == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(comment.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");
        try {
            theme = themeDAO.getThemeById(comment.getThemeid());
            if (!isUserSubscribeOrAdmin(theme.getGroupid()))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
            comment = commentDAO.updateComment(id, comment.getAnswer());
            if(comment == null)
                throw new NotFoundException("Comment with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return comment;
    }

    @Path("/{id}")
    @DELETE
    public void deleteComment(@PathParam("id") String id) {
        GroupDAO groupDAO = new GroupDAOImpl();
        ThemeDAO themeDAO = new ThemeDAOImpl();
        CommentDAO commentDAO = new CommentDAOImpl();
        Theme theme = null;
        Comment comment = null;
        try {
            comment = commentDAO.getCommentById(id);
            if(comment == null)
                throw new NotFoundException("Comment with id = " + id +" doesn't exist");
            theme = themeDAO.getThemeById(comment.getThemeid());
            if(theme == null)
                throw new NotFoundException("Theme with id = " + id +" doesn't exist");
            if (!isUserSubscribeOrAdmin(theme.getGroupid()))
                throw new ForbiddenException("operation not allowed - Need Subscribe to Group");
            if(!commentDAO.deleteComment(id))
                throw new NotFoundException("Comment with id = "+id+" doesn't exist");
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
