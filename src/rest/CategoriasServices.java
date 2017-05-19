package rest;

import tm.CategoriasCM;
import vos.CategoriaVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 */
@Path( "Categoria" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CategoriasServices extends Services
{
    @GET
    public Response getCategorias( )
    {
        CategoriasCM cm = new CategoriasCM( getPath( ) );
        try
        {
            ArrayList<CategoriaVos> list = cm.getCategoriasLocal();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}" )
    public Response getCategoria( @PathParam( "id" ) Long id)
    {
        CategoriasCM cm = new CategoriasCM( getPath( ) );
        try
        {
            CategoriaVos dao = cm.getCategoriaLocal(id);
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
    public Response createCategoria( CategoriaVos object )
    {
        CategoriasCM cm = new CategoriasCM( getPath( ) );
        try
        {
            cm.createCategoriaLocal(object);
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
    public Response updateCategoria( CategoriaVos object )
    {
        CategoriasCM cm = new CategoriasCM( getPath( ));
        try
        {
            cm.updateCategoriaLocal(object);
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
    public Response deleteCategoria( @PathParam( "id" ) Long id)
    {
        CategoriasCM cm = new CategoriasCM( getPath( ) );
        try
        {
            cm.deleteCategoriaLocal(id);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}