package rest;

import tm.AbonoCM;
import vos.AbonoVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "abono" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AbonoServices extends Services
{
    @GET
    public Response getAbonos( )
    {
        AbonoCM cm = new AbonoCM( getPath( ) );
        try
        {
            ArrayList<AbonoVos> list = cm.getAbonosLocal();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{idusu}/{tipoId}" )
    public Response getAbono( @PathParam( "idusu" ) Long idusu, @PathParam( "tipoId") String tipoId  )
    {
        AbonoCM cm = new AbonoCM( getPath( ) );
        try
        {
            AbonoVos dao = cm.getAbonoLocal(idusu, tipoId);
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
    public Response createAbono( AbonoVos object, @HeaderParam("id")Long id, @HeaderParam("tipo")String tipo )
    {
        AbonoCM cm = new AbonoCM( getPath( ) );
        try
        {
            cm.createAbonoLocal(object, id, tipo);
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
    public Response updateAbono(AbonoVos object)
    {
        AbonoCM cm = new AbonoCM( getPath( ));
        try
        {
            cm.updateAbonoLocal(object);
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
    public Response deleteAbono( @PathParam( "idusu" ) Long id, @PathParam( "tipoId" ) String tipoId )
    {
        AbonoCM cm = new AbonoCM( getPath( ) );
        try
        {
            cm.devolverAbonoLocal(id, tipoId);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}