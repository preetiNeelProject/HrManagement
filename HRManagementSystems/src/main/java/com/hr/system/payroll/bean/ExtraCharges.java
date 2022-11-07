package com.hr.system.payroll.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "extraCharges")
public class ExtraCharges {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long charhesId;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name ="empctcid")
	private EmployeeCTCAccessBean employeeCTCAccessBean;	
	
     private Long mediclame;
     
     private Long bonus;
     
     private Long incentives;
     
     private Long tds;

	/**
	 * @param charhesId
	 * @param employeeCTCAccessBean
	 * @param mediclame
	 * @param bonus
	 * @param incentives
	 * @param tds
	 */
	public ExtraCharges(Long charhesId, EmployeeCTCAccessBean employeeCTCAccessBean, Long mediclame, Long bonus,
			Long incentives, Long tds) {
		super();
		this.charhesId = charhesId;
		this.employeeCTCAccessBean = employeeCTCAccessBean;
		this.mediclame = mediclame;
		this.bonus = bonus;
		this.incentives = incentives;
		this.tds = tds;
	}

	/**
	 * 
	 */
	public ExtraCharges() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getCharhesId() {
		return charhesId;
	}

	public void setCharhesId(Long charhesId) {
		this.charhesId = charhesId;
	}

	public EmployeeCTCAccessBean getEmployeeCTCAccessBean() {
		return employeeCTCAccessBean;
	}

	public void setEmployeeCTCAccessBean(EmployeeCTCAccessBean employeeCTCAccessBean) {
		this.employeeCTCAccessBean = employeeCTCAccessBean;
	}

	public Long getMediclame() {
		return mediclame;
	}

	public void setMediclame(Long mediclame) {
		this.mediclame = mediclame;
	}

	public Long getBonus() {
		return bonus;
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

	public Long getIncentives() {
		return incentives;
	}

	public void setIncentives(Long incentives) {
		this.incentives = incentives;
	}

	public Long getTds() {
		return tds;
	}

	public void setTds(Long tds) {
		this.tds = tds;
	}	

}
