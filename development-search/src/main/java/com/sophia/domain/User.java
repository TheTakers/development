package com.sophia.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * user
 *
 * @author zkning
 * @email ningzuokun@ppmoney.com
 */
@Entity
@Table(name="TB_AT_USER")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4381728134193745979L;
	
	/** 用户ID */
	@Id
	@Column(name="USER_ID")
	private Integer userId;
	/** 用户帐户 */
	@Column(name="ACCOUNT")
	private String account;
	/** 用户姓名 */
	@Column(name="USERNAME")
	private String username;

	/** 用户密码 */
	@Column(name="PWD")
	private String pwd;

	/** 用户是否有效 */
	@Column(name="ISENABLED")
	private Integer isenabled;
	
	/** 说明 */
	@Column(name="udesc")
	private String udesc;

	/** 员工ID */
	@Column(name="EMPLOYEE_ID")
	private Integer empId;
	
	@Transient
	private String sessionId;
	
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getIsenabled() {
		return isenabled;
	}

	public void setIsenabled(Integer isenabled) {
		this.isenabled = isenabled;
	}


	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		
		this.empId = empId;
	}

	public String getUdesc() {
		return udesc;
	}

	public void setUdesc(String udesc) {
		this.udesc = udesc;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
