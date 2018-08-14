package com.diego.geographicapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.diego.geographicapi.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
	@Query("select city from City city where city.cityCode = :cityCode and city.state.stateCode = :stateCode and city.state.country.countryCode = :countryCode")
	Optional<City> findByCityCodeAndStateCodeAndCountryCode(@Param("cityCode") String cityCode,
			@Param("stateCode") String stateCode, @Param("countryCode") String countryCode);

	@Query("select city from City city where city.state.stateCode = :stateCode and city.state.country.countryCode = :countryCode")
	Optional<List<City>> findAllByStateCodeCountryCode(@Param("stateCode") String stateCode, @Param("countryCode") String countryCode);
} 
