package com.diego.geographicapi.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.repository.StateRepository;
import com.diego.geographicapi.service.implementation.StateServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class StateServiceImplTest {

	@Mock
	private CountryRepository countryRepositoryMock;
	
	@Mock
	private StateRepository stateRepositoryMock;

	@InjectMocks
	private StateServiceImpl stateService;

	private Country country;
	private State state;

	private final long countryId = 1;
	private final String stateCode = "PR";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		country = new Country();
		country.setName("Brazil");
		country.setCountryCode("BR");
		country.setId(countryId);

		state = new State();
		state.setName("Paraná");
		state.setStateCode(stateCode);
	}

	@Test
	public void shouldReturnStateWithIdWhenNewStateisInsertedInExistingCountry() {
		long id = 2;
		State stateToReturn = new State();
		stateToReturn.setId(id);
		stateToReturn.setName(state.getName());
		stateToReturn.setStateCode(state.getStateCode());
		Country countryWithState = new Country();
		countryWithState.setName(country.getName());
		countryWithState.setCountryCode(country.getCountryCode());
		countryWithState.setId(countryId);
		countryWithState.getStates().add(stateToReturn);
		when(countryRepositoryMock.findOne(countryId)).thenReturn(country);
		when(countryRepositoryMock.save(country)).thenReturn(countryWithState);

		State stateReturned = stateService.insertState(state, countryId);

		assertEquals(state, stateReturned);
	}

	@Test
	public void shouldReturnExceptionWhenNewStateisInsertedInUnexistingCountry() {
		when(countryRepositoryMock.findOne(countryId)).thenReturn(null);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", countryId));
		stateService.insertState(state, countryId);
		fail();
	}

	@Test
	public void shouldReturnStateWhenExistingStateIdAndExistingCountryIdAreGiven() {
		long id = 2;
		state.setId(id);
		country.getStates().add(state);
		when(countryRepositoryMock.findOne(countryId)).thenReturn(country);
		when(stateRepositoryMock.findByIdByCountry(id, countryId)).thenReturn(state);
		
		State stateReturned = stateService.getStateById(state.getId(), countryId);

		assertEquals(state, stateReturned);
	}

	@Test
	public void shouldReturnCountryExceptionWhenExistingStateIdAndUnexistingCountryIdAreGiven() {
		long id = 2;
		state.setId(id);
		country.getStates().add(state);
		when(countryRepositoryMock.findOne(countryId)).thenReturn(null);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", countryId));
		stateService.getStateById(id, countryId);
	}

	@Test
	public void shouldReturnStateExceptionWhenUnexistingStateIdAndExistingCountryIdAreGiven() {
		long id = 2;
		state.setId(id);
		when(countryRepositoryMock.findOne(countryId)).thenReturn(country);
		when(stateRepositoryMock.findByIdByCountry(id, countryId)).thenReturn(null);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("State not found with Id : '%s'", id));
		stateService.getStateById(state.getId(), countryId);

	}
	
	@Test
	public void shouldReturnStateWhenGetStateByStateCodeMethodIsCalledWithExistingStateCode() {
		when(stateRepositoryMock.findByStateCodeByCountry(stateCode, countryId)).thenReturn(state);
		when(countryRepositoryMock.findOne(countryId)).thenReturn(country);
		State stateReturned = stateService.getStateByStateCode(stateCode, countryId);
		
		assertEquals(state, stateReturned);
	}
	
	@Test
	public void shouldReturnStateExceptionWhenGetStateByStateCodeMethodIsCalledWithUnexistingStateCode() {
		String unexistingStateCode = "AA";
		when(stateRepositoryMock.findByStateCodeByCountry(unexistingStateCode, countryId)).thenReturn(null);
		when(countryRepositoryMock.findOne(countryId)).thenReturn(country);
		
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode : '%s'", unexistingStateCode));
		stateService.getStateByStateCode(unexistingStateCode, countryId);
	}
	
	@Test
	public void shouldReturnCountryExceptionWhenGetStateByStateCodeMethodIsCalledWithExistingStateCodeAndUnexistingCountryId() {
		Long unexistingCountryId = (long) 3;
		when(countryRepositoryMock.findOne(unexistingCountryId)).thenReturn(null);
		
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", unexistingCountryId));
		stateService.getStateByStateCode(stateCode, unexistingCountryId);
	}
	
	@Test
	public void shouldReturnStateListWhenGetAllStatesByCountryMethodIsCalledWithExistingCountry(){
		List<State> list = new ArrayList<>();
		list.add(state);
		when(stateRepositoryMock.findByCountry(countryId)).thenReturn(list);
		
		List<State> returnedList = stateService.getAllStatesByCountry(countryId);
		
		assertEquals(list, returnedList);
	}
	
	@Test
	public void shouldReturnCountryExceptionWhenGetAllStatesByCountryMethodIsCalledWithUnexistingCountry(){
		long unexistingCountryId = 3;
		when(stateRepositoryMock.findByCountry(unexistingCountryId)).thenReturn(null);
		
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", unexistingCountryId));
		stateService.getAllStatesByCountry(unexistingCountryId);
		

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
		updatedCountry.setId(countryId);
		updatedCountry.setName(country.getName());
		updatedCountry.setCountryCode(country.getCountryCode());
		updatedCountry.getStates().add(updatedState);
		when(countryRepositoryMock.findOne(countryId)).thenReturn(country);
		when(countryRepositoryMock.save(updatedCountry)).thenReturn(updatedCountry);

		stateService.updateState(updatedState, updatedCountry.getId());

		verify(countryRepositoryMock, times(1)).save(updatedCountry);
	}

	@Test
	public void shouldReturnStateExceptionWhenUnexistingStateAndExistingCountryIsUpdated() {
		long id = 2;
		State updatedState = new State();
		updatedState.setName("São Paulo");
		updatedState.setStateCode("SP");
		updatedState.setId(id);
		Country updatedCountry = new Country();
		updatedCountry.setId(countryId);
		updatedCountry.setName(country.getName());
		updatedCountry.setCountryCode(country.getCountryCode());
		when(countryRepositoryMock.findOne(countryId)).thenReturn(updatedCountry);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("State not found with Id : '%s'", id));
		stateService.updateState(updatedState, countryId);

	}

	@Test
	public void shouldReturnCountryExceptionWhenExistingStateAndUnexistingCountryIsUpdated() {
		long id = 2;
		state.setId(id);
		country.getStates().add(state);
		State updatedState = new State();
		updatedState.setName("São Paulo");
		updatedState.setStateCode("SP");
		updatedState.setId(state.getId());
		when(countryRepositoryMock.findOne(countryId)).thenReturn(null);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", countryId));
		stateService.updateState(updatedState, countryId);

	}

	@Test
	public void shouldDeleteStateWhenExistingStateIsDeletedInExistingCountry() {
		long id = 2;
		state.setId(id);
		country.getStates().add(state);
		Country updatedCountry = new Country();
		updatedCountry.setId(countryId);
		updatedCountry.setName(country.getName());
		updatedCountry.setCountryCode(country.getCountryCode());
		when(countryRepositoryMock.findOne(countryId)).thenReturn(country);

		stateService.deleteState(state.getId(), countryId);

		verify(countryRepositoryMock, times(1)).save(updatedCountry);
	}

	@Test
	public void shouldReturnCountryExceptionWhenStateIsDeletedInUnexistingCountry() {
		long id = 2;
		state.setId(id);
		when(countryRepositoryMock.findOne(countryId)).thenReturn(null);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", countryId));
		stateService.deleteState(state.getId(), countryId);
	}

	@Test
	public void shouldReturnStateExceptionWhenUnexistingStateIsDeletedInExistingCountry() {
		long id = 2;
		state.setId(id);
		when(countryRepositoryMock.findOne(countryId)).thenReturn(country);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("State not found with Id : '%s'", id));
		stateService.deleteState(state.getId(), countryId);
	}

}
