package com.diego.geographicapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diego.geographicapi.model.State;


@Repository
public interface StateRepository extends JpaRepository<State, Long> {
	
	@Query(value = "SELECT * FROM states WHERE country_id = ?1", nativeQuery = true)
	List<State> findByCountry(Long countryId);
	
	@Query(value = "SELECT * FROM states WHERE id = ?1 and country_id = ?2", nativeQuery = true)
	State findByIdByCountry(Long id, Long countryId);
	
	@Query(value = "SELECT * FROM states WHERE state_code = ?1 and country_id = ?2", nativeQuery = true)
	State findByStateCodeByCountry(String stateCode, Long countryId);
	
}
