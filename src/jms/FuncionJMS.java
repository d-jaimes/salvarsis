package jms;

import protocolos.ProtocoloFuncion;
import tm.FuncionesCM;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

/**
 * Created by d.jaimes on 23/05/2017.
 */
public class FuncionJMS extends JMSManager<ProtocoloFuncion>
{
    private static FuncionJMS instancia;
    private FuncionesCM master;

    public static FuncionJMS getInstance( FuncionesCM transactionManager )
    {
        instancia = instancia == null ? new FuncionJMS( ) : instancia;
        instancia.master = transactionManager;
        return instancia;
    }

    @Override
    void sendMessage() throws NamingException, JMSException
    {
        sendMessageSetUp( ALL_FUNCIONES_ASK, "" );

    }

    @Override
    public void onMessage(Message message) {

        try
        {
            OnMessageResponse response = onMessageSetUp( message, ALL_FUNCIONES_ASK, ALL_FUNCIONES_RESPONSE );
            if( response.isAnswer( ) )
            {
                if( waiting )
                {
                    this.respuesta.addAll( protocolToList( response.getParams( ), ProtocoloFuncion.class ) );
                    this.numberApps++;
                }
            }
            else if( response.getQueue( ) != null )
            {
                String protocol = ALL_FUNCIONES_RESPONSE + CONNECTOR + listToProtocol( FuncionesCM.ProtocoloFuncion( master.getFuncionesLocal( ) ) );
                enqueueResponse( response.getQueue( ), protocol );
            }
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
