package com.ieli.tieasy.model.api;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class IncidentResult implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("sys_id")
	private String sysId;

	public IncidentResult() {

	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
}
