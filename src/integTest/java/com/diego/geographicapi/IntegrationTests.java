package com.diego.geographicapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.diego.geographicapi.controller.CountryController;
import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.repository.CountryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class IntegrationTests {
	
	@Autowired
	private CountryController countryController; 
	
	@Autowired
	private CountryRepository countryRepository;
	
	private final String countryName = "Brazil";
	private final String countryCode = "BR";

	private CountryDto countryDto;
	private Country countryModel;
	
	
	@Before
	public void setup() {
		countryDto = new CountryDto();
		countryDto.setName(countryName);
		countryDto.setCountryCode(countryCode);
		
		countryModel = new Country();
		countryModel.setName(countryName);
		countryModel.setCountryCode(countryCode);
	}
	
	@After
	public void teardown() {
		countryRepository.deleteAll();
	}

	@Test
	public void shouldAddCountryInDatabaseWhenInsertMethodIsCalledFromController() {		
		countryDto = countryController.insertCountry(countryDto);
		
		assertEquals(countryName, countryRepository.findByCountryCode(countryCode).get().getName());
		assertNotNull(countryRepository.findByCountryCode(countryCode).get().getId());
	}
	
	@Test
	public void shouldReturnCountryWhenGetCountryMethodIsCalledFromController() {
		countryRepository.save(countryModel);
		
		CountryDto returnedCountryDto = countryController.getCountry(countryCode);
		
		assertEquals(countryDto, returnedCountryDto);
	}

}
