package jms;


import protocolos.Protocolo;
import protocolos.ProtocoloCompania;
import tm.CompaniasTeatroCM;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

/**
 * /**
 * Created by d.jaimes on 23/05/2017.
 */
public class CompaniasTeatroJMS extends JMSManager<ProtocoloCompania>
{
private static CompaniasTeatroJMS instancia;
private CompaniasTeatroCM master;

public static CompaniasTeatroJMS getInstance(CompaniasTeatroCM transactionManager)
        {
        instancia=instancia==null?new CompaniasTeatroJMS():instancia;
        instancia.master=transactionManager;
        return instancia;
        }

@Override
    void sendMessage()throws NamingException,JMSException
            {
            sendMessageSetUp(ELIMINAR_COMPANIA_ASK,"");

            }

@Override
public void onMessage(Message message){

        try
        {
        OnMessageResponse response=onMessageSetUp(message,ELIMINAR_COMPANIA_ASK,ELIMINAR_COMPANIA_RESPONSE);
        if(response.isAnswer())
        {
        if(waiting)
        {
        this.respuesta.add(new ProtocoloCompania(response.getParams()));
        this.numberApps++;
        }
        }
        else if(response.getQueue()!=null)
        {
            ProtocoloCompania protocolo= new ProtocoloCompania();
            try
            {
                String[] split = response.getParams().split(Protocolo.SEPARADOR_PARAMS);
               Long id= Long.parseLong( split[0]);
               String tipoid= split[1];
                protocolo=  master.deleteCompaniasTeatro(id,tipoid );
            }
            catch (Exception e)
            {
                protocolo.setResponse(0);
            }
        enqueueResponse(response.getQueue(),master.toString());
        }
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        }
        }