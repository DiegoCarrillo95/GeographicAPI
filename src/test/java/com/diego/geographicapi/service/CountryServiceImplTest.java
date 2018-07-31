package com.diego.geographicapi.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
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
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.implementation.CountryServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CountryServiceImplTest {

	@InjectMocks
	private CountryServiceImpl countryService;
	// Teste da implementação ou da interface?

	@Mock
	private CountryRepository countryRepositoryMock;

	private Country country;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		country = new Country();
		country.setName("Brazil");
		country.setCountryCode("BR");
	}

	@Test
	public void shouldReturnCountryWithIdWhenNewCountryisInserted() {
		long id = 1;
		Country countryReturned = new Country();
		countryReturned.setId(id);
		countryReturned.setName(country.getName());
		countryReturned.setCountryCode(country.getCountryCode());
		countryReturned.setStates(country.getStates());
		when(countryRepositoryMock.save(country)).thenReturn(countryReturned);

		long returnedId = countryService.insertCountry(country);

		assertEquals(id, returnedId);
	}

	@Test
	public void shouldReturnCountryWhenCountryIdIsGiven() {
		long id = 1;
		country.setId(id);
		when(countryRepositoryMock.findOne(id)).thenReturn(country);

		Country countryReturned = countryService.getCountry(id);

		assertEquals(country, countryReturned);
	}

	@Test
	public void shouldReturnResourceNotFoundExceptionWhenInexistingCountryIdIsGiven() {
		long id = 1;
		when(countryRepositoryMock.findOne(id)).thenReturn(null);

		try {
			countryService.getCountry(id);

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(id, e.getFieldValue());
		}
	}

	@Test
	public void shouldUpdateCountryWhenExistingCountryIsUpdated() {
		long id = 1;
		country.setId(id);
		Country updatedCountry = new Country();
		updatedCountry.setId(country.getId());
		updatedCountry.setName("Spain");
		updatedCountry.setCountryCode("ES");
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);
		when(countryRepositoryMock.save(updatedCountry)).thenReturn(updatedCountry);

		countryService.updateCountry(updatedCountry);

		verify(countryRepositoryMock, times(1)).save(updatedCountry);
	}

	@Test
	public void shouldReturnExceptionWhenUnexistingCountryIsUpdated() {
		long id = 1;
		country.setId(id);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {
			countryService.updateCountry(country);

			fail();
			
		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(id, e.getFieldValue());	
		}
	}
	
	@Test
	public void shouldDeleteCountryWhenExistingCountryIsDeleted() {
		long id = 1;
		country.setId(id);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(country);

		countryService.deleteCountry(country.getId());

		verify(countryRepositoryMock, times(1)).delete(country);;
	}

	@Test
	public void shouldReturnExceptionWhenUnexistingCountryIsDeleted() {
		long id = 1;
		country.setId(id);
		when(countryRepositoryMock.findOne(country.getId())).thenReturn(null);

		try {
			countryService.deleteCountry(country.getId());

			fail();
			
		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(id, e.getFieldValue());	
		}
	}

}
