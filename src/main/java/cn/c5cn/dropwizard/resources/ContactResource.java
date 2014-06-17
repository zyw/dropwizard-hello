package cn.c5cn.dropwizard.resources;

import cn.c5cn.dropwizard.core.Contact;
import cn.c5cn.dropwizard.dao.ContactDAO;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ZYW on 2014/6/17.
 */
@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDAO contactDAO;

    public ContactResource(DBI jdbi){
        contactDAO = jdbi.onDemand(ContactDAO.class);
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id")int id){
        return Response.ok(contactDAO.getContactById(id)).build();
    }

    @POST
    public Response createContact(Contact contact){
        return Response.created(null).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id")int id,Contact contact){
        return Response.ok(new Contact(id,contact.getFistName(),
                contact.getLastName(),contact.getPhone())).build();
    }

}
