package com.ieli.tieasy.service.restapi;

import com.ieli.tieasy.model.api.TokenResponse;

public interface IAPICaller {

	public TokenResponse getReponseToken();
	
	public String submitTicket(String token);
}
