package com.ieli.tieasy.service.util;

import com.ieli.tieasy.model.api.IncidentInput;
import com.ieli.tieasy.model.api.user.UserOSData;

public interface IUtilityService {

	IncidentInput getIncidentInput(String propFilePath);

	UserOSData getUserOSData();
}
