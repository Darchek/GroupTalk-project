package edu.upc.eetac.dsa.groupTalk;

import edu.upc.eetac.dsa.groupTalk.dao.GroupDAO;
import edu.upc.eetac.dsa.groupTalk.dao.GroupDAOImpl;
import edu.upc.eetac.dsa.groupTalk.entity.AuthToken;
import edu.upc.eetac.dsa.groupTalk.entity.Group;
import edu.upc.eetac.dsa.groupTalk.entity.GroupCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Marti on 06/03/2016.
 */
@Path("groups")
public class GroupResource {

    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GroupTalkMediaType.GROUPTALK_GROUP)
    public Response createGroup(@FormParam("name") String name, @Context UriInfo uriInfo) throws URISyntaxException {
        if(name==null)
            throw new BadRequestException("all parameters are mandatory");

        if(!securityContext.isUserInRole("administrator"))
            throw new ForbiddenException("operation not allowed - Only Administrators");

        GroupDAO groupDAO = new GroupDAOImpl();
        Group group = null;
        try {
            group = groupDAO.createGroup(securityContext.getUserPrincipal().getName(), name);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + group.getId());
        return Response.created(uri).type(GroupTalkMediaType.GROUPTALK_GROUP).entity(group).build();
    }

    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_GROUP_COLLECTION)
    public GroupCollection getGroups(){
        GroupCollection groupCollection = null;
        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            groupCollection = groupDAO.getGroups();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return groupCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(GroupTalkMediaType.GROUPTALK_GROUP)
    public Group getGroupById(@PathParam("id") String id){
        Group group = null;
        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            group = groupDAO.getGroupById(id);
            if(group == null)
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return group;
    }

    @Path("/{id}")
    @PUT
    @Consumes(GroupTalkMediaType.GROUPTALK_GROUP)
    @Produces(GroupTalkMediaType.GROUPTALK_GROUP)
    public Group updateGroup(@PathParam("id") String id, Group group) {
        if(group == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(group.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        if(!securityContext.isUserInRole("administrator"))
            throw new ForbiddenException("operation not allowed - Only Administrators");

        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            group = groupDAO.updateGroup(id, group.getName());
            if(group == null)
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return group;
    }

    @Path("subscribe")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void subscribeGroup(@FormParam("id") String id) {
        if(id == null)
            throw new BadRequestException("entity is null");

        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            if(!groupDAO.subscribeGroup(id, securityContext.getUserPrincipal().getName()))
                throw new NotFoundException("Group with id = " + id + " doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @DELETE
    public void deleteGroup(@PathParam("id") String id) {
        GroupDAO groupDAO = new GroupDAOImpl();
        try {
            if(!securityContext.isUserInRole("administrator"))
                throw new ForbiddenException("operation not allowed - Only Administrators");
            if(!groupDAO.deleteGroup(id))
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}