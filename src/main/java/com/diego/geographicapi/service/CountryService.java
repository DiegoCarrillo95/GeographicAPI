package com.diego.geographicapi.service;

import java.util.List;

import com.diego.geographicapi.model.Country;

public interface CountryService {
	
	public Long insertCountry(Country country);
	public Country getCountry(Long id);
	public List<Country> getAllCountries();
	public void updateCountry(Country country);
	public void deleteCountry(Long id);
}
