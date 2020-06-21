package com.example.countriesws.controller;

// import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.countriesws.repository.CountryRepository;
import com.example.countriesws.xsd.Country;
import com.example.countriesws.xsd.Currency;
import com.example.countriesws.xsd.GetCountryRequest;
import com.example.countriesws.xsd.GetCountryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CountryEndpointTests {
  @Mock private CountryRepository repo;

  @InjectMocks private CountryEndpoint endpoint;

  private static ObjectMapper objMapper;

  @BeforeAll
  public static void init() {
    objMapper = new ObjectMapper();
  }

  @Test
  public void test_whenValidRequest_getValidResponse() {
    GetCountryRequest request = new GetCountryRequest();
    request.setName("United Kingdom");

    Country uk = new Country();
    uk.setName("United Kingdom");
    uk.setCapital("London");
    uk.setCurrency(Currency.GBP);
    uk.setPopulation(63705000);

    Country uk2 = new Country();
    uk2.setName("United Kingdom");
    uk2.setCapital("London");
    uk2.setCurrency(Currency.GBP);
    uk2.setPopulation(63705000);

    Mockito.when(repo.findCountry("United Kingdom")).thenReturn(uk);
    GetCountryResponse res = endpoint.getCountry(request);
    log.info("Enpoint response: [{}]", getObjectAsString(res));
    
    Assertions.assertNotNull(res, "Endpoint response cannot be null.");
    Assertions.assertNotNull(res.getCountry(), "Country details in the response cannot be null");
    
    Country country = res.getCountry();

    // assertEquals() not a good candidate for comparing POJO objects and their values.
    // Assertions.assertEquals(uk2, country, "Country details must match the expected values.");

    // Use Hamcrest matchers to match object values.
    assertThat(country, samePropertyValuesAs(uk2));

    // Use sameBeanAs(referenceObj) from https://github.com/shazam/shazamcrest for matching complex
    // objects.
    // assertThat(country, sameBeanAs(uk2));
  }

  private String getObjectAsString(Object obj) {
    try {
      return objMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      // e.printStackTrace();
      log.error("Error parsing object into JSON string. [{}]", e.getMessage());
    }
    // default, in case of errors.
    return obj.toString();
  }
}
