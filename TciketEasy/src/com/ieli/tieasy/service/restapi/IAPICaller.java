package com.ieli.tieasy.service.restapi;

import java.io.File;

import com.ieli.tieasy.model.api.Incident;
import com.ieli.tieasy.model.api.Upload;

public interface IAPICaller {

	public Incident createIncident(String title, String description);

	public Upload uploadFile(File file, String sysId);
}
