package com.gnw.credit_omni.soap.utils;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.client.ServiceInvoker;

public class SOAPUtils 
{
	private static Logger log = Logger.getLogger(SOAPUtils.class);

	public static void routeESBMessage(Message esbMessage, String category, String name) throws Exception
	{
		log.info("SOAPUtils.routeESBMessage: entry: message=" + esbMessage + " category=" + category + " name=" + name);
		
		ServiceInvoker invoker = new ServiceInvoker(category, name);
		invoker.deliverAsync(esbMessage);

		log.info("SOAPUtils.routeESBMessage: return");

	}

}
