package rest;

import tm.FuncionesCM;
import vos.FuncionVos;
import vos.UsuarioVos;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 */
@Path( "funcion" )
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class FuncionesServices extends Services
{
    @GET
    public Response getFuncions( )
    {
        FuncionesCM cm = new FuncionesCM( getPath( ) );
        try
        {
            ArrayList<FuncionVos> list = cm.getFuncionesLocal();
            return Response.status( 200 ).entity( list ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @GET
    @Path( "{id}" )
    public Response getFuncion( @PathParam( "id" ) Long id)
    {
        FuncionesCM cm = new FuncionesCM( getPath( ) );
        try
        {
            FuncionVos dao = cm.getFuncionLocal(id);
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
    public Response createFuncion( FuncionVos object,@HeaderParam("pass") String pass,@HeaderParam("id") Long id )
    {
        FuncionesCM cm = new FuncionesCM( getPath( ) );
        try
        {
            cm.createFuncionLocal(object,pass,id);
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
    public Response updateFuncion( FuncionVos object )
    {
        FuncionesCM cm = new FuncionesCM( getPath( ));
        try
        {
            cm.updateFuncionLocal(object);
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
    public Response deleteFuncion( @PathParam( "id" ) Long id)
    {
        FuncionesCM cm = new FuncionesCM( getPath( ) );
        try
        {
            cm.deleteFuncionLocal(id);
            return Response.status( 200 ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }

    @PUT
    @Path( "{id}" )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelarFuncion(  @PathParam( "id" ) Long id)
    {
        FuncionesCM cm = new FuncionesCM( getPath( ));

        try
        {
            ArrayList <String> object = cm.cancelarFuncionLocal(id);
            return Response.status( 200 ).entity( object ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
    @GET
    @Path( "{rq9}" )
    public Response usuariosAsistentes(@HeaderParam("id") Long id ,@HeaderParam("inicio") Date inicio, @HeaderParam("fin") Date fin   )
    {
        FuncionesCM cm = new FuncionesCM( getPath( ) );
        try
        {
            ArrayList<UsuarioVos> dao = cm.getUsuariosPorFuncionLocal(id,inicio,fin);
            return Response.status( 200 ).entity( dao ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
    @GET
    @Path( "{rq9_2}" )
    public Response usuariosNoAsistentes(@HeaderParam("id") Long id ,@HeaderParam("inicio") Date inicio, @HeaderParam("fin") Date fin   )
    {
        FuncionesCM cm = new FuncionesCM( getPath( ) );
        try
        {
            ArrayList<UsuarioVos> dao = cm.getUsuariosNoFuncionLocal(id,inicio,fin);
            return Response.status( 200 ).entity( dao ).build( );
        }
        catch( Exception e )
        {
            return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
        }
    }
}