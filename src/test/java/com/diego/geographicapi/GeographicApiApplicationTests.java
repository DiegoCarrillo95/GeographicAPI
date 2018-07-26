package com.diego.geographicapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CountryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GeographicApiApplicationTests {
	//TODO: Separar testes de repositório corretamente
	
	@Autowired
	CountryRepository countryRepository;
	
	Country country;
	
	
	@Before
	public void initData() {
		country = new Country();
		country.setName("Brazil");
		country.setCountryCode("BR");
	}
	
	@After
	public void clearDatabase() {
		countryRepository.deleteAll();
	}
	
	@Test
	public void shouldReturnCountryWhenNewCountryIsInserted() {

		Country insertedCountry = countryRepository.save(country);
		
		assertEquals(country, countryRepository.findOne(insertedCountry.getId()));		
	}
	
	
	@Test
	public void shouldReturnUpdatedCountryWhenNewStateIsInserted() {
		Country insertedCountry = countryRepository.save(country);
		State state = new State();
		state.setName("Paraná");
		state.setStateCode("PR");
		
		insertedCountry.getStates().add(state);
		insertedCountry = countryRepository.save(country);
		
		assertTrue(insertedCountry.getStates().contains(state));
	}
	
	@Test
	public void shouldReturnUpdatedCountryWhenNewCityIsInserted() {
		State state = new State();
		state.setName("Paraná");
		state.setStateCode("PR");
		country.getStates().add(state);
		Country insertedCountry = countryRepository.save(country);
		City city = new City();
		city.setName("Curitiba");
		city.setCityCode("CWB");
		insertedCountry.getStates().get(0).getCities().add(city);
		
		insertedCountry = countryRepository.save(country);
		
		assertEquals(city, insertedCountry.getStates().get(0).getCities().get(0));
	}

}
