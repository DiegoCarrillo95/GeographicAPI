package com.diego.geographicapi.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.service.CountryService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CountryFacadeTest {

	@InjectMocks
	private CountryFacade countryFacade;

	@Mock
	private CountryService countryService;

	final long id1 = 1;
	final long id2 = 2;

	Country country1 = new Country();
	Country country2 = new Country();
	List<Country> countryList = new ArrayList<>();

	CountryDto countryDto1 = new CountryDto();
	CountryDto countryDto2 = new CountryDto();
	List<CountryDto> countryDtoList = new ArrayList<>();

	@Before
	public void init() {
		country1.setId(id1);
		country1.setName("Brazil");
		country1.setCountryCode("BR");
		country2.setId(id2);
		country2.setName("Mexico");
		country2.setCountryCode("MX");
		countryList.add(country1);
		countryList.add(country2);

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
	public void shouldReturnCountryDtoListWhenGetAllCountriesMethodIsCalled() {
		when(countryService.getAllCountries()).thenReturn(countryList);

		List<CountryDto> listReturned = countryFacade.getAllCountries();

		assertEquals(countryDtoList, listReturned);
	}

	@Test
	public void shouldReturnCountryDtoWhenGetCountryIsCalledWithExistingId() {
		when(countryService.getCountry(id1)).thenReturn(country1);

		CountryDto countryReturned = countryFacade.getCountry(id1);

		assertEquals(countryDto1, countryReturned);
	}

	@Test
	public void shouldReturnCountryExceptionWhenGetCountryIsCalledWithUnexistingId() {
		long id3 = 3;
		when(countryService.getCountry(id3)).thenThrow(new ResourceNotFoundException("Country", "Id", id3));

		try {
			countryFacade.getCountry(id3);

			fail();

		} catch (ResourceNotFoundException e) {
			assertEquals("Country", e.getResourceName());
			assertEquals("Id", e.getFieldName());
			assertEquals(id3, e.getFieldValue());
		}

	}

	@Test
	public void shouldCallUpdateCountryServiceWhenUpdateCountryisCalledWithExistingId() {
		countryFacade.updateCountry(countryDto1);

		verify(countryService, times(1)).updateCountry(country1);
	}

	@Test
	public void shouldCallDeleteCountryServiceWhenDeleteCountryIsCalledWithExistingId() {
		countryFacade.deleteCountry(countryDto1);

		verify(countryService, times(1)).deleteCountry(country1.getId());
	}
}
