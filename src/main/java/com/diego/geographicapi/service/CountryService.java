package com.diego.geographicapi.service;

import java.util.List;

import com.diego.geographicapi.model.Country;

public interface CountryService {
	
	public Country insertCountry(Country country);
	public Country getCountryByCountryCode(String countryCode);
	public List<Country> getAllCountries();
	public Country updateCountry(String countryCode, Country country);
	public void deleteCountry(String countryCode);
}
