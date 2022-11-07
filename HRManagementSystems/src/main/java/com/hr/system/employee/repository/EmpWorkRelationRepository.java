package com.hr.system.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.system.company.bean.EmpWorkRelationAccessBean;
import com.hr.system.employee.bean.EmployeeAccessBean;

public interface EmpWorkRelationRepository extends JpaRepository<EmpWorkRelationAccessBean, Long> {

	//EmpWorkRelationAccessBean findByEmployeeId(Long employeeId);
	EmpWorkRelationAccessBean findDesignationByEmployeeAccessBean(EmployeeAccessBean employeeAccessBean);


}
