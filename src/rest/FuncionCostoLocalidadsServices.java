package rest;

import tm.FuncionCostoLocalidadCM;
import vos.FuncionCostoLocalidadVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "FuncionCostoLocalidad" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class FuncionCostoLocalidadsServices extends Services
{
    @GET
    public Response getFuncionCostoLocalidades( )
    {
        FuncionCostoLocalidadCM cm = new FuncionCostoLocalidadCM( getPath( ) );
        try
        {
            ArrayList<FuncionCostoLocalidadVos> list = cm.getFuncionCostoLocalidadesLocal();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{idFuncion}/{idLocalidad}" )
    public Response getFuncionCostoLocalidad( @PathParam( "idFuncion" ) Long idFuncion, @PathParam("idLocalidad") Long idLocalidad)
    {
        FuncionCostoLocalidadCM cm = new FuncionCostoLocalidadCM( getPath( ) );
        try
        {
            FuncionCostoLocalidadVos dao = cm.getFuncionCostoLocalidadLocal(idFuncion, idLocalidad);
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
    public Response createFuncionCostoLocalidad( FuncionCostoLocalidadVos object )
    {
        FuncionCostoLocalidadCM cm = new FuncionCostoLocalidadCM( getPath( ) );
        try
        {
            cm.createFuncionCostoLocalidadLocal(object);
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
    public Response updateFuncionCostoLocalidad( FuncionCostoLocalidadVos object )
    {
        FuncionCostoLocalidadCM cm = new FuncionCostoLocalidadCM( getPath( ));
        try
        {
            cm.updateFuncionCostoLocalidadLocal(object);
            return Response.status( 200 ).entity( object ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @DELETE
    @Path( "{idFuncion}/{idLocalidad}" )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFuncionCostoLocalidad(@PathParam( "idFuncion" ) Long idFuncion, @PathParam("idLocalidad") Long idLocalidad)
    {
        FuncionCostoLocalidadCM cm = new FuncionCostoLocalidadCM( getPath( ) );
        try
        {
            cm.deleteFuncionCostoLocalidadLocal(idFuncion, idLocalidad);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}