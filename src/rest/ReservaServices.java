package rest;

import tm.ReservaCM;
import vos.ReservaVos;
import vos.UsuarioVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "Reserva" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ReservaServices extends Services
{
    @GET
    public Response getReservas( )
    {
        ReservaCM cm = new ReservaCM( getPath( ) );
        try
        {
            ArrayList<ReservaVos> list = cm.getReservas();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{idReserva}/{idusu}/{tipoid}" )
    public Response getReserva( @PathParam( "idReserva" ) Long idReserva, @PathParam( "idusu" ) Long idusu, @PathParam( "tipoid" ) String tipoId)
    {
        ReservaCM cm = new ReservaCM( getPath( ) );
        try
        {
            ReservaVos dao = cm.getReserva(idReserva, idusu, tipoId);
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
    public Response createReserva( ReservaVos object,@HeaderParam("id")Long id, @HeaderParam("tipo")String tipo )
    {
        ReservaCM cm = new ReservaCM( getPath( ) );
        try
        {
            cm.createReserva(object,id,tipo);
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
    public Response updateReserva( ReservaVos object )
    {
        ReservaCM cm = new ReservaCM( getPath( ));
        try
        {
            cm.updateReserva(object);
            return Response.status( 200 ).entity( object ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @DELETE
    @Path( "{idReserva}/{idusu}/{tipoid}" )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReserva( @PathParam( "idReserva" ) Long idReserva, @PathParam( "idusu" ) Long idusu, @PathParam( "tipoid" ) String tipoId)
    {
        ReservaCM cm = new ReservaCM( getPath( ) );
        try
        {
            cm.deleteReserva(idReserva, idusu, tipoId);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

//TODO María
    @POST
    @Path("1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReservaMultiple( int cantSillas, Long idFuncion, Long idLocalidad,  Long idusu, String tipo)
    {
        ReservaCM cm = new ReservaCM( getPath( ) );
        try
        {
            ArrayList object = cm.reservaMultiple(cantSillas,idFuncion, idLocalidad, idusu,tipo);
            return Response.status( 200 ).entity( object ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
    @GET
    @Path("clienteFiel")
    public Response getClienteFiel( @HeaderParam("num") int num )
    {
        ReservaCM cm = new ReservaCM( getPath( ) );
        try
        {
            ArrayList<UsuarioVos> list = cm.getClienteFiel(num);
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}