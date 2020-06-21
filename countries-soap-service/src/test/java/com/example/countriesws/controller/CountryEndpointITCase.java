package com.example.countriesws.controller;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

import java.io.IOException;

import javax.xml.transform.Source;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;


/*
 * Sample code referred from: https://memorynotfound.com/spring-ws-server-side-integration-testing/
 * It has been updated to make use of JUnit5.
 * Also, renaming the class to have a suffix of 'ITCase',
 * to have it detected as an integration-test by maven-faisafe-plugin.
 * Reference: https://dzone.com/articles/learn-how-to-use-junit-5-to-test-your-spring-boot
 * > "The Failsafe Plugin will differentiate the types of tests by the names. 
 * By default, it will consider any test that begins or ends with IT as an integration test. 
 * It also considers tests that end in ITCase an integration test."
 */
//@ContextConfiguration(classes = WebServiceConfig.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CountryEndpointITCase {
  // Making this static to work with the static @BeforAll method won't help.
  // It results in: "'applicationContext' must not be null" error.
  // Inject it into the @BeforeAll method as an argument instead.
  //@Autowired private ApplicationContext applicationContext;

  private static MockWebServiceClient mockClient;
  private static Resource xsdSchema = new ClassPathResource("xsd/countries.xsd");

  @BeforeAll
  public static void init(ApplicationContext applicationContext) {
    mockClient = MockWebServiceClient.createClient(applicationContext);
  }

  @Test
  public void valid_xsd_request_response_test() throws IOException {
    String getCountryRequest =
        "<ws:getCountryRequest xmlns:ws=\"http://example.com/demo/countriesws/ws\">"
            + "<ws:name>United Kingdom</ws:name>\n"
            + "</ws:getCountryRequest>";
    String getCountryResponse =
        "<ns2:getCountryResponse xmlns:ns2=\"http://example.com/demo/countriesws/ws\">"
            + "<ns2:country>"
            + "<ns2:name>United Kingdom</ns2:name>"
            + "<ns2:population>63705000</ns2:population>"
            + "<ns2:capital>London</ns2:capital>"
            + "<ns2:currency>GBP</ns2:currency>"
            + "</ns2:country>"
            + "</ns2:getCountryResponse>";
    /*
    String requestXml =
        "<ns2:getBeerRequest xmlns:ns2=\"https://memorynotfound.com/beer\">"
            + "<ns2:id>1</ns2:id>"
            + "</ns2:getBeerRequest>";

    String responseXml = "<ns2:getBeerResponse xmlns:ns2=\"https://memorynotfound.com/beer\"></ns2:getBeerResponse>";
    */
    Source requestPayload = new StringSource(getCountryRequest);

    Source responsePayload = new StringSource(getCountryResponse);

    mockClient
        .sendRequest(withPayload(requestPayload))
        .andExpect(noFault())
        .andExpect(payload(responsePayload))
        .andExpect(validPayload(xsdSchema));
  }
}
