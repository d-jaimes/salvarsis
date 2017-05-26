package jms;

import com.rabbitmq.jms.client.message.RMQBytesMessage;
import exceptions.IncompleteReplyException;
import exceptions.NonReplyException;
import protocolos.Protocolo;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Manager of JMS. Sends and recieves Messages in the Queue
 */
public abstract class JMSManager<T> implements MessageListener, ExceptionListener, JMSConstantes
{
	class OnMessageResponse
	{
		private String params;
		
		private String queue;
		
		private boolean response;
		
		/**
		 * Retrieves the response of the OnMessageResponse
		 *
		 * @return The response of the OnMessageResponse
		 */
		boolean isAnswer( )
		{
			return response;
		}
		
		/**
		 * Retrieves the params of the OnMessageResponse
		 *
		 * @return The params of the OnMessageResponse
		 */
		String getParams( )
		{
			return params;
		}
		
		/**
		 * Retrieves the queue of the OnMessageResponse
		 *
		 * @return The queue of the OnMessageResponse
		 */
		String getQueue( )
		{
			return queue;
		}
	}
	
	// ----------------------------------------
	// PROTOCOL
	// ----------------------------------------
	
	private boolean isSuscribed;
	
	/**
	 * Maximum time for application to respond to some request
	 */
	private final static int TIME_OUT = 10;
	
	/**
	 * Route for the Remote Connection Factory
	 */
	private final static String REMOTE_CONNECTION_FACTORY = "java:global/RMQClient";
	
	/**
	 * Connector between main components in the protocol
	 */
	final static String CONNECTOR = "@@@";
	
	/**
	 * List in which other applications answers are stored
	 */
	List<T> respuesta = new LinkedList<>( );
	
	/**
	 * Establishes it the Application waits or not for other applications to answer the request
	 */
	boolean waiting;
	
	/**
	 * Number of Application available in the Sistem.
	 */
	private int numberAppsTotal;
	
	/**
	 * Number of Applications that have responded to the request made
	 */
	int numberApps;
	
	/**
	 * TopicSession used in the Topic Connection
	 */
	private TopicSession topicSession;
	
	/**
	 * Topic that manages the connection to the Topic given
	 */
	private Topic topic;
	
	// ----------------------------------------
	// QUEUE
	// ----------------------------------------
	
	/**
	 * Personal Application Queue
	 */
	private String queueAsk;
	
	private String queueResponse;
	
	// ----------------------------------------
	// CONFIGURATION
	// ----------------------------------------
	
	/**
	 * Method that initializes the JMSManager with the given parameters<br>
	 * The method generates the suscriptions and publications to the Queues and Topics
	 *
	 * @param numerApps     - Total application number
	 * @param queueAsk      - Queue de pregunta
	 * @param queueResponse - Queue de respuesta
	 * @param topicTheme    - Route of the Topic
	 */
	public void setUpJMSManager( int numerApps, String queueAsk, String queueResponse, String topicTheme )
	{
		try
		{
			this.numberAppsTotal = numerApps - 1;
			this.queueAsk = queueAsk;
			this.queueResponse = queueResponse;
			setupMyQueue( ); // 1
			setupSubscriptions( topicTheme ); // 2
			waiting = false;
		}
		catch( JMSException | NamingException e )
		{
			e.printStackTrace( );
		}
	}
	
	/**
	 * Method that initializes all the suscriptions to the Topics
	 * En este caso solo al topic de topicAllVideos ( ? ) //TODO
	 */
	private void setupSubscriptions( String topicTheme )
	{
		if( !isSuscribed )
		{
			// Init Topic para consumir donde llegan las peticiones
			try
			{
				InitialContext ctx = new InitialContext( );
				this.topic = ( Topic ) ctx.lookup( topicTheme );
				TopicConnectionFactory connFactory = ( TopicConnectionFactory ) ctx.lookup( REMOTE_CONNECTION_FACTORY );
				TopicConnection topicConn = connFactory.createTopicConnection( );
				this.topicSession = topicConn.createTopicSession( false, Session.AUTO_ACKNOWLEDGE );
				TopicSubscriber topicSubscriber = topicSession.createSubscriber( topic );
				topicSubscriber.setMessageListener( this );
				topicConn.setExceptionListener( this );
				topicConn.start( );
				
				isSuscribed = true;
			}
			catch( NamingException | JMSException e )
			{
				e.printStackTrace( );
			}
		}
	}
	
	/**
	 * Method that publishes and suscribes the personal queue in the application
	 *
	 * @throws NamingException see {@link NamingException}
	 * @throws JMSException    see {@link JMSException}
	 */
	private void setupMyQueue( ) throws NamingException, JMSException
	{
		// conecta a la cola para respuestas propias.
		InitialContext ctx = new InitialContext( );
		Queue queue = ( Queue ) ctx.lookup( this.queueAsk );
		QueueConnectionFactory connFactory = ( QueueConnectionFactory ) ctx.lookup( REMOTE_CONNECTION_FACTORY );
		QueueConnection queueConn = connFactory.createQueueConnection( );
		QueueSession queueSession = queueConn.createQueueSession( false, Session.AUTO_ACKNOWLEDGE );
		QueueReceiver queueReceiver = queueSession.createReceiver( queue );
		queueReceiver.setMessageListener( this );
		queueConn.setExceptionListener( this );
		queueConn.start( );
		
		// Connecta a la cola para respuestas ajenas
		InitialContext ctx2 = new InitialContext( );
		Queue queue2 = ( Queue ) ctx2.lookup( this.queueResponse );
		QueueConnectionFactory connFactory2 = ( QueueConnectionFactory ) ctx2.lookup( REMOTE_CONNECTION_FACTORY );
		QueueConnection queueConn2 = connFactory2.createQueueConnection( );
		QueueSession queueSession2 = queueConn2.createQueueSession( false, Session.AUTO_ACKNOWLEDGE );
		QueueReceiver queueReceiver2 = queueSession2.createReceiver( queue2 );
		queueReceiver2.setMessageListener( this );
		queueConn2.setExceptionListener( this );
		queueConn2.start( );
	}
	
	// ----------------------------------------
	// COMUNICACION
	// ----------------------------------------
	
	/**
	 * Method that sends the message given by parameter to the Queue given
	 *
	 * @param queueName - Queue Route
	 * @param response  - Message to Send
	 * @throws NamingException see {@link NamingException}
	 * @throws JMSException    see {@link JMSException}
	 */
	void enqueueResponse( String queueName, String response ) throws NamingException, JMSException
	{
		System.out.println( "En Queue: " + queueName );
		System.out.println( "Answer: " + response );
		
		// Conecta a la cola de respuestas del que pidio
		InitialContext ctx = new InitialContext( );
		Queue queue = ( Queue ) ctx.lookup( queueName );
		QueueConnectionFactory connFactory = ( QueueConnectionFactory ) ctx.lookup( REMOTE_CONNECTION_FACTORY );
		QueueConnection queueConn = connFactory.createQueueConnection( );
		QueueSession queueSession = queueConn.createQueueSession( false, Session.DUPS_OK_ACKNOWLEDGE );
		QueueSender queueSender = queueSession.createSender( queue );
		queueSender.setDeliveryMode( DeliveryMode.NON_PERSISTENT );
		TextMessage message = queueSession.createTextMessage( response );
		queueSender.send( message );
		System.out.println( "Sent: " + message.getText( ) );
		queueConn.close( );
	}
	
	/**
	 * Method which recieves the messages and inform about the type of message and its information
	 *
	 * @param message Message received
	 * @param ask     String of the protocol when the message received is making a request, and must be resolved by the implementer of the method
	 * @param reply   String of the protocol when the message received is giving an answer for the request made.
	 * @return OnMessageReponse with the information needed
	 */
	OnMessageResponse onMessageSetUp( Message message, String ask, String reply )
	{
		OnMessageResponse response = new OnMessageResponse( );
		RMQBytesMessage msg = ( RMQBytesMessage ) message;
		
		try
		{
			byte[] arr = new byte[ ( int ) msg.getBodyLength( ) ];
			msg.readBytes( arr );
			
			String protocolo = new String( arr, StandardCharsets.UTF_8 );
			System.out.println( "Received: " + protocolo );
			String[] parts = protocolo.split( CONNECTOR );
			if( parts.length >= 2 )
			{
				String command = parts[ 0 ];
				//				String queue = parts[ 1 ];
				// boolean mePreguntoAMi = queue.equals( this.queueAsk );
				
				if( command.equals( ask ) )  // Respondo
				{
					response.queue = this.queueResponse;
					if( parts.length == 3 )
					{
						response.params = parts[ 2 ];
					}
				}
				else if( command.equals( reply ) )             // Me Responden
				{
					response.params = parts[ 1 ];
					response.response = true;
				}
			}
		}
		catch( Exception ex )
		{
			System.err.println( ex.getMessage( ) );
		}
		return response;
	}
	
	/**
	 * Method that sends the request to the servers
	 *
	 * @throws JMSException    - Caso de JMSException
	 * @throws NamingException - Caso de NamingException
	 */
	void sendMessageSetUp( String ask, String params ) throws JMSException, NamingException
	{
		String protocol = ask + CONNECTOR + this.queueAsk + CONNECTOR + params;
		
		// Conecta al Topic para mandar la petición
		TopicPublisher topicPublisher = this.topicSession.createPublisher( this.topic );
		topicPublisher.setDeliveryMode( DeliveryMode.NON_PERSISTENT );
		TextMessage message = topicSession.createTextMessage( );
		message.setText( protocol );
		topicPublisher.publish( message );
		System.out.println( "Published: " + message.getText( ) );
	}
	
	/**
	 * Method calldes when an error occurrs with JMS
	 */
	@Override
	public void onException( JMSException exception )
	{
		System.err.println( "Something bad happended: " + exception );
	}
	
	/**
	 * Gets the response of the Request
	 *
	 * @return List - List with the response to the request given by the servers
	 * @throws IncompleteReplyException - Caso de IncompleteReplyException
	 * @throws NonReplyException        - Caso de NonReplyException
	 * @throws JMSException             - Caso de JMSException
	 */
	public List<T> getResponse( ) throws IncompleteReplyException, JMSException, NamingException, InterruptedException, NonReplyException
	{
		sendMessage( );                           // Envía el mensaje de solicitud del requerimiento al Topic
		
		waiting = true;                           // Lo hace para indicar que si esta esperando respuestas
		numberApps = 0;                           // Pone en 0 el numero de respuestas que han llegado
		int count = 0;                            // Pone en 0 los segundos de espera transcurridos
		
		while( count < TIME_OUT && this.numberApps != this.numberAppsTotal )
		{
			TimeUnit.SECONDS.sleep( 1 );
			count++;
			System.out.println( ( TIME_OUT - count ) + " Seconds Left..." );
		}
		
		waiting = false;
		numberApps = 0;
		
		verifyResponse( count );
		
		List<T> res = new LinkedList<>( this.respuesta );
		respuesta.clear( );
		
		// Retorna con la respuesta completa de todas las aplicaciones
		return res;
	}
	
	private void verifyResponse( int count ) throws NonReplyException, IncompleteReplyException
	{
		if( count == TIME_OUT ) // Verifica si se cumplió el TimeOut
		{
			if( respuesta.isEmpty( ) )
			{
				throw new NonReplyException( "Time Out - No Reply" ); // Exception que indica que se cumplido el time out y nadie respondido
			}
			throw new IncompleteReplyException( "Time out ", respuesta ); // Exception que indica que se cumplido el time out pero posiblemente algunos no han respondido
		}
		if( respuesta.isEmpty( ) )
		{
			throw new NonReplyException( "Got all responses but no data were detected" ); // Exception que indica que todos respondieron pero no llegaron videos
		}
	}
	
	/**
	 * Sends the message to the Queue<br>
	 * It must call the method {@link #sendMessageSetUp(String, String)}
	 *
	 * @throws NamingException see {@link NamingException}
	 * @throws JMSException    see {@link JMSException}
	 */
	abstract void sendMessage( ) throws NamingException, JMSException;
	
	// UTILIDADES
	
	/**
	 * Transforms the List of Objects to the protocol containing list params<br>
	 * The type of the elements in the list must override the toString method, in order to make the protocol work
	 *
	 * @param list List of Objects to parse to the protocol
	 * @return The Protocol with the information of the List
	 */
	String listToProtocol( List<T> list )
	{
		StringBuilder sBuilder = new StringBuilder( );
		for( T t : list )
		{
			sBuilder.append( t.toString( ) ).append( Protocolo.SEPARADOR_ELEMENTOS_LISTA );
		}
		return sBuilder.toString( );
	}
	
	/**
	 * Transforms the Protocol to the List of the Desired Class
	 *
	 * @param protocol Protocol to transform. <strong>MUST</strong> be a List
	 * @param cl       Type of Class to the elements in the Protocol. The Type Class <strong>MUST</strong> implement the {@link Protocolo} interface
	 * @return The List of Elements in the Protocol String
	 */
	List<T> protocolToList( String protocol, Class<? extends Protocolo> cl )
	{
		List<T> list = new LinkedList<>( );
		try
		{
			for( String s : protocol.split( Protocolo.SEPARADOR_ELEMENTOS_LISTA ) )
			{
				list.add( protocolToObject( s, cl ) );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
		return list;
	}
	
	@SuppressWarnings( "unchecked" )
	T protocolToObject( String protocol, Class<? extends Protocolo> cl ) throws Exception
	{
		Method method = cl.getMethod( "fromProtocol", String.class );
		return ( T ) method.invoke( cl.newInstance( ), protocol );
	}
}
