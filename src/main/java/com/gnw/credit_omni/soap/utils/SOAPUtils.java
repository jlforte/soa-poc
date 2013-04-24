package com.gnw.credit_omni.soap.utils;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;
import org.jboss.soa.esb.client.ServiceInvoker;

public class SOAPUtils 
{
	private static Logger log = Logger.getLogger(SOAPUtils.class);

	public static void routeESBMessage(Message esbMessage, String category, String name) throws Exception
	{
		log.info("SOAPUtils.routeESBMessage: entry: message=" + esbMessage + " category=" + category + " name=" + name);
		
		ServiceInvoker invoker = new ServiceInvoker(category, name);
		invoker.deliverAsync(esbMessage);

		log.info("SOAPUtils.routeESBMessage: return");

	}

	public static final String SCORE_TYPE_OMNI_AND_CREDIT           = "OMNIScoreCalculatorAction AND CREDIT";
	public static final String SCORE_TYPE_OMNI                      = "OMNIScoreCalculatorAction";

	public static final String SERVICE_CATAEGORY_CREDIT             = "Credit";
	public static final String SERVICE_CATAEGORY_OMNI               = "OMNI";
	public static final String SERVICE_CATAEGORY_WEBSERVICES        = "CreditBureauWebServices";
	public static final String SERVICE_CATAEGORY_IIMSRESPONSE       = "IIMSResponse";

	public static final String SERVICE_NAME_PROCESS_CREDIT_REQUESTS = "ProcessCreditRequests";
	public static final String SERVICE_NAME_OMNI                    = "OMNI";
	public static final String SERVICE_NAME_WEBSERVICES             = "CreditBureauWebServices";
	public static final String SERVICE_NAME_IIMS_RESPONSE           = "IIMSResponse";

}
