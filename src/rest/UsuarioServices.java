package rest;

import tm.UsuarioCM;
import vos.UsuarioVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "usuario" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UsuarioServices extends Services
{
    @GET
    public Response getUsuarios( )
    {
        UsuarioCM cm = new UsuarioCM( getPath( ) );
        try
        {
            ArrayList<UsuarioVos> list = cm.getUsuarios();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{idusu}/{tipoId}" )
    public Response getUsuario( @PathParam( "idusu" ) Long idusu, @PathParam("tipoId") String tipoId)
    {
        UsuarioCM cm = new UsuarioCM( getPath( ) );
        try
        {
            UsuarioVos dao = cm.getUsuario(idusu, tipoId);
            return Response.status( 200 ).entity( dao ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUsuario( UsuarioVos object )
    {
        UsuarioCM cm = new UsuarioCM( getPath( ) );
        try
        {
            cm.createUsuario(object);
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
        return Response.status( 200 ).entity( object ).build( );
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsuario( UsuarioVos object )
    {
        UsuarioCM cm = new UsuarioCM( getPath( ));
        try
        {
            cm.updateUsuario(object);
            return Response.status( 200 ).entity( object ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @DELETE
    @Path( "{idusu}/{tipoId}" )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsuario( @PathParam( "idusu" ) Long idusu, @PathParam("tipoId") String tipoId )
    {
        UsuarioCM cm = new UsuarioCM( getPath( ) );
        try
        {
            cm.deleteUsuario(idusu, tipoId);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

}