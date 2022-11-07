package com.hr.system.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.hr.system.company.bean.AnnouncementAccessBean;
import com.hr.system.company.repository.AnnouncementRepository;
import com.hr.system.employee.bean.EmployeeAccessBean;
import com.hr.system.employee.repository.EmployeeRepository;
@Component
public class CustomFilter extends GenericFilterBean{

    @Autowired
	private AnnouncementRepository announcementRepo;
	
    
   
    static String genrateRandomCchar() {
    	String string = UUID.randomUUID().toString();
    	return string;
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		List<AnnouncementAccessBean> announcementBydate = announcementRepo.findByapplyDate(new Date());
		request.setAttribute("totalAnnoucement", announcementBydate.size());
		
		chain.doFilter(request, response);
		
	}

}
