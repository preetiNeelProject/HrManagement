package com.hr.system.authentication.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="statecity")
public class CityState {
		
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String city;
	private String state;
	
	public CityState() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param city
	 * @param state
	 */
	public CityState(Integer id, String city, String state) {
		super();
		this.id = id;
		this.city = city;
		this.state = state;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	
	 

	
	
	

}
