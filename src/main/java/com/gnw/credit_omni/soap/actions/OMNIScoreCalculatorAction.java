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
	//The Name of the csv file to pull from.
	private String fileName;

	//The Config Tree
	protected ConfigTree _config;

	private static Logger log = Logger.getLogger(OMNIScoreCalculatorAction.class);

	//Constructor
	public OMNIScoreCalculatorAction(ConfigTree config)
	{
		_config = config;
	}

	//Setter
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}

	//The Process Method
	public Message process(Message message) throws ActionProcessingException,FileNotFoundException,IOException 
	{
		//Open the file stream
		File file = new File(_config.getAttribute("fileName"));

		//Create a new message object, add the list to the message body.
		message = MessageFactory.getInstance().getMessage();
		message.getBody().add("fileName", file.getAbsolutePath());

		log.info("ProcessOmniRequestAction: message.get('fileName')= " + message.getBody().get("fileName"));

		return message;
	}	

}
