package com.diego.geographicapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diego.geographicapi.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	
	public Country findByCountryCode(String countryCode);
	
}
