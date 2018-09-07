package com.ieli.tieasy.model.api;

import java.io.Serializable;

public class IncidentInput implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	private String createIncidentPath;
	private String uploadFilePath;

	public IncidentInput() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateIncidentPath() {
		return createIncidentPath;
	}

	public void setCreateIncidentPath(String createIncidentPath) {
		this.createIncidentPath = createIncidentPath;
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

}
