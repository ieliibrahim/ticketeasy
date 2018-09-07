package com.ieli.tieasy.service.util.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import org.apache.log4j.Logger;

import com.ieli.tieasy.model.api.IncidentInput;
import com.ieli.tieasy.service.util.IPropertyService;
import com.ieli.tieasy.util.StackTraceHandler;

public class PropertyServiceImpl implements IPropertyService {

	final static Logger logger = Logger.getLogger(PropertyServiceImpl.class);

	@Override
	public IncidentInput getIncidentInput(String propFilePath) {

		Properties prop = new Properties();
		InputStream input = null;
		IncidentInput incidentInput = new IncidentInput();
		try {

			prop.load(new FileInputStream(new File("./api.properties")));

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

}
