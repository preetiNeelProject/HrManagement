/*
 * package com.hr.system.security;
 * 
 * import java.io.IOException;
 * 
 * import javax.servlet.ServletException; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.core.Authentication; import
 * org.springframework.security.core.context.SecurityContextHolder; import
 * org.springframework.security.web.authentication.
 * SavedRequestAwareAuthenticationSuccessHandler; import
 * org.springframework.stereotype.Component;
 * 
 * import com.hr.system.employee.bean.EmployeeAccessBean; import
 * com.hr.system.employee.repository.EmployeeRepository;
 * 
 * @Component public class AfterSuccesFullyAuthenticated extends
 * SavedRequestAwareAuthenticationSuccessHandler {
 * 
 * 
 * @Autowired private EmployeeRepository employeRepo;
 * 
 * @Override public void onAuthenticationSuccess(HttpServletRequest request,
 * HttpServletResponse response, Authentication authentication) throws
 * ServletException, IOException {
 * 
 * System.err.println(SecurityContextHolder.getContext().getAuthentication().
 * getName()); String LogonId =
 * SecurityContextHolder.getContext().getAuthentication().getName();
 * EmployeeAccessBean logedInUser = employeRepo.findByLogonId(LogonId);
 * if(logedInUser != null) {
 * request.setAttribute("empId",logedInUser.getEmployeeId());
 * 
 * super.onAuthenticationSuccess(request, response, authentication); }
 * 
 * 
 * } }
 */