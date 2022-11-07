package com.hr.system.payroll.service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.hql.internal.ast.HqlASTFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.system.attendance.bean.TimesheetAccessBean;
import com.hr.system.attendance.repository.TimesheetRepository;
import com.hr.system.common.service.CommonService;
import com.hr.system.company.bean.EmpWorkRelationAccessBean;
import com.hr.system.employee.bean.AccountDetailsAccessBean;
import com.hr.system.employee.bean.AddressAccessBean;
import com.hr.system.employee.bean.EmployeeAccessBean;
import com.hr.system.employee.repository.AccountDetailsRepository;
import com.hr.system.employee.repository.AddressRepository;
import com.hr.system.employee.repository.EmpWorkRelationRepository;
import com.hr.system.employee.repository.EmployeeRepository;
import com.hr.system.payroll.bean.AllowanceTransAccessBean;
import com.hr.system.payroll.bean.AllowancesAccessBean;
import com.hr.system.payroll.bean.CtcHistoryAccessBean;
import com.hr.system.payroll.bean.EmployeeCTCAccessBean;
import com.hr.system.payroll.repository.AllowancesTransRepository;
import com.hr.system.payroll.repository.CtcHistoryRepository;
import com.hr.system.payroll.repository.EmployeeCTCRepository;
import com.hr.system.payroll.repository.PayrollRepository;

@Service
public class PayrollServiceImpl implements PayrollService {

	@Autowired
	AllowancesTransRepository allowancesTransRepository;

	@Autowired
	private  AddressRepository addressRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeCTCRepository employeeCTCRepository;

	@Autowired
	private EmpWorkRelationRepository empWorkRelationRepository;
	
	@Autowired
	CtcHistoryRepository ctcHistoryRepository;

	@Autowired
	PayrollRepository payrollRepository;

	@Autowired
	CommonService commonService;

	@Autowired
	AccountDetailsRepository accountsRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TimesheetRepository timeSheetRepository;

	@Override
	public void addEmployeeAllowances(String LogonId, Long allowanceTypeId) {

		EmployeeAccessBean empAB = employeeRepository.findByLogonId("Preeti@gmail.com");

		AllowancesAccessBean allowancesAccessBean = new AllowancesAccessBean();
		allowancesAccessBean.setEmployeeAccessBean(empAB);
		allowancesAccessBean.setAllowancesAmt(1301L);
		allowancesAccessBean.setEffectiveDate(new Date());
		allowancesAccessBean.setEndDate(new Date());
		allowancesAccessBean.setAllowanceTypeId(allowanceTypeId);

		AllowanceTransAccessBean allowanceTransAccessBean = new AllowanceTransAccessBean();
		allowanceTransAccessBean.setAllowanceAmt(1201L);
		allowanceTransAccessBean.setAllowancesAccessBean(allowancesAccessBean);

		allowancesTransRepository.saveAndFlush(allowanceTransAccessBean);
	}

	@Override
	public EmployeeCTCAccessBean addEmployeeSalary(EmployeeCTCAccessBean salary) {
		Long employeeId = salary.getEmployeeId();
		boolean checkFlag = employeeCTCRepository.existsByEmployeeId(salary.getEmployeeId());
		if (checkFlag == true) {
			EmployeeCTCAccessBean emp = employeeCTCRepository.findByEmployeeId(salary.getEmployeeId());
			Long ctcId = emp.getEmployeeCTCId();
			employeeCTCRepository.deleteById(ctcId);
			return employeeCTCRepository.save(salary);
		}
		return employeeCTCRepository.save(salary);
		/*
		 * Optional<EmployeeCTCAccessBean> empSalary =
		 * employeeCTCRepository.findById(employeeId);
		 * commonService.savePreviousSalary(empSalary);
		 * employeeCTCRepository.deleteById(empSalary.get().getEmployeeId());
		 * employeeCTCRepository.save(salary);
		 * empSalary.get().getName());
		 */
	}

	@Override
	public List<CtcHistoryAccessBean> salaryRevision() {
		return ctcHistoryRepository.findAll();
	}

	@Override
	public List<EmployeeCTCAccessBean> currentSalaryRevision() {
		return employeeCTCRepository.findAll();
	}

	@Override
	public EmployeeCTCAccessBean salarySlip(Long employeeCTCId, HttpServletRequest request,
			HttpServletResponse response) {
		Long month = null;
		commonService.salarySlip(employeeCTCId, month, request, response);
		return null;
	}

	@Override
	public EmployeeCTCAccessBean monthlySalaryCalculator(Long employeeCTCId, Long month, HttpServletRequest request,
			HttpServletResponse response) {
		commonService.salarySlip(employeeCTCId, month, request, response);
		return null;
	}

	@Override
	public EmployeeCTCAccessBean findEmployeeCTCByEmployeeCTCId(Long employeeCTCId) {
		return employeeCTCRepository.findEmployeeCTCByEmployeeCTCId(employeeCTCId);
	}

	@Override
	public void salaryDownload(HttpServletResponse response, String date) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Salary Slip");
		sheet.addMergedRegion(CellRangeAddress.valueOf("A1:k1"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("A2:K3"));



		Month monthName = Month.of(Integer.valueOf(date.split("-")[1]));
		Long daysInMonth = Long.valueOf(monthName.length(true));

		HSSFFont titleFont = workbook.createFont();
		titleFont.setBold(true);
		titleFont.setFontHeight((short) 400);

		HSSFFont headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor((short) 500);
		headerFont.setFontHeight((short) 210);

		HSSFFont shortHeadingFont = workbook.createFont();
		shortHeadingFont.setBold(true);
		shortHeadingFont.setFontHeight((short) 300);

		HSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		HSSFCellStyle shortHeaderStyle = workbook.createCellStyle();
		shortHeaderStyle.setFont(shortHeadingFont);
		shortHeaderStyle.setAlignment(HorizontalAlignment.CENTER);

		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
		titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleCellStyle.setFont(titleFont);

		HSSFRow titleRow = sheet.createRow(0);
		titleRow.setRowStyle(titleCellStyle);

		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleCellStyle);
		titleCell.setCellValue("Neel Data Pro It Solutions Pvt. Ltd.");

		HSSFRow headerRow = sheet.createRow(3);

		HSSFCell sno = headerRow.createCell(0);
		sno.setCellStyle(headerCellStyle);
		sno.setCellValue("Sno");

		HSSFCell name = headerRow.createCell(1);
		name.setCellStyle(headerCellStyle);
		name.setCellValue("Name");
		
		HSSFCell designation = headerRow.createCell(2);
		designation.setCellStyle(headerCellStyle);
		designation.setCellValue("Designation");

		HSSFCell bank = headerRow.createCell(3);
		bank.setCellStyle(headerCellStyle);
		bank.setCellValue("Bank");

		HSSFCell ifsc = headerRow.createCell(4);
		ifsc.setCellStyle(headerCellStyle);
		ifsc.setCellValue("IFSC");

		HSSFCell accountNo = headerRow.createCell(5);
		accountNo.setCellStyle(headerCellStyle);
		accountNo.setCellValue("Account No");

		HSSFCell doj = headerRow.createCell(6);
		doj.setCellStyle(headerCellStyle);
		doj.setCellValue("D.O.J");

		HSSFCell month = headerRow.createCell(7);
		month.setCellStyle(headerCellStyle);
		month.setCellValue("Present Month");

		HSSFCell dayInMonth = headerRow.createCell(8);
		dayInMonth.setCellStyle(headerCellStyle);
		dayInMonth.setCellValue("Days In Month");

		HSSFCell presentDays = headerRow.createCell(9);
		presentDays.setCellStyle(headerCellStyle);
		presentDays.setCellValue("Present Days");

		HSSFCell actualPresentDays = headerRow.createCell(10);
		actualPresentDays.setCellStyle(headerCellStyle);
		actualPresentDays.setCellValue(" Absent Days");

		HSSFCell noOfHalfDays = headerRow.createCell(11);
		noOfHalfDays.setCellStyle(headerCellStyle);
		noOfHalfDays.setCellValue(" Half Days");

		HSSFCell saturdaySundayWork = headerRow.createCell(12);
		saturdaySundayWork.setCellStyle(headerCellStyle);
		saturdaySundayWork.setCellValue(" Saturday/Sunday");


		HSSFCell allowedLeaves = headerRow.createCell(13);
		allowedLeaves.setCellStyle(headerCellStyle);
		allowedLeaves.setCellValue("Earn Leaves");
		

		HSSFCell remainingLeaves = headerRow.createCell(14);
		remainingLeaves.setCellStyle(headerCellStyle);
		remainingLeaves.setCellValue("Remaining Leaves");
		

		HSSFCell presentMonthLeave = headerRow.createCell(15);
		presentMonthLeave.setCellStyle(headerCellStyle);
		presentMonthLeave.setCellValue("Taken Leave");
		
		HSSFCell totalPresentDays = headerRow.createCell(16);
		totalPresentDays.setCellStyle(headerCellStyle);
		totalPresentDays.setCellValue("Total Present days");

		HSSFCell ctc = headerRow.createCell(17);
		ctc.setCellStyle(headerCellStyle);
		ctc.setCellValue(" CTC");

		HSSFCell basic = headerRow.createCell(18);
		basic.setCellStyle(headerCellStyle);
		basic.setCellValue("Basic Salary");

		HSSFCell hra = headerRow.createCell(19);
		hra.setCellStyle(headerCellStyle);
		hra.setCellValue("HRA");

		HSSFCell employersPf = headerRow.createCell(20);
		employersPf.setCellStyle(headerCellStyle);
		employersPf.setCellValue("Emplyers P.F");
		
		HSSFCell employePf = headerRow.createCell(21);
		employePf.setCellStyle(headerCellStyle);
		employePf.setCellValue("Employers  P.F");

		HSSFCell employersEsi = headerRow.createCell(22);
		employersEsi.setCellStyle(headerCellStyle);
		employersEsi.setCellValue("EMployerer ESI");
		
		HSSFCell employeEsi = headerRow.createCell(23);
		employeEsi.setCellStyle(headerCellStyle);
		employeEsi.setCellValue("EMployerer ESI");

		HSSFCell mediclame = headerRow.createCell(24);
		mediclame.setCellStyle(headerCellStyle);
		mediclame.setCellValue("Medicalme");

		HSSFCell travelExpensis = headerRow.createCell(25);
		travelExpensis.setCellStyle(headerCellStyle);
		travelExpensis.setCellValue("Travel Expensis");

		HSSFCell bonus = headerRow.createCell(26);
		bonus.setCellStyle(headerCellStyle);
		bonus.setCellValue("Bonus");

		HSSFCell incentives = headerRow.createCell(27);
		incentives.setCellStyle(headerCellStyle);
		incentives.setCellValue("Incentives");

		HSSFCell noOfPaidHoliday = headerRow.createCell(28);
		noOfPaidHoliday.setCellStyle(headerCellStyle);
		noOfPaidHoliday.setCellValue("No. of Paid Sunday + Holiday");

		HSSFCell LWP = headerRow.createCell(29);
		LWP.setCellStyle(headerCellStyle);
		LWP.setCellValue("LWP");

		HSSFCell totalGrossSalaryBasedOnWorkingDays = headerRow.createCell(30);
		totalGrossSalaryBasedOnWorkingDays.setCellStyle(headerCellStyle);
		totalGrossSalaryBasedOnWorkingDays.setCellValue("Amount TO be Paid");

		HSSFCell percentageSalaryToBePaid = headerRow.createCell(31);
		percentageSalaryToBePaid.setCellStyle(headerCellStyle);
		percentageSalaryToBePaid.setCellValue("% Salary to be Paid");

		HSSFCell grossSalaryPaidAfterDeduction = headerRow.createCell(32);
		grossSalaryPaidAfterDeduction.setCellStyle(headerCellStyle);
		grossSalaryPaidAfterDeduction.setCellValue("Total Salary Based on Working Days");
			
		HSSFRow shortHeading = sheet.createRow(1);

		HSSFCell shortHeadingCell = shortHeading.createCell(0);
		shortHeadingCell.setCellStyle(shortHeaderStyle);
		shortHeadingCell.setCellValue("Software Genrated Report .");
		

		int index = 1;
		int startRow = 4;

		List<AccountDetailsAccessBean> accountDetails = accountsRepository.findAll();

		for (AccountDetailsAccessBean accountBean : accountDetails) {
			
			Long basicSalary = 0l, noOfHalDays = 0l, noOfSundaysPresent = 0l, noOfSaturdayPresent = 0l,
					noOfPresentDays = 0l, noOfAbsentDays = 0l, empHra = 0l, empCtc = 0l, empMediclame = 0l,
					empIncentives = 0l, empTravelExpensis = 0l, empBonus = 0l, empPf = 0l, empEsi = 0l,totalRemainningLeaves=0l;

			String joiningDate = "";

			List<TimesheetAccessBean> listOfPresent = timeSheetRepository.findByEmployeeIdAndStatusAndDateLike(
					accountBean.getEmployeeAccessBean().getEmployeeId(), "P", "%" + date + "%");
			
			List<TimesheetAccessBean> listOfLeaveOfMonth = timeSheetRepository.findByEmployeeIdAndStatusAndDateLike(
					accountBean.getEmployeeAccessBean().getEmployeeId(), "L", "%" + date + "%");
			
			List<TimesheetAccessBean> listOfLeave = timeSheetRepository.findByEmployeeIdAndStatusAndDateLike(
					accountBean.getEmployeeAccessBean().getEmployeeId(), "L", "%" + date.split("-")[0] + "%");

			List<TimesheetAccessBean> listOfAbsent = timeSheetRepository.findByEmployeeIdAndStatusAndDateLike(
					accountBean.getEmployeeAccessBean().getEmployeeId(), "A", "%" + date + "%");

			List<TimesheetAccessBean> listOfHalfDay = timeSheetRepository.findByEmployeeIdAndStatusAndDateLike(
					accountBean.getEmployeeAccessBean().getEmployeeId(), "H", "%" + date + "%");
			
			List<TimesheetAccessBean> listOfSundaysPresent = timeSheetRepository
					.findByEmployeeIdAndStatusAndDayLikeAndDateLike(accountBean.getEmployeeAccessBean().getEmployeeId(),
							"P", "%Sunday%", "%" + date + "%");
			List<TimesheetAccessBean> listOfsaturdayPresents = timeSheetRepository
					.findByEmployeeIdAndStatusAndDayLikeAndDateLike(accountBean.getEmployeeAccessBean().getEmployeeId(),
							"P", "%Saturday%", "%" + date + "%");

			noOfSundaysPresent = Long.valueOf(listOfSundaysPresent.size());

			noOfSaturdayPresent = Long.valueOf(listOfsaturdayPresents.size());

			EmployeeCTCAccessBean employeCtc = employeeCTCRepository
					.findByEmployeeId(accountBean.getEmployeeAccessBean().getEmployeeId());
			EmployeeAccessBean employeeAccessBean = accountBean.getEmployeeAccessBean();

			EmpWorkRelationAccessBean desination = empWorkRelationRepository.findDesignationByEmployeeAccessBean(employeeAccessBean);
			
			
			
			if (employeCtc != null) {

				empCtc = employeCtc.getcTC() == null ? Long.valueOf("0") : Long.valueOf(employeCtc.getcTC().toString());
				empMediclame = employeCtc.getMediclame() == null ? Long.valueOf("0")
						: Long.valueOf(employeCtc.getMediclame().toString());
				empIncentives = employeCtc.getIncentives() == null ? Long.valueOf("0")
						: Long.valueOf(employeCtc.getIncentives().toString());

				empTravelExpensis = employeCtc.getTravelExpensis() == null ? Long.valueOf("0")
						: Long.valueOf(employeCtc.getTravelExpensis().toString());
				empBonus = employeCtc.getBonus() == null ? Long.valueOf("0")
						: Long.valueOf(employeCtc.getBonus().toString());
				empPf = employeCtc.getPf() == null ? Long.valueOf("0") : Long.valueOf(employeCtc.getPf().toString());
				empEsi = employeCtc.getEsi() == null ? Long.valueOf("0") : Long.valueOf(employeCtc.getEsi().toString());

				empHra = employeCtc.getHra() == null ? Long.valueOf("0") : employeCtc.getHra();

				joiningDate = employeeCTCRepository.findByEmployeeId(employeeAccessBean.getEmployeeId())
						.getJoiningDate() == null ? "No date"
								: employeeCTCRepository.findByEmployeeId(employeeAccessBean.getEmployeeId())
										.getJoiningDate().toString();
				
				basicSalary = employeCtc.getAnnualBasicSalary();

				noOfAbsentDays = Long.valueOf(listOfAbsent.size());
				noOfPresentDays = Long.valueOf(listOfPresent.size());
				noOfHalDays = Long.valueOf(listOfHalfDay.size());
				
				totalRemainningLeaves = remainingLeaves(employeeAccessBean.getAllowedLeaves(), Long.valueOf(listOfLeave.size()));
				
			}
			HSSFRow row = sheet.createRow(startRow++);

			HSSFCell cellSno = row.createCell(0);
			cellSno.setCellValue(index++);

			HSSFCell cellName = row.createCell(1);
			cellName.setCellValue(accountBean.getHolderName());

			HSSFCell cellDesignation = row.createCell(2);
			cellDesignation.setCellValue(desination.getDesignation());
			
			HSSFCell cellBank = row.createCell(3);
			cellBank.setCellValue(accountBean.getBankName());

			HSSFCell cellIfsc = row.createCell(4);
			cellIfsc.setCellValue(accountBean.getIfscCode());

			HSSFCell cellAccountNo = row.createCell(5);
			cellAccountNo.setCellValue(accountBean.getAccountNumber());

			HSSFCell cellDoj = row.createCell(6);
			cellDoj.setCellValue(joiningDate.split(" ")[0]);

			HSSFCell cellMonth = row.createCell(7);
			cellMonth.setCellValue(monthName.name());

			HSSFCell cellDayInMonth = row.createCell(8);
			cellDayInMonth.setCellValue(daysInMonth);

			HSSFCell cellPresentDays = row.createCell(9);
			cellPresentDays.setCellValue(noOfPresentDays);

			HSSFCell cellActualPresentDays = row.createCell(10);
			cellActualPresentDays.setCellValue(noOfAbsentDays);

			HSSFCell cellNoOfHalfDay = row.createCell(11);
			cellNoOfHalfDay.setCellValue(noOfHalDays);

			HSSFCell cellSaturdaySunday = row.createCell(12);
			cellSaturdaySunday.setCellValue(noOfSaturdayPresent + noOfSundaysPresent);

			HSSFCell cellallowedLeaves = row.createCell(13);
			cellallowedLeaves.setCellValue(employeeAccessBean.getAllowedLeaves());
			

			//Long totalRemainingLeaves=  remainingLeaves( Long.valueOf(employeeAccessBean.getAllowedLeaves()) ,Long.valueOf(listOfLeave.size()));
			
			HSSFCell cellremainigLeaves = row.createCell(14);
			cellremainigLeaves.setCellValue(totalRemainningLeaves);

			HSSFCell cellpresentMonthLeave = row.createCell(15);
			cellpresentMonthLeave.setCellValue(listOfLeaveOfMonth.size());

			
			HSSFCell celltotalPresentdays = row.createCell(16);
			celltotalPresentdays.setCellValue(noOfPresentDays-noOfAbsentDays);

						
			HSSFCell cellCTC = row.createCell(17);
			cellCTC.setCellValue(empCtc);

			HSSFCell cellBasic = row.createCell(18);
			cellBasic.setCellValue(basicSalary);

			HSSFCell cellHra = row.createCell(19);
			cellHra.setCellValue(empHra);

			HSSFCell cellEmployersPf = row.createCell(20);
			cellEmployersPf.setCellValue(empPf/2);
			
			HSSFCell cellEmployePf = row.createCell(21);
			cellEmployePf.setCellValue(empPf/2);

			HSSFCell cellEmployersEsi = row.createCell(22);
			cellEmployersEsi.setCellValue(empCtc*3.25/100);
			
			HSSFCell cellEmployeEsi = row.createCell(23);
			cellEmployeEsi.setCellValue(empCtc*0.75/100);

			HSSFCell cellmediclame = row.createCell(24);
			cellmediclame.setCellValue(empMediclame);

			HSSFCell cellTravelExpensis = row.createCell(25);
			cellTravelExpensis.setCellValue(empTravelExpensis);

			HSSFCell cellBonus = row.createCell(26);
			cellBonus.setCellValue(empBonus);

			HSSFCell cellIncentives = row.createCell(27);
			cellIncentives.setCellValue(empIncentives);

			HSSFCell cellNoOfpaidHolidays = row.createCell(28);
			cellNoOfpaidHolidays.setCellValue(4);

			Long netAmount = netAmount(empPf, empEsi, empMediclame, empTravelExpensis, empBonus, empIncentives, empCtc);
			
			Long payOff = payOff(noOfAbsentDays,noOfHalDays,empCtc,daysInMonth);
			
			HSSFCell cellLwp = row.createCell(29);			
			cellLwp.setCellValue(payOff);

			

			HSSFCell celltotalSalaryBasedOnWorkingDays = row.createCell(30);
			celltotalSalaryBasedOnWorkingDays.setCellValue(netAmount);

			HSSFCell cellPercentageSalaryToBePaid = row.createCell(31);
			cellPercentageSalaryToBePaid.setCellValue("100");

			Long grossSalary = amountBasedOnPresentAndAbsent(empCtc, netAmount, daysInMonth, noOfAbsentDays,
					noOfPresentDays, noOfHalDays, noOfSaturdayPresent, noOfSundaysPresent);

			HSSFCell cellGrossSalaryPaidAfterDeduction = row.createCell(32);
			cellGrossSalaryPaidAfterDeduction.setCellValue(grossSalary);
			
			

		}

		ByteArrayOutputStream byStream = new ByteArrayOutputStream();
		try {
			// Write Data in ByteArrayOutputStream is mandatory to get Proper Results
			workbook.write(byStream);
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "max-age=-1");
			response.setContentType("application/excel");
			response.setHeader("Content-Disposition", "attachment; filename=salary.xls");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(byStream.toByteArray());
			byStream.flush();
			outputStream.flush();
			outputStream.close();
			byStream.close();
			workbook.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	static List<LocalDate> getWeekends(int month) {
		LocalDate firstDateOfTheMonth = LocalDate.now().withMonth(month).with(TemporalAdjusters.firstDayOfMonth());

		return firstDateOfTheMonth.datesUntil(firstDateOfTheMonth.plusMonths(1))
				.filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
				.collect(Collectors.toList());
	}

	@Override
	public Long areaRemboursement(Long travelExpensive, Long bonus, Long incentives, Long tds, Long loan,
			Long annualRefSalary, Long basicSalary) {

		Long amount = travelExpensive + annualRefSalary + bonus + incentives;
		if (loan != null)
			amount = amount - loan;
		return amount;

	}
	
	private Long payOff(Long noOfAbsentDays,Long halfDay,Long ctc,Long noOfDaysInMonth) {			
		return (ctc/noOfDaysInMonth) *noOfAbsentDays+ (ctc/noOfDaysInMonth)/2 *halfDay;
	}

	private Long netAmount(Long pf, Long esi, Long mediclame, Long travelExpensis, Long Bonus, Long Incentives,
			Long ctc) {
		Long netAmount = 0l;

		Long deduction = pf + esi + mediclame;

		Long addition = travelExpensis + Bonus + Incentives;

		netAmount = ctc + addition - deduction;

		return netAmount;
	}

	private Long amountBasedOnPresentAndAbsent(Long ctc, Long netAmount, Long totalDaysInMonth, Long Absent,
			Long Present, Long halfDay, Long saturday, Long sunday) {
		    Long salaryOfOneDay = ctc / totalDaysInMonth;
		  Long abstentDaysalaryDeductiopn= salaryOfOneDay * Absent;
		  Long halfDaySalary=salaryOfOneDay/2;
		  
		  Long halfDaySalaryDeduction=halfDaySalary*halfDay;
		  
		Long afterAbsentsalaryDeduction = netAmount - abstentDaysalaryDeductiopn;

		Long totalSaturadyAndSundaySalary = salaryOfOneDay * (saturday + sunday);

		System.err.println("Saturday and Sunday Salary" + totalSaturadyAndSundaySalary);

		
		
		Long afterHalfDaySalaryDeduction = afterAbsentsalaryDeduction - halfDaySalaryDeduction	+ totalSaturadyAndSundaySalary;
		
		return afterHalfDaySalaryDeduction;

	}

	private Long remainingLeaves(Long totalLeaves,Long totalLeavesTakesByEmploye){
		System.err.println(totalLeaves-totalLeavesTakesByEmploye);
		return totalLeaves-totalLeavesTakesByEmploye;
		
	}
	
}
