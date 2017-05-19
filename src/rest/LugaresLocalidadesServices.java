package rest;

import tm.LugaresLocalidadCM;
import vos.LugaresLocalidadVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "lugareslocalidad" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LugaresLocalidadesServices extends Services
{
    @GET
    public Response getLugaresLocalidades( )
    {
        LugaresLocalidadCM cm = new LugaresLocalidadCM( getPath( ) );
        try
        {
            ArrayList<LugaresLocalidadVos> list = cm.getLugaresLocalidades();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{idLugar}/{idLocalidad}" )
    public Response getLugaresLocalidad( @PathParam( "idLugar" ) Long idLugares, @PathParam("idLocalidad") Long idLocalidad)
    {
        LugaresLocalidadCM cm = new LugaresLocalidadCM( getPath( ) );
        try
        {
            LugaresLocalidadVos dao = cm.getLugaresLocalidad(idLugares, idLocalidad);
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
    public Response createLugaresLocalidad( LugaresLocalidadVos object )
    {
        LugaresLocalidadCM cm = new LugaresLocalidadCM( getPath( ) );
        try
        {
            cm.createLugaresLocalidad(object);
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
    public Response updateLugaresLocalidad( LugaresLocalidadVos object )
    {
        LugaresLocalidadCM cm = new LugaresLocalidadCM( getPath( ));
        try
        {
            cm.updateLugaresLocalidad(object);
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
    public Response deleteLugaresLocalidad( @PathParam( "id" ) Long id)
    {
        LugaresLocalidadCM cm = new LugaresLocalidadCM( getPath( ) );
        try
        {
            cm.deleteLugaresLocalidad(id);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}