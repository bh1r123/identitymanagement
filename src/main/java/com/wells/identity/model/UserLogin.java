package com.wells.identity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//create table np.USERLOGIN (EMPID varchar(255) PRIMARY KEY, FIRST_NAME varchar(255), LAST_NAME varchar(255),EMAIL_ID varchar(255),PASSWORD varchar(255));


@Entity
@Table(name = "USERLOGIN")
public class UserLogin {
	

	private String empid;
	private String password;
	private String firstName;
	private String lastName;
	private String emailId;
	
	@Id
	@Column(name = "EMPID", nullable = false)
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	
	@Column(name = "PASSWORD", nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "FIRST_NAME", nullable = false)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LAST_NAME", nullable = false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "EMAIL_ID", nullable = false)
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
