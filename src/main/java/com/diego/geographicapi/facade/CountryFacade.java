package com.diego.geographicapi.facade;

import java.util.List;

import com.diego.geographicapi.dto.CountryDto;

public interface CountryFacade {
	public CountryDto insertCountry(CountryDto countryDto);
	public List<CountryDto> getAllCountries();
	public CountryDto getCountry(String countryCode);
	public CountryDto updateCountry(String countryCode, CountryDto countryDto);
	public void deleteCountry(String countryCode);
}
