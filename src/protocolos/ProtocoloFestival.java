package protocolos;

/**
 * Created by dnarv on 21/05/2017.
 */
public class ProtocoloFestival implements Protocolo<ProtocoloFestival>
{
	private Long id;
	
	private String nombre;
	
	private String ciudad;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	public ProtocoloFestival( )
	{
	}
	
	public ProtocoloFestival( Long id, String nombre, String ciudad, String fechaInicio, String fechaFin )
	{
		this.id = id;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	/**
	 * Retrieves the id of the ProtocoloFestival
	 *
	 * @return The id of the ProtocoloFestival
	 */
	public Long getId( )
	{
		return id;
	}
	
	/**
	 * Updates the id of the ProtocoloFestival by the one given by parameter
	 *
	 * @param id The new id of the ProtocoloFestival
	 */
	public void setId( Long id )
	{
		this.id = id;
	}
	
	/**
	 * Retrieves the nombre of the ProtocoloFestival
	 *
	 * @return The nombre of the ProtocoloFestival
	 */
	public String getNombre( )
	{
		return nombre;
	}
	
	/**
	 * Updates the nombre of the ProtocoloFestival by the one given by parameter
	 *
	 * @param nombre The new nombre of the ProtocoloFestival
	 */
	public void setNombre( String nombre )
	{
		this.nombre = nombre;
	}
	
	/**
	 * Retrieves the ciudad of the ProtocoloFestival
	 *
	 * @return The ciudad of the ProtocoloFestival
	 */
	public String getCiudad( )
	{
		return ciudad;
	}
	
	/**
	 * Updates the ciudad of the ProtocoloFestival by the one given by parameter
	 *
	 * @param ciudad The new ciudad of the ProtocoloFestival
	 */
	public void setCiudad( String ciudad )
	{
		this.ciudad = ciudad;
	}
	
	/**
	 * Retrieves the fechaInicio of the ProtocoloFestival
	 *
	 * @return The fechaInicio of the ProtocoloFestival
	 */
	public String getFechaInicio( )
	{
		return fechaInicio;
	}
	
	/**
	 * Updates the fechaInicio of the ProtocoloFestival by the one given by parameter
	 *
	 * @param fechaInicio The new fechaInicio of the ProtocoloFestival
	 */
	public void setFechaInicio( String fechaInicio )
	{
		this.fechaInicio = fechaInicio;
	}
	
	/**
	 * Retrieves the fechaFin of the ProtocoloFestival
	 *
	 * @return The fechaFin of the ProtocoloFestival
	 */
	public String getFechaFin( )
	{
		return fechaFin;
	}
	
	/**
	 * Updates the fechaFin of the ProtocoloFestival by the one given by parameter
	 *
	 * @param fechaFin The new fechaFin of the ProtocoloFestival
	 */
	public void setFechaFin( String fechaFin )
	{
		this.fechaFin = fechaFin;
	}
	
	@Override
	public ProtocoloFestival fromProtocol( String s )
	{
		String[] componentes = s.split( SEPARADOR_PARAMS );
		
		Long id = Long.parseLong( componentes[ 0 ] );
		String nombre = componentes[ 1 ];
		String ciudad = componentes[ 2 ];
		String fechaInicio = componentes[ 3 ];
		String fechaFin = componentes[ 4 ];
		return new ProtocoloFestival( id, nombre, ciudad, fechaInicio, fechaFin );
	}
	
	@Override
	public String toString( )
	{
		return String.format( "%s%s%s%s%s%s%s%s%s", id, SEPARADOR_PARAMS, nombre, SEPARADOR_PARAMS, ciudad, SEPARADOR_PARAMS, fechaInicio, SEPARADOR_PARAMS, fechaFin );
	}
}