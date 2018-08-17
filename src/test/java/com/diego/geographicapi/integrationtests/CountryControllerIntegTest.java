package com.diego.geographicapi.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.diego.geographicapi.GeographicApiApplication;
import com.diego.geographicapi.dto.CountryDto;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.repository.CountryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeographicApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CountryControllerIntegTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Autowired
	CountryRepository countryRepository;

	private final String countryName = "Brazil";
	private final String countryCode = "BR";

	private final String unexistingCountryCode = "AA";

	private Country countryModel;
	private CountryDto countryDto;

	HttpEntity<String> emptyEntity;
	HttpEntity<CountryDto> countryDtoEntity;
	
	@Before
	public void setUp() {
		countryModel = new Country();
		countryModel.setName(countryName);
		countryModel.setCountryCode(countryCode);
		countryModel = countryRepository.save(countryModel);
		
		countryDto = new CountryDto();
		countryDto.setName(countryName);
		countryDto.setCountryCode(countryCode);
		countryDto.setId(countryModel.getId());

		countryDtoEntity = new HttpEntity<CountryDto>(countryDto, headers);
		emptyEntity = new HttpEntity<String>(null, headers);
	}

	@After
	public void tearDown() {
		countryRepository.deleteAll();
	}

	@Test
	public void shouldGetCountryListWhenGetMethodIsRequestedFromController() {
		CountryDto[] response = restTemplate.getForObject(createURIWithPort("/api/v1/country/"), CountryDto[].class);

		assertEquals(countryDto, response[0]);
	}
	
	@Test
	public void shouldGetCountryWhenGetMethodIsRequestedFromControllerWithExistingCountryCode() {
		CountryDto response = restTemplate.getForObject(createURIWithPort("/api/v1/country/" + countryCode), CountryDto.class);

		assertEquals(countryDto, response);
	}

	@Test
	public void shouldGetCountryWhenGetMethodIsRequestedFromControllerWithUnexistingCountryCode() {
		ResponseEntity<String> response = restTemplate.exchange(
				createURIWithPort("/api/v1/country/" + unexistingCountryCode), HttpMethod.GET, emptyEntity,
				String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void shouldGetCountryFromRepositoryWhenPostMethodIsCalledFromRepositoryWithValidCountry() {
		countryRepository.deleteAll();

		ResponseEntity<CountryDto> response = restTemplate.exchange(createURIWithPort("/api/v1/country/"), HttpMethod.POST,
				countryDtoEntity, CountryDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(countryModel, countryRepository.findByCountryCode(countryCode).get());
	}

	@Test
	public void shouldUpdateCountryFromRepositoryWhenPutMethodIsRequestedFromControllerWithValidCountry() {
		CountryDto updatedCountryDto = new CountryDto();
		BeanUtils.copyProperties(countryDto, updatedCountryDto);
		updatedCountryDto.setName("United States Of America");
		updatedCountryDto.setCountryCode("US");
		countryModel.setName(updatedCountryDto.getName());
		countryModel.setCountryCode(updatedCountryDto.getCountryCode());
		countryDtoEntity = new HttpEntity<CountryDto>(updatedCountryDto, headers);

		ResponseEntity<CountryDto> response = restTemplate.exchange(createURIWithPort("/api/v1/country/" + countryCode), HttpMethod.PUT, countryDtoEntity, CountryDto.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(countryModel, countryRepository.findByCountryCode("US").get());
	}
	
	@Test
	public void shouldDeleteCountryFromRepositoryWhenDeletMethodIsRequestedFromControllerWithExistingCountryCode(){
		ResponseEntity<String> response = restTemplate.exchange(createURIWithPort("/api/v1/country/" + countryCode), HttpMethod.DELETE,
				emptyEntity, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(countryRepository.findByCountryCode(countryCode).isPresent());
	}
	
	private String createURIWithPort(String urn) {
		return "http://localhost:" + port + urn;
	}

}
