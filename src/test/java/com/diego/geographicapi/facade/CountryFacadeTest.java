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

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.facade.implementation.CountryFacadeImpl;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.service.CountryService;
import com.diego.geographicapi.util.CountryDtoModelTransformer;

@RunWith(MockitoJUnitRunner.class)
public class CountryFacadeTest {

	@InjectMocks
	private CountryFacadeImpl countryFacade;

	@Mock
	private CountryService countryService;
	
	@Mock
	private CountryDtoModelTransformer countryDtoModelTransformer;

	private Country country = new Country();

	private CountryDto countryDto = new CountryDto();

	private final long countryId = 1;
	private final String countryName = "Brazil";
	private final String countryCode = "BR";

	private final String unexistingCountryCode = "AA";
	private final String resourceName = "Country";
	private final String fieldName = "CountryCode";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		country.setId(countryId);
		country.setName(countryName);
		country.setCountryCode(countryCode);

		countryDto.setId(countryId);
		countryDto.setName(countryName);
		countryDto.setCountryCode(countryCode);
		
		when(countryDtoModelTransformer.transformToDto(country)).thenReturn(countryDto);
		when(countryDtoModelTransformer.transformToModel(countryDto)).thenReturn(country);
	}

	@Test
	public void shouldReturnInsertedCountryDtoWhenInsertCountryMethodIsCalledWithCountryDto() {
		when(countryService.insertCountry(country)).thenReturn(country);

		CountryDto insertedCountry = countryFacade.insertCountry(countryDto);

		assertEquals(countryDto, insertedCountry);
	}

	@Test
	public void shouldReturnCountryDtoListWhenGetAllCountriesMethodIsCalled() {
		List<Country> list = new ArrayList<>();
		list.add(country);
		when(countryService.getAllCountries()).thenReturn(list);

		List<CountryDto> returnedList = countryFacade.getAllCountries();

		assertEquals(countryId, (long) returnedList.get(0).getId());
		assertEquals(countryCode, returnedList.get(0).getCountryCode());
	}

	@Test
	public void shouldReturnCountryDtoWhenGetCountryMethodIsCalledWithExistingCountry() {
		when(countryService.getCountryByCountryCode(countryCode)).thenReturn(country);

		CountryDto returnedCountry = countryFacade.getCountry(countryCode);

		assertEquals(countryDto, returnedCountry);
	}

	@Test
	public void shouldReturnCountryDtoExceptionWhenGetCountryMethodIsCalledWithUnexistingCountry() {
		when(countryService.getCountryByCountryCode(unexistingCountryCode))
				.thenThrow(new EntityNotFoundException(resourceName, fieldName, unexistingCountryCode));

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(
				String.format("%s not found with %s: '%s'", resourceName, fieldName, unexistingCountryCode));
		countryFacade.getCountry(unexistingCountryCode);
	}

	@Test
	public void shouldReturnCountryDtoWhenUpdateCountryMethodIsCalledWithExistingCountry() {
		when(countryService.updateCountry(countryCode, country)).thenReturn(country);

		CountryDto returnedCountry = countryFacade.updateCountry(countryCode, countryDto);

		assertEquals(countryDto, returnedCountry);
	}

	@Test
	public void shouldReturnCountryDtoExceptionWhenUpdateCountryMethodIsCalledWithUnexistingCountry() {
		when(countryService.updateCountry(unexistingCountryCode, country))
				.thenThrow(new EntityNotFoundException(resourceName, fieldName, unexistingCountryCode));

		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(
				String.format("%s not found with %s: '%s'", resourceName, fieldName, unexistingCountryCode));
		countryFacade.updateCountry(unexistingCountryCode, countryDto);
	}

	@Test
	public void shouldCallServiceDeleteCountryWhenDeleteCountryMethodIsCalledWithExistingCountry() {
		countryFacade.deleteCountry(countryCode);

		verify(countryService, times(1)).deleteCountry(countryCode);
	}

	@Test
	public void shouldReturnCountryDtoExceptionWhenDeleteCountryMethodIsCalledWithUnexistingCountry() {
		doThrow(new EntityNotFoundException(resourceName, fieldName, unexistingCountryCode)).when(countryService).deleteCountry(unexistingCountryCode);
				
		thrown.expect(ResourceNotFoundException.class);
		thrown.expectMessage(
				String.format("%s not found with %s: '%s'", resourceName, fieldName, unexistingCountryCode));
		countryFacade.deleteCountry(unexistingCountryCode);
	}

}
