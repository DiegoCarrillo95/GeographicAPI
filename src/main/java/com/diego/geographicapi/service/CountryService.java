package com.diego.geographicapi.service;

import java.util.List;

import com.diego.geographicapi.model.Country;

public interface CountryService {
	
	public Country insertCountry(Country country);
	public Country getCountryById(Long id);
	public Country getCountryByCountryCode(String countryCode);
	public List<Country> getAllCountries();
	public Country updateCountry(Country country);
	public void deleteCountry(Long id);
}
