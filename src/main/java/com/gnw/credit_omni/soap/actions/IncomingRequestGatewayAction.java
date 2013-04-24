package com.gnw.credit_omni.soap.actions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import com.gnw.credit_omni.soap.utils.SOAPUtils;


public class IncomingRequestGatewayAction extends AbstractActionLifecycle
{
	protected ConfigTree config;

	private static Logger log = Logger.getLogger(IncomingRequestGatewayAction.class);

	//Constructor
	public IncomingRequestGatewayAction(ConfigTree config) {
		this.config = config;
	}

	//The Process Method
	public Message process(Message message) throws ActionProcessingException,FileNotFoundException,IOException 
	{
		//	Examine the incoming message and route based on the scoreType in the message body
		
		message = MessageFactory.getInstance().getMessage();
		
		String scoreType = (String)message.getBody().get("scoreType");
		
		//
		// OMNI
		//
		if (scoreType.equalsIgnoreCase(SOAPUtils.SCORE_TYPE_OMNI)) {
			log.info("IncomingRequestGatewayAction.process: received OMNI Score request.  routing message to " + SOAPUtils.SERVICE_NAME_OMNI);
			routeMessage(message, SOAPUtils.SERVICE_CATAEGORY_OMNI, SOAPUtils.SERVICE_NAME_OMNI, scoreType);
		//
		//	OMNI + Credit Bureaus
		//
		} else if (scoreType.equalsIgnoreCase(SOAPUtils.SCORE_TYPE_OMNI_AND_CREDIT)) {
			log.info("IncomingRequestGatewayAction.process: received OMNI + Credit request.  routing message to " + SOAPUtils.SERVICE_CATAEGORY_OMNI + 
					 " and " + SOAPUtils.SERVICE_CATAEGORY_WEBSERVICES);
			routeMessage(message, SOAPUtils.SERVICE_CATAEGORY_OMNI,        SOAPUtils.SERVICE_NAME_OMNI, scoreType);
			routeMessage(message, SOAPUtils.SERVICE_CATAEGORY_WEBSERVICES, SOAPUtils.SERVICE_NAME_WEBSERVICES, scoreType);
		//
		//	flag unknown score type as an error
		//
		} else {
			log.error("IncomingRequestGatewayAction.process: received unknown score type request: " + scoreType);
		}
		
		return message;
	}
	
	/**
	 * @param message
	 * @param category
	 * @param name
	 * @param scoreType
	 */
	public void routeMessage(Message message, String category, String name, String scoreType) {
		try {
			SOAPUtils.routeESBMessage(message, category, name);
		} catch (Exception e) {
			log.error("IncomingRequestGatewayAction.routeMessage: received unknown score type request: " + scoreType);
		}
		
	}

}
