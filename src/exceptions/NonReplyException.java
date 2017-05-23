/**
 * -------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package jms;

/**
 * clase NonReplyException
 *
 * @author Juan
 */
public class NonReplyException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Método constructor de la clase IncompleteReplyException
	 * <b>post: </b> Crea la  NonReplyException con los valores que entran como parámetro
	 *
	 * @param message - mensaje de la NonReplyException
	 */
	public NonReplyException( String message )
	{
		super( message );
	}
}