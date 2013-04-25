package com.gnw.credit_omni.soap.actions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.gnw.credit_omni.soap.utils.SOAPUtils;


public class WebServicesAction extends AbstractActionLifecycle
{

	protected ConfigTree config;
	private static final String SCORE_TYPE_ATTR_NAME = "scoreType";
	
	private static Logger log = Logger.getLogger(WebServicesAction.class);

	//Constructor
	public WebServicesAction(ConfigTree config){
		this.config = config;
	}

	//The Process Method
	public Message process(Message message) throws ActionProcessingException,FileNotFoundException,IOException 
	{
		log.info("WebServicesAction.process:  entry: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));
		
		
		log.debug("WebServicesAction.process: entry: message: " + SOAPUtils.messageToString(message) + 
				  " header: "  + message.getHeader() + 
				  " context: " + message.getContext());
		
		
		log.info("WebServicesAction.process: return: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " messageId="     + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));
		
		return message;
	}	

}
