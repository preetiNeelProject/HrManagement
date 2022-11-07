package com.hr.system.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hr.system.attendance.bean.AttendanceAccessBean;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceAccessBean, Long> {

	List<AttendanceAccessBean> findByEmployeeId(Long employeeId);

	List<AttendanceAccessBean> findByemployeeId(Long employeId);

}
