package rest;

import tm.RequerimientosTecnicosCM;
import vos.RequerimientosTecnicosVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "RequerimientosTecnicos" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class RequerimientosTecnicosServices extends Services
{
    @GET
    public Response getRequerimientosTecnicoss( )
    {
        RequerimientosTecnicosCM cm = new RequerimientosTecnicosCM( getPath( ) );
        try
        {
            ArrayList<RequerimientosTecnicosVos> list = cm.getRequerimientosTecnicoss();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}" )
    public Response getRequerimientosTecnicos( @PathParam( "id" ) Long id)
    {
        RequerimientosTecnicosCM cm = new RequerimientosTecnicosCM( getPath( ) );
        try
        {
            RequerimientosTecnicosVos dao = cm.getRequerimientosTecnicos(id);
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
    public Response createRequerimientosTecnicos( RequerimientosTecnicosVos object )
    {
        RequerimientosTecnicosCM cm = new RequerimientosTecnicosCM( getPath( ) );
        try
        {
            cm.createRequerimientosTecnicos(object);
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
    public Response updateRequerimientosTecnicos( RequerimientosTecnicosVos object )
    {
        RequerimientosTecnicosCM cm = new RequerimientosTecnicosCM( getPath( ));
        try
        {
            cm.updateRequerimientosTecnicos(object);
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
    public Response deleteRequerimientosTecnicos( @PathParam( "id" ) Long id)
    {
        RequerimientosTecnicosCM cm = new RequerimientosTecnicosCM( getPath( ) );
        try
        {
            cm.deleteRequerimientosTecnicos(id);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}