package com.hr.system.attendance.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hr.system.attendance.bean.TimesheetAccessBean;
import com.hr.system.attendance.repository.AttendanceRepository;
import com.hr.system.attendance.repository.TimesheetRepository;
import com.hr.system.common.files.FileUtils;
import com.hr.system.common.files.Utils;
import com.hr.system.company.bean.DepartmentAccessBean;
import com.hr.system.company.bean.EmpWorkRelationAccessBean;
import com.hr.system.company.bean.WorkLocationAccessBean;
import com.hr.system.company.repository.DepartmentRepository;
import com.hr.system.employee.bean.AddressAccessBean;
import com.hr.system.employee.bean.EmployeeAccessBean;
import com.hr.system.employee.repository.EmployeeRepository;
import com.hr.system.employee.repository.WorkLocationRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	Utils utils;

	@Autowired
	FileUtils fileUtils;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	TimesheetRepository timesheetRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	AttendanceRepository attendanceRepository;

	@Autowired
	WorkLocationRepository workLocationRepository;

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view Attendance Log of all employee and call on
	 *          jsp in service
	 * @param records used for fetch the all employees attendance data from database
	 *
	 */
	@Override
	public List<Map<String, Object>> attendanceRevision(Long attendanceId, String date) {
		if (attendanceId == null) {
			attendanceId = 3L;
		}
		/*******************
		 * LOGIC TO DISPLAY ONLY SELECTED VALUES IN ATTENDANCE DISPLAY PAGE
		 **************************************/

		Boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst()
				.get().getAuthority().equalsIgnoreCase("ADMIN");
		Boolean isEmployee = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.findFirst().get().getAuthority().equalsIgnoreCase("Employee");
		Long employeId = employeeRepository
				.findByLogonId(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployeeId();

		List<TimesheetAccessBean> timesheetData = null;

		if (date != "" && isAdmin) {
			timesheetData = timesheetRepository.findByDateLike("%" + date + "%");
		}

		else if (date != "" && isEmployee) {
			timesheetData = timesheetRepository.findByEmployeeIdAndDateLike(employeId, "%" + date + "%");
		}

		else {
			TimesheetAccessBean timesheetAccessBean = timesheetRepository.findByTimeSheetId(attendanceId);
			Long employeeId = timesheetAccessBean.getEmployeeId();
			timesheetData = timesheetRepository.findByEmployeeId(employeeId);
		}

		List<Map<String, Object>> responseAttendanceList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < timesheetData.size(); i++) {

			Map<String, Object> attendanceMap = new HashMap<String, Object>();
			attendanceMap.put("timesheetId", timesheetData.get(i).getTimeSheetId());
			attendanceMap.put("inTime", timesheetData.get(i).getCheckIn());
			attendanceMap.put("outTime", timesheetData.get(i).getCheckOut());
			attendanceMap.put("workDuration", timesheetData.get(i).getRawTime());
			attendanceMap.put("status", timesheetData.get(i).getStatus());
			attendanceMap.put("date", timesheetData.get(i).getDate());
			attendanceMap.put("employeeId", timesheetData.get(i).getEmployeeId());

			EmployeeAccessBean employeeAccessBean = employeeRepository.getOne(timesheetData.get(i).getEmployeeId());
			attendanceMap.put("employeeCode", employeeAccessBean.getEmployeeCode());

			if (employeeAccessBean.getAddressAccessBean() != null) {
				List<AddressAccessBean> addressAccessBeans = employeeAccessBean.getAddressAccessBean();
				if (addressAccessBeans != null && addressAccessBeans.size() != 0) {
					attendanceMap.put("addressId", addressAccessBeans.get(0).getAddressId());
					attendanceMap.put("firstName", addressAccessBeans.get(0).getFirstName());
					attendanceMap.put("lastName", addressAccessBeans.get(0).getLastName());
					Long worklocationId = addressAccessBeans.get(0).getWorkLocationId();
					WorkLocationAccessBean workLocationAccessBean = workLocationRepository.getOne(worklocationId);
					attendanceMap.put("add1", workLocationAccessBean.getAddress1());
					attendanceMap.put("add2", workLocationAccessBean.getAddress2());
					attendanceMap.put("add3", workLocationAccessBean.getAddress3());
				}
			}
			if (employeeAccessBean.getEmpWorkRelationAccessBean() != null) {
				EmpWorkRelationAccessBean empWorkRelationAccessBean = employeeAccessBean.getEmpWorkRelationAccessBean();
				attendanceMap.put("empWorkRelation", empWorkRelationAccessBean.getEmpWorkRelationId());
				attendanceMap.put("manager", empWorkRelationAccessBean.getManager());
				attendanceMap.put("departmentId", empWorkRelationAccessBean.getDepartmentId());

				Long d = empWorkRelationAccessBean.getDepartmentId();
				if (d != null) {
					DepartmentAccessBean departmentAccessBean = departmentRepository
							.getOne(empWorkRelationAccessBean.getDepartmentId());
					attendanceMap.put("departmentName", departmentAccessBean.getDeptName());
				} else {
					attendanceMap.put("departmentName", "Not Specified");
				}

			}

			responseAttendanceList.add(attendanceMap);
		}

		return responseAttendanceList;
	}
	/*
	 * @Override public void exportData(String fromDate, String toDate) {
	 * List<TimesheetAccessBean>
	 * data=timesheetRepository.findDataWithDateRange(Date.valueOf(fromDate),
	 * Date.valueOf(toDate)); data); }
	 */

	@Transactional
	public void exportData(HttpServletResponse response, RedirectAttributes flashAttributes, String fromDate,
			String toDate, Long timesheetId) {

		String home = System.getProperty("user.home");
		String location = home + "\\Downloads\\";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sqlDate = null, sqlDate2 = null;

		try {
			java.util.Date utilDate = format.parse(fromDate);
			sqlDate = new java.sql.Date(utilDate.getTime());
			java.util.Date utilDate2 = format.parse(toDate);
			sqlDate2 = new java.sql.Date(utilDate2.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// employeeId for timesheet...
		Long employeeId = null;
		if (timesheetId != null) {
			TimesheetAccessBean timesheetAccessBean = timesheetRepository.getOne(timesheetId);
			employeeId = timesheetAccessBean.getEmployeeId();
		}
		Query q = null;
		if (sqlDate2 != null) {
			q = entityManager.createNativeQuery(
					"SELECT * FROM Timesheet WHERE date BETWEEN '" + sqlDate + "' AND '" + sqlDate2 + "'",
					TimesheetAccessBean.class);
		} else {
			q = entityManager.createNativeQuery("SELECT * FROM Timesheet WHERE date ='" + sqlDate + "'",
					TimesheetAccessBean.class);
		}
		@SuppressWarnings("unchecked")
		List<TimesheetAccessBean> dataAll = q.getResultList();
		List<TimesheetAccessBean> data = new ArrayList<TimesheetAccessBean>();
		if (timesheetId != null) {
			if (dataAll.size() > 0) {
				for (int i = 0; i < dataAll.size(); i++) {
					if (dataAll.get(i).getEmployeeId() != null) {
						if (dataAll.get(i).getEmployeeId().equals(employeeId)) {
							data.add(dataAll.get(i));
						}
					}
				}
				if (data != null)
					try {
						fileUtils.generateTimesheetReport(data, location, response);
					} catch (IOException e) {

						flashAttributes.addFlashAttribute("msg", "Sorry! No match found...");

						e.printStackTrace();
					}

			} else {
				flashAttributes.addFlashAttribute("msg", "Sorry! No match found...");
			}

		} else {

			try {
				if (dataAll.size() > 0) {
					fileUtils.generateTimesheetReport(dataAll, location, response);
				}
				flashAttributes.addFlashAttribute("msg", "Sorry! No match found...");

			} catch (IOException e) {
				flashAttributes.addFlashAttribute("msg", "Sorry! No match found...");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for delete Attendance of employee
	 * @param timesheetId used delete employee attendance data from database
	 *
	 */
	@Override
	public void deleteAttendance(Long timesheetId) {
		timesheetRepository.deleteById(timesheetId);
	}

	@Override
	public TimesheetAccessBean add_Attendance(TimesheetAccessBean timesheetAccessBean) {
		return timesheetRepository.save(timesheetAccessBean);
	}

	@Override
	public void downloadAttendenceHeader(HttpServletResponse response) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet hssfSheet = workbook.createSheet("Attendence");
		HSSFRow hssfRow = hssfSheet.createRow(0);

		HSSFCell sNo = hssfRow.createCell(0);
		sNo.setCellValue("S.No");

		HSSFCell employeeId = hssfRow.createCell(1);
		employeeId.setCellValue("Logon Id");

		HSSFCell date = hssfRow.createCell(2);
		date.setCellValue("Date");

		HSSFCell day = hssfRow.createCell(3);
		day.setCellValue("Day");

		HSSFCell checkIn = hssfRow.createCell(4);
		checkIn.setCellValue("Check In");

		HSSFCell checkOut = hssfRow.createCell(5);
		checkOut.setCellValue("Check Out");

		HSSFCell roundTime = hssfRow.createCell(6);
		roundTime.setCellValue("Round Time");

		HSSFCell status = hssfRow.createCell(7);
		status.setCellValue("Status");

		List<EmployeeAccessBean> allEmplopyees = employeeRepository.findAll();

		int row = 1;
		;

		List<String> allEmplyesLogonId = allEmplopyees.stream().map(e -> e.getLogonId()).collect(Collectors.toList());

		for (String logonId : allEmplyesLogonId) {

			HSSFRow dataRow = hssfSheet.createRow(row);

			HSSFCell sno = dataRow.createCell(0);
			sno.setCellValue(row);

			HSSFCell logonid = dataRow.createCell(1);
			logonid.setCellValue(logonId);

			HSSFCell localeDate = dataRow.createCell(2);

			localeDate.setCellValue(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")));

			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			
			

			Format format=new SimpleDateFormat("EEEE");
			String currentDay = format.format(new Date());
			
			HSSFCell localesDay = dataRow.createCell(3);
	
			 
			 
			localesDay.setCellValue(currentDay);

			row++;

		}

		// response.setContent Type("file/excel");

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		workbook.write(arrayOutputStream);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/excel");
		response.setHeader("Content-Disposition", "Attachment; filename=Attendence.xls");
		response.getOutputStream().write(arrayOutputStream.toByteArray());
		workbook.close();
	}

}
