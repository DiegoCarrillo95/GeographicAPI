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
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.implementation.CountryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceImplTest {

	@InjectMocks
	private CountryServiceImpl countryService;

	@Mock
	private CountryRepository countryRepositoryMock;

	private Country country;
	private Optional<Country> optionalCountry;

	private final long countryId = 1;
	private final String countryName = "Brazil";
	private final String countryCode = "BR";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before 
	public void setup() {
		country = new Country();
		country.setId(countryId);
		country.setName(countryName);
		country.setCountryCode(countryCode);
		optionalCountry = Optional.of(country);
	}
	
	@Test
	public void shouldReturnInsertedCountryWhenInsertCountryMethodIsCalledWithNewCountry() {
		Country insertedCountry = new Country();
		insertedCountry.setName(countryName);
		insertedCountry.setCountryCode(countryCode);
		when(countryRepositoryMock.save(insertedCountry)).thenReturn(country);
		
		insertedCountry = countryService.insertCountry(country);

		assertEquals(country, insertedCountry);
	}
	
	@Test
	public void shouldReturnCountryWhenGetCountryByCountryCodeMethodIsCalledWithExistingCountry() {
		when(countryRepositoryMock.findByCountryCode(countryCode)).thenReturn(optionalCountry);
		
		Country returnedCountry = countryService.getCountryByCountryCode(countryCode);

		assertEquals(country, returnedCountry);
	}
	
	@Test
	public void shouldReturnCountryExceptionWhenGetCountryByCountryCodeMethodIsCalledWithUnexistingCountry() {
		String unexistingCountryCode = "AA";
		when(countryRepositoryMock.findByCountryCode(unexistingCountryCode)).thenReturn(Optional.ofNullable(null));
		
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with CountryCode : '%s'", unexistingCountryCode));
		countryService.getCountryByCountryCode(unexistingCountryCode);
	}
	
	@Test
	public void shouldReturnCountryListWhenGetAllCountriesMethodIsCalled() {
		List<Country> list = new ArrayList<>();
		list.add(country);
		when(countryRepositoryMock.findAll()).thenReturn(list);
		
		List<Country> returnedList = countryService.getAllCountries();
		
		assertEquals(list, returnedList);
	}
	
	@Test
	public void shouldReturnUpdatedCountryWhenUpdateCountryMethodIsCalledWithValidCountry(){
		Country updatedCountry = new Country();
		updatedCountry.setName("Argentina");
		updatedCountry.setCountryCode("AR");
		when(countryRepositoryMock.findByCountryCode(countryCode)).thenReturn(optionalCountry);
		when(countryRepositoryMock.save(updatedCountry)).thenReturn(updatedCountry);
		
		Country returnedCountry = countryService.updateCountry(countryCode, updatedCountry);
		
		assertEquals(updatedCountry, returnedCountry);
	}
	
	@Test
	public void shouldReturnCountryExceptionWhenUpdateCountryMethodIsCalledWithUnexistingCountryCode(){
		String unexistingCountryCode = "AA";
		Country updatedCountry = new Country();
		when(countryRepositoryMock.findByCountryCode(unexistingCountryCode)).thenReturn(Optional.ofNullable(null));
	
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with CountryCode : '%s'", unexistingCountryCode));
		countryService.updateCountry(unexistingCountryCode, updatedCountry);
	}
	
	@Test
	public void shloudCallRepositoryDeletMethodWhenDeleteMethodIsCalledWithExistingCountry() {
		when(countryRepositoryMock.findByCountryCode(countryCode)).thenReturn(optionalCountry);
		
		countryService.deleteCountry(countryCode);
		
		verify(countryRepositoryMock, times(1)).delete(country);
	}
	
	@Test
	public void shouldReturnCountryExceptionWhenDeleteCountryMethodIsCalledWithUnexistingCountryCode(){
		String unexistingCountryCode = "AA";
		when(countryRepositoryMock.findByCountryCode(unexistingCountryCode)).thenReturn(Optional.ofNullable(null));
	
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with CountryCode : '%s'", unexistingCountryCode));
		countryService.deleteCountry(unexistingCountryCode);
	}

	
}
