package rest;

import tm.ClienteRegistradoCM;
import vos.ClienteRegistradoVos;
import vos.FuncionVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Path("clienteregistrado")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClienteRegistradoServices extends Services {
    @GET
    public Response getClienteRegistrados() {
        ClienteRegistradoCM cm = new ClienteRegistradoCM(getPath());
        try {
            ArrayList<ClienteRegistradoVos> list = cm.getClienteRegistradosLocal();
            return Response.status(200).entity(list).build();
        } catch (Exception e) {
            return Response.status(500).entity(doErrorMessage(e)).build();
        }
    }

    @GET
    @Path("{idusu}/{tipoId}")
    public Response getClienteRegistrado(@PathParam("idusu") Long idusu, @PathParam("tipoId") String tipoID) {
        ClienteRegistradoCM cm = new ClienteRegistradoCM(getPath());
        try {
            ClienteRegistradoVos dao = cm.getClienteRegistradoLocal(idusu, tipoID);
            return Response.status(200).entity(dao).build();
        } catch (Exception e) {
            return Response.status(500).entity(doErrorMessage(e)).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClienteRegistrado(@HeaderParam("id") Long id, @HeaderParam("tipo") String tipo, ClienteRegistradoVos object) {
        ClienteRegistradoCM cm = new ClienteRegistradoCM(getPath());
        try {
            cm.createClienteRegistradoLocal(id, tipo, object);
        } catch (Exception e) {
            return Response.status(500).entity(doErrorMessage(e)).build();
        }
        return Response.status(200).entity(object).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClienteRegistrado(ClienteRegistradoVos object) {
        ClienteRegistradoCM cm = new ClienteRegistradoCM(getPath());
        try {
            cm.updateClienteRegistradoLocal(object);
            return Response.status(200).entity(object).build();
        } catch (Exception e) {
            return Response.status(500).entity(doErrorMessage(e)).build();
        }
    }

    @DELETE
    @Path("{idusu}/{tipoId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClienteRegistrado(@PathParam("idusu") Long idusu, @PathParam("tipoId") String tipoId) {
        ClienteRegistradoCM cm = new ClienteRegistradoCM(getPath());
        try {
            cm.deleteClienteRegistradoLocal(idusu, tipoId);
            return Response.status(200).build();
        } catch (Exception e) {
            return Response.status(500).entity(doErrorMessage(e)).build();
        }
    }

    @GET
    @Path("{idusu}/{tipoId}/asistencia")
    public Response asistencia(@PathParam("idusu") Long idusu, @PathParam("tipoId") String tipoId) {
        ClienteRegistradoCM cm = new ClienteRegistradoCM(getPath());

        try {
            List<FuncionVos> list = cm.darAsistenciaClienteLocal(idusu, tipoId);
            return Response.status(200).entity(list).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).entity(doErrorMessage(e)).build();
        }
    }
}