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
		log.info("RetrieveCreditScoresFromCacheAction.process: message contents: " + SOAPUtils.mapToString((Map<String,Object>)message.getBody()));

		return message;
	}	

}
