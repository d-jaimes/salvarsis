package rest;

import tm.LugaresCM;
import vos.LugaresVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "lugares" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LugaresServices extends Services
{
    @GET
    public Response getLugares( )
    {
        LugaresCM cm = new LugaresCM( getPath( ) );
        try
        {
            ArrayList<LugaresVos> list = cm.getLugares();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}" )
    public Response getLugar( @PathParam( "id" ) Long id)
    {
        LugaresCM cm = new LugaresCM( getPath( ) );
        try
        {
            LugaresVos dao = cm.getLugar(id);
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
    public Response createLugar( LugaresVos object, @HeaderParam("id") Long id, @HeaderParam("tipo")String tipo )
    {
        LugaresCM cm = new LugaresCM( getPath( ) );
        try
        {
            cm.createLugar(object,id,tipo);
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
    public Response updateLugar( LugaresVos object )
    {
        LugaresCM cm = new LugaresCM( getPath( ));
        try
        {
            cm.updateLugar(object);
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
    public Response deleteLugar( @PathParam( "id" ) Long id)
    {
        LugaresCM cm = new LugaresCM( getPath( ) );
        try
        {
            cm.deleteLugar(id);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}