package tm;

import dao.DAOFestival;
import exceptions.IncompleteReplyException;
import exceptions.NonReplyException;
import jms.FestivalJMS;
import protocolos.ProtocoloFestival;
import utilities.SQLUtils;
import vos.Festival;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FestivalCM extends TransactionManager
{
	public FestivalCM( String contextPathP )
	{
		super( contextPathP );
	}
	
	public Festival createFestival( Festival festival ) throws SQLException
	{
		DAOFestival dao = new DAOFestival( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			festival = dao.createFestival( festival );
			
			connection.commit( );
		}
		catch( SQLException e )
		{
			System.err.println( "SQLException:" + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		catch( Exception e )
		{
			System.err.println( "GeneralException:" + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		finally
		{
			closeDAO( dao );
		}
		return festival;
	}
	
	public List<Festival> getFestivals( ) throws SQLException
	{
		List<Festival> list;
		DAOFestival dao = new DAOFestival( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			list = dao.getFestivales( );
			
			connection.commit( );
		}
		catch( SQLException e )
		{
			System.err.println( "SQLException: " + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		catch( Exception e )
		{
			System.err.println( "GeneralException: " + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		finally
		{
			closeDAO( dao );
		}
		return list;
	}
	
	@SuppressWarnings( "unchecked" )
	public List<ProtocoloFestival> getFestivalesRemote( ) throws Exception
	{
		List<ProtocoloFestival> list = new LinkedList<>( );
		try
		{
			list = festivalesToProtocol( getFestivals( ) );
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			FestivalJMS jms = FestivalJMS.getInstance( this );
			jms.setUpJMSManager( NUMBER_APPS, QUEUE_FESTIVAL, FestivalJMS.TOPIC_ALL_FESTIVALES_GLOBAL );
			list.addAll( jms.getResponse( ) );
			
			connection.commit( );
		}
		catch( NonReplyException e )
		{
			throw new IncompleteReplyException( "No Reply from apps", list );
		}
		catch( IncompleteReplyException e )
		{
			List<ProtocoloFestival> partialResponse = ( List<ProtocoloFestival> ) e.getPartialResponse( );
			list.addAll( partialResponse );
			throw new IncompleteReplyException( "Incomplete Reply:", partialResponse );
		}
		catch( SQLException e )
		{
			System.err.println( "SQLException: " + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		catch( Exception e )
		{
			System.err.println( "GeneralException: " + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		finally
		{
			if( connection != null )
			{
				connection.close( );
			}
		}
		return list;
	}
	
	public Festival getFestival( Long id ) throws SQLException
	{
		Festival festival;
		DAOFestival dao = new DAOFestival( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			festival = dao.getFestival( id );
			
			connection.commit( );
		}
		catch( SQLException e )
		{
			System.err.println( "SQLException:" + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		catch( Exception e )
		{
			System.err.println( "GeneralException:" + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		finally
		{
			closeDAO( dao );
		}
		return festival;
	}
	
	public Festival updateFestival( Long id, Festival festival ) throws SQLException
	{
		DAOFestival dao = new DAOFestival( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			festival = dao.updateFestival( id, festival );
			
			connection.commit( );
		}
		catch( SQLException e )
		{
			System.err.println( "SQLException:" + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		catch( Exception e )
		{
			System.err.println( "GeneralException:" + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		finally
		{
			closeDAO( dao );
		}
		return festival;
	}
	
	public void deleteFestival( Long id ) throws SQLException
	{
		DAOFestival dao = new DAOFestival( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			dao.deleteFestival( id );
			
			connection.commit( );
		}
		catch( SQLException e )
		{
			System.err.println( "SQLException:" + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		catch( Exception e )
		{
			System.err.println( "GeneralException:" + e.getMessage( ) );
			connection.rollback( );
			e.printStackTrace( );
			throw e;
		}
		finally
		{
			closeDAO( dao );
		}
	}
	
	public static List<ProtocoloFestival> festivalesToProtocol( List<Festival> list )
	{
		List<ProtocoloFestival> resultList = new LinkedList<>( );
		for( Festival festival : list )
		{
			resultList.add( new ProtocoloFestival( festival.getId( ), festival.getNombre( ), festival.getCiudad( ), SQLUtils.DateUtils.timeToString( festival.getFechaInicio( ) ), SQLUtils.DateUtils
					.timeToString( festival.getFechaFin( ) ) ) );
		}
		return resultList;
	}
}