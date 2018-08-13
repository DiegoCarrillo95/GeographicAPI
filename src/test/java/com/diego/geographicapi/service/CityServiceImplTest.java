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
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.model.State;
import com.diego.geographicapi.repository.CityRepository;
import com.diego.geographicapi.repository.StateRepository;
import com.diego.geographicapi.service.implementation.CityServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceImplTest {

	@InjectMocks
	private CityServiceImpl cityService;

	@Mock
	private CityRepository cityRepositoryMock;

	@Mock
	private StateRepository stateRepositoryMock;

	private Country country;
	private State state;
	private City city;

	private Optional<State> optionalState;
	private Optional<City> optionalCity;

	private final long countryId = 1;
	private final String countryName = "Brasil";
	private final String countryCode = "BR";

	private final long stateId = 2;
	private final String stateName = "Paraná";
	private final String stateCode = "PR";

	private final long cityId = 3;
	private final String cityName = "Curitiba";
	private final String cityCode = "CWB";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		country = new Country();
		country.setName(countryName);
		country.setCountryCode(countryCode);
		country.setId(countryId);

		state = new State();
		state.setName(stateName);
		state.setStateCode(stateCode);
		state.setId(stateId);

		country.addState(state);
		optionalState = Optional.of(state);

		city = new City();
		city.setId(cityId);
		city.setName(cityName);
		city.setCityCode(cityCode);

		state.addCity(city);
		optionalCity = Optional.of(city);
	}

	@Test
	public void shouldReturnInsertedCityWhenInsertCityMethodIsCalledWithNewCityAndExistingStateAndCountry() {
		City insertedCity = new City();
		insertedCity.setName(cityName);
		insertedCity.setCityCode(cityCode);
		when(stateRepositoryMock.findByStateCodeAndCountryCode(stateCode, countryCode)).thenReturn(optionalState);
		when(cityRepositoryMock.save(insertedCity)).thenReturn(city);

		City returnedCity = cityService.insertCity(insertedCity, stateCode, countryCode);

		assertEquals(city, returnedCity);
	}

	@Test
	public void shouldReturnStateExceptionWhenInsertCityMethodIsCalledWithNewCityAndUnexistingStateAndCountry() {
		String unexistingStateCode = "BB";
		City insertedCity = new City();
		when(stateRepositoryMock.findByStateCodeAndCountryCode(unexistingStateCode, countryCode))
				.thenReturn(Optional.ofNullable(null));

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode : '%s'", unexistingStateCode));
		cityService.insertCity(insertedCity, unexistingStateCode, countryCode);
	}

	@Test
	public void shouldReturnCityWhengGetCityByCityCodeMethodIsCalledWithExistingCity() {
		when(cityRepositoryMock.findByCityCodeAndStateCodeAndCountryCode(cityCode, stateCode, countryCode))
				.thenReturn(optionalCity);

		City returnedCity = cityService.getCityByCityCode(cityCode, stateCode, countryCode);

		assertEquals(city, returnedCity);
	}

	@Test
	public void shouldReturnCityExceptionWhengGetCityByCityCodeMethodIsCalledWithUnexistingCity() {
		String unexistingCityCode = "CCC";
		when(cityRepositoryMock.findByCityCodeAndStateCodeAndCountryCode(unexistingCityCode, stateCode, countryCode))
				.thenReturn(Optional.ofNullable(null));

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("City not found with CityCode : '%s'", unexistingCityCode));
		cityService.getCityByCityCode(unexistingCityCode, stateCode, countryCode);
	}

	@Test
	public void shouldReturnCityListWhenGetAllCitiesByStateCodeAndCountryCodeMethodIsCalledWithExistingStateAndCountry() {
		List<City> list = new ArrayList<>();
		list.add(city);
		Optional<List<City>> optionalList = Optional.of(list);
		when(cityRepositoryMock.findAllByStateCodeCountryCode(stateCode, countryCode)).thenReturn(optionalList);

		List<City> returnedList = cityService.getAllCitiesByStateCodeAndCountryCode(stateCode, countryCode);

		assertEquals(list, returnedList);
	}

	@Test
	public void shouldReturnStateExceptionWhenGetAllCitiesByStateCodeAndCountryCodeMethodIsCalledWithUnexistingStateAndCountry() {
		String unexistingStateCode = "BB";
		when(cityRepositoryMock.findAllByStateCodeCountryCode(unexistingStateCode, countryCode))
				.thenReturn(Optional.ofNullable(null));

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode : '%s'", unexistingStateCode));
		cityService.getAllCitiesByStateCodeAndCountryCode(unexistingStateCode, countryCode);
	}

	@Test
	public void shouldReturnUpdatedCityWhenUpdateCityMethodIsCalledWithExistingCityAndStateAndCountry() {
		City updatedCity = new City();
		updatedCity.setName("São Paulo");
		updatedCity.setCityCode("SPO");
		updatedCity.setState(city.getState());
		when(cityRepositoryMock.findByCityCodeAndStateCodeAndCountryCode(cityCode, stateCode, countryCode))
				.thenReturn(optionalCity);
		when(cityRepositoryMock.save(updatedCity)).thenReturn(updatedCity);

		City returnedCity = cityService.updateCity(updatedCity, cityCode, stateCode, countryCode);

		assertEquals(updatedCity, returnedCity);
	}

	@Test
	public void shouldReturnCityExceptionWhenUpdateCityMethodIsCalledWithUnexistingCityAndStateAndCountry() {
		String unexistingCityCode = "CCC";
		City updatedCity = new City();
		when(cityRepositoryMock.findByCityCodeAndStateCodeAndCountryCode(unexistingCityCode, stateCode, countryCode))
				.thenReturn(Optional.ofNullable(null));

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("City not found with CityCode : '%s'", unexistingCityCode));
		cityService.updateCity(updatedCity, unexistingCityCode, stateCode, countryCode);
	}

	@Test
	public void shouldCallRepositoryDeletMethodWhenDeleteMethodIsCalledWithExistingCity() {
		when(cityRepositoryMock.findByCityCodeAndStateCodeAndCountryCode(cityCode, stateCode, countryCode))
				.thenReturn(optionalCity);

		cityService.deleteCity(cityCode, stateCode, countryCode);

		verify(cityRepositoryMock, times(1)).delete(city);
	}

	@Test
	public void shouldReturnCityExceptionWhenDeleteCityMethodIsCalledWithUnexistingCityAndStateAndCountry() {
		String unexistingCityCode = "CCC";
		when(cityRepositoryMock.findByCityCodeAndStateCodeAndCountryCode(unexistingCityCode, stateCode, countryCode))
				.thenReturn(Optional.ofNullable(null));

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("City not found with CityCode : '%s'", unexistingCityCode));
		cityService.deleteCity(unexistingCityCode, stateCode, countryCode);
	}

}