package com.hr.system.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hr.system.employee.bean.EmployeeAccessBean;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeAccessBean, Long> {

	EmployeeAccessBean findByLogonId(String logonId);
	EmployeeAccessBean findEmployeeIdByLogonId(String name);
	EmployeeAccessBean findByEmployeeId(Long employeeId);
	EmployeeAccessBean save(EmployeeAccessBean emp);
	EmployeeAccessBean findByEmployeeCode(String employeeCode);
	
	
	
	

}
