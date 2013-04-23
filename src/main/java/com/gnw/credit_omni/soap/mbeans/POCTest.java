package com.gnw.credit_omni.soap.mbeans;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.client.ServiceInvoker;

public class POCTest implements POCTestMBean
{
	private static Logger log = Logger.getLogger(POCTest.class);

	public void deliver() throws Exception
	{
		log.info("SOAPUtils.deliver: entry");
		
		// set the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");

		Message esbMessage = MessageFactory.getInstance().getMessage();
		
		esbMessage.getBody().add("scoreType", "OMNIScoreCalculatorAction");
		esbMessage.getBody().add("cutomerId", "1234");
		esbMessage.getBody().add("firstName", "David");
		esbMessage.getBody().add("lastName",  "Suzuki");
		esbMessage.getBody().add("city",      "Toronto");
				
		ServiceInvoker invoker;

		invoker = new ServiceInvoker("RequestHandler", "HandleIncomingRequest");
		invoker.deliverAsync(esbMessage);
		
		log.info("SOAPUtils.deliver: return");

	}

	public void start() throws Exception {}
	public void stop() throws Exception {}
}
