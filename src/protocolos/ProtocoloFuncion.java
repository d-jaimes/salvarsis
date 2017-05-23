package protocolos;

public class ProtocoloFuncion implements Protocolo<ProtocoloFuncion>
{
	private String nombreApp;
	
	private long idFuncion;
	
	private String fecha;
	
	private long idLugar;
	
	private long idEspectaculo;
	
	private boolean realizado;

	public ProtocoloFuncion(String nombreApp, long idFuncion, String fecha, long idLugar, long idEspectaculo,
			boolean realizado) {
		super();
		this.nombreApp = nombreApp;
		this.idFuncion = idFuncion;
		this.fecha = fecha;
		this.idLugar = idLugar;
		this.idEspectaculo = idEspectaculo;
		this.realizado = realizado;
	}
	
	public ProtocoloFuncion(String respuesta) 
	{
		String[] componentes = respuesta.split(SEPARADOR_PARAMS);
		this.nombreApp = componentes[0];
		this.idFuncion = Long.parseLong(componentes[1]);
		this.fecha = componentes[2];
		this.idLugar = Long.parseLong(componentes[3]);
		this.idEspectaculo =Long.parseLong(componentes[3]);
		this.realizado = Boolean.parseBoolean(componentes[4]);
		
	}

	public String getNombreApp() {
		return nombreApp;
	}

	public void setNombreApp(String nombreApp) {
		this.nombreApp = nombreApp;
	}

	public long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(long idFuncion) {
		this.idFuncion = idFuncion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public long getIdLugar() {
		return idLugar;
	}

	public void setIdLugar(long idLugar) {
		this.idLugar = idLugar;
	}

	public long getIdEspectaculo() {
		return idEspectaculo;
	}

	public void setIdEspectaculo(long idEspectaculo) {
		this.idEspectaculo = idEspectaculo;
	}

	public boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(nombreApp + SEPARADOR_DATOS_ELEMENTOS_LISTA);
		builder.append(idFuncion + SEPARADOR_DATOS_ELEMENTOS_LISTA);
		builder.append(fecha + SEPARADOR_DATOS_ELEMENTOS_LISTA);
		builder.append(idLugar + SEPARADOR_DATOS_ELEMENTOS_LISTA);
		builder.append(idEspectaculo + SEPARADOR_DATOS_ELEMENTOS_LISTA);
		builder.append(realizado);
		
		return builder.toString();
	}

	@Override
	public ProtocoloFuncion fromProtocol(String s) {
		
		return new ProtocoloFuncion(s);
	}


}
