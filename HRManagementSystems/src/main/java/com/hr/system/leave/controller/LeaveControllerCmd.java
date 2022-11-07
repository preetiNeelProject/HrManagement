package com.hr.system.leave.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hr.system.common.service.CommonService;
import com.hr.system.employee.bean.EmployeeAccessBean;
import com.hr.system.employee.repository.EmployeeRepository;
import com.hr.system.leave.bean.LeaveTypeAccessBean;
import com.hr.system.leave.bean.LeavesAccessBean;
import com.hr.system.leave.repository.LeaveRepository;
import com.hr.system.leave.repository.LeaveTypeRepository;
import com.hr.system.leave.service.LeaveService;

/**
 * 
 * @author Mitesh Sharma
 * @since 15 Oct 2020
 * @apiNote This controller used for Leave management of employee
 *
 */

@Controller
public class LeaveControllerCmd {

	@Autowired
	private LeaveService leaveService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private LeaveRepository leaveRepo;

	@Autowired
	private EmployeeRepository employeRepo;

	@Autowired
	private LeaveTypeRepository leaveType;
	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view dashboard of all employee and call on jsp
	 *
	 */
	@RequestMapping(value = "/admin/leave/dashboard")
	public String leaveDashboard() {
		return "admin/leave/dashboard";

	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view dashboard of all employee and call on jsp
	 *
	 */

	@RequestMapping(value = "/leave/employe")
	public String applyLeaveForEmaploye(Model model, Authentication authentication) {
		// model.addAttribute(attributeName, attributeValue)
		String name = authentication.getName();

		EmployeeAccessBean employeeAccessBean = employeRepo.findByLogonId(name);
		List<LeavesAccessBean> leavesAccessBean = leaveRepo.findByemployeeId(employeeAccessBean.getEmployeeId());
		
	   List<HashMap<String, String>> leave=new ArrayList<HashMap<String,String>>();
		
		for(LeavesAccessBean accessBean:leavesAccessBean) {
			HashMap<String, String> childMap=new HashMap<String, String>();
			 LeaveTypeAccessBean typeLeave = leaveType.findByLeaveTypeId(accessBean.getLeaveTypeId());
		String logonId = employeRepo.findByEmployeeId(accessBean.getEmployeeId()).getLogonId();
		childMap.put("leaveId",logonId);		
		childMap.put("leaveType",typeLeave.getLeaveName());
		
	   SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyy");
	   
	  
		childMap.put("fromDate", dateFormat.format(accessBean.getFromDate()));		
		childMap.put("toDate",dateFormat.format(accessBean.getToDate()));
		childMap.put("reason",accessBean.getReason());
		childMap.put("approvedBy",accessBean.getApprovedBy());
		childMap.put("status",accessBean.getStatus());
		childMap.put("comments",accessBean.getComments());
			 leave.add(childMap);
		}		
		
		model.addAttribute("leave", leave);
		model.addAttribute("leaveType", leaveService.leavesType());

		return "employee/employeLeaveApply";
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view Leave Log of all employee and call on jsp
	 * @param records used for fetch the all employees leave data from database
	 * @throws ParseException
	 *
	 */
	@RequestMapping(value = "admin/leave/viewLeaveLog", method = RequestMethod.GET, produces = "application/json")
	public ModelAndView viewLeaveLog() throws ParseException {
		Long leaveId = null;
		String date = null;
		Date fdate = null;
		if (date == null) {
			fdate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			
			date = format.format(fdate);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("records", leaveService.leaveRevision(leaveId, date));
		modelAndView.setViewName("admin/leave/viewLeaveLog");
		return modelAndView;
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view Leave Log of all employee and call on jsp
	 * @param records used for fetch the all employees leave data from database to
	 *                date filter
	 *
	 */
	@RequestMapping(value = "admin/leave/viewLeaveLogDate", method = RequestMethod.POST, produces = "application/json")
		public ModelAndView viewLeaveLogDate(@RequestParam("filterDate") String date, HttpServletRequest req,
			ModelMap model, RedirectAttributes flashAttributes) {
		Long leaveId = null;
		List<Map<String, Object>> records = leaveService.leaveRevision(leaveId, date);
		if (records.isEmpty())
			flashAttributes.addFlashAttribute("msg", "Sorry! No records found...");
		else
			model.addAttribute("data", records);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("records", leaveService.leaveRevision(leaveId, date));
		modelAndView.setViewName("admin/leave/viewLeaveLog");
		return modelAndView;
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for upload Leave excel sheet on viewLeaveLog.jsp
	 * @param csv used for get the all employees leave data in excel sheet from jsp
	 *
	 */
	/*
	 * @RequestMapping(value = "admin/leave/uploadLeave", method =
	 * RequestMethod.POST) public String uploadBulk(HttpServletRequest
	 * request, @RequestParam("csv") MultipartFile csv, RedirectAttributes
	 * flashAttributes) throws IOException { Long id = 0L; String flage =
	 * commonService.uploadFiles(csv, "timesheet", id); if (flage.equals("Success"))
	 * flashAttributes.addFlashAttribute("msg",
	 * "All files have been uploaded successfully."); else {
	 * flashAttributes.addFlashAttribute("err", "1"); if (flage.equals("Failed"))
	 * flashAttributes.addFlashAttribute("msg", "Files couldn't be uploaded!"); else
	 * flashAttributes.addFlashAttribute("msg",
	 * "All files can't be uploaded successfully!"); } return
	 * "redirect:admin/leave/viewLeaveLog"; }
	 */

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for upload Leave excel sheet for One day for Update
	 *          Purpose viewLeaveLog.jsp
	 * @param csv used for get the all employees leave data in excel sheet from jsp
	 *
	 */
	/*
	 * @RequestMapping(value = "admin/leave/updateLeaveByUpload", method =
	 * RequestMethod.POST) public String updateLeaveByUpload(HttpServletRequest
	 * request, @RequestParam("csv") MultipartFile csv,
	 * 
	 * @RequestParam("timesheeId") Long timesheet, RedirectAttributes
	 * flashAttributes) throws IOException { String flage =
	 * commonService.uploadFiles(csv, "timesheet", timesheet); if
	 * (flage.equals("Success")) flashAttributes.addFlashAttribute("msg",
	 * "All files have been uploaded successfully."); else {
	 * flashAttributes.addFlashAttribute("err", "1"); if (flage.equals("Failed"))
	 * flashAttributes.addFlashAttribute("msg", "Files couldn't be uploaded!"); else
	 * flashAttributes.addFlashAttribute("msg",
	 * "All files can't be uploaded successfully!"); } return
	 * "redirect:admin/leave/viewLeaveLog"; }
	 */

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for download Leave excel sheet on viewLeaveLog.jsp
	 * @param records used for fetch the all employees leave data from database in
	 *                excel form datewise
	 *
	 */
	@RequestMapping(value = "admin/leave/exportData", method = RequestMethod.GET)
	public String exportData(HttpServletRequest rep, HttpServletResponse res, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, RedirectAttributes flashAttributes) {
		
		Long timesheetId = null;
		leaveService.exportData(res, flashAttributes, fromDate, toDate, timesheetId);
		return null;
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for download Leave excel sheet of a employeeon
	 *          viewLeaveLog.jsp
	 * @param records used for fetch of an employes leave data from database in
	 *                excel form datewise
	 *
	 */
	@RequestMapping(value = "admin/leave/exportDataEmployee", method = RequestMethod.GET)
	public String exportData(HttpServletRequest rep, HttpServletResponse res, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("timeshId") Long timesheetId,
			RedirectAttributes flashAttributes) {
		
		leaveService.exportData(res, flashAttributes, fromDate, toDate, timesheetId);
		return null;
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for delete Leave of an employee viewLeaveLog.jsp
	 * @param timesheetId only
	 *
	 */
	@RequestMapping(value = "admin/leave/deleteLeave/{timesheetId}", method = RequestMethod.GET)
	public String deleteLeave(HttpServletRequest rep, HttpServletResponse res,
			@PathVariable("timesheetId") Long timesheetId, RedirectAttributes flashAttributes) {
		leaveService.deleteLeave(res, flashAttributes, timesheetId);
		flashAttributes.addFlashAttribute("msg", "Delete successfully!");
		return "redirect:admin/leave/viewLeaveLog";
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view Leave Log of all employee and call on jsp
	 * @param records used for fetch the all employees leave data from database
	 * @throws ParseException
	 *
	 */
	@RequestMapping(value = "admin/leave/viewEmpLeaveLog/{leaveId}", method = RequestMethod.GET, produces = "application/json")
	public ModelAndView viewEmpLeaveLog(@PathVariable("leaveId") Long leaveId) throws ParseException {
		String date = "";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("records", leaveService.leaveRevision(leaveId, date));
		modelAndView.setViewName("admin/leave/viewEmpLeaveLog");
		return modelAndView;
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view Applied Leave Log of all employee and call
	 *          on jsp
	 * @param records used for fetch the all employees applied leave data from
	 *                database
	 * @throws ParseException
	 *
	 */
	@RequestMapping(value = "admin/leave/appliedLeaveLog", method = RequestMethod.GET, produces = "application/json")
	public ModelAndView appliedLeaveLog() throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("leaveType", leaveService.leavesType());
		modelAndView.addObject("records", leaveService.appliedLeaveLogs("Pending"));
		modelAndView.setViewName("admin/leave/appliedLeaveLog");
		return modelAndView;
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for add apply Leave from form to database
	 *
	 */
	@RequestMapping(value = "admin/leave/applyLeave", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Long applyLeave(@RequestBody LeavesAccessBean leavesAccessBean,
			RedirectAttributes flashAttributes) {
		LeavesAccessBean leaAB = leaveService.applyLeave(leavesAccessBean);
		return leaAB.getLeavesId();
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for accept apply Leave request and change the
	 *          status in database(Pending->Accept)
	 *
	 */
	@RequestMapping(value = "admin/leave/acceptLeave/{leavesId}", method = RequestMethod.GET, produces = "application/json")
	public String acceptLeave(@PathVariable("leavesId") Long leavesId, Model model) {
		LeavesAccessBean leaAB = leaveService.acceptLeave(leavesId);

		String message = leaAB != null ? "Succes" : "Failed";
		
		model.addAttribute("msg", message);

		return "forward:/admin/leave/appliedLeaveLog";
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view approved Leave Log of all employee and
	 *          call on jsp
	 * @param records used for fetch the all employees approved leave data from
	 *                database
	 * @throws ParseException
	 *
	 */
	@RequestMapping(value = "admin/leave/approvedLeaveLog", method = RequestMethod.GET, produces = "application/json")
	public ModelAndView approvedLeaveLog() throws ParseException {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("leaveType", leaveService.leavesType());
		modelAndView.addObject("records", leaveService.appliedLeaveLogs("Approved"));
		modelAndView.setViewName("admin/leave/approvedLeaveLog");
		return modelAndView;
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view rejected Leave Log of all employee and
	 *          call on jsp
	 * @param records used for fetch the all employees rejected leave data from
	 *                database
	 * @throws ParseException
	 *
	 */
	@RequestMapping(value = "admin/leave/rejectedLeaveLog", method = RequestMethod.GET, produces = "application/json")
	public ModelAndView rejectedLeaveLog() throws ParseException {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("leaveType", leaveService.leavesType());
		modelAndView.addObject("records", leaveService.appliedLeaveLogs("Rejected"));
		modelAndView.setViewName("admin/leave/rejectedLeaveLog");
		return modelAndView;
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for add apply Leave from form to database
	 *
	 */
	@RequestMapping(value = "admin/leave/rejectLeave", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Long rejectLeave(@RequestBody LeavesAccessBean leavesAccessBean) {
		LeavesAccessBean leaAB = leaveService.rejectLeave(leavesAccessBean);
		return leaAB.getLeavesId();
	}
}
