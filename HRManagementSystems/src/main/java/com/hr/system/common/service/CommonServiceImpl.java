package com.hr.system.common.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hr.system.attendance.bean.AttendanceAccessBean;
import com.hr.system.attendance.bean.TimesheetAccessBean;
import com.hr.system.attendance.repository.AttendanceRepository;
import com.hr.system.attendance.repository.TimesheetRepository;
import com.hr.system.common.files.FileUtils;
import com.hr.system.common.files.Keys;
import com.hr.system.common.files.Utils;
import com.hr.system.company.bean.JobTitleAccessBean;
import com.hr.system.company.bean.NotificationAccessBean;
import com.hr.system.company.repository.JobTitleRepository;
import com.hr.system.company.repository.NotificationRepository;
import com.hr.system.employee.bean.AccountDetailsAccessBean;
import com.hr.system.employee.bean.AddressAccessBean;
import com.hr.system.employee.bean.EmployeeAccessBean;
import com.hr.system.employee.repository.AccountDetailsRepository;
import com.hr.system.employee.repository.AddressRepository;
import com.hr.system.employee.repository.EmployeeRepository;
import com.hr.system.employee.repository.WorkLocationRepository;
import com.hr.system.payroll.bean.EmployeeCTCAccessBean;
import com.hr.system.payroll.controller.CTCCalulatorControllerCmd;
import com.hr.system.payroll.repository.EmployeeCTCRepository;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	EmployeeCTCRepository employeeCTCRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	TimesheetRepository timesheetRepository;

	@Autowired
	JobTitleRepository jobTitleRepository;

	@Autowired
	WorkLocationRepository workLocationRepository;

	@Autowired
	AccountDetailsRepository accountDetailsRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	AttendanceRepository attendanceRepository;

	@Autowired
	CTCCalulatorControllerCmd cmd;

	@Autowired
	CommonServiceImpl commonServiceImpl;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private Utils utils;

	@Autowired
	private Keys keys;

	@Autowired
	private TimesheetRepository timeRepository;

	@Transactional
	public void savePreviousSalary(Optional<EmployeeCTCAccessBean> empSalary) {

		String historyQuery = "Insert into ctchistory(EmployeeId,Name,JoiningDate,IncrementDate,AnnualBasicSalary,AnnualFlexibleBenifits,AnnualRefSalary"
				+ ",PF,HRA,CONVEY,ESI,Gratuity,CTC,newCtc,Comments) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		entityManager.createNativeQuery(historyQuery).setParameter(1, empSalary.get().getEmployeeId())
				.setParameter(2, empSalary.get().getName()).setParameter(3, empSalary.get().getJoiningDate())
				.setParameter(4, empSalary.get().getIncrementDate())
				.setParameter(5, empSalary.get().getAnnualBasicSalary())
				.setParameter(6, empSalary.get().getAnnualFlexibleBenifits())
				.setParameter(7, empSalary.get().getAnnualRefSalary()).setParameter(8, empSalary.get().getPf())
				.setParameter(9, empSalary.get().getHra()).setParameter(10, empSalary.get().getConvey())
				.setParameter(11, empSalary.get().getEsi()).setParameter(12, empSalary.get().getGratuity())
				.setParameter(13, empSalary.get().getcTC()).setParameter(14, empSalary.get().getNewCtc())
				.setParameter(15, empSalary.get().getComments()).executeUpdate();
	}

	public StringBuilder uploadFiles(MultipartFile csv, String type, Long timesheetId) throws IOException {
		
		String[] csvHeader = utils.getHeader(type);
		BufferedReader csvReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csv.getBytes())));

		StringBuilder errors = new StringBuilder();

		CSVParser csvParser = new CSVParser(csvReader,
				CSVFormat.DEFAULT.withHeader(csvHeader).withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

		List<CSVRecord> csvRecordList = csvParser.getRecords();

		try {

			for (CSVRecord csvRecord : csvRecordList) {
				Integer sno = Integer.parseInt(csvRecord.get("S.No"));
				String employeName = csvRecord.get("Logon Id");
				String date = csvRecord.get("Date");
				String day = csvRecord.get("Day");
				String checkIn = csvRecord.get("Check In");
				String checkOut = csvRecord.get("Check Out");
				String roundTime = csvRecord.get("Round Time");
				String status = csvRecord.get("Status");

				EmployeeAccessBean employe = employeeRepository.findEmployeeIdByLogonId(employeName);

				if(sno.equals(0)||employeName.trim().equals("")||
						date.trim().equals("")||day.trim().equals("")||
						roundTime.trim().equals("")||
						status.trim().equals("")||
						checkOut.trim().equals("")||
						checkIn.trim().equals("")) {
					errors.append("\n Provided Data may be Null at  S.No :"+sno);
					continue;
				}			
				
				if (employe == null) {
					errors.append("Cannot Find the Employee with Employe Id : '" +  employeName + "' and S.No :"+sno);
					continue;
				}

				if(!date.contains("/")  && !date.contains("-")) {
					errors.append("\n Date Format Is not Correct Where Employe Id : " +employeName +" and S.No :"+sno);
					continue;
				}	
				String[] split = null;
				if(date.contains("/")) {
					 split = date.split("/");	
					
				}
				if(date.contains("-")) {
					
					 split = date.split("-");	
				}
				
				
				String dateAfterSplit=split[2]+"-"+split[1]+"-"+split[0];
				TimesheetAccessBean isEmployeAlreadyRegistered = timeRepository.findByEmployeeIdAndDate(employe.getEmployeeId(), dateAfterSplit);
				if(isEmployeAlreadyRegistered != null) {
					errors.append("\n The Employe With Employe Id  : " + employeName +" and S.No :"+sno+" Alredy Registered");
					continue;
				}				
				
				TimesheetAccessBean timesheetAccessBean = new TimesheetAccessBean();
				timesheetAccessBean.setEmployeeId(employe.getEmployeeId());				
				timesheetAccessBean.setDate(dateAfterSplit);
				timesheetAccessBean.setDay(day);
				timesheetAccessBean.setCheckIn(checkIn);
				timesheetAccessBean.setCheckOut(checkOut);
				timesheetAccessBean.setRawTime("0");// ???????
				timesheetAccessBean.setRoundTime(roundTime);
				timesheetAccessBean.setStatus(status);
				timesheetAccessBean.setHolidayId(1l);				
				uploadTimesheet(timesheetAccessBean);
				
				}

		} catch (IllegalArgumentException exception) {
			errors.append("\t Header does not match");
			csvParser.close();
			exception.printStackTrace();
		}
		csvParser.close();
		return errors;
	}

	private void uploadTimesheet(TimesheetAccessBean timesheetAccessBean) {
		timesheetRepository.save(timesheetAccessBean);
	}

	@Override
	public boolean addNotification(NotificationAccessBean notificationAccessBean) {
		notificationRepository.save(notificationAccessBean);
		return true;
	}

	@Transactional
	public void salarySlip(Long employeeCTCID, Long month, HttpServletRequest request, HttpServletResponse response) {

		EmployeeCTCAccessBean employeeCTCAccessBean = employeeCTCRepository.getOne(employeeCTCID);

		FileOutputStream file = null;
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.BLUE);
		Font blackFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
		Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
		Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
		

		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					keys.getTimesheetDownloadLocation() + employeeCTCAccessBean.getName() + "_Salary-Slip" + ".pdf"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}

		document.open();

		try {
			file = new FileOutputStream(
					new File(keys.getTimesheetDownloadLocation() + employeeCTCAccessBean.getName() + ".pdf"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		Image image1 = null;
		try {
			
			image1 = Image
					.getInstance(request.getServletContext().getRealPath("\\") + "\\staticResources\\images\\logo.png");
		} catch (BadElementException e4) {
			e4.printStackTrace();
		} catch (MalformedURLException e4) {
			e4.printStackTrace();
		} catch (IOException e4) {
			e4.printStackTrace();
		}
		image1.setAlignment(Element.ALIGN_CENTER);
		image1.scaleAbsolute(520, 100);
		// Add to document
		try {
			document.add(image1);
		} catch (DocumentException e3) {
			e3.printStackTrace();
		}

		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 3);
		// Lets write a big header
		preface.add(new Paragraph("NEEL DATA PRO IT SOLUTIONS PVT LTD", catFont));
		addEmptyLine(preface, 0);
		preface.add(
				new Paragraph("LOGIX OFFICE TOWER UNIT 804,FLOOR 8  SECTOR 32 NOIDA ,PIN CODE 201301 U.P", subFont));

		addEmptyLine(preface, 0);

		preface.add(new Paragraph("MONTHLY SALARY SLIP", blackFont));

		addEmptyLine(preface, 2);
		
		// Creating a table
		float[] pointColumnWidths = { 200F, 200F, 200F, 200F };

		PdfPTable table = new PdfPTable(pointColumnWidths);

		EmployeeAccessBean employeeAccessBean = employeeRepository
				.findByEmployeeId(employeeCTCAccessBean.getEmployeeId());
		AddressAccessBean addressAccessBean = addressRepository.findByEmployeeAccessBean(employeeAccessBean);
		JobTitleAccessBean jobTitleAccessBean = jobTitleRepository.findByJobTitleId(addressAccessBean.getJobTitleId());
		AccountDetailsAccessBean accountDetailsAccessBean = accountDetailsRepository
				.findByEmployeeAccessBean(employeeAccessBean);

		if (month != 0) {
			String m = String.format("%02d", month);
			Query query = entityManager.createNativeQuery("SELECT COUNT(date) FROM timesheet WHERE " + m
					+ "= SUBSTRING(Date,6,2) AND employeeId=" + employeeCTCAccessBean.getEmployeeId() + ";");
			int count = ((Number) query.getSingleResult()).intValue();
			EmployeeCTCAccessBean data = cmd.monthlySalaryCalculate(employeeCTCAccessBean.getcTC(), count,
					accountDetailsAccessBean.isPf(), accountDetailsAccessBean.isEsi());
			Long deducations = data.getcTC() - data.getAnnualRefSalary();
			table = commonServiceImpl.createCell("EMP ID", subFont, table);
			table = commonServiceImpl.createCell(employeeAccessBean.getEmployeeCode(), subFont, table);
			table = commonServiceImpl.createCell("EMPLOYEE NAME", subFont, table);
			table = commonServiceImpl.createCell(addressAccessBean.getFirstName(), subFont, table);
			table = commonServiceImpl.createCell("DESIGNATION", subFont, table);
			table = commonServiceImpl.createCell(jobTitleAccessBean.getJobTitle(), subFont, table);
			table = commonServiceImpl.createCell("", subFont, table);
			table = commonServiceImpl.createCell("", subFont, table);
			table = commonServiceImpl.createCell("PF no.", subFont, table);
			table = commonServiceImpl.createCell("0", subFont, table);
			table = commonServiceImpl.createCell("HDFC Bank A/c no.", subFont, table);
			if (accountDetailsAccessBean.getAccountNumber() != null)
				table = commonServiceImpl.createCell(accountDetailsAccessBean.getAccountNumber().toString(), subFont,
						table);
			else
				table = commonServiceImpl.createCell("-", subFont, table);
			table = commonServiceImpl.createCell("CTC", subFont, table);
			table = commonServiceImpl.createCell(employeeCTCAccessBean.getcTC().toString(), subFont, table);
			table = commonServiceImpl.createCell("Date Of Joining", subFont, table);
			table = commonServiceImpl.createCell(
					String.valueOf((employeeCTCAccessBean.getJoiningDate().toString().split(" ")[0])), subFont, table);

			table = commonServiceImpl.createCell("Working Day in a month", subFont, table);
			table = commonServiceImpl.createCell("30", subFont, table);
			table = commonServiceImpl.createCell("Present day in a month", subFont, table);
			table = commonServiceImpl.createCell(Integer.toString(count), subFont, table);

			table = commonServiceImpl.createCell2("EARNING", smallBold, table);
			table = commonServiceImpl.createCell2("", smallBold, table);
			table = commonServiceImpl.createCell2("DEDUCTION", smallBold, table);
			table = commonServiceImpl.createCell2("", smallBold, table);
			table = commonServiceImpl.createCell1("Basic", smallBold, table);
			table = commonServiceImpl.createCell1(data.getAnnualBasicSalary().toString(), smallBold, table);
			table = commonServiceImpl.createCell1("EPF", smallBold, table);
			table = commonServiceImpl.createCell1(data.getPf().toString(), smallBold, table);
			table = commonServiceImpl.createCell1("HRA", smallBold, table);
			table = commonServiceImpl.createCell1(data.getHra().toString(), smallBold, table);

			table = commonServiceImpl.createCell1(" My EsiESIC", smallBold, table);
			table = commonServiceImpl.createCell1(data.getEsi().toString(), smallBold, table);
			table = commonServiceImpl.createCell1("Transport allw.", smallBold, table);
			table = commonServiceImpl.createCell1(data.getConvey().toString(), smallBold, table);
			table = commonServiceImpl.createCell1("Tax on salary", smallBold, table);
			table = commonServiceImpl.createCell1("", smallBold, table);
			table = commonServiceImpl.createCell1("Spl. Allow.", smallBold, table);
			table = commonServiceImpl.createCell1("--", smallBold, table);
			table = commonServiceImpl.createCell1("Other's", smallBold, table);
			table = commonServiceImpl.createCell1(data.getGratuity().toString(), smallBold, table);
			table = commonServiceImpl.createCell1("Loan/advance", smallBold, table);
			table = commonServiceImpl.createCell1("0", smallBold, table);
			table = commonServiceImpl.createCell1("Total Gross Salary", smallBold, table);
			table = commonServiceImpl.createCell1(data.getcTC().toString(), smallBold, table);
			table = commonServiceImpl.createCell1("Total Deduction", smallBold, table);
			table = commonServiceImpl.createCell1(deducations.toString(), smallBold, table);
			table = commonServiceImpl.createCell1("", smallBold, table);
			table = commonServiceImpl.createCell1("", smallBold, table);
			table = commonServiceImpl.createCell2("Take home  salary", smallBold, table);
			table = commonServiceImpl.createCell2(data.getAnnualRefSalary().toString(), smallBold, table);

			try {
				document.add(preface);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}

			try {
				document.add(table);
			} catch (DocumentException e2) {
				e2.printStackTrace();
			}
		} else {
			Long deducations = employeeCTCAccessBean.getEsi() + employeeCTCAccessBean.getPf();
			table = commonServiceImpl.createCell("EMP ID", subFont, table);
			table = commonServiceImpl.createCell(employeeAccessBean.getEmployeeCode(), subFont, table);
			table = commonServiceImpl.createCell("EMPLOYEE NAME", subFont, table);
			table = commonServiceImpl.createCell(addressAccessBean.getFirstName(), subFont, table);
			table = commonServiceImpl.createCell("DESIGNATION", subFont, table);
			table = commonServiceImpl.createCell(jobTitleAccessBean.getJobTitle(), subFont, table);
			table = commonServiceImpl.createCell("PF no.", subFont, table);
			table = commonServiceImpl.createCell("0", subFont, table);
			table = commonServiceImpl.createCell("HDFC Bank A/c no.", subFont, table);
			if (accountDetailsAccessBean.getAccountNumber() != null)
				table = commonServiceImpl.createCell(accountDetailsAccessBean.getAccountNumber().toString(), subFont,
						table);
			else
				table = commonServiceImpl.createCell("-", subFont, table);
			table = commonServiceImpl.createCell("CTC", subFont, table);
			table = commonServiceImpl.createCell(employeeCTCAccessBean.getcTC().toString(), subFont, table);
			table = commonServiceImpl.createCell("Date Of Joining", subFont, table);
			String valueOf[] = employeeCTCAccessBean.getJoiningDate().toString().split(" ");

			table = commonServiceImpl.createCell(valueOf[2] + "/" + valueOf[0] + "/" + valueOf[1], subFont, table);
			table = commonServiceImpl.createCell("", subFont, table);
			table = commonServiceImpl.createCell("", subFont, table);
			table = commonServiceImpl.createCell("Working Day in a month", subFont, table);
			table = commonServiceImpl.createCell("30", subFont, table);
			table = commonServiceImpl.createCell("Present day in a month", subFont, table);
			table = commonServiceImpl.createCell("30", subFont, table);

			table = commonServiceImpl.createCell2("EARNING", smallBold, table);
			table = commonServiceImpl.createCell2("", smallBold, table);
			table = commonServiceImpl.createCell2("DEDUCTION", smallBold, table);
			table = commonServiceImpl.createCell2("", smallBold, table);
			table = commonServiceImpl.createCell1("Basic", smallBold, table);
			table = commonServiceImpl.createCell1(employeeCTCAccessBean.getAnnualBasicSalary().toString(), smallBold,
					table);
			table = commonServiceImpl.createCell1("Eomployerer PF", smallBold, table);
			table = commonServiceImpl.createCell1(String.valueOf(employeeCTCAccessBean.getPf() / 2), smallBold, table);

			table = commonServiceImpl.createCell1("", smallBold, table);
			table = commonServiceImpl.createCell1("", smallBold, table);

			table = commonServiceImpl.createCell1("Eomploye PF", smallBold, table);
			table = commonServiceImpl.createCell1(String.valueOf(employeeCTCAccessBean.getPf() / 2), smallBold, table);

			table = commonServiceImpl.createCell1("HRA", smallBold, table);
			table = commonServiceImpl.createCell1(employeeCTCAccessBean.getHra().toString(), smallBold, table);
			table = commonServiceImpl.createCell1("Employerer ESI", smallBold, table);
			table = commonServiceImpl.createCell1(String.valueOf((employeeCTCAccessBean.getcTC() * 3.25) / 100),
					smallBold, table);

			table = commonServiceImpl.createCell1("Transport allw.", smallBold, table);
			table = commonServiceImpl.createCell1(employeeCTCAccessBean.getConvey().toString(), smallBold, table);
			table = commonServiceImpl.createCell1("Employe ESI", smallBold, table);
			table = commonServiceImpl.createCell1(String.valueOf((employeeCTCAccessBean.getcTC() * 0.75) / 100),
					smallBold, table);
			
			table = commonServiceImpl.createCell1("Spl. Allow.", smallBold, table);
			table = commonServiceImpl.createCell1("--", smallBold, table);
			
			table = commonServiceImpl.createCell1("Tax on salary", smallBold, table);
			table = commonServiceImpl.createCell1(employeeCTCAccessBean.getTds().toString(),
					smallBold, table);

			table = commonServiceImpl.createCell1("Other's", smallBold, table);
			table = commonServiceImpl.createCell1(employeeCTCAccessBean.getAnnualFlexibleBenifits().toString(),
					smallBold, table);
			table = commonServiceImpl.createCell1("Loan/advance", smallBold, table);
			table = commonServiceImpl.createCell1("0", smallBold, table);
			table = commonServiceImpl.createCell1("Total Gross Salary", smallBold, table);
			table = commonServiceImpl.createCell1(employeeCTCAccessBean.getcTC().toString(), smallBold, table);
			table = commonServiceImpl.createCell1("Total Deduction", smallBold, table);
			table = commonServiceImpl.createCell1(deducations.toString(), smallBold, table);
			table = commonServiceImpl.createCell1("", smallBold, table);
			table = commonServiceImpl.createCell1("", smallBold, table);
			table = commonServiceImpl.createCell2("Take home  salary", smallBold, table);
			table = commonServiceImpl.createCell2(employeeCTCAccessBean.getAnnualRefSalary().toString(), smallBold,
					table);

			try {
				document.add(preface);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}

			try {
				document.add(table);
			} catch (DocumentException e2) {
				e2.printStackTrace();
			}

		}

		Paragraph preface2 = new Paragraph();
		preface2.add(new Paragraph("", blackFont));
		addEmptyLine(preface2, 1);

		Paragraph preface1 = new Paragraph();
		preface1.add(new Paragraph("Salary slip Systems generated signature not required", blackFont));
		addEmptyLine(preface1, 10);
		try {
			document.add(preface1);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		Image image2 = null;
		try {
			
			image2 = Image
					.getInstance(request.getServletContext().getRealPath("\\") + "\\staticResources\\images\\base.png");
		} catch (BadElementException e4) {
			e4.printStackTrace();
		} catch (MalformedURLException e4) {
			e4.printStackTrace();
		} catch (IOException e4) {
			e4.printStackTrace();
		}
		image2.setAlignment(Element.ALIGN_CENTER);
		image2.scaleAbsolute(470, 75);

		// Add to document
		try {
			document.add(image2);
		} catch (DocumentException e3) {
			e3.printStackTrace();
		}

		// Start a new page
		document.newPage();

		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		document.close();

	}

	private void monthlySalaryCalculate(Long getcTC, int count) {
		// TODO Auto-generated method stub

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public PdfPTable createCell(String str, Font f, PdfPTable table) {

		PdfPCell cell = new PdfPCell(new Phrase(str, f));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		return table;

	}

	public PdfPTable createCell1(String str, Font f, PdfPTable table) {
		PdfPCell cell = new PdfPCell(new Phrase(str, f));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		return table;

	}

	public PdfPTable createCell2(String str, Font f, PdfPTable table) {

		PdfPCell cell = new PdfPCell(new Phrase(str, f));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(cell);
		return table;

	}

}
