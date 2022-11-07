package com.hr.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hr.system.attendance.bean.TimesheetAccessBean;
import com.hr.system.attendance.repository.TimesheetRepository;
import com.hr.system.payroll.service.PayrollService;

@SpringBootTest
class HrManagementSystemsApplicationTests {

	@Autowired
	TimesheetRepository timesheetRepo;
	
	@Autowired
	PayrollService payrollSrvice;

	/*
	 * @Test void contextLoads() { TimesheetAccessBean timesheetAccessBean =
	 * timesheetRepo.findByEmployeeIdAndDate(140l, "2022-09-12");
	 * assertTrue(timesheetAccessBean.getEmployeeId()==141l);
	 * 
	 * 
	 * }
	 */
	@Test
	void donloadsalary() {
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-DD");
	LocalDate date2=LocalDate.now();
	//System.err.println("Month Value is :"+date.getMonthValue());
		 String date="2022-09";
		List<TimesheetAccessBean> timesheetAccessBean = timesheetRepo.findByEmployeeIdAndStatusAndDateLike(140l, "P","%"+date+"%");		
		System.err.println("Size is :"+timesheetAccessBean.size());
	}
	
	/*
	 * @Test void dayStatus() {
	 * 
	 * List<TimesheetAccessBean> daysIn =
	 * timesheetRepo.findByEmployeeIdAndDayLike(140l, "%Monday%");
	 * System.err.println(daysIn.size());
	 * 
	 * }
	 */}
