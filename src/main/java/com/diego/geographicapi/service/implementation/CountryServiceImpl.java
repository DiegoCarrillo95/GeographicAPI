package com.diego.geographicapi.service.implementation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.diego.geographicapi.exceptions.EntityNotFoundException;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	private final CountryRepository countryRepository;
	
	public CountryServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public Country insertCountry(Country country) {
		return countryRepository.save(country);
	}
	
	@Override
	public Country getCountryByCountryCode(String countryCode) {
		Country countryReturned = countryRepository.findByCountryCode(countryCode);
		if (countryReturned == null) { 
			throw new EntityNotFoundException("Country", "CountryCode", countryCode);
		}
		
		return countryReturned;
	}

	@Override
	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}
	
	@Override
	public Country updateCountry(String countryCode, Country updatedCountry) {
		
		Country currentCountry = countryRepository.findByCountryCode(countryCode);
		
		if(currentCountry == null) {
			throw new EntityNotFoundException("Country", "CountryCode", countryCode);
		}
		
		BeanUtils.copyProperties(updatedCountry, currentCountry, "id", "states");

		return countryRepository.save(currentCountry);
	}

	@Override
	public void deleteCountry(String countryCode) {
		Country countryToDelete = countryRepository.findByCountryCode(countryCode);
		if(countryToDelete == null) {
			throw new EntityNotFoundException("Country", "CountryCode", countryCode);
		}
		
		countryRepository.delete(countryToDelete);

	}


}
