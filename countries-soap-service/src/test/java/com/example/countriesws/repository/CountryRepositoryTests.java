package com.example.countriesws.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.countriesws.xsd.Country;
import com.example.countriesws.xsd.Currency;

@SpringBootTest
public class CountryRepositoryTests {
	//This won't trigger the @PostConstruct logic that populates the data.
	//Which means, repo.findCountry(<countryName>) will always return null;
	//private CountryRepository repo = new CountryRepository();
	
	@Autowired private CountryRepository repo;
	
	@Test
	public void test_whenGivenValidCountry_getValidDetails() {
		Country spain = new Country();
		spain.setName("Spain");
		spain.setCapital("Madrid");
		spain.setCurrency(Currency.EUR);
		spain.setPopulation(46704314);
		
		Country response = repo.findCountry("Spain");
		
		Assertions.assertNotNull(response, "Country response cannot be null");
		assertThat(response, samePropertyValuesAs(spain));
	}
}
