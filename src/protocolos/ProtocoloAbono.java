package protocolos;


import java.util.LinkedList;
import java.util.List;

public class ProtocoloAbono implements Protocolo<ProtocoloAbono>
{
	private String nombreApp;
	
	private String nombreFestival;
	
	private long idUsuario;
	
	private String tipoId;
	
	private double descuento;
	
	private List<FuncionAbono> funciones;
	
	private class FuncionAbono
	{
		private String nombreEspectaculo;
		
		private String fecha;
		
		private String nombreLocalidad;
		
		public FuncionAbono(String respuesta) 
		{
			String[] componentes = respuesta.split(SEPARADOR_DATOS_ELEMENTOS_LISTA);
			
			this.nombreEspectaculo = componentes[0];
			this.fecha = componentes[1];
			this.nombreLocalidad = componentes[2];
		}

		public FuncionAbono(String nombreEspectaculo, String fecha, String nombreLocalidad) {
			
			this.nombreEspectaculo = nombreEspectaculo;
			this.fecha = fecha;
			this.nombreLocalidad = nombreLocalidad;
		}

		public String getNombreEspectaculo() {
			return nombreEspectaculo;
		}

		public void setNombreEspectaculo(String nombreEspectaculo) {
			this.nombreEspectaculo = nombreEspectaculo;
		}

		public String getFecha() {
			return fecha;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		public String getNombreLocalidad() {
			return nombreLocalidad;
		}

		public void setNombreLocalidad(String nombreLocalidad) {
			this.nombreLocalidad = nombreLocalidad;
		}
		
		public String toString()
		{
			return nombreEspectaculo + SEPARADOR_DATOS_ELEMENTOS_LISTA
					+ fecha + SEPARADOR_DATOS_ELEMENTOS_LISTA
					+ nombreLocalidad;
		}
		
		
	}

	public ProtocoloAbono(String nombreApp, String nombreFestival, long idUsuario, String tipoId, double descuento,
			List<FuncionAbono> funciones) {
		super();
		this.nombreApp = nombreApp;
		this.nombreFestival = nombreFestival;
		this.idUsuario = idUsuario;
		this.tipoId = tipoId;
		this.descuento = descuento;
		this.funciones = funciones;
	}
	
	public ProtocoloAbono(String respuesta)
	{
		
		String[] componentes = respuesta.split(SEPARADOR_PARAMS);
		
		this.nombreApp = componentes[0];
		this.nombreFestival = componentes[1];
		this.idUsuario = Integer.parseInt(componentes[2]);
		this.tipoId = componentes[3];
		this.descuento = Double.parseDouble(componentes[4]);
		this.funciones = new LinkedList<FuncionAbono>();
		String[] subcomponentes = componentes[5].split(SEPARADOR_ELEMENTOS_LISTA);
		
		for (int i = 0; i < subcomponentes.length; i++) 
		{
			funciones.add(new FuncionAbono(subcomponentes[i]));
		}
	}

	public String getNombreFestival() {
		return nombreFestival;
	}

	public void setNombreFestival(String nombreFestival) {
		this.nombreFestival = nombreFestival;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public List<FuncionAbono> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<FuncionAbono> funciones) {
		this.funciones = funciones;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(nombreApp + SEPARADOR_PARAMS);
		builder.append(nombreFestival + SEPARADOR_PARAMS);
		builder.append(idUsuario + SEPARADOR_PARAMS);
		builder.append(tipoId + SEPARADOR_PARAMS);
		builder.append(descuento + SEPARADOR_PARAMS);
		
		for (int i = 0; i < funciones.size(); i++) 
		{
			if(i != (funciones.size()-1))
			{
				builder.append(funciones.get(i).toString() + SEPARADOR_ELEMENTOS_LISTA);
			}
			else
			{
				builder.append(funciones.get(i).toString());
			}
		}
		
		return builder.toString();
	}

	@Override
	public ProtocoloAbono fromProtocol(String s)
	{
		return new ProtocoloAbono(s);
	}
	
}
