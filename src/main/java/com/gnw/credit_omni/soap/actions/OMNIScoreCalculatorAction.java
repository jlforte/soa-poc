package com.gnw.credit_omni.soap.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;


public class OMNIScoreCalculatorAction extends AbstractActionLifecycle
{
	protected ConfigTree config;

	public static final String OMNI_SCORE_ATTR_NAME  = "OMNI_Score";
	public static final String OMNI_SCORE_VALUE      = "777";
	
	private static Logger log = Logger.getLogger(OMNIScoreCalculatorAction.class);

	//Constructor
	public OMNIScoreCalculatorAction(ConfigTree config){
		this.config = config;
	}

	//The Process Method
	public Message process(Message message) throws ActionProcessingException,FileNotFoundException,IOException 
	{

		// add the OMNI score to the message body preserving any existing contents	
		message.getBody().add(OMNI_SCORE_ATTR_NAME, OMNI_SCORE_VALUE);

		log.info("OMNIScoreCalculatorAction.process: adding attribute=" + OMNI_SCORE_ATTR_NAME + " value=" + message.getBody().get(OMNI_SCORE_ATTR_NAME));

		return message;
	}	

}
