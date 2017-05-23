package jms;

public interface JMSConstantes
{
	/**
	 *
	 */
	String TOPIC_ALL_FESTIVALES_GLOBAL = "global/RMQTopicAllFestivales";
	
	/**
	 *
	 */
	String TOPIC_ALL_FESTIVALES_LOCAL = "global/global/RMQAllFestivalesLocal";
	
	/**
	 * Atributo que representa, dentro del mensaje, la solicitud de todos los festivales de manera distribuida
	 */
	String ALL_FESTIVALES_ASK = "allFestivalesAsk";
	
	/**
	 * Atributo que representa, dentro del mensaje, la respuesta del requerimiento dar todos los videos.
	 */
	String ALL_FESTIVALES_RESPONSE = "allFestivalesResponse";
	
	/**
	 *
	 */
	String TOPIC_ALL_FUNCIONES_GLOBAL = "global/RMQTopicAllFunciones";
	
	/**
	 *
	 */
	String TOPIC_ALL_FUNCIONES_LOCAL = "global/global/RMQTopicAllFuncionesLocal";
	
	/**
	 *
	 */
	String ALL_FUNCIONES_ASK = "allFuncionesAsk";
	
	/**
	 *
	 */
	String ALL_FUNCIONES_RESPONSE = "allFuncionesResponse";
	
	/**
	 *
	 */
	String TOPIC_ABONOS_GLOBAL = "global/RMQTopicRegistroAbono";
	
	/**
	 *
	 */
	String TOPIC_ABONOS_LOCAL = "global/global/RMQTopicRegistroAbonoLocal";
	
	/**
	 *
	 */
	String CREAR_ABONOS_ASK = "crearAbonosAsk";
	
	/**
	 *
	 */
	String CREAR_ABONOS_RESPONSE = "crearAbonosResponse";
	
	/**
	 *
	 */
	String TOPIC_COMPANIA_GLOBAL = "global/RMQTopicRetirarComp";
	
	/**
	 *
	 */
	String TOPIC_COMPANIA_LOCAL = "global/global/RMQTopicRetirarCompLocal";
	
	/**
	 *
	 */
	String ELIMINAR_COMPANIA_ASK = "EliminarCompaniaAsk";
	
	/**
	 *
	 */
	String ELIMINAR_COMPANIA_RESPONSE = "EliminarCompaniaResponse";
	
	/**
	 *
	 */
	String TOPIC_REPORTE_GLOBAL = "global/RMQTopicRentabilidad";
	
	/**
	 *
	 */
	String TOPIC_REPORTE_LOCAL = "global/global/RMQTopicRentabilidadLocal";
	
	/**
	 *
	 */
	String REPORTE_ASK = "ReporteAsk";
	
	/**
	 *
	 */
	String REPORTE_RESPONSE = "ReporterESPONSE";
	
	String CONNECTOR_PARAMS = "###";
}