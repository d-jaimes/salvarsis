package tm;

import dao.DAOEspectaculo;
import dao.DAOFestival;
import dao.DAOFuncion;
import dao.DAOUsuario;
import exceptions.IncompleteReplyException;
import exceptions.NonReplyException;
import jms.FuncionJMS;
import protocolos.ProtocoloFuncion;
import utilities.SQLUtils;
import vos.Espectaculo;
import vos.Festival;
import vos.Funcion;
import vos.Usuario;
import vos.reportes.RFC11;
import vos.reportes.RFC3;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static utilities.SQLUtils.DateUtils.timeToString;

public class FuncionCM extends TransactionManager
{
	public FuncionCM( String contextPathP )
	{
		super( contextPathP );
	}
	
	public Funcion createFuncion( Long id, String tipo, String password, Funcion funcion ) throws Exception
	{
		DAOFuncion dao = new DAOFuncion( );
		DAOUsuario daoUsuario = new DAOUsuario( );
		DAOEspectaculo daoEspectaculo = new DAOEspectaculo( );
		DAOFestival daoFestival = new DAOFestival( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			daoUsuario.setConnection( this.connection );
			daoEspectaculo.setConnection( this.connection );
			daoFestival.setConnection( this.connection );
			dao.setConnection( this.connection );
			
			if( daoUsuario.isUserRole( id, tipo, password, Usuario.USUARIO_ADMINISTRADOR ) )
			{
				Espectaculo espectaculo = daoEspectaculo.getEspectaculo( funcion.getIdEspectaculo( ) );
				if( espectaculo != null )
				{
					Festival festival = daoFestival.getFestival( espectaculo.getIdFestival( ) );
					if( !SQLUtils.DateUtils.isDateBetween( funcion.getFecha( ), festival.getFechaInicio( ), festival.getFechaFin( ) ) )
					{
						connection.rollback( );
						throw new Exception( "Las fechas de la función están fuera del rango del espectaculo" );
					}
				}
				funcion = dao.createFuncion( funcion );
			}
			
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
		return funcion;
	}
	
	public List<Funcion> getFuncionesFrom( Long idEspectaculo ) throws SQLException
	{
		List<Funcion> list;
		DAOFuncion dao = new DAOFuncion( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			list = dao.getFuncionesFrom( idEspectaculo );
			
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
	
	public List<Funcion> getFunciones( ) throws SQLException
	{
		List<Funcion> list;
		DAOFuncion dao = new DAOFuncion( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			list = dao.getFunciones( );
			
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
	public List<ProtocoloFuncion> getFuncionesRemote( ) throws IncompleteReplyException, SQLException, InterruptedException, JMSException, NamingException
	{
		List<ProtocoloFuncion> list = new LinkedList<>( );
		try
		{
			// GET LOCAL
			list = funcionesToProtocol( getFunciones( ) );
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			// GET REMOTE
			FuncionJMS jms = FuncionJMS.getInstance( this );
			jms.setUpJMSManager( NUMBER_APPS, QUEUE_FUNCION, FuncionJMS.TOPIC_ALL_FUNCIONES_GLOBAL );
			list.addAll( jms.getResponse( ) );
			
			connection.commit( );
		}
		catch( NonReplyException e )
		{
			throw new IncompleteReplyException( "No Reply from apps - Local Videos:", list );
		}
		catch( IncompleteReplyException e )
		{
			List<ProtocoloFuncion> partialResponse = ( List<ProtocoloFuncion> ) e.getPartialResponse( );
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
	
	public Funcion getFuncion( Date fecha, Long idLugar ) throws SQLException
	{
		Funcion funcion;
		DAOFuncion dao = new DAOFuncion( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			funcion = dao.getFuncion( fecha, idLugar );
			
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
		return funcion;
	}
	
	public Funcion updateFuncion( Long idUsuario, String tipo, String password, Date fecha, Long idLugar, Funcion funcion ) throws Exception
	{
		DAOFuncion dao = new DAOFuncion( );
		DAOUsuario daoUsuario = new DAOUsuario( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			daoUsuario.setConnection( this.connection );
			dao.setConnection( this.connection );
			
			if( daoUsuario.isUserRole( idUsuario, tipo, password, Usuario.USUARIO_ORGANIZADOR ) )
			{
				funcion = dao.updateFuncion( fecha, idLugar, funcion );
				connection.commit( );
			}
			else
			{
				connection.rollback( );
				throw new Exception( "El usuario no tiene privilegios para hacer la modificacion" );
			}
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
		return funcion;
	}
	
	public void deleteFuncion( Date fecha, Long idLugar ) throws SQLException
	{
		DAOFuncion dao = new DAOFuncion( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			dao.deleteFuncion( fecha, idLugar );
			
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
	
	public List<Funcion> generarReporte1( String nombreCategoria, String nombreCompania, String ciudad, String pais, String nombreEspectaculo, String idioma, String fechaInicio, String fechaFin, Integer duracionInicio, Integer duracionFin, String lugar, String accesoEspecial, String publicoObjetivo, List<String> order, boolean asc ) throws SQLException
	{
		List<Funcion> list;
		DAOFuncion dao = new DAOFuncion( );
		
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			list = dao.RFC1( nombreCategoria, nombreCompania, ciudad, pais, nombreEspectaculo, idioma, fechaInicio, fechaFin, duracionInicio, duracionFin, lugar, accesoEspecial, publicoObjetivo, order, asc );
			
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
		return list;
	}
	
	public RFC3 generarReporte3( Date fecha, Long idLugar ) throws SQLException
	{
		RFC3 rfc3;
		DAOFuncion dao = new DAOFuncion( );
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			dao.setConnection( this.connection );
			
			rfc3 = dao.RFC3( fecha, idLugar );
			
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
		return rfc3;
	}
	
	public Double cancelarFuncion( Long id, String tipo, String password, Date fecha, Long idLugar ) throws Exception
	{
		DAOUsuario daoUsuario = new DAOUsuario( );
		DAOFuncion dao = new DAOFuncion( );
		Funcion funcion = new Funcion( );
		funcion.setSeRealiza( 2 );
		double plata = -1;
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			
			daoUsuario.setConnection( this.connection );
			dao.setConnection( this.connection );
			
			if( daoUsuario.isUserRole( id, tipo, password, Usuario.USUARIO_ADMINISTRADOR ) )
			{
				if( dao.validarCancelacion( fecha, idLugar ) )
				{
					plata = dao.cantidadDevuelta( fecha, idLugar );
					// dao.devolverPlata( fecha, idLugar );
					dao.updateFuncion( fecha, idLugar, funcion );
					connection.commit( );
				}
				else
				{
					throw new Exception( "La funcion no se puede cancelar porque ya termino o porque no existe" );
				}
			}
			else
			{
				throw new Exception( "Este usuario no tiene permisos para cancelar funciones" );
			}
			
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
		return plata;
	}
	
	public List<RFC11> rfc11( Long id, String tipo, String password, String localidad, List<String> requerimientosTecnicos, Date hInicio, Date hFin, Date fInicio, Date fEnd ) throws SQLException
	{
		List<RFC11> list = null;
		DAOFuncion dao = new DAOFuncion( );
		DAOUsuario daoUsuario = new DAOUsuario( );
		
		try
		{
			this.connection = getConnection( );
			this.connection.setAutoCommit( false );
			this.connection.setTransactionIsolation( Connection.TRANSACTION_READ_COMMITTED );
			
			daoUsuario.setConnection( this.connection );
			dao.setConnection( this.connection );
			
			if( daoUsuario.isUserRole( id, tipo, password, Usuario.USUARIO_ADMINISTRADOR ) )
			{
				list = dao.rfc11( localidad, requerimientosTecnicos, hInicio, hFin, fInicio, fEnd );
				this.connection.commit( );
			}
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
		return list;
	}
	
	public static List<ProtocoloFuncion> funcionesToProtocol( List<Funcion> list )
	{
		List<ProtocoloFuncion> resultList = new LinkedList<>( );
		for( Funcion funcion : list )
		{
			boolean seRealiza = funcion.getSeRealiza( ) == 1;
			resultList.add( new ProtocoloFuncion( APP, -1, timeToString( funcion.getFecha( ) ), funcion.getIdLugar( ), funcion.getIdEspectaculo( ), seRealiza ) );
		}
		return resultList;
	}
}