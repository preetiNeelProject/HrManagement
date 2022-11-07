package com.hr.system.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {



	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CustomFilter customfilterForNotification;
	
	@Autowired
private	LoginSuccesHandler loginSuccesHandler;
/*
 * @Autowired private AfterSuccesFullyAuthenticated
 * afterSuccesFullyAuthenticated;
 */
	
	private String[] csrfAllowedAntMatcher= {
			"/**",
			"/admin/attendance/viewAttendanceLogDate",
			"/getCity","/admin/company/addRole"	,
			"/admin/company/add_Employee",
			"/calBasicSalary",
			"/admin/company/addAnnouncement",
			"/admin/leave/applyLeave",
			"/admin/leave/rejectLeave",
			"/admin/payroll/addEmployeeSalary"
			
	};
	
	private String[] whiteListUrls={
			"/login",
			"/logout",
			"/admin/leave/**",
			"admin/attendance/viewAttendanceLog"
	};
	
	public String[] onlyAdminAllowedUrls= {
			"/admin/company/department",
			"/admin/company/role",
			"/admin/company/deleteRole/**",
			"/calBasicSalary",
			"/admin/payroll/**",
			"/admin/company/addAnnouncement/",
			"/admin/company/deleteAnnouncement",
			"/admin/leave/**",
			"/admin/leave/rejectLeave",
			"/admin/company/add_Employee",
			"/admin/payroll/addEmployeeSalary"
			
	};
	
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		 web.ignoring().antMatchers("/webjars/**");
	}

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("Select LogonId,LogonPassword,enabled  from Employee where LogonId=?").authoritiesByUsernameQuery("select LogonId,roles from Employee where LogonId=?");
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf(c-> c.ignoringAntMatchers(csrfAllowedAntMatcher)).authorizeRequests()
		.antMatchers("/webjars/**").permitAll()
			    .antMatchers(onlyAdminAllowedUrls).hasAuthority("ADMIN")
				.antMatchers(whiteListUrls).permitAll()				
				.anyRequest().authenticated()
				.and().formLogin()
				.loginPage("/login")
				.successHandler(loginSuccesHandler)
				.and()
				.addFilterAfter(customfilterForNotification, UsernamePasswordAuthenticationFilter.class)
				.logout().
				 logoutUrl("/logout")
				.permitAll()
				.logoutSuccessUrl("/login")	
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				.clearAuthentication(true)					
				.and().exceptionHandling()
				.accessDeniedPage("/403error");

	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	 
}
