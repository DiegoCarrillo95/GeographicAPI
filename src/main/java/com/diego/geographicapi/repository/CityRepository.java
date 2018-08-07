package com.diego.geographicapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.diego.geographicapi.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
	@Query("select c from City c where c.cityCode = :cityCode and c.state.stateCode = :stateCode and c.state.country.countryCode = :countryCode")
	City findByCityCodeAndStateCodeAndCountryCode(@Param("cityCode") String cityCode,
			@Param("stateCode") String stateCode, @Param("countryCode") String countryCode);

	@Query("select c from City c where c.state.stateCode = :stateCode and c.state.country.countryCode = :countryCode")
	List<City> findAllByStateCodeCountryCode(@Param("stateCode") String stateCode, @Param("countryCode") String countryCode);
} 
