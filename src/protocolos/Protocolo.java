package protocolos;

public interface Protocolo<T>
{
	String SEPARADOR_PARAMS = "###";
	
	String SEPARADOR_ELEMENTOS_LISTA = ";;;";
	
	String SEPARADOR_DATOS_ELEMENTOS_LISTA = ":::";
	
	String SEPARADOR_LISTA_RFC5 = "|||";
	
	T fromProtocol( String s );
}
