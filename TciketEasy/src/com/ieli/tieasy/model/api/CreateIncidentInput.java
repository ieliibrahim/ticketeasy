package com.ieli.tieasy.model.api;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class CreateIncidentInput implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("caller_id")
	private String callerId;
	
	@SerializedName("short_description")
	private String title;
	
	@SerializedName("description")
	private String description;

	public CreateIncidentInput() {

	}

	public String getCallerId() {
		return callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
