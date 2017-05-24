package jms;

import protocolos.ProtocoloFestival;
import tm.FestivAndesCM;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

/**
 * Created by: dnarv on 20/05/2017.
 */
public class FestivalJMS extends JMSManager<ProtocoloFestival>
{
	/**
	 * Atributo tipo JMS para manejar la única instancia del patron singleton
	 */
	private static FestivalJMS instancia;
	
	/**
	 * Referencia a la clase principal FuncionCM (Connection Manager) para su uso.
	 * Se usa para responder a requerimientos que llegan de otras aplicaciones
	 */
	private FestivAndesCM master;
	
	/**
	 * Método que retorna la instancia única de la clase
	 *
	 * @param transactionManager - instancia que hace referencia a la clase principal del TransactionManager
	 * @return FuncionJMS - instancia única de la clase
	 */
	public static FestivalJMS getInstance( FestivAndesCM transactionManager )
	{
		instancia = instancia == null ? new FestivalJMS( ) : instancia;
		instancia.master = transactionManager;
		return instancia;
	}
	
	/**
	 * Método listener que recibe los mensajes, lo formatea y  hace lo que corresponde en cada caso:
	 * - Caso 1: a[0].equals(GET_ALL_VIDEO_ASK): llega la petición del requerimiento dar todos los video por lo
	 * que pide todos los videos a la clase principal y manda un mensaje con la respuesta
	 * - Caso 2: a[0].equals(GET_ALL_VIDEO_REPLY): llega la respuesta de la petición del requerimiento por lo tanto
	 * Coge la respuesta y la guarda en respuesta
	 * <b>post: </b> se ha mandado el mensaje a la cola
	 *
	 * @param message - mensaje que llego
	 */
	@Override
	public void onMessage( Message message )
	{
		try
		{
			OnMessageResponse response = onMessageSetUp( message, ALL_FESTIVALES_ASK, ALL_FESTIVALES_RESPONSE );
			if( response.isAnswer( ) )
			{
				if( waiting )
				{
					this.respuesta.addAll( protocolToList( response.getParams( ), ProtocoloFestival.class ) );
					this.numberApps++;
				}
			}
			else if( response.getQueue( ) != null )
			{
				String protocol = ALL_FESTIVALES_RESPONSE + CONNECTOR + listToProtocol( FestivAndesCM.festivalesToProtocol( master.getFestivalesLocal() ) );
				enqueueResponse( response.getQueue( ), protocol );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
	
	/**
	 * Método que manda el mensaje para solicitar el requerimiento de las todos los videos
	 * <b>post: </b> se han mandado todos los mensaje
	 *
	 * @throws JMSException    - Caso de JMSException
	 * @throws NamingException - Caso de NamingException
	 */
	@Override
	void sendMessage( ) throws NamingException, JMSException
	{
		sendMessageSetUp( ALL_FESTIVALES_ASK, "" );
	}
}
