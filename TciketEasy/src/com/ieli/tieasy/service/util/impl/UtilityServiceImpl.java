package com.ieli.tieasy.service.util.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ieli.tieasy.model.api.IncidentInput;
import com.ieli.tieasy.model.api.user.UserOSData;
import com.ieli.tieasy.service.util.IUtilityService;
import com.ieli.tieasy.util.StackTraceHandler;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

public class UtilityServiceImpl implements IUtilityService {

	final static Logger logger = Logger.getLogger(UtilityServiceImpl.class);

	@Override
	public IncidentInput getIncidentInput(String propFilePath) {

		Properties prop = new Properties();
		InputStream input = null;
		IncidentInput incidentInput = new IncidentInput();
		try {

			prop.load(new FileInputStream(new File("./config.properties")));

			incidentInput.setUsername(prop.getProperty("username"));
			incidentInput.setPassword(prop.getProperty("password"));
			incidentInput.setEmail(prop.getProperty("email"));
			incidentInput.setCreateIncidentPath(prop.getProperty("sn_incident_create"));
			incidentInput.setUploadFilePath(prop.getProperty("sn_upload_file"));

		} catch (IOException ex) {
			logger.error(StackTraceHandler.getErrString(ex));
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(StackTraceHandler.getErrString(e));
				}
			}
		}

		return incidentInput;
	}

	@Override
	public UserOSData getUserOSData() {

		SystemInfo si = new SystemInfo();

		HardwareAbstractionLayer hal = si.getHardware();
		OperatingSystem os = si.getOperatingSystem();

		UserOSData userOSData = new UserOSData();

		userOSData.setCurrentUser("Current User:\t" + System.getProperty("user.name"));
		userOSData.setOsBuild("Operating System:\t" + os.toString());
		userOSData.setLastRebootTime("Last Reboot Time:\t" + "Uptime: "
				+ FormatUtil.formatElapsedSecs(hal.getProcessor().getSystemUptime()));

		String currentUnit = "Unknown";

		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			currentUnit = addr.getHostName();
		} catch (UnknownHostException e) {
			logger.error(StackTraceHandler.getErrString(e));
		}

		userOSData.setCurrentUnit("Current Unit:\t" + currentUnit + "\t" + hal.getComputerSystem().getSerialNumber());

		return userOSData;
	}


}
