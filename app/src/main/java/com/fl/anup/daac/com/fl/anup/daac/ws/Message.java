package com.fl.anup.daac.com.fl.anup.daac.ws;

import java.util.HashMap;
import java.util.Map;

public class Message {

	private String status;
	
	private String message;
	
	private String userId;
	
	private String userName;
	
	private String userType;
	
	private Map<String, String> addLogs = new HashMap<String, String>();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Map<String, String> getAddLogs() {
		return addLogs;
	}

	public void setAddLogs(Map<String, String> addLogs) {
		this.addLogs = addLogs;
	}	
}
