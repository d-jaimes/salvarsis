package protocolos;

/**
 * Created by Dave on 21/05/2017.
 */
public class ProtocoloCompania implements Protocolo<ProtocoloCompania>
{
	private Integer response;
	
	public ProtocoloCompania( Integer response )
	{
		this.response = response;
	}
	
	public ProtocoloCompania( )
	{
	}
	
	public ProtocoloCompania( String protocolo )
	{
		String[] componentes = protocolo.split( SEPARADOR_ELEMENTOS_LISTA );
		
		response = Integer.parseInt( componentes[ 0 ] );
	}
	
	/**
	 * Retrieves the response of the ProtocoloCompania
	 *
	 * @return The response of the ProtocoloCompania
	 */
	public Integer getResponse( )
	{
		return response;
	}
	
	/**
	 * Updates the response of the ProtocoloCompania by the one given by parameter
	 *
	 * @param response The new response of the ProtocoloCompania
	 */
	public void setResponse( Integer response )
	{
		this.response = response;
	}
	
	@Override
	public ProtocoloCompania fromProtocol( String s )
	{
		return new ProtocoloCompania( s );
	}
	
	@Override
	public String toString( )
	{
		return String.valueOf( response );
	}
}
