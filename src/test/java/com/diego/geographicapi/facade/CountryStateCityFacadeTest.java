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

import com.diego.geographicapi.dto.CityDto;
import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.model.City;
import com.diego.geographicapi.service.CityService;

@RunWith(MockitoJUnitRunner.class)
public class CountryStateCityFacadeTest {

	@Mock
	private CityService cityService;

	@InjectMocks
	private CountryStateCityFacade countryStateCityFacade;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private final String countryCode = "BR";

	private final String stateCode = "PR";
	
	private final long cityId = 3;
	private final String cityName = "Cutitiba";
	private final String cityCode = "CWB";

	private final String unexistingStateCode = "BB";
	private final String unexistingCityCode = "CCC";

	
	private CityDto cityDto = new CityDto();
	private City city = new City();

	@Before
	public void init() {
		cityDto.setId(cityId);
		cityDto.setName(cityName);
		cityDto.setCityCode(cityCode);

		city.setId(cityId);
		city.setName(cityName);
		city.setCityCode(cityCode);
	}

	@Test
	public void shouldReturnInsertedCityDtoWhenInsertCityMethodIsCalledWithCityDto() {
		when(cityService.insertCity(city, stateCode, countryCode)).thenReturn(city);

		CityDto insertedCityDto = countryStateCityFacade.insertCity(cityDto, stateCode, countryCode);

		assertEquals(cityDto, insertedCityDto);
	}

	@Test
	public void shouldReturnCityDtoListWhenGetAllCitiesMethodIsCalledWithExistingState() {
		List<City> list = new ArrayList<>();
		list.add(city);
		when(cityService.getAllCitiesByStateCodeAndCountryCode(stateCode, countryCode)).thenReturn(list);

		List<CityDto> returnedList = countryStateCityFacade.getAllCities(stateCode, countryCode);

		assertEquals(cityId, (long) returnedList.get(0).getId());
		assertEquals(cityCode, returnedList.get(0).getCityCode());
	}

	@Test
	public void shouldReturnStateExceptionWhenGetAllCitiesMethodIsCalledWithUnexistingState() {
		when(cityService.getAllCitiesByStateCodeAndCountryCode(unexistingStateCode, countryCode)).thenThrow(new EntityNotFoundException("State", "StateCode", unexistingStateCode));

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("State not found with StateCode: '%s'", unexistingStateCode));
		countryStateCityFacade.getAllCities(unexistingStateCode, countryCode);
	}
	
	@Test
	public void shouldReturnCityDtoWhenGetCityMethodIsCalledWithExistingCityAndStateAndCountry() {
		when(cityService.getCityByCityCode(cityCode, stateCode, countryCode)).thenReturn(city);

		CityDto returnedCity = countryStateCityFacade.getCity(cityCode, stateCode, countryCode);

		assertEquals(cityDto, returnedCity);
	}
	
	@Test
	public void shouldReturnCityDtoExceptionWhenGetCityMethodIsCalledWithUnexistingCityAndStateAndCountry() {
		when(cityService.getCityByCityCode(unexistingCityCode, stateCode, countryCode)).thenThrow(new EntityNotFoundException("City", "CityCode", unexistingCityCode));

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("City not found with CityCode: '%s'", unexistingCityCode));
		countryStateCityFacade.getCity(unexistingCityCode, stateCode, countryCode);
	}
	
	@Test
	public void shouldReturnCityDtoWhenUpdateCityMethodIsCalledWithExistingState() {
		when(cityService.updateCity(city, cityCode, stateCode, countryCode)).thenReturn(city);

		CityDto returnedCity = countryStateCityFacade.updateCity(cityDto, cityCode, stateCode, countryCode);

		assertEquals(cityDto, returnedCity);
	}
	
	@Test
	public void shouldReturnCityDtoExceptionWhenUpdateCityMethodIsCalledWithUnexistingCity() {
		when(cityService.updateCity(city, unexistingCityCode, stateCode, countryCode)).thenThrow(new EntityNotFoundException("City", "CityCode", unexistingCityCode));

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("City not found with CityCode: '%s'", unexistingCityCode));
		countryStateCityFacade.updateCity(cityDto, unexistingCityCode, stateCode, countryCode);
	}
	
	@Test
	public void shouldCallServiceDeleteCityWhenDeleteCityMethodIsCalledWithExistingCity() {
		countryStateCityFacade.deleteCity(cityCode, stateCode, countryCode);

		verify(cityService, times(1)).deleteCity(cityCode, stateCode, countryCode);
	}

	@Test
	public void shouldReturnCityDtoExceptionWhenDeleteCityMethodIsCalledWithUnexistingCity() {
		doThrow(new EntityNotFoundException("City", "CityCode", unexistingCityCode)).when(cityService).deleteCity(unexistingCityCode, stateCode, countryCode);
				
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(String.format("City not found with CityCode: '%s'", unexistingCityCode));
		countryStateCityFacade.deleteCity(unexistingCityCode, stateCode, countryCode);
	}
}
