package com.diego.geographicapi.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.implementation.CityServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CityServiceImplTest {

	@InjectMocks
	private CityServiceImpl cityService;

	@Mock
	private CountryRepository countryRepositoryMock;

	private Country country;
	private State state;
	private City city;
	private long cityId = 3;

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
		state.setId((long) 2);

		country.getStates().add(state);

		city = new City();
		city.setName("Curitiba");
		city.setCityCode("CWB");

	}

	@Test
	public void shouldReturnCityIdWhenNewCityIsInsertedInExistingStateAndExistingCountry() {
		City cityReturnded = new City();
		cityReturnded.setId(cityId);
		cityReturnded.setName(city.getName());
		cityReturnded.setCityCode(city.getCityCode());
		State stateWithCity = new State();
		stateWithCity.setName(state.getName());
		stateWithCity.setStateCode(state.getStateCode());
		stateWithCity.setId(state.getId());
		stateWithCity.getCities().add(cityReturnded);
		Country countryWithState = new Country();
		countryWithState.setName(country.getName());
		countryWithState.setCountryCode(country.getCountryCode());
		countryWithState.setId(country.getId());
		countryWithState.getStates().add(stateWithCity);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
		when(countryRepositoryMock.save(country)).thenReturn(countryWithState);

		long returnedId = cityService.insertCity(city, state.getId(), country.getId());

		assertEquals(cityId, returnedId);
	}

	@Test
	public void shouldReturnCountryExceptionWhenNewCityIsInsertedInExistingStateAndUnexistingCountry() {

		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {

			cityService.insertCity(city, state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(country.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldReturnStateExceptionWhenNewCityIsInsertedInUnexistingStateAndExistingCountry() {

		country.setStates(new ArrayList<>());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		try {

			cityService.insertCity(city, state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("State", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(state.getId(), e.getFieldValue());
		}

	}

	@Test
	public void shouldReturnCityWhenExistingCityIdAndExistingStateIdAndExistingCountryIdAreGiven() {
		city.setId(cityId);
		state.getCities().add(city);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		City returnedCity = cityService.getCity(city.getId(), state.getId(), country.getId());

		assertEquals(city, returnedCity);
	}

	@Test
	public void shouldReturnCountryExceptionWhenExistingCityIdAndExistingStateIdAndUnexistingCountryIdAreGiven() {
		city.setId(cityId);
		state.getCities().add(city);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {

			cityService.getCity(city.getId(), state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(country.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldReturnStateExceptionWhenExistingCityIdAndUnexistingStateIdAndExistingCountryIdAreGiven() {
		city.setId(cityId);
		state.getCities().add(city);
		country.setStates(new ArrayList<>());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		try {

			cityService.getCity(city.getId(), state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("State", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(state.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldReturnCityExceptionWhenUnexistingCityIdAndExistingStateIdAndExistingCountryIdAreGiven() {
		city.setId(cityId);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		try {

			cityService.getCity(city.getId(), state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("City", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(city.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldUpdateCityWhenExistingCityAndExistingStateAndExistingCountryIsUpdated() {
		city.setId(cityId);
		state.getCities().add(city);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
		City updatedCity = new City();
		updatedCity.setName("Ponta Grossa");
		updatedCity.setCityCode("PGR");
		state.setCities(new ArrayList<>());
		state.getCities().add(updatedCity);
		when(countryRepositoryMock.save(country)).thenReturn(country);

		cityService.updateCity(updatedCity, state.getId(), country.getId());

		verify(countryRepositoryMock, times(1)).save(country);
	}

	@Test
	public void shouldReturnCountryExceptionWhenExistingCityAndExistingStateAndUnexistingCountryIsUpdated() {
		City updatedCity = new City();
		updatedCity.setName("Ponta Grossa");
		updatedCity.setCityCode("PGR");
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {

			cityService.updateCity(updatedCity, state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(country.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldReturnStateExceptionWhenExistingCityAndUnexistingStateAndExistingCountryIsUpdated() {
		City updatedCity = new City();
		updatedCity.setName("Ponta Grossa");
		updatedCity.setCityCode("PGR");
		country.setStates(new ArrayList<>());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		try {

			cityService.updateCity(updatedCity, state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("State", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(state.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldReturnCityExceptionWhenUnexistingCityAndExistingStateAndExistingCountryIsUpdated() {
		City updatedCity = new City();
		updatedCity.setName("Ponta Grossa");
		updatedCity.setCityCode("PGR");
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		try {

			cityService.updateCity(updatedCity, state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("City", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(city.getId(), e.getFieldValue());
		}
	}

	@Test
	public void shouldDeleteCityWhenExistingCityInExistingStateInExistingCountryIsDeleted() {
		city.setId(cityId);
		state.getCities().add(city);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
		State updatedState = new State();
		updatedState.setId(state.getId());
		updatedState.setName(state.getName());
		updatedState.setStateCode(state.getStateCode());
		Country updatedCountry = new Country();
		updatedCountry.setId(country.getId());
		updatedCountry.setName(country.getName());
		updatedCountry.setCountryCode(country.getCountryCode());
		updatedCountry.getStates().add(updatedState);

		cityService.deleteCity(city.getId(), state.getId(), country.getId());

		verify(countryRepositoryMock, times(1)).save(updatedCountry);
	}

	@Test
	public void shouldReturnCountryExceptionWhenExistingCityInExistingStateInUnexistingCountryIsDeleted() {
		city.setId(cityId);
		state.getCities().add(city);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {

			cityService.deleteCity(city.getId(), state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(country.getId(), e.getFieldValue());
		}
	}
	
	@Test
	public void shouldReturnStateExceptionWhenExistingCityInUnexistingStateInExistingCountryIsDeleted() {
		city.setId(cityId);
		state.getCities().add(city);
		country.setStates(new ArrayList<>());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		try {

			cityService.deleteCity(city.getId(), state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("State", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(state.getId(), e.getFieldValue());
		}
	}
	
	@Test
	public void shouldReturnCityExceptionWhenUnexistingCityInExistingStateInExistingCountryIsDeleted() {
		city.setId(cityId);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		try {

			cityService.deleteCity(city.getId(), state.getId(), country.getId());

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("City", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(city.getId(), e.getFieldValue());
		}
	}

	// TODO: Testar update e delete para todos os casos e depois ver no sonar e
	// comitar

	//
	// @Test
	// public void shouldReturnExceptionWhenNewStateisInsertedInUnexistingCountry()
	// {
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);
	//
	// try {
	// stateService.insertState(state, country.getId());
	// fail();
	//
	// } catch (ResourceNotFoundException e) {
	// assertEquals("Country", e.getResourceName());
	// assertEquals("Id", e.getFieldName());
	// assertEquals(country.getId(), e.getFieldValue());
	// }
	// }
	//
	// @Test
	// public void
	// shouldReturnStateWhenExistingStateIdAndExistingCountryIdAreGiven() {
	// long id = 2;
	// state.setId(id);
	// country.getStates().add(state);
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
	//
	// State stateReturned = stateService.getState(state.getId(), country.getId());
	//
	// assertEquals(state, stateReturned);
	// }
	//
	// @Test
	// public void
	// shouldReturnCountryExceptionWhenExistingStateIdAndUnexistingCountryIdAreGiven()
	// {
	// long id = 2;
	// state.setId(id);
	// country.getStates().add(state);
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);
	//
	// try {
	// stateService.getState(id, country.getId());
	//
	// fail();
	//
	// } catch (ResourceNotFoundException e) {
	// assertEquals("Country", e.getResourceName());
	// assertEquals("Id", e.getFieldName());
	// assertEquals(country.getId(), e.getFieldValue());
	// }
	// }
	//
	// @Test
	// public void
	// shouldReturnStateExceptionWhenUnexistingStateIdAndExistingCountryIdAreGiven()
	// {
	// long id = 2;
	// state.setId(id);
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
	//
	// try {
	// stateService.getState(state.getId(), country.getId());
	//
	// fail();
	//
	// } catch (ResourceNotFoundException e) {
	// assertEquals("State", e.getResourceName());
	// assertEquals("Id", e.getFieldName());
	// assertEquals(state.getId(), e.getFieldValue());
	// }
	// }
	//
	// @Test
	// public void shouldUpdateStateWhenExistingStateIsUpdated() {
	// long id = 2;
	// state.setId(id);
	// country.getStates().add(state);
	// State updatedState = new State();
	// updatedState.setName("São Paulo");
	// updatedState.setStateCode("SP");
	// updatedState.setId(state.getId());
	// Country updatedCountry = new Country();
	// updatedCountry.setId(country.getId());
	// updatedCountry.setName(country.getName());
	// updatedCountry.setCountryCode(country.getCountryCode());
	// updatedCountry.getStates().add(updatedState);
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
	// when(countryRepositoryMock.save(updatedCountry)).thenReturn(updatedCountry);
	//
	// stateService.updateState(updatedState, updatedCountry.getId());
	//
	// verify(countryRepositoryMock, times(1)).save(updatedCountry);
	// }
	//
	// @Test
	// public void
	// shouldReturnCountryExceptionWhenExistingStateAndUnexistingCountryIsUpdated()
	// {
	// long id = 2;
	// State updatedState = new State();
	// updatedState.setName("São Paulo");
	// updatedState.setStateCode("SP");
	// updatedState.setId(id);
	// Country updatedCountry = new Country();
	// updatedCountry.setId(country.getId());
	// updatedCountry.setName(country.getName());
	// updatedCountry.setCountryCode(country.getCountryCode());
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(updatedCountry);
	//
	// try {
	// stateService.updateState(updatedState, country.getId());
	//
	// fail();
	//
	// } catch (ResourceNotFoundException e) {
	// assertEquals("State", e.getResourceName());
	// assertEquals("Id", e.getFieldName());
	// assertEquals(updatedState.getId(), e.getFieldValue());
	// }
	// }
	//
	// @Test
	// public void
	// shouldReturnStateExceptionWhenUnexistingStateAndExistingCountryIsUpdated() {
	// long id = 2;
	// state.setId(id);
	// country.getStates().add(state);
	// State updatedState = new State();
	// updatedState.setName("São Paulo");
	// updatedState.setStateCode("SP");
	// updatedState.setId(state.getId());
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);
	//
	// try {
	// stateService.updateState(updatedState, country.getId());
	//
	// fail();
	//
	// } catch (ResourceNotFoundException e) {
	// assertEquals("Country", e.getResourceName());
	// assertEquals("Id", e.getFieldName());
	// assertEquals(country.getId(), e.getFieldValue());
	// }
	// }
	//
	// @Test
	// public void shouldDeleteStateWhenExistingStateIsDeletedInExistingCountry() {
	// long id = 2;
	// state.setId(id);
	// country.getStates().add(state);
	// Country updatedCountry = new Country();
	// updatedCountry.setId(country.getId());
	// updatedCountry.setName(country.getName());
	// updatedCountry.setCountryCode(country.getCountryCode());
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
	//
	// stateService.deleteState(state.getId(), country.getId());
	//
	// verify(countryRepositoryMock, times(1)).save(updatedCountry);
	// }
	//
	// @Test
	// public void
	// shouldReturnCountryExceptionWhenStateIsDeletedInUnexistingCountry() {
	// long id = 2;
	// state.setId(id);
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);
	//
	// try {
	//
	// stateService.deleteState(state.getId(), country.getId());
	//
	// fail();
	//
	// } catch (ResourceNotFoundException e) {
	// assertEquals("Country", e.getResourceName());
	// assertEquals("Id", e.getFieldName());
	// assertEquals(country.getId(), e.getFieldValue());
	// }
	//
	// }
	//
	// @Test
	// public void
	// shouldReturnStateExceptionWhenUnexistingStateIsDeletedInExistingCountry() {
	// long id = 2;
	// state.setId(id);
	// when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
	//
	// try {
	// stateService.deleteState(state.getId(), country.getId());
	//
	// fail();
	//
	// } catch (ResourceNotFoundException e) {
	// assertEquals("State", e.getResourceName());
	// assertEquals("Id", e.getFieldName());
	// assertEquals(state.getId(), e.getFieldValue());
	// }
	//
	//
	//
	// }

}
