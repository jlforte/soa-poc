package com.gnw.credit_omni.soap.actions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.gnw.credit_omni.soap.utils.SOAPUtils;


public class ResponseHandlerAction extends AbstractActionLifecycle
{
	protected ConfigTree config;
	private static final String TIMESTAMP_ATTR_NAME = "timestamp";
	
	private static Logger log = Logger.getLogger(ResponseHandlerAction.class);

	//Constructor
	public ResponseHandlerAction(ConfigTree config) {
		this.config = config;
	}

	//The Process Method
	public Message process(Message message) throws ActionProcessingException,FileNotFoundException,IOException 	
	{
		log.info("ResponseHandlerAction.process:  entry: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));
		
		log.debug("ResponseHandlerAction.process: entry: message: " + SOAPUtils.messageToString(message) + 
				  " header: "  + message.getHeader() + 
				  " context: " + message.getContext());
		String ts = String.valueOf(System.currentTimeMillis());
		
		log.debug("ResponseHandlerAction.process: adding attribute=" + TIMESTAMP_ATTR_NAME + " value=" + ts);
		
		// add a timestamp to the message body preserving any existing contents	
		SOAPUtils.addAttributeAndValueToMessage(message, TIMESTAMP_ATTR_NAME, ts);

		log.info("ResponseHandlerAction.process: return: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " timestamp="     + SOAPUtils.getValueOfAttributeInMessage(message, TIMESTAMP_ATTR_NAME) +
				 " messageId="     + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));

		
		return message;
	}	

}
