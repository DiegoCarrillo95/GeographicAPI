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
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.implementation.CountryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceImplTest {

	@InjectMocks
	private CountryServiceImpl countryService;

	@Mock
	private CountryRepository countryRepositoryMock;

	private Country country;

	private final long id = 1;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		country = new Country();
		country.setName("Brazil");
		country.setCountryCode("BR");
	}

	@Test
	public void shouldReturnCountryWithIdWhenNewCountryisInserted() {
		Country countryToReturn = new Country();
		countryToReturn.setId(id);
		countryToReturn.setName(country.getName());
		countryToReturn.setCountryCode(country.getCountryCode());
		countryToReturn.setStates(country.getStates());
		when(countryRepositoryMock.save(country)).thenReturn(countryToReturn);

		Country countryReturned = countryService.insertCountry(country);

		assertEquals(country, countryReturned);
	}

	@Test
	public void shouldReturnAllCountriesWhenGetAllCountriesMethodIsCalled() {
		country.setId(id);
		Country country2 = new Country();
		country2.setId((long) 2);
		country2.setName("Mexico");
		country2.setCountryCode("MX");
		List<Country> countryList = new ArrayList<>();
		countryList.add(country);
		countryList.add(country2);
		when(countryRepositoryMock.findAll()).thenReturn(countryList);

		List<Country> listReturned = countryService.getAllCountries();

		assertEquals(countryList, listReturned);
	}

	@Test
	public void shouldReturnCountryWhenCountryIdIsGiven() {
		country.setId(id);
		when(countryRepositoryMock.findOne(id)).thenReturn(country);

		Country countryReturned = countryService.getCountryById(id);

		assertEquals(country, countryReturned);
	}

	@Test
	public void shouldReturnResourceNotFoundExceptionWhenInexistingCountryIdIsGiven() {
		when(countryRepositoryMock.findOne(id)).thenReturn(null);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", id));
		countryService.getCountryById(id);
	}
	
	@Test
	public void shouldReturnCountryWhenGetCountryByCountryCodeMethodisCalledWithExistingCountryCode(){
		when(countryRepositoryMock.findByCountryCode("BR")).thenReturn(country);
		
		Country returnedCountry = countryService.getCountryByCountryCode("BR");
		
		assertEquals(country, returnedCountry);
	}
	
	@Test
	public void shouldReturnCountryExceptionWhenGetCountryByCountryCodeMethodisCalledWithUnexistingCountryCode(){
		String unexistingCountryCode = "US";
		when(countryRepositoryMock.findByCountryCode(unexistingCountryCode)).thenReturn(null);
		
		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with CountryCode : '%s'", unexistingCountryCode));
		countryService.getCountryByCountryCode(unexistingCountryCode);
	}

	@Test
	public void shouldUpdateCountryWhenExistingCountryIsUpdated() {
		country.setId(id);
		Country updatedCountry = new Country();
		updatedCountry.setId(country.getId());
		updatedCountry.setName("Spain");
		updatedCountry.setCountryCode("ES");
		when(countryRepositoryMock.findByCountryCode(country.getCountryCode())).thenReturn(country);
		when(countryRepositoryMock.save(updatedCountry)).thenReturn(updatedCountry);

		countryService.updateCountry(country.getCountryCode(), updatedCountry);

		verify(countryRepositoryMock, times(1)).save(updatedCountry);
	}

	@Test
	public void shouldReturnExceptionWhenUnexistingCountryIsUpdated() {
		String unexistingCountryCode = "AA";
		country.setCountryCode(unexistingCountryCode);
		when(countryRepositoryMock.findByCountryCode(unexistingCountryCode)).thenReturn(null);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with CountryCode : '%s'", unexistingCountryCode));
		countryService.updateCountry(unexistingCountryCode, country);
	}

	@Test
	public void shouldDeleteCountryWhenExistingCountryIsDeleted() {
		country.setId(id);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		countryService.deleteCountry(country.getId());

		verify(countryRepositoryMock, times(1)).delete(country);

	}

	@Test
	public void shouldReturnExceptionWhenUnexistingCountryIsDeleted() {
		country.setId(id);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		thrown.expect(EntityNotFoundException.class);
		thrown.expectMessage(String.format("Country not found with Id : '%s'", id));
		countryService.deleteCountry(country.getId());
	}
}
