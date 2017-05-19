package rest;

import tm.LocalidadCM;
import vos.LocalidadVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "localidad" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LocalidadServices extends Services
{
    @GET
    public Response getLocalidades( )
    {
        LocalidadCM cm = new LocalidadCM( getPath( ) );
        try
        {
            ArrayList<LocalidadVos> list = cm.getLocalidadesLocal();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}" )
    public Response getLocalidad( @PathParam( "id" ) Long id)
    {
        LocalidadCM cm = new LocalidadCM( getPath( ) );
        try
        {
            LocalidadVos dao = cm.getLocalidadLocal(id);
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
    public Response createLocalidad( LocalidadVos object )
    {
        LocalidadCM cm = new LocalidadCM( getPath( ) );
        try
        {
            cm.createLocalidadLocal(object);
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
    public Response updateLocalidad( LocalidadVos object )
    {
        LocalidadCM cm = new LocalidadCM( getPath( ));
        try
        {
            cm.updateLocalidadLocal(object);
            return Response.status( 200 ).entity( object ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @DELETE
    @Path( "{id}" )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLocalidad( @PathParam( "id" ) Long id)
    {
        LocalidadCM cm = new LocalidadCM( getPath( ) );
        try
        {
            cm.deleteLocalidadLocal(id);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}