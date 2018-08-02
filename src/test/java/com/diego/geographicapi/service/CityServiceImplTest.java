package com.diego.geographicapi.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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

import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.implementation.CityServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceImplTest {

	@InjectMocks
	private CityServiceImpl cityService;

	@Mock
	private CountryRepository countryRepositoryMock;

	private Country country;
	private State state;
	private City city;

	private final long countryId = 1;
	private final long stateId = 2;
	private final long cityId = 3;

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
		state.setStateCode("PR");
		state.setId(stateId);

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

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", countryId));
		cityService.insertCity(city, state.getId(), country.getId());
	}

	@Test
	public void shouldReturnStateExceptionWhenNewCityIsInsertedInUnexistingStateAndExistingCountry() {

		country.setStates(new ArrayList<>());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("State not found with Id : '%s'", stateId));
		cityService.insertCity(city, state.getId(), country.getId());
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

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", countryId));
		cityService.getCity(city.getId(), state.getId(), country.getId());
	}

	@Test
	public void shouldReturnStateExceptionWhenExistingCityIdAndUnexistingStateIdAndExistingCountryIdAreGiven() {
		city.setId(cityId);
		state.getCities().add(city);
		country.setStates(new ArrayList<>());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("State not found with Id : '%s'", stateId));
		cityService.getCity(city.getId(), state.getId(), country.getId());
	}

	@Test
	public void shouldReturnCityExceptionWhenUnexistingCityIdAndExistingStateIdAndExistingCountryIdAreGiven() {
		city.setId(cityId);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("City not found with Id : '%s'", cityId));
		cityService.getCity(city.getId(), state.getId(), country.getId());

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

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", countryId));
		cityService.updateCity(updatedCity, state.getId(), country.getId());
	}

	@Test
	public void shouldReturnStateExceptionWhenExistingCityAndUnexistingStateAndExistingCountryIsUpdated() {
		City updatedCity = new City();
		updatedCity.setName("Ponta Grossa");
		updatedCity.setCityCode("PGR");
		country.setStates(new ArrayList<>());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("State not found with Id : '%s'", stateId));
		cityService.updateCity(updatedCity, state.getId(), country.getId());
	}

	@Test
	public void shouldReturnCityExceptionWhenUnexistingCityAndExistingStateAndExistingCountryIsUpdated() {
		City updatedCity = new City();
		updatedCity.setName("Ponta Grossa");
		updatedCity.setCityCode("PGR");
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("City not found with Id : '%s'", city.getId()));
		cityService.updateCity(updatedCity, state.getId(), country.getId());
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

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", countryId));
		cityService.deleteCity(city.getId(), state.getId(), country.getId());
	}

	@Test
	public void shouldReturnStateExceptionWhenExistingCityInUnexistingStateInExistingCountryIsDeleted() {
		city.setId(cityId);
		state.getCities().add(city);
		country.setStates(new ArrayList<>());
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("State not found with Id : '%s'", stateId));
		cityService.deleteCity(city.getId(), state.getId(), country.getId());
	}

	@Test
	public void shouldReturnCityExceptionWhenUnexistingCityInExistingStateInExistingCountryIsDeleted() {
		city.setId(cityId);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("City not found with Id : '%s'", cityId));
		cityService.deleteCity(city.getId(), state.getId(), country.getId());
	}

}