package com.hr.system.common.controller;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hr.system.authentication.bean.CityState;
import com.hr.system.authentication.service.MyUserDetailsService;
import com.hr.system.common.service.CommonService;
import com.hr.system.company.bean.NotificationAccessBean;
import com.hr.system.company.repository.StateCity;
import com.hr.system.company.service.CompanyService;
import com.hr.system.config.JwtTokenUtil;
import com.hr.system.employee.repository.EmployeeRepository;

/**
 * 
 * @author Mitesh Sharma
 * @since 25 Sep 2020
 * @apiNote This controller used for Attendance management of employee
 *
 */

@Controller

public class CommonControllerCmd {

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CommonService commonservice;
	
	@Autowired
	CompanyService companyService;

	@GetMapping("index")
	public String index() {
		return "index";
	}
	@Autowired
	private StateCity stateCity;

	@GetMapping("login")
	public String login() {
		return "login";
	}
@GetMapping("/403error")
public String error() {
	return "403error";
}
	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for authenticate Login of all employee and call a
	 *          jsp
	 *
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView createAuthenticationToken(String username, String password) throws Exception {

		/*
		 * "Employee Id ::" + username);
		 * "password ::" + password); //
		 * authenticate(empIdNumber,password);
		 * 
		 * UserDetails details = myUserDetailsService.loadUserByUsername(username);
		 * final String token = jwtTokenUtil.generateToken(details);
		 * 
		 * "JwtResponse :: " + token);
		 * 
		 * Map<String,String > map = new HashMap<String, String>();
		 * map.put("token",token); map.put("username", username);
		 */

		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/company/department");
		return mv;

	}

	/*
	 * private void authenticate(String empIdNumber, String password) throws
	 * Exception { try { UsernamePasswordAuthenticationToken up = new
	 * UsernamePasswordAuthenticationToken(empIdNumber, password); //
	 * authenticationManager.authenticate(up); } catch (DisabledException e) {
	 * "user is disable.."); throw new Exception("USER_DISABLED",
	 * e); } catch (BadCredentialsException e) {
	 * "invalid credentials..."); throw new
	 * Exception("INVALID_CREDENTIALS", e); } }
	 */
	@RequestMapping(value = "/commonView/dashboard")
	public String dash() {
		return "commonView/dashboard";
	}

	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for authenticate Logout of all employee and call a
	 *          jsp
	 *
	 */
	/*
	 * @RequestMapping("/logout") public String logout( HttpSession session) {
	 * session.invalidate(); return "logout"; }
	 */
	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for view dashboard and call a jsp
	 *
	 *//*
		 * @RequestMapping(value = "/dashboard", method = RequestMethod.GET) public
		 * String dashboard() { return "commonView/dashboard"; }
		 */

	
	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for Modification of all date from database
	 *
	 * @Param date (Date YYYY-MM-DD) convert date (Date DD-MM-YYY)
	 */
	//@RequestMapping("logout-success")
	public String dateFromDatabase(Date date) {
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		if(date==null) {
			Calendar calobj = Calendar.getInstance();
			Date todayDate=calobj.getTime();
			
			date=todayDate;
		}
		String dates = format.format(date);
		return dates;
	}
	
	
	/**
	 * 
	 * @author Mitesh Sharma
	 * @apiNote This method used for add notification , this method is global
	 *
	 */
	public boolean addNotifications(Long employeeId,String activity) {
		
		NotificationAccessBean notificationAccessBean=new NotificationAccessBean();
		notificationAccessBean.setEmployeeId(employeeId);
		notificationAccessBean.setActivity(activity);
		long millis=System.currentTimeMillis();  
		java.sql.Date date=new java.sql.Date(millis);  
		
		notificationAccessBean.setDate(date);
		notificationAccessBean.setTime(java.time.LocalTime.now());
		boolean flag=commonservice.addNotification(notificationAccessBean);
		return true;
	}


/**
 * 
 * @author Mitesh Sharma
 * @apiNote This method used for List of notifications and view and call a jsp
 *
 */
@RequestMapping(value = "/notifications", method = RequestMethod.GET)
public ModelAndView Notific() {
	ModelAndView modelAndView=new ModelAndView();
	modelAndView.addObject("notifications", companyService.findAllNotitfications(true));
	modelAndView.setViewName("commonView/notifications");
	return modelAndView;
}


/**
 * 
 * @author Mitesh Sharma
 * @apiNote This method used for List of notifications and view and call a jsp
 *
 */
@RequestMapping(value = "/commonView/notifications", method = RequestMethod.GET)
public ModelAndView allNotifictions() {
	ModelAndView modelAndView=new ModelAndView();
	modelAndView.addObject("notifications", companyService.findAllNotitfications(false));
	modelAndView.setViewName("commonView/notificationsDetails");
	return modelAndView;
}

@PostMapping(value = "/getCity")
@ResponseBody
public List<CityState> getCityByState(@RequestParam("id") Integer id,HttpServletResponse response) throws IOException {
	    CityState cityState = stateCity.findById(id).orElseThrow(()-> new RuntimeException("Id NOt Found"));
		List<CityState> cities = stateCity.findBystate(cityState.getState()).orElseThrow(()-> new RuntimeException("State not found"));
		   return cities;
	
	
	
	
}
}

