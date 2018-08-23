package com.ieli.tieasy.service.user;

import com.ieli.tieasy.model.User;

public interface IUserService {

	User getUserByCred(String username, String password);
}
