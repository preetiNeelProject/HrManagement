package com.hr.system.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hr.system.employee.bean.EmployeeAccessBean;

public class EmployeePrincipal implements UserDetails {
	
	private static final long serialVersionUID = -1108292744709975092L;
	
	@Autowired
	private EmployeeAccessBean employee;
	HashMap<String, SimpleGrantedAuthority> roleBasedOnUsername;
	
	public EmployeePrincipal(EmployeeAccessBean employee) {
		super();
		this.employee=employee;
	
		 
	}

	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		System.err.println(this.employee.getRoles());
		List<GrantedAuthority> authorities = new ArrayList<>();  
		if(this.employee.getRoles()!=null) {               
          authorities.add(new SimpleGrantedAuthority(this.employee.getRoles().toUpperCase()));
		}
    
        return authorities;
    }

	@Override
	public String getPassword() {
		return employee.getLogonPassword();
	}

	@Override
	public String getUsername() {
		return employee.getLogonId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
