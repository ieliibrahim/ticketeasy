package com.ieli.tieasy.model.api;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Incident implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("result")
	private IncidentResult incidentResult;

	private String number;

	public Incident() {

	}

	public IncidentResult getIncidentResult() {
		return incidentResult;
	}

	public void setIncidentResult(IncidentResult incidentResult) {
		this.incidentResult = incidentResult;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
