package com.gnw.credit_omni.soap.actions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.gnw.credit_omni.soap.utils.SOAPUtils;


public class IncomingRequestGatewayAction extends AbstractActionLifecycle
{
	private static final String TRUE = "true";

	private static final String SCORE_TYPE_ATTR_NAME = "scoreType";

	private static final String PROCESSED_FLAG_ATTR_NAME = "processedFlag";

	protected ConfigTree config;

	private static Logger log = Logger.getLogger(IncomingRequestGatewayAction.class);

	//Constructor
	public IncomingRequestGatewayAction(ConfigTree config) {
		this.config = config;
	}

	//The Process Method
	public Message process(Message message) throws ActionProcessingException,FileNotFoundException,IOException 
	{
		log.info("IncomingRequestGatewayAction.process: entry: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, SCORE_TYPE_ATTR_NAME) +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag")); 
		
		log.debug("IncomingRequestGatewayAction.process: entry: message: " + SOAPUtils.messageToString(message) + 
				  " header: " + message.getHeader() + 
				  " context: " + message.getContext());
		
		//	skip messages that have been processed
		String processedFlag = SOAPUtils.getValueOfAttributeInMessage(message, PROCESSED_FLAG_ATTR_NAME);
		log.info("IncomingRequestGatewayAction.process: processedFlag=" + processedFlag); 
		
		if ( processedFlag != null && processedFlag.equalsIgnoreCase(TRUE)) {
			log.info("IncomingRequestGatewayAction.process: return: message has already been processed"); 
			return message;
		}
		
		//	mark message as processed
		SOAPUtils.addAttributeAndValueToMessage(message, PROCESSED_FLAG_ATTR_NAME, TRUE);
		
		//	route the incoming message based on the score type in the message body
		String scoreType = SOAPUtils.getValueOfAttributeInMessage(message, SCORE_TYPE_ATTR_NAME);		
		
		//
		// OMNI
		//
		if (scoreType.equalsIgnoreCase(SOAPUtils.SCORE_TYPE_OMNI)) {
			log.info("IncomingRequestGatewayAction.process: received OMNI Score request"); 
			log.info("IncomingRequestGatewayAction.process: routing message to " + SOAPUtils.SERVICE_NAME_OMNI);
			routeMessage(message, SOAPUtils.SERVICE_CATAEGORY_OMNI, SOAPUtils.SERVICE_NAME_OMNI, scoreType);
		//
		//	OMNI + Credit Bureaus
		//
		} else if (scoreType.equalsIgnoreCase(SOAPUtils.SCORE_TYPE_OMNI_AND_CREDIT)) {
			log.info("IncomingRequestGatewayAction.process: received OMNI + Credit request"); 
			log.info("IncomingRequestGatewayAction.process: routing message to " + SOAPUtils.SERVICE_CATAEGORY_OMNI);
			routeMessage(message, SOAPUtils.SERVICE_CATAEGORY_OMNI,        SOAPUtils.SERVICE_NAME_OMNI, scoreType);
			log.info("IncomingRequestGatewayAction.process: routing message to " + SOAPUtils.SERVICE_NAME_WEBSERVICES);
			routeMessage(message, SOAPUtils.SERVICE_CATAEGORY_WEBSERVICES, SOAPUtils.SERVICE_NAME_WEBSERVICES, scoreType);
		//
		//	flag unknown score type as an error
		//
		} else {
			log.error("IncomingRequestGatewayAction.process: received unknown score type request: " + scoreType +
					  " message: " + SOAPUtils.messageToString(message) + " message header: " + message.getHeader());
		}
		

		log.info("IncomingRequestGatewayAction.process: return: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, SCORE_TYPE_ATTR_NAME) +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));
		
		return message;
	}
	
	/**
	 * @param message
	 * @param serviceCategory
	 * @param serviceName
	 * @param scoreType
	 */
	public void routeMessage(Message message, String serviceCategory, String serviceName, String scoreType) {
		log.debug("IncomingRequestGatewayAction.routeMessage: entry: message: " + SOAPUtils.messageToString(message) + 
				 " serviceCategory=" + serviceCategory + 
				 " serviceName="     + serviceName     + 
				 " scoreType="       + scoreType);
		try {
			SOAPUtils.routeESBMessage(message, serviceCategory, serviceName);
		} catch (Exception e) {
			log.error("IncomingRequestGatewayAction.routeMessage: caught Exception " + e + " on esb message: " +  SOAPUtils.messageToString(message) + 
				      " serviceCategory="    + serviceCategory + 
				 	  " serviceName="        + serviceName     + 
					  " scoreType="          + scoreType       +
					  " exception message: " + e.getMessage());
		}
		log.debug("IncomingRequestGatewayAction.routeMessage: return");		
	}
	
	/**
	 * default success handler
	 * @param message
	 */
	public void processSuccess(Message message) {
		log.info("IncomingRequestGatewayAction.processSuccess: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, SCORE_TYPE_ATTR_NAME) +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag")); 
	}
	
	/**
	 * @param message
	 * @param th
	 */
	public void processException(Message message, java.lang.Throwable th) {
		log.info("IncomingRequestGatewayAction.processException: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, SCORE_TYPE_ATTR_NAME) +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag") +
				 " exception: " + th + 
				 " exception message: " + th.getMessage()); 
	}
}
