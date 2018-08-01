package com.diego.geographicapi.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diego.geographicapi.exceptions.ResourceNotFoundException;
import com.diego.geographicapi.model.Country;
import com.diego.geographicapi.repository.CountryRepository;
import com.diego.geographicapi.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryRepository countryRepository;

	@Override
	public Long insertCountry(Country country) {
		return countryRepository.save(country).getId();
	}

	@Override
	public Country getCountry(Long id) {
		Country countryReturned = countryRepository.findOne(id);
		if (countryReturned == null) {
			throw new ResourceNotFoundException("Country", "Id", id);
		}
		
		return countryReturned;
	}

	@Override
	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}
	
	@Override
	public void updateCountry(Country country) {
		if(countryRepository.findOne(country.getId()) == null) {
			throw new ResourceNotFoundException("Country", "Id", country.getId());
		}
		
		countryRepository.save(country);

	}

	@Override
	public void deleteCountry(Long id) {
		Country countryToDelete = countryRepository.findOne(id);
		if(countryToDelete == null) {
			throw new ResourceNotFoundException("Country", "Id", id);
		}
		
		countryRepository.delete(countryToDelete);

	}


}
