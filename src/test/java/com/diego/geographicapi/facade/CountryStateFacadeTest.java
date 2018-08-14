package com.diego.geographicapi.facade;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.diego.geographicapi.dto.StateDto;
import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.facade.implementation.StateFacadeImpl;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.service.StateService;

@RunWith(MockitoJUnitRunner.class)
public class CountryStateFacadeTest {

	@Mock
	private StateService stateService;

	@InjectMocks
	private StateFacadeImpl countryStateFacade;

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private final String countryCode = "BR";

	private final long stateId = 2;
	private final String stateName = "Parana";
	private final String stateCode = "PR";

	private final String unexistingCountryCode = "AA";
	private final String unexistingStateCode = "BB";

	private StateDto stateDto = new StateDto();
	private State state = new State();

	@Before
	public void init() {

		stateDto.setId(stateId);
		stateDto.setName(stateName);
		stateDto.setStateCode(stateCode);

		state.setId(stateId);
		state.setName(stateName);
		state.setStateCode(stateCode);
	}

	@Test
	public void shouldReturnInsertedStateDtoWhenInsertStateMethodIsCalledWithStateDto() {
		when(stateService.insertState(state, countryCode)).thenReturn(state);

		StateDto insertedStateDto = countryStateFacade.insertState(stateDto, countryCode);

		assertEquals(stateDto, insertedStateDto);
	}

	@Test
	public void shouldReturnStateDtoListWhenGetAllStatesMethodIsCalledWithExistingCountry() {
		List<State> list = new ArrayList<>();
		list.add(state);
		when(stateService.getAllStatesByCountryCode(countryCode)).thenReturn(list);

		List<StateDto> returnedList = countryStateFacade.getAllStates(countryCode);

		assertEquals(stateId, (long) returnedList.get(0).getId());
		assertEquals(stateCode, returnedList.get(0).getStateCode());
	}

	@Test
	public void shouldReturnCountryExceptionWhenGetAllStatesMethodIsCalledWithUnexistingCountry() {
		when(stateService.getAllStatesByCountryCode(unexistingCountryCode)).thenThrow(new EntityNotFoundException("Country", "CountryCode", unexistingCountryCode));

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with CountryCode: '%s'", unexistingCountryCode));
		countryStateFacade.getAllStates(unexistingCountryCode);
	}
	
	@Test
	public void shouldReturnStateDtoWhenGetStateMethodIsCalledWithExistingStateAndCountry() {
		when(stateService.getStateByStateCode(stateCode, countryCode)).thenReturn(state);

		StateDto returnedState = countryStateFacade.getState(stateCode, countryCode);

		assertEquals(stateDto, returnedState);
	}
	
	@Test
	public void shouldReturnStateDtoExceptionWhenGetStateMethodIsCalledWithUnexistingStateAndCountry() {
		when(stateService.getStateByStateCode(unexistingStateCode, countryCode)).thenThrow(new EntityNotFoundException("State", "StateCode", unexistingStateCode));

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode: '%s'", unexistingStateCode));
		countryStateFacade.getState(unexistingStateCode, countryCode);
	}
	
	@Test
	public void shouldReturnStateDtoWhenUpdateStateMethodIsCalledWithExistingState() {
		when(stateService.updateState(state, stateCode,countryCode)).thenReturn(state);

		StateDto returnedState = countryStateFacade.updateState(stateDto, stateCode, countryCode);

		assertEquals(stateDto, returnedState);
	}
	
	@Test
	public void shouldReturnStateDtoExceptionWhenUpdateStateMethodIsCalledWithUnexistingState() {
		when(stateService.updateState(state, unexistingStateCode,countryCode)).thenThrow(new EntityNotFoundException("State", "StateCode", unexistingStateCode));

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode: '%s'", unexistingStateCode));
		countryStateFacade.updateState(stateDto, unexistingStateCode, countryCode);
	}
	
	@Test
	public void shouldCallServiceDeleteStateWhenDeleteStateMethodIsCalledWithExistingState() {
		countryStateFacade.deleteState(stateCode, countryCode);

		verify(stateService, times(1)).deleteState(stateCode, countryCode);
	}

	@Test
	public void shouldReturnCountryDtoExceptionWhenDeleteCountryMethodIsCalledWithUnexistingState() {
		doThrow(new EntityNotFoundException("State", "StateCode", unexistingStateCode)).when(stateService).deleteState(unexistingStateCode, countryCode);
				
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode: '%s'", unexistingStateCode));
		countryStateFacade.deleteState(unexistingStateCode, countryCode);
	}
}
