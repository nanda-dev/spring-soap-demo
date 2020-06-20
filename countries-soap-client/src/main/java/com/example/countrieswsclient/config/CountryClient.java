package com.example.countrieswsclient.config;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.countrieswsclient.constants.AppConstants;
import com.example.countrieswsclient.wsdl.GetCountryRequest;
import com.example.countrieswsclient.wsdl.GetCountryResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountryClient extends WebServiceGatewaySupport {

  public GetCountryResponse getCountry(String country) {

    GetCountryRequest request = new GetCountryRequest();
    request.setName(country);

    log.info("Requesting location details for: [{}]", country);

    GetCountryResponse response =
        (GetCountryResponse)
            getWebServiceTemplate()
                .marshalSendAndReceive(
                    AppConstants.WS_URL,
                    request,
                    new SoapActionCallback(
                    		AppConstants.WS_SOAP_ACTION_CALLBACK_URI));

    return response;
  }
}
