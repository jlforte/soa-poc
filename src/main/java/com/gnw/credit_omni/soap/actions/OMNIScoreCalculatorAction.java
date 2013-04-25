package com.gnw.credit_omni.soap.actions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.gnw.credit_omni.soap.utils.SOAPUtils;


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

		log.info("OMNIScoreCalculatorAction.process:  entry: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));
		
		log.debug("OMNIScoreCalculatorAction.process: entry: message: " + SOAPUtils.messageToString(message) + 
				  " header: "  + message.getHeader() + 
				  " context: " + message.getContext());
		
		log.debug("OMNIScoreCalculatorAction.process: adding attribute=" + OMNI_SCORE_ATTR_NAME + " value=" + OMNI_SCORE_VALUE);
		
		// add the OMNI score to the message body preserving any existing contents
		SOAPUtils.addAttributeAndValueToMessage(message, OMNI_SCORE_ATTR_NAME, OMNI_SCORE_VALUE);

		log.info("OMNIScoreCalculatorAction.process: return: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " OMNI score="    + SOAPUtils.getValueOfAttributeInMessage(message, OMNI_SCORE_ATTR_NAME) +
				 " messageId="     + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));

		return message;
	}	

}
