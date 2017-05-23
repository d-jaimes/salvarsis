package protocolos;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProtocoloRFC5 implements Protocolo<ProtocoloRFC5>
{
	@JsonProperty(value="nombreApp")
	private String nombreApp;
	
	@JsonProperty(value="nombreSitio")
	private String nombreSitio;
	
	@JsonProperty(value="tipoDeSitio")
	private String tipoDeSitio;
	
	@JsonProperty(value="espectaculo")
	private String espectaculo;
	
	@JsonProperty(value="categoria")
	private String categoria;
	
	@JsonProperty(value="boletasVendidas")
	private Integer boletasVendidas;
	
	@JsonProperty(value="valorTotalFacturado")
	private Double valorTotalFacturado;
	
	@JsonProperty(value="funciones")
	private List<RFC5Funcion> funciones;
	
	public class RFC5Funcion
	{
	
		@JsonProperty(value="idFuncion")
		private Long idFuncion;
		
		@JsonProperty(value="asistentes")
		private Integer asistentes;
		
		@JsonProperty(value="proporcionAsistencia")
		private Double proporcionAsistencia;
		
		@JsonProperty(value="producido")
		private Double producido;
		
		

		public RFC5Funcion(String respuesta) 
		{
			String[] componentes = respuesta.split(SEPARADOR_DATOS_ELEMENTOS_LISTA);
			this.idFuncion = Long.parseLong(componentes[0]);
			this.asistentes = Integer.parseInt(componentes[1]);
			this.proporcionAsistencia = Double.parseDouble(componentes[2]);
			this.producido = Double.parseDouble(componentes[3]);
		}

		public Long getIdFuncion() {
			return idFuncion;
		}

		public void setIdFuncion(Long idFuncion) {
			this.idFuncion = idFuncion;
		}

		public Integer getAsistentes() {
			return asistentes;
		}

		public void setAsistentes(Integer asistentes) {
			this.asistentes = asistentes;
		}

		public Double getProporcionAsistencia() {
			return proporcionAsistencia;
		}

		public void setProporcionAsistencia(Double proporcionAsistencia) {
			this.proporcionAsistencia = proporcionAsistencia;
		}

		public Double getProducido() {
			return producido;
		}

		public void setProducido(Double producido) {
			this.producido = producido;
		}
		
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			
			builder.append(idFuncion+SEPARADOR_DATOS_ELEMENTOS_LISTA);
			builder.append(asistentes+SEPARADOR_DATOS_ELEMENTOS_LISTA);
			builder.append(proporcionAsistencia+SEPARADOR_DATOS_ELEMENTOS_LISTA);
			builder.append(producido+SEPARADOR_DATOS_ELEMENTOS_LISTA);
			
			return builder.toString();
		}
		
	}

	
	
	public ProtocoloRFC5(String respuesta) {
		
		String[] componentes = respuesta.split(SEPARADOR_PARAMS);
		
		this.nombreApp = componentes[0];
		this.nombreSitio = componentes[2];
		this.tipoDeSitio = componentes[3];
		this.espectaculo = componentes[4];
		this.categoria = componentes[5];
		this.boletasVendidas = Integer.parseInt(componentes[6]);
		this.valorTotalFacturado = Double.parseDouble(componentes[7]);
		this.funciones = new LinkedList<RFC5Funcion>();
		
		String[] subcomponentes = componentes[8].split(SEPARADOR_LISTA_RFC5);
		
		for (int i = 0; i < componentes.length; i++) 
		{
			funciones.add(new RFC5Funcion(subcomponentes[i]));
		}
	}

	public String getNombreSitio() {
		return nombreSitio;
	}

	public void setNombreSitio(String nombreSitio) {
		this.nombreSitio = nombreSitio;
	}

	public String getTipoDeSitio() {
		return tipoDeSitio;
	}

	public void setTipoDeSitio(String tipoDeSitio) {
		this.tipoDeSitio = tipoDeSitio;
	}

	public String getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(String espectaculo) {
		this.espectaculo = espectaculo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getBoletasVendidas() {
		return boletasVendidas;
	}

	public void setBoletasVendidas(Integer boletasVendidas) {
		this.boletasVendidas = boletasVendidas;
	}

	public Double getValorTotalFacturado() {
		return valorTotalFacturado;
	}

	public void setValorTotalFacturado(Double valorTotalFacturado) {
		this.valorTotalFacturado = valorTotalFacturado;
	}

	public List<RFC5Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<RFC5Funcion> funciones) {
		this.funciones = funciones;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(nombreApp + SEPARADOR_PARAMS);
		builder.append(nombreSitio + SEPARADOR_PARAMS);
		builder.append(tipoDeSitio + SEPARADOR_PARAMS);
		builder.append(espectaculo + SEPARADOR_PARAMS);
		builder.append(categoria + SEPARADOR_PARAMS);
		builder.append(boletasVendidas + SEPARADOR_PARAMS);
		builder.append(valorTotalFacturado + SEPARADOR_PARAMS);
		
		for (int i = 0; i < funciones.size(); i++) 
		{
			if(i != (funciones.size()-1))
			{
				builder.append(funciones.get(i).toString() + SEPARADOR_LISTA_RFC5);
			}
			else
			{
				builder.append(funciones.get(i).toString());
			}
		}
		
		return builder.toString();
	}

	@Override
	public ProtocoloRFC5 fromProtocol(String s) 
	{
		
		return new ProtocoloRFC5(s);
	}
	

}
