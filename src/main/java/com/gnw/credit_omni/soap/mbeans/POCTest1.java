package com.gnw.credit_omni.soap.mbeans;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import com.gnw.credit_omni.soap.utils.SOAPUtils;

public class POCTest1 implements POCTest1MBean
{

	private static Logger log = Logger.getLogger(POCTest1.class);

	public void deliver() throws Exception
	{
		log.info("POCTest1.deliver: entry");
		
		// set the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");

		Message esbMessage = MessageFactory.getInstance().getMessage();
		
//		esbMessage.getBody().add("scoreType", SOAPUtils.SCORE_TYPE_OMNI);
//		esbMessage.getBody().add("cutomerId", "1234");
//		esbMessage.getBody().add("firstName", "David");
//		esbMessage.getBody().add("lastName",  "Suzuki");
//		esbMessage.getBody().add("city",      "Toronto");
				
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put(SOAPUtils.CUSTOMER_ID_ATTR_NAME,     "1234");
		bodyMap.put(SOAPUtils.FIRST_NAME_ATTR_NAME,      "David");
		bodyMap.put(SOAPUtils.LAST_NAME_ATTR_NAME,       "Suzuki");
		bodyMap.put(SOAPUtils.CITY_ATTR_NAME,            "Toronto");
		bodyMap.put(SOAPUtils.SCORE_TYPE_ATTR_NAME,       SOAPUtils.SCORE_TYPE_OMNI);
		bodyMap.put(SOAPUtils.MESSAGE_ID_ATTR_NAME,      "message 1");
		bodyMap.put(SOAPUtils.PROCESSED_FLAG_ATTR_NAME,  "false");
		
		esbMessage.getBody().add(bodyMap);
		ServiceInvoker invoker;
		
		log.info("POCTest1.deliver message 1: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(esbMessage, SOAPUtils.SCORE_TYPE_ATTR_NAME) + 
				 " messageID="  + SOAPUtils.getValueOfAttributeInMessage(esbMessage, SOAPUtils.MESSAGE_ID_ATTR_NAME) + 
				 " service category=" + SOAPUtils.SERVICE_CATAEGORY_CREDIT + 
				 " service name=" + SOAPUtils.SERVICE_NAME_PROCESS_CREDIT_REQUESTS);
		log.debug("POCTest1.deliver message 1: message: " + SOAPUtils.messageToString(esbMessage));

		invoker = new ServiceInvoker(SOAPUtils.SERVICE_CATAEGORY_CREDIT, SOAPUtils.SERVICE_NAME_PROCESS_CREDIT_REQUESTS);
		invoker.deliverAsync(esbMessage);
		
//		Thread.sleep(2000); // wait 2 seconds
//		
//		esbMessage = MessageFactory.getInstance().getMessage();
//		bodyMap.put(SOAPUtils.SCORE_TYPE_ATTR_NAME,   SOAPUtils.SCORE_TYPE_OMNI_AND_CREDIT);
//		bodyMap.put(SOAPUtils.MESSAGE_ID_ATTR_NAME,  "message 2");
//		esbMessage.getBody().add(bodyMap);
//		
//		log.info("POCTest1.deliver message 2: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(esbMessage, SOAPUtils.SCORE_TYPE_ATTR_NAME) + 
//				 " messageID="  + SOAPUtils.getValueOfAttributeInMessage(esbMessage, SOAPUtils.MESSAGE_ID_ATTR_NAME) + 
//				 " service category=" + SOAPUtils.SERVICE_CATAEGORY_CREDIT + 
//				 " name=" + SOAPUtils.SERVICE_NAME_PROCESS_CREDIT_REQUESTS);
//		log.debug("POCTest1.deliver message 2: message: " + SOAPUtils.messageToString(esbMessage));
//
//		invoker = new ServiceInvoker(SOAPUtils.SERVICE_CATAEGORY_CREDIT, SOAPUtils.SERVICE_NAME_PROCESS_CREDIT_REQUESTS);
//		invoker.deliverAsync(esbMessage);

		log.info("POCTest1.deliver: return");

	}

	public void start() throws Exception {}
	public void stop() throws Exception {}
}
