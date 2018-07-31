package com.diego.geographicapi.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.implementation.StateServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StateServiceImplTest {

	@InjectMocks
	private StateServiceImpl stateService;

	@Mock
	private CountryRepository countryRepositoryMock;

	private Country country;
	private State state;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		country = new Country();
		country.setName("Brazil");
		country.setCountryCode("BR");
		country.setId((long) 1);

		state = new State();
		state.setName("Paraná");
		state.setStateCode("PR");
	}

	@Test
	public void shouldReturnStateWithIdWhenNewStateisInsertedInExistingCountry() {
		long id = 2;
		State stateReturned = new State();
		stateReturned.setId(id);
		stateReturned.setName(state.getName());
		stateReturned.setStateCode(state.getStateCode());
		Country countryWithState = new Country();
		countryWithState.setName(country.getName());
		countryWithState.setCountryCode(country.getCountryCode());
		countryWithState.setId(country.getId());
		countryWithState.getStates().add(stateReturned);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
		when(countryRepositoryMock.save(country)).thenReturn(countryWithState);

		long returnedId = stateService.insertState(state, country.getId());

		assertEquals(id, returnedId);
	}

	@Test
	public void shouldReturnExceptionWhenNewStateisInsertedInUnexistingCountry() {
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {
			stateService.insertState(state, country.getId());
			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(country.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldReturnStateWhenExistingStateIdAndExistingCountryIdAreGiven() {
		long id = 2;
		state.setId(id);
		country.getStates().add(state);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		State stateReturned = stateService.getState(state.getId(), country.getId());

		assertEquals(state, stateReturned);
	}

	@Test
	public void shouldReturnCountryExceptionWhenExistingStateIdAndUnexistingCountryIdAreGiven() {
		long id = 2;
		state.setId(id);
		country.getStates().add(state);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {
			stateService.getState(id, country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(country.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldReturnStateExceptionWhenUnexistingStateIdAndExistingCountryIdAreGiven() {
		long id = 2;
		state.setId(id);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		try {
			stateService.getState(state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("State", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(state.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldUpdateStateWhenExistingStateIsUpdated() {
		long id = 2;
		state.setId(id);
		country.getStates().add(state);
		State updatedState = new State();
		updatedState.setName("São Paulo");
		updatedState.setStateCode("SP");
		updatedState.setId(state.getId());
		Country updatedCountry = new Country();
		updatedCountry.setId(country.getId());
		updatedCountry.setName(country.getName());
		updatedCountry.setCountryCode(country.getCountryCode());
		updatedCountry.getStates().add(updatedState);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
		when(countryRepositoryMock.save(updatedCountry)).thenReturn(updatedCountry);

		stateService.updateState(updatedState, updatedCountry.getId());

		verify(countryRepositoryMock, times(1)).save(updatedCountry);
	}

	@Test
	public void shouldReturnCountryExceptionWhenExistingStateAndUnexistingCountryIsUpdated() {
		long id = 2;
		State updatedState = new State();
		updatedState.setName("São Paulo");
		updatedState.setStateCode("SP");
		updatedState.setId(id);
		Country updatedCountry = new Country();
		updatedCountry.setId(country.getId());
		updatedCountry.setName(country.getName());
		updatedCountry.setCountryCode(country.getCountryCode());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(updatedCountry);

		try {
			stateService.updateState(updatedState, country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("State", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(updatedState.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldReturnStateExceptionWhenUnexistingStateAndExistingCountryIsUpdated() {
		long id = 2;
		state.setId(id);
		country.getStates().add(state);
		State updatedState = new State();
		updatedState.setName("São Paulo");
		updatedState.setStateCode("SP");
		updatedState.setId(state.getId());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {
			stateService.updateState(updatedState, country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(country.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldDeleteStateWhenExistingStateIsDeletedInExistingCountry() {
		long id = 2;
		state.setId(id);
		country.getStates().add(state);
		Country updatedCountry = new Country();
		updatedCountry.setId(country.getId());
		updatedCountry.setName(country.getName());
		updatedCountry.setCountryCode(country.getCountryCode());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		stateService.deleteState(state.getId(), country.getId());

		verify(countryRepositoryMock, times(1)).save(updatedCountry);
	}

	@Test
	public void shouldReturnCountryExceptionWhenStateIsDeletedInUnexistingCountry() {
		long id = 2;
		state.setId(id);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {
			
			stateService.deleteState(state.getId(), country.getId());
			
			fail();
			
		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(country.getId(), e.getFieldValue());
		}

	}
	
	@Test
	public void shouldReturnStateExceptionWhenUnexistingStateIsDeletedInExistingCountry() {
		long id = 2;
		state.setId(id);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		try {
			stateService.deleteState(state.getId(), country.getId());
			
			fail();
			
		} catch (ResourceNotFoundException e) {
			assertEquals("State", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(state.getId(), e.getFieldValue());
		}
		
		

	}

}
