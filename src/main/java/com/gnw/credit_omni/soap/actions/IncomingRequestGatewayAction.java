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

import com.gnw.credit_omni.soap.utils.SOAPUtils;


public class IncomingRequestGatewayAction extends AbstractActionLifecycle
{

	private static final String SCORE_TYPE_OMNI_AND_CREDIT       = "OMNIScoreCalculatorAction AND CREDIT";
	private static final String SCORE_TYPE_OMNI                  = "OMNIScoreCalculatorAction";
	private static final String SERVICE_CATAEGORY_CREDIT         = "Credit";
	private static final String SERVICE_CATAEGORY_CALCULATE_OMNI = "CalculateOMNIScore";
	private static final String SERVICE_NAME_CALCULATE_OMNI      = "OMNI";
	
	private static Logger log = Logger.getLogger(IncomingRequestGatewayAction.class);

	//Constructor
	public IncomingRequestGatewayAction() {
	}

	//The Process Method
	public Message process(Message message) throws ActionProcessingException,FileNotFoundException,IOException 
	{
		//Examine the incoming message and route based on the scoreType in the message body
		message = MessageFactory.getInstance().getMessage();
		
		String scoreType = (String)message.getBody().get("scoreType");
		
		// OMNI
		if (scoreType.equalsIgnoreCase(SCORE_TYPE_OMNI)) {
			log.info("ProcessOmniRequestAction: received OMNIScoreCalculatorAction request.  routing message to OMNIScoreCalculatorAction");
			routeMessage(message, SERVICE_CATAEGORY_CREDIT, SERVICE_NAME_CALCULATE_OMNI);
		//
		//
		//
		} else if (scoreType.equalsIgnoreCase(SCORE_TYPE_OMNI_AND_CREDIT)) {
			log.info("ProcessOmniRequestAction: received OMNIScoreCalculatorAction + Credit request.  routing message to OMNIScoreCalculatorAction and Credit Bureaus");
			routeMessage(message, SERVICE_CATAEGORY_CALCULATE_OMNI, SERVICE_NAME_CALCULATE_OMNI);
		} else {
			log.info("ProcessOmniRequestAction: received unknown score type request: " + scoreType);
		}
		
		return message;
	}
	
	public void routeMessage(Message message, String category, String name) {
		try {
			SOAPUtils.routeESBMessage(message, SERVICE_CATAEGORY_CALCULATE_OMNI, SERVICE_NAME_CALCULATE_OMNI);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
