package rest;

import tm.PublicoAdmitidoCM;
import vos.PublicoAdmitidoVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "publicoadmitido" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PublicoAdmitidoServices extends Services
{
    @GET
    public Response getPublicoAdmitidos( )
    {
        PublicoAdmitidoCM cm = new PublicoAdmitidoCM( getPath( ) );
        try
        {
            ArrayList<PublicoAdmitidoVos> list = cm.getPublicoAdmitidos();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}" )
    public Response getPublicoAdmitido( @PathParam( "id" ) Long id)
    {
        PublicoAdmitidoCM cm = new PublicoAdmitidoCM( getPath( ) );
        try
        {
            PublicoAdmitidoVos dao = cm.getPublicoAdmitido(id);
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
    public Response createPublicoAdmitido( PublicoAdmitidoVos object )
    {
        PublicoAdmitidoCM cm = new PublicoAdmitidoCM( getPath( ) );
        try
        {
            cm.createPublicoAdmitido(object);
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
    public Response updatePublicoAdmitido( PublicoAdmitidoVos object )
    {
        PublicoAdmitidoCM cm = new PublicoAdmitidoCM( getPath( ));
        try
        {
            cm.updatePublicoAdmitido(object);
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
    public Response deletePublicoAdmitido( @PathParam( "id" ) Long id)
    {
        PublicoAdmitidoCM cm = new PublicoAdmitidoCM( getPath( ) );
        try
        {
            cm.deletePublicoAdmitido(id);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}