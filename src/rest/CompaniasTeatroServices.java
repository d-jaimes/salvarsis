package rest;

import tm.CompaniasTeatroCM;
import tm.FuncionesCM;
import vos.CompaniasTeatroVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "companiasteatro" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CompaniasTeatroServices extends Services
{
    @GET
    public Response getCompaniasTeatros( )
    {
        CompaniasTeatroCM cm = new CompaniasTeatroCM( getPath( ) );
        try
        {
            ArrayList<CompaniasTeatroVos> list = cm.getCompaniasTeatrosLocal();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}/{tipoId}" )
    public Response getCompaniasTeatro( @PathParam( "id" ) Long id, @PathParam("tipoId") String tipoId)
    {
        CompaniasTeatroCM cm = new CompaniasTeatroCM( getPath( ) );
        try
        {
            CompaniasTeatroVos dao = cm.getCompaniasTeatroLocal(id, tipoId);
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
    public Response createCompaniasTeatro( CompaniasTeatroVos object, @HeaderParam("id") Long id, @HeaderParam("tipo") String tipo )
    {
        CompaniasTeatroCM cm = new CompaniasTeatroCM( getPath( ) );
        try
        {
            cm.createCompaniasTeatroLocal(object,id,tipo);
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
    public Response updateCompaniasTeatro( CompaniasTeatroVos object )
    {
        CompaniasTeatroCM cm = new CompaniasTeatroCM( getPath( ));
        try
        {
            cm.updateCompaniasTeatroLocal(object);
            return Response.status( 200 ).entity( object ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @DELETE
    @Path( "{id}/{tipoId}" )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCompaniasTeatro( @PathParam( "id" ) Long id, @PathParam("tipoId") String tipoId)
    {
        CompaniasTeatroCM cm = new CompaniasTeatroCM( getPath( ) );
        try
        {
            cm.deleteCompaniasTeatroLocal(id, tipoId);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    //todo: req 16
    @DELETE
    @Path( "{id}/{tipoId}" )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retirarCompaniaTeatro( @PathParam( "id" ) Long id, @PathParam("tipoId") String tipoId)
    {
        CompaniasTeatroCM cm = new CompaniasTeatroCM( getPath( ) );

        FuncionesCM cm2 = new FuncionesCM( getPath( ) );

        try
        {
            ArrayList<Long> funciones = cm.traerFuncionesPorCompaniaLocal(id, tipoId);

            for(Long idFuncion: funciones)
            {
                cm2.cancelarFuncionLocal(idFuncion);
            }
            cm.deleteCompaniasTeatroLocal(id, tipoId);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}