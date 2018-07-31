package com.diego.geographicapi.service;

import com.diego.geographicapi.model.Country;

public interface CountryService {
	
	public Long insertCountry(Country country);
	public Country getCountry(Long id);
	public void updateCountry(Country country);
	public void deleteCountry(Long id);
}
