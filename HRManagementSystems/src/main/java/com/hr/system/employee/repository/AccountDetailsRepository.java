package com.hr.system.employee.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hr.system.employee.bean.AccountDetailsAccessBean;
import com.hr.system.employee.bean.EmployeeAccessBean;

public interface AccountDetailsRepository extends JpaRepository<AccountDetailsAccessBean, Long> {

	AccountDetailsAccessBean findByEmployeeAccessBean(EmployeeAccessBean mployeeAccessBean);
	void deleteByEmployeeAccessBean(Long employeeId);
	
	@Query("Delete  From AccountDetailsAccessBean u where u.employeeAccessBean.employeeId=:employeId")	
	@Modifying
	@Transactional
	void deleteByEmployeId(Long employeId);
	
	

}
