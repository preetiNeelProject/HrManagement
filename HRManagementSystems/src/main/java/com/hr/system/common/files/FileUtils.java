package com.hr.system.common.files;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hr.system.attendance.bean.TimesheetAccessBean;
import com.hr.system.company.bean.ProjectAccessBean;
import com.hr.system.company.repository.ProjectRepository;
import com.hr.system.employee.bean.EmployeeAccessBean;
import com.hr.system.employee.repository.EmpWorkRelationRepository;
import com.hr.system.employee.repository.EmployeeRepository;
import com.itextpdf.text.log.SysoCounter;

@Component
public class FileUtils {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmpWorkRelationRepository empWorkRelationRepository;

	@Autowired
	ProjectRepository projectRepository;

	public void generateTimesheetReport(List<TimesheetAccessBean> records, String location,
			HttpServletResponse response) throws IOException {
		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=Attendance.csv");
		try (

				CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(),
						CSVFormat.DEFAULT.withHeader("EmpId", "Employee Name",  "Date", "Day",
								"Check In", "Check Out","Round Time", "Status"));) {
			
			for (TimesheetAccessBean timesheet : records) {
				
				Long employeeId2 = timesheet.getEmployeeId();
				EmployeeAccessBean employeAccessBean = new EmployeeAccessBean();

				Long employeeId = timesheet.getEmployeeId();

				
				
				EmployeeAccessBean employeeAccessBean = employeeRepository.findByEmployeeId(employeeId);

				ProjectAccessBean projectAccessBean = projectRepository.findByProjectId(timesheet.getProjectId());

				List<String> data = Arrays.asList(String.valueOf(timesheet.getTimeSheetId()),
						employeeAccessBean.getLogonId(),
						 String.valueOf(timesheet.getDate()),
						timesheet.getDay(), timesheet.getCheckIn(), timesheet.getCheckOut(),
						timesheet.getRoundTime(), timesheet.getStatus()

				);
				csvPrinter.printRecord(data);
			}
			csvPrinter.flush();
		} catch (Exception e) {


			e.printStackTrace();
		}
	}

	public void generateTimesheetReport2(TimesheetAccessBean records, String location, HttpServletResponse response)
			throws IOException {
		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=Attendance.csv");
		try (

				CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(),
						CSVFormat.DEFAULT.withHeader("S.No", "Employee Id", "Project Id", "Manager Id", "Date", "Day",
								"Check In", "Check Out", "Raw Time", "Round Time", "Status", "Leave Id",
								"Holiday Id"));) {



			
			Long employeeId = records.getEmployeeId();
			

			EmployeeAccessBean employeeAccessBean = employeeRepository.findByEmployeeId(employeeId);
			
		
			ProjectAccessBean projectAccessBean = projectRepository.findByProjectId(records.getProjectId());
			

			List<String> data = Arrays.asList(String.valueOf(records.getTimeSheetId()),
					String.valueOf(employeeAccessBean.getEmployeeCode()), String.valueOf(records.getManagerId()),
					String.valueOf(projectAccessBean.getProjectCode()), String.valueOf(records.getDate()),
					records.getDay(), records.getCheckIn(), records.getCheckOut(), records.getRawTime(),
					records.getRoundTime(), records.getStatus(), String.valueOf(records.getHolidayId())

			);
			csvPrinter.printRecord(data);

			csvPrinter.flush();
		} catch (Exception e) {
				e.printStackTrace();

		}

	}
}
