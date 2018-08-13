package com.diego.geographicapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.diego.geographicapi.model.State;


@Repository
public interface StateRepository extends JpaRepository<State, Long> {
	
	@Query("select s from State s where s.stateCode = :stateCode and s.country.countryCode = :countryCode")
	Optional<State> findByStateCodeAndCountryCode(@Param("stateCode") String stateCode, @Param("countryCode") String countryCode);
	
	@Query("select s from State s where s.country.countryCode = :countryCode")
	Optional<List<State>> findAllByCountryCode(@Param("countryCode") String countryCode);
}
