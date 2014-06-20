package cn.c5cn.dropwizard.resources;


import cn.c5cn.dropwizard.core.Contact;
import cn.c5cn.dropwizard.views.ContactView;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ZYW on 2014/6/20.
 */
@Path("/client/")
@Produces(MediaType.TEXT_HTML)
public class ClientResource {
    private Client client;

    public ClientResource(Client client){
        this.client = client;
    }

    @GET
    @Path("showContact/{id}")
    public ContactView showContact(@PathParam("id")int id){
        WebResource contactResource = client.resource("http://localhost:8080/contact/"+id);
        Contact contact = contactResource.get(Contact.class);
        return new ContactView(contact);
    }
    @GET
    @Path("newContact")
    public Response newContact(@QueryParam("firstName")String firstName,
                               @QueryParam("lastName")String lastName,
                               @QueryParam("phone")String phone){
        WebResource contactResource = client.resource("http://localhost:8080/contact");
        ClientResponse response = contactResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, new Contact(0, firstName, lastName, phone));
        if(response.getStatus() == 201){
            return Response.status(302).entity("The contact was created successfully! The new contact can be found at" +
                    "" + response.getHeaders().getFirst("Location")).build();
        }else{
            return Response.status(422).entity(response.
                    getEntity(String.class)).build();
        }
    }
    @GET
    @Path("updateContact")
    public Response updateContact(@QueryParam("id") int id,
                                  @QueryParam("firstName") String firstName,
                                  @QueryParam("lastName") String lastName,
                                  @QueryParam("phone") String phone) {
        WebResource contactResource =
                client.resource("http://localhost:8080/contact/" +
                        id+"/update");
        ClientResponse response = contactResource.type(MediaType.
                APPLICATION_JSON).post(ClientResponse.class, new Contact(id,
                firstName, lastName, phone));
        if (response.getStatus() == 200) {
// Created
            return Response.status(302).entity("The contact was updated successfully!").build();
        }
        else {
// Other Status code, indicates an error
            return Response.status(422).entity(response.
                    getEntity(String.class)).build();
        }
    }
    @GET
    @Path("deleteContact")
    public Response deleteContact(@QueryParam("id") int id) {
        WebResource contactResource =
                client.resource("http://localhost:8080/contact/"+id+"/delete");
        System.out.println("id = [" + id + "]==================================================");
        contactResource.delete();
        return Response.noContent().entity("Contact was deleted!").build();
    }
}
