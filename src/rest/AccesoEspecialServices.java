package rest;

import tm.AccesoEspecialCM;
import vos.AccesoEspecialVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "accesoespecial" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AccesoEspecialServices extends Services
{
    @GET
    public Response getAccesoEspeciales( )
    {
        AccesoEspecialCM cm = new AccesoEspecialCM( getPath( ) );
        try
        {
            ArrayList<AccesoEspecialVos> list = cm.getAccesoEspecialesLocal();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}" )
    public Response getAccesoEspecial( @PathParam( "id" ) Long id)
    {
        AccesoEspecialCM cm = new AccesoEspecialCM( getPath( ) );
        try
        {
            AccesoEspecialVos dao = cm.getAccesoEspecialLocal(id);
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
    public Response createAccesoEspecial( AccesoEspecialVos object )
    {
        AccesoEspecialCM cm = new AccesoEspecialCM( getPath( ) );
        try
        {
            cm.createAccesoEspecialLocal(object);
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
    public Response updateAccesoEspecial( AccesoEspecialVos object )
    {
        AccesoEspecialCM cm = new AccesoEspecialCM( getPath( ));
        try
        {
            cm.updateAccesoEspecialLocal(object);
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
    public Response deleteAccesoEspecial( @PathParam( "id" ) Long id)
    {
        AccesoEspecialCM cm = new AccesoEspecialCM( getPath( ) );
        try
        {
            cm.deleteAccesoEspecialLocal(id);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}