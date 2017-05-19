package rest;

import tm.ShowsCM;
import vos.ShowsVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "shows" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ShowsServices extends Services
{
    @GET
    public Response getShows( )
    {
        ShowsCM cm = new ShowsCM( getPath( ) );
        try
        {
            ArrayList<ShowsVos> list = cm.getShows();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}" )
    public Response getShow( @PathParam( "id" ) Long id)
    {
        ShowsCM cm = new ShowsCM( getPath( ) );
        try
        {
            ShowsVos dao = cm.getShow(id);
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
    public Response createShow(  @HeaderParam("id") Long id, @HeaderParam("tipo") String tipo, ShowsVos object )
    {
        ShowsCM cm = new ShowsCM( getPath( ) );
        try
        {
            cm.createShow(id, tipo, object);
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
    public Response updateShow( ShowsVos object )
    {
        ShowsCM cm = new ShowsCM( getPath( ));
        try
        {
            cm.updateShow(object);
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
    public Response deleteShow( @PathParam( "id" ) Long id)
    {
        ShowsCM cm = new ShowsCM( getPath( ) );
        try
        {
            cm.deleteShow(id);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}