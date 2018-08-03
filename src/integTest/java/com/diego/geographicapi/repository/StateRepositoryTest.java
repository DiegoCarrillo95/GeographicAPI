package com.diego.geographicapi.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StateRepositoryTest {
	
	@Autowired
	CountryRepository countryRepository;
	
	@Autowired
	StateRepository stateRepository;
	
	Country country;
	State state;
	
	
	@Before
	public void initData() {
		country = new Country();
		state = new State();
		country.setName("Brazil");
		country.setCountryCode("BR");
		
		state.setName("Paraná");
		state.setStateCode("PR");
		
		country.getStates().add(state);
		
		country = countryRepository.save(country);
	}
	
	@After
	public void clearDatabase() {
		countryRepository.deleteAll();
		stateRepository.deleteAll();
	}
	
	@Test
	public void shouldReturnStateWhenFindByCountryMethodIsCalled() {
		
		List<State> returnedStates = stateRepository.findByCountry(country.getId());
		
		assertTrue(returnedStates.contains(state));
		assertEquals(1, returnedStates.size());
	}
	
	@Test
	public void shouldReturnNullWhenNewFindByCountryMethodIsCalledWithNoStateInCountry() {
		
		country.setStates(new ArrayList<>());
		country = countryRepository.save(country);
		
		List<State> returnedStates = stateRepository.findByCountry(country.getId());
		
		assertEquals(0, returnedStates.size());
	}
}
