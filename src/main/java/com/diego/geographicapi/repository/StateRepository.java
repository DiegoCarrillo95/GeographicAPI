package com.diego.geographicapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diego.geographicapi.model.State;


@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
