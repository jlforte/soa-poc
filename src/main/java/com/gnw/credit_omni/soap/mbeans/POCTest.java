package com.gnw.credit_omni.soap.mbeans;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.format.MessageFactory;

import com.gnw.credit_omni.soap.utils.SOAPUtils;

public class POCTest implements POCTestMBean
{
	private static final String SCORE_TYPE = "scoreType";
	private static Logger log = Logger.getLogger(POCTest.class);

	public void deliver() throws Exception
	{
		log.info("POCTest.deliver: entry");
		
		// set the ConnectionFactory such that it will use scout
		System.setProperty("javax.xml.registry.ConnectionFactoryClass","org.apache.ws.scout.registry.ConnectionFactoryImpl");

		Message esbMessage = MessageFactory.getInstance().getMessage();
		
//		esbMessage.getBody().add("scoreType", SOAPUtils.SCORE_TYPE_OMNI);
//		esbMessage.getBody().add("cutomerId", "1234");
//		esbMessage.getBody().add("firstName", "David");
//		esbMessage.getBody().add("lastName",  "Suzuki");
//		esbMessage.getBody().add("city",      "Toronto");
				
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("customerId",     "1234");
		bodyMap.put("firstName",      "David");
		bodyMap.put("lastName",       "Suzuki");
		bodyMap.put("city",           "Toronto");
		bodyMap.put(SCORE_TYPE,       SOAPUtils.SCORE_TYPE_OMNI);
		bodyMap.put("messageID",      "message 1");
		bodyMap.put("processedFlag",  "false");
		
		esbMessage.getBody().add(bodyMap);
		ServiceInvoker invoker;
		
		log.info("POCTest.deliver message 1: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(esbMessage, SCORE_TYPE) + 
				 " service category=" + SOAPUtils.SERVICE_CATAEGORY_CREDIT + 
				 " service name=" + SOAPUtils.SERVICE_NAME_PROCESS_CREDIT_REQUESTS);
		log.debug("POCTest.deliver message 1: message: " + SOAPUtils.messageToString(esbMessage));

		invoker = new ServiceInvoker(SOAPUtils.SERVICE_CATAEGORY_CREDIT, SOAPUtils.SERVICE_NAME_PROCESS_CREDIT_REQUESTS);
		invoker.deliverAsync(esbMessage);
		
		esbMessage = MessageFactory.getInstance().getMessage();
		bodyMap.put(SCORE_TYPE,   SOAPUtils.SCORE_TYPE_OMNI_AND_CREDIT);
		bodyMap.put("messageID",  "message 2");
		esbMessage.getBody().add(bodyMap);
		
		log.info("POCTest.deliver message 2: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(esbMessage, SCORE_TYPE) + 
				 " service category=" + SOAPUtils.SERVICE_CATAEGORY_CREDIT + 
				 " name=" + SOAPUtils.SERVICE_NAME_PROCESS_CREDIT_REQUESTS);
		log.debug("POCTest.deliver message 2: message: " + SOAPUtils.messageToString(esbMessage));

		invoker = new ServiceInvoker(SOAPUtils.SERVICE_CATAEGORY_CREDIT, SOAPUtils.SERVICE_NAME_PROCESS_CREDIT_REQUESTS);
		invoker.deliverAsync(esbMessage);

		log.info("POCTest.deliver: return");

	}

	public void start() throws Exception {}
	public void stop() throws Exception {}
}
