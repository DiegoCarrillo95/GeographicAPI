package com.diego.geographicapi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	private StateRepository stateRepositoryMock;
	
	@Mock
	private CountryRepository countryRepositoryMock;

	@InjectMocks
	private StateServiceImpl stateService;

	private State state;
	private Country country;
	private Optional<State> optionalState;
	private Optional<Country> optionalCountry;
	
	private final long stateId = 2;
	private final String stateName = "Paraná";
	private final String stateCode = "PR";
	
	private final long countryId = 1;
	private final String countryName = "Brasil";
	private final String countryCode = "BR";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		state = new State();
		state.setId(stateId);
		state.setName(stateName);
		state.setStateCode(stateCode);
		optionalState = Optional.of(state);
		
		country = new Country();
		country.setId(countryId);
		country.setName(countryName);
		country.setCountryCode(countryCode);
		country.addState(state);
		optionalCountry = Optional.of(country);
	}
	
	@Test
	public void shouldReturnInsertedStateWhenInsertStateMethodIsCalledWithNewStateAndExistingCountry() {
		State insertedState = new State();
		insertedState.setName(stateName);
		insertedState.setStateCode(stateCode);
		when(stateRepositoryMock.save(insertedState)).thenReturn(state);
		when(countryRepositoryMock.findByCountryCode(countryCode)).thenReturn(optionalCountry);
		
		stateService.insertState(insertedState, countryCode);

		assertEquals(state, insertedState);
	}
	
	@Test
	public void shouldReturnCountryExceptionWhenInsertStateMethodIsCalledWithNewStateAndUnexistingCountry() {
		State insertedState = new State();
		String unexistingCountryCode = "AA";
		when(countryRepositoryMock.findByCountryCode(unexistingCountryCode)).thenReturn(Optional.ofNullable(null));
		
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with CountryCode : '%s'", unexistingCountryCode));
		stateService.insertState(insertedState, unexistingCountryCode);
	}
	
	@Test
	public void shouldReturnStateWhenGetStateByStateCodeWithExistingStateAndCountry() {
		when(stateRepositoryMock.findByStateCodeAndCountryCode(stateCode, countryCode)).thenReturn(optionalState);
		
		State returnedState = stateService.getStateByStateCode(stateCode, countryCode);
		
		assertEquals(state, returnedState);
	}
	
	@Test
	public void shouldReturnStateExceptionWhenGetStateByStateCodeWithUnexistingStateAndCountry() {
		String unexistingStateCode = "BB";
		when(stateRepositoryMock.findByStateCodeAndCountryCode(unexistingStateCode, countryCode)).thenReturn(Optional.ofNullable(null));
		
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode : '%s'", unexistingStateCode));
		stateService.getStateByStateCode(unexistingStateCode, countryCode);
	}
	
	@Test
	public void shouldReturnStatesListWhenGetAllStatesByCountryCodeMethodIsCalledWithExistingCountry() {
		List<State> list = new ArrayList<>();
		list.add(state);
		Optional<List<State>> optionalList = Optional.of(list);
		when(stateRepositoryMock.findAllByCountryCode(countryCode)).thenReturn(optionalList);
		
		List<State> returnedList = stateService.getAllStatesByCountryCode(countryCode);
		
		assertEquals(list, returnedList);
	}
	
	@Test
	public void shouldReturnCountryExceptionWhenGetAllStatesByCountryCodeMethodIsCalledWithUnexistingCountry() {
		String unexistingCountryCode = "AA";
		when(stateRepositoryMock.findAllByCountryCode(unexistingCountryCode)).thenReturn(Optional.ofNullable(null));
		
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with CountryCode : '%s'", unexistingCountryCode));
		stateService.getAllStatesByCountryCode(unexistingCountryCode);
	}
	
	@Test
	public void shouldReturnUpdatedStateWhenUpdateStateMethodIsCalledWithExistingStateAndCountry(){
		State updatedState = new State();
		updatedState.setName("Santa Catarina");
		updatedState.setStateCode("SC");
		updatedState.setCountry(state.getCountry());
		when(stateRepositoryMock.findByStateCodeAndCountryCode(stateCode, countryCode)).thenReturn(optionalState);
		when(stateRepositoryMock.save(updatedState)).thenReturn(updatedState);
		
		State returnedState = stateService.updateState(updatedState, stateCode, countryCode);
		
		assertEquals(updatedState, returnedState);
	}
	
	@Test
	public void shouldReturnStateExceptionWhenUpdateStateMethodIsCalledWithUnexistingStateAndCountry(){
		String unexistingStateCode = "BB";
		State updatedState = new State();
		when(stateRepositoryMock.findByStateCodeAndCountryCode(unexistingStateCode, countryCode)).thenReturn(Optional.ofNullable(null));

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode : '%s'", unexistingStateCode));
		stateService.updateState(updatedState, unexistingStateCode, countryCode);
	}
	
	@Test
	public void shouldCallRepositoryDeletMethodWhenDeleteMethodIsCalledWithExistingState() {
		when(stateRepositoryMock.findByStateCodeAndCountryCode(stateCode, countryCode)).thenReturn(optionalState);
		
		stateService.deleteState(stateCode, countryCode);
		
		verify(stateRepositoryMock, times(1)).delete(state);
	}
	
	@Test
	public void shouldReturnStateExceptionWhenDeleteStateMethodIsCalledWithUnexistingStateAndCountry(){
		String unexistingStateCode = "BB";
		when(stateRepositoryMock.findByStateCodeAndCountryCode(unexistingStateCode, countryCode)).thenReturn(Optional.ofNullable(null));

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode : '%s'", unexistingStateCode));
		stateService.deleteState(unexistingStateCode, countryCode);
	}

}