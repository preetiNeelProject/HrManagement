package com.hr.system.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hr.system.employee.bean.EmployeeAccessBean;
import com.hr.system.employee.repository.EmployeeRepository;

@Component

public class LoginSuccesHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private EmployeeRepository employeRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Optional<? extends GrantedAuthority> authorityOfUser = authorities.stream().findFirst();
		System.err.println("Authority is :" + authorityOfUser.get());

		String authority = authorityOfUser.stream().findFirst()
				.orElseThrow(() -> new UsernameNotFoundException("User Does Not Have Any Authority")).getAuthority();

//		String ls = UUID.randomUUID().toString() + UUID.randomUUID().toString();

		String LogonId = SecurityContextHolder.getContext().getAuthentication().getName();
		EmployeeAccessBean logedInUser = employeRepo.findByLogonId(LogonId);

		HttpSession session = request.getSession(false);
		
		
	
		
		Long impId = logedInUser.getEmployeeId();
		session.setAttribute("impId", impId);
		String redirectUrl = "/403error";

		if (authority.trim().equalsIgnoreCase("ADMIN"))
			redirectUrl = "/admin/company/dashboard";
		if (authority.trim().equalsIgnoreCase("EMPLOYEE"))
			redirectUrl = "/employee/dashboard";

		response.sendRedirect(redirectUrl);

	}
}
