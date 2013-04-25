package com.gnw.credit_omni.soap.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.gnw.credit_omni.soap.utils.SOAPUtils;


public class RetrieveCreditScoresFromCacheAction extends AbstractActionLifecycle
{
	//The Config Tree
	protected ConfigTree config;

	private static Logger log = Logger.getLogger(RetrieveCreditScoresFromCacheAction.class);

	//Constructor
	public RetrieveCreditScoresFromCacheAction(ConfigTree config)
	{
		this.config = config;
	}


	//The Process Method
	public Message process(Message message) throws ActionProcessingException,FileNotFoundException,IOException 
	{
		log.info("RetrieveCreditScoresFromCacheAction.process:  entry: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));
		
		
		log.debug("RetrieveCreditScoresFromCacheAction.process: entry: message: " + SOAPUtils.messageToString(message) + 
				  " header: "  + message.getHeader() + 
				  " context: " + message.getContext());
		
		
		log.info("RetrieveCreditScoresFromCacheAction.process: return: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " messageId="     + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));
		

		return message;
	}	

	/**
	 * default success handler
	 * @param message
	 */
	public void processSuccess(Message message) {
		log.info("RetrieveCreditScoresFromCacheAction.processSuccess: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, SOAPUtils.SCORE_TYPE_ATTR_NAME) +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag")); 
	}
	
	/**
	 * @param message
	 * @param th
	 */
	public void processException(Message message, java.lang.Throwable th) {
		log.info("RetrieveCreditScoresFromCacheAction.processException: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, SOAPUtils.SCORE_TYPE_ATTR_NAME) +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag") +
				 " exception: " + th + 
				 " exception message: " + th.getMessage()); 
	}

}
