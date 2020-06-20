package com.example.countrieswsclient.constants;

public class AppConstants {
	public static final String WS_NAMESPACE_URI = "http://example.com/demo/countriesws/ws";
	public static final String WS_SOAP_ACTION_CALLBACK_URI = WS_NAMESPACE_URI + "/GetCountryRequest";
	public static final String WS_HOST_CONTEXT = "http://localhost:8080/ws";
	public static final String WS_URL = WS_HOST_CONTEXT + "/countries";
}
