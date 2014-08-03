package com.eis;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;


public class WebService {
	
	private final String NAMESPACE="http://rebelrewardsuri.org/";
	private final String URL = "http://130.74.17.62/RebelRewardsWebService/Service.asmx?WSDL";
	
	public boolean checkUser(String userName,String passWord)
    {
    	boolean result = false;
    	
    	final String SOAP_ACTION = "http://rebelrewardsuri.org/CheckUser";
    	final String METHOD_NAME = "CheckUser";
    	
    	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 		

		PropertyInfo propInfo1 =new PropertyInfo();
		propInfo1.setName("_mailId");
		propInfo1.setValue(userName);
		propInfo1.setType(String.class);
        request.addProperty(propInfo1);
        
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_passWord");
		propInfo2.setValue(passWord);
		propInfo2.setType(String.class);
        request.addProperty(propInfo2);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			Log.i("myApp", response.toString());
			if(response.toString().equalsIgnoreCase("true")){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
	
	public boolean forgotPassword(String userName)
    {
    	boolean result = false;
    	
    	final String SOAP_ACTION = "http://rebelrewardsuri.org/ForgotPassword";
    	final String METHOD_NAME = "ForgotPassword";
    	
    	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 		

		PropertyInfo propInfo1 =new PropertyInfo();
		propInfo1.setName("_mailId");
		propInfo1.setValue(userName);
		propInfo1.setType(String.class);
        request.addProperty(propInfo1);
   

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			Log.i("myApp", response.toString());
			if(response.toString().equalsIgnoreCase("true")){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
	
	public boolean SavePlannedAttendee(String _userId,String _eventId)
    {
    	boolean result = false;
    	
    	final String SOAP_ACTION = "http://rebelrewardsuri.org/PlannedAttendee";
    	final String METHOD_NAME = "PlannedAttendee";
    	
    	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 		

		PropertyInfo propInfo1 =new PropertyInfo();
		propInfo1.setName("_userId");
		propInfo1.setValue(_userId);
		propInfo1.setType(String.class);
        request.addProperty(propInfo1);
        
		PropertyInfo propInfo2 = new PropertyInfo();
		propInfo2.setName("_eventId");
		propInfo2.setValue(_eventId);
		propInfo2.setType(String.class);
        request.addProperty(propInfo2);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true; // put this only if the web service is .NET one
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			Log.i("myApp", response.toString());
			if(response.toString().equalsIgnoreCase("true")){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
	
	
}
