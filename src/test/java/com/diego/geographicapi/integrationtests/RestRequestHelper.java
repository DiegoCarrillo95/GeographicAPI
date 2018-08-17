package com.diego.geographicapi.integrationtests;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;

import com.diego.geographicapi.dto.CountryDto;

public class RestRequestHelper {
		
	@LocalServerPort
	private int port;
	
	private final String urn = "/api/v1/country/";

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	public CountryDto getCountry(String countryCode) {
		return restTemplate.getForObject(createURIWithPort(urn + countryCode), CountryDto.class);
	}
	
	public List<CountryDto> getAllCountries() {
		return Arrays.asList(restTemplate.getForObject(createURIWithPort("/api/v1/country/"), CountryDto[].class));
	}
	
	public CountryDto postCountry(CountryDto countryDto) {
		return restTemplate.postForObject(createURIWithPort(urn), countryDto, CountryDto.class);
	}
	
	public void updateCountry(String countryCode, CountryDto countryDto) {
		restTemplate.put(createURIWithPort(urn + countryCode), countryDto);
	}
	
	public void deleteCountry(String countryCode) {
		restTemplate.delete(createURIWithPort(urn + countryCode));
	}
	
	private String createURIWithPort(String urn) {
		return "http://localhost:" + port + urn;
	}
}
