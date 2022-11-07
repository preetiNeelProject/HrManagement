package com.hr.system.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hr.system.attendance.bean.TimesheetAccessBean;

@Repository
public interface TimesheetRepository extends JpaRepository<TimesheetAccessBean, Long> {

	List<TimesheetAccessBean> findByDate(String date);
	
	TimesheetAccessBean  findByEmployeeIdAndDate(Long employeeId,String date);

	TimesheetAccessBean findByTimeSheetId(Long attendanceId);

	List<TimesheetAccessBean> findByEmployeeId(Long attendanceId);

    long deleteByEmployeeId(Long EmployeId);
    List<TimesheetAccessBean> findByEmployeeIdAndStatusAndDateLike(Long employeeId,String status,String date);      
		
    
    List<TimesheetAccessBean>  findByEmployeeIdAndStatusAndDayLikeAndDateLike(Long employeeId,String status,String day,String date);

	List<TimesheetAccessBean> findByEmployeeIdAndStatus(Long employeeId, String string);

	List<TimesheetAccessBean> findByDateLike(String date);
	
	List<TimesheetAccessBean> findByStatusAndDateLike(String status, String date);

	

	
	
	List<TimesheetAccessBean> findByEmployeeIdAndDateLike(Long employeId, String string);

	List<TimesheetAccessBean> findBydateLike(String string);	
    
    /*
		 * @Query(value="Select t from timesheet t where t.") List<TimesheetAccessBean>
		 * findByEmployeeIdAndDateAndStatus();
		 */
      
           
	/*
	 * @Query("FROM timesheet t WHERE STR_TO_DATE(t.date, '%a, %c %b %Y %k:%i:%s') BETWEEN :startTime AND :endTime"
	 * ) List<TimesheetAccessBean> findDataWithDateRange(
	 * 
	 * @Param("startTime") Date fromDate, @Param("endTime") Date toDate);
	 */
	
}
