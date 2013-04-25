package com.gnw.credit_omni.soap.utils;

import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.message.Message;

public class SOAPUtils 
{
	private static final String EMPTY_STRING = "";
	private static final String COMMA_AND_SPACE = ", ";
	public static final String SCORE_TYPE_OMNI_AND_CREDIT           = "OMNI AND CREDIT"; //"OMNIScoreCalculatorAction AND CREDIT";
	public static final String SCORE_TYPE_OMNI                      = "OMNI"; // "OMNIScoreCalculatorAction";

	public static final String SERVICE_CATAEGORY_CREDIT             = "Credit";
	public static final String SERVICE_CATAEGORY_OMNI               = "OMNI";
	public static final String SERVICE_CATAEGORY_WEBSERVICES        = "CreditBureauWebServices";
	public static final String SERVICE_CATAEGORY_IIMSRESPONSE       = "IIMSResponse";

	public static final String SERVICE_NAME_PROCESS_CREDIT_REQUESTS = "ProcessCreditRequests";
	public static final String SERVICE_NAME_OMNI                    = "OMNI";
	public static final String SERVICE_NAME_WEBSERVICES             = "CreditBureauWebServices";
	public static final String SERVICE_NAME_IIMS_RESPONSE           = "IIMSResponse";
	
	// attribute names
	public static final String SCORE_TYPE_ATTR_NAME                 = "scoreType";
	public static final String CUSTOMER_ID_ATTR_NAME                = "customerId";
	public static final String FIRST_NAME_ATTR_NAME                 = "firstName";
	public static final String LAST_NAME_ATTR_NAME                  = "lastName";
	public static final String CITY_ATTR_NAME                       = "city";
	public static final String MESSAGE_ID_ATTR_NAME                 = "messageID";
	public static final String PROCESSED_FLAG_ATTR_NAME             = "processedFlag";
	
	private static Logger log = Logger.getLogger(SOAPUtils.class);

	/**
	 * routes an esb message to the supplied service
	 * @param esbMessage
	 * @param serviceCategory
	 * @param serviceName
	 * @throws Exception
	 */
	public static void routeESBMessage(Message esbMessage, String serviceCategory, String serviceName) throws Exception
	{
		log.debug("SOAPUtils.routeESBMessage: entry: message=" + messageToString(esbMessage) + " serviceCategory=" + serviceCategory + " name=" + serviceName);
		
		ServiceInvoker invoker = new ServiceInvoker(serviceCategory, serviceName);
		invoker.deliverAsync(esbMessage);

		log.debug("SOAPUtils.routeESBMessage: return");

	}
	
	/**
	 * return string-ified version of message: [attr1=value1, attr2=value2, ... attrN=valueN]
	 * @param message
	 * @return
	 */
	public static String messageToString(Message message) {
		return "[" + SOAPUtils.mapToString((Map<String,Object>)message.getBody().get()) + "]";
	}

	/**
	 * create an attr1=value1, attr2=value2, ... attrN=valueN" representation of a Map
	 * @param bodyMap
	 * @return
	 */
	public static String mapToString(Map<String, Object> bodyMap) {
		if ( bodyMap == null || bodyMap.isEmpty()) {
			return EMPTY_STRING;
		}
		StringBuilder sb = new StringBuilder();
		for( Map.Entry<String, Object> entry : bodyMap.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue());
			sb.append(COMMA_AND_SPACE);			
		}
		int lastIndexOfCommaSpace = sb.lastIndexOf(COMMA_AND_SPACE);
		log.debug("SOAPUtils.mapToString: maxIndex=" + sb.length() + " lastIndexOfCommaSpace=" + lastIndexOfCommaSpace + " sb=<" + sb + ">");
		if ( sb.toString().endsWith(COMMA_AND_SPACE)) {
			sb.delete(lastIndexOfCommaSpace, sb.length());
		}
		return sb.toString();
	}
	
	/**
	 * add an attribute and value to the body (map) of an esb message
	 * @param message
	 * @param attributeName
	 * @param attributeValue
	 */
	public static void addAttributeAndValueToMessage(Message message, String attributeName, String attributeValue) {
		// add the OMNI score to the message body preserving any existing contents
		Map<String, Object> bodyMap = (Map<String, Object>)message.getBody().get();
		bodyMap.put(attributeName, attributeValue);
		message.getBody().add(bodyMap);
	}
	
	/**
	 * @param message
	 * @param attributeName
	 * @return
	 */
	public static String getValueOfAttributeInMessage(Message message, String attributeName) {
		// return empty string on bad input
		if ( message == null || attributeName == null || attributeName.length() == 0 ) {
			return EMPTY_STRING;
		}
		Map<String, Object> bodyMap = (Map<String, Object>)message.getBody().get();
		String value = (String)bodyMap.get(attributeName);
		return value;
	}
}
