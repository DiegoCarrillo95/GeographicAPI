package com.diego.geographicapi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.facade.CountryFacade;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

	@InjectMocks
	CountryController countryController;
	
	@Mock
	private CountryFacade countryFacade;
	
	final long id1 = 1;
	final long id2 = 2;

	CountryDto countryDto1 = new CountryDto();
	CountryDto countryDto2 = new CountryDto();
	List<CountryDto> countryDtoList = new ArrayList<>();

	@Before
	public void init() {
		countryDto1.setId(id1);
		countryDto1.setName("Brazil");
		countryDto1.setCountryCode("BR");
		countryDto2.setId(id2);
		countryDto2.setName("Mexico");
		countryDto2.setCountryCode("MX");
		countryDtoList.add(countryDto1);
		countryDtoList.add(countryDto2);
	}
	
	@Test
	public void shouldReturnCountryDtoWithIdWhenNewCountryisInserted(){
		CountryDto insertedCountry = new CountryDto();
		insertedCountry.setName(countryDto1.getName());
		insertedCountry.setCountryCode(countryDto1.getCountryCode());

		when(countryFacade.insertCountry(insertedCountry)).thenReturn(countryDto1);
		
		CountryDto returnedCountry = countryController.insertCountry(insertedCountry);
		
		assertEquals(countryDto1, returnedCountry);
	}
	
	@Test
	public void shouldReturnCountryDtoListWhenGetAllCountriesMethodIsCalled(){
		when(countryFacade.getAllCountries()).thenReturn(countryDtoList);
		
		List<CountryDto> returnedList = countryController.getAllCountries();
		
		assertEquals(countryDtoList, returnedList);
	}
	
	@Test
	public void shouldReturnCountryDtoWhenGetCountryIsCalledWithExistingId() {
		when(countryFacade.getCountry(id1)).thenReturn(countryDto1);
		
		CountryDto returnedCountry = countryController.getCountry(id1);
		
		assertEquals(countryDto1, returnedCountry);
	
	}
}
