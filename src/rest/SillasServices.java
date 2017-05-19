package rest;

import tm.SillasCM;
import vos.SillaVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "silla" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SillasServices extends Services
{
    @GET
    public Response getSillas( )
    {
        SillasCM cm = new SillasCM( getPath( ) );
        try
        {
            ArrayList<SillaVos> list = cm.getSillas();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{fila}/{numero}")
    public Response getSilla( @PathParam( "fila" ) Integer fila, @PathParam("numero") Integer numero)
    {
        SillasCM cm = new SillasCM( getPath( ) );
        try
        {
            SillaVos dao = cm.getSilla(fila, numero);
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
    public Response createSilla( SillaVos object )
    {
        SillasCM cm = new SillasCM( getPath( ) );
        try
        {
            cm.createSilla(object);
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
    public Response updateSilla( SillaVos object )
    {
        SillasCM cm = new SillasCM( getPath( ));
        try
        {
            cm.updateSilla(object);
            return Response.status( 200 ).entity( object ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @DELETE
    @Path( "{fila}/{numero}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSilla( @PathParam( "fila" ) Integer fila, @PathParam("numero") Integer numero)
    {
        SillasCM cm = new SillasCM( getPath( ) );
        try
        {
            cm.deleteSilla(fila, numero);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}