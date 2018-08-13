package com.diego.geographicapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diego.geographicapi.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	
	public Optional<Country> findByCountryCode(String countryCode);
	
}
