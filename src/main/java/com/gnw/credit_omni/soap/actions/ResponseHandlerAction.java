package com.gnw.credit_omni.soap.actions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;


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
		// add a timestamp to the message body preserving any existing contents	
		message.getBody().add(TIMESTAMP_ATTR_NAME, String.valueOf(System.currentTimeMillis()));

		log.info("ResponseHandlerAction.process: adding attribute=" + TIMESTAMP_ATTR_NAME + " value=" + message.getBody().get(TIMESTAMP_ATTR_NAME));

		return message;
	}	

}
