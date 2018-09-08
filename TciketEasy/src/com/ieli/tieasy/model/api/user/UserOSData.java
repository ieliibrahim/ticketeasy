package com.ieli.tieasy.model.api.user;

import java.io.Serializable;

public class UserOSData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String currentUser;
	private String currentUnit;
	private String osBuild;
	private String lastRebootTime;

	public UserOSData() {

	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getCurrentUnit() {
		return currentUnit;
	}

	public void setCurrentUnit(String currentUnit) {
		this.currentUnit = currentUnit;
	}

	public String getOsBuild() {
		return osBuild;
	}

	public void setOsBuild(String osBuild) {
		this.osBuild = osBuild;
	}

	public String getLastRebootTime() {
		return lastRebootTime;
	}

	public void setLastRebootTime(String lastRebootTime) {
		this.lastRebootTime = lastRebootTime;
	}

	@Override
	public String toString() {
		return currentUser + "\n" + currentUnit + "\n" + osBuild + "\n" + lastRebootTime;
	}

}
