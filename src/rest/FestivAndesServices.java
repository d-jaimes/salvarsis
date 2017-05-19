package rest;

import tm.FestivAndesCM;
import vos.FestivandesVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path( "festival" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class FestivAndesServices extends Services
{
    @GET
    public Response getFestivals( )
    {
        FestivAndesCM cm = new FestivAndesCM( getPath( ) );
        try
        {
            ArrayList<FestivandesVos> list = cm.getFestivalesLocal();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}" )
    public Response getFestival( @PathParam( "id" ) Long id )
    {
        FestivAndesCM cm = new FestivAndesCM( getPath( ) );
        try
        {
            FestivandesVos dao = cm.getFestivalLocal( id );
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
    public Response createFestival( FestivandesVos object )
    {
        FestivAndesCM cm = new FestivAndesCM( getPath( ) );
        try
        {
            cm.createFestivalLocal( object );
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
    public Response updateFestival( FestivandesVos object )
    {
        FestivAndesCM cm = new FestivAndesCM( getPath( ) );
        try
        {
            cm.updateFestivalLocal(object);
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
    public Response deleteFestival( @PathParam( "id" ) Long id )
    {
        FestivAndesCM cm = new FestivAndesCM( getPath( ) );
        try
        {
            cm.deleteFestivalLocal( id );
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

}
