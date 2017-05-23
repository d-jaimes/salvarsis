package rest;

import exceptions.IncompleteReplyException;
import protocolos.ProtocoloFestival;
import tm.FestivalCM;
import vos.Festival;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path( "festivales" )
@Produces( { MediaType.APPLICATION_JSON } )
@Consumes( { MediaType.APPLICATION_JSON } )
public class FestivalServices extends Services
{
	public FestivalServices( )
	{
	}
	
	public FestivalServices( ServletContext context )
	{
		super( context );
	}
	
	@POST
	public Response createFestival( Festival festival )
	{
		FestivalCM tm = new FestivalCM( getPath( ) );
		try
		{
			festival = tm.createFestival( festival );
		}
		catch( SQLException e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).entity( festival ).build( );
	}
	
	@GET
	public Response getFestivals( )
	{
		List<ProtocoloFestival> list;
		FestivalCM tm = new FestivalCM( getPath( ) );
		try
		{
			// list = tm.getFestivals( );
			list = tm.getFestivalesRemote( );
		}
		catch( IncompleteReplyException e )
		{
			return Response.status( Response.Status.OK ).entity( e.getPartialResponse( ) ).build( );
		}
		catch( Exception e )
		{
			return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( Response.Status.OK ).entity( list ).build( );
	}
	
	@GET
	@Path( "{id}" )
	public Response getFestival( @PathParam( "id" ) Long id )
	{
		Festival festival;
		FestivalCM tm = new FestivalCM( getPath( ) );
		try
		{
			festival = tm.getFestival( id );
		}
		catch( SQLException e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).entity( festival ).build( );
	}
	
	@PUT
	@Path( "{id}" )
	public Response updateFestival( @PathParam( "id" ) Long id, Festival festival )
	{
		FestivalCM tm = new FestivalCM( getPath( ) );
		try
		{
			festival = tm.updateFestival( id, festival );
		}
		catch( SQLException e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).entity( festival ).build( );
	}
	
	@DELETE
	@Path( "{id}" )
	public Response deleteFestival( @PathParam( "id" ) Long id )
	{
		FestivalCM tm = new FestivalCM( getPath( ) );
		try
		{
			tm.deleteFestival( id );
		}
		catch( SQLException e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
		return Response.status( 200 ).build( );
	}
	
	// ESPECTACULOS
	
	@Path( "{idFestival}/espectaculos" )
	public EspectaculoServices getCustomer( )
	{
		return new EspectaculoServices( context );
	}
}