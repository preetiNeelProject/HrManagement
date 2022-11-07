package com.hr.system.company.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hr.system.authentication.bean.CityState;

@Repository
public interface StateCity extends JpaRepository<CityState, Integer> {

	
	Optional<List<CityState>> findBystate(String state);
}
