package com.ieli.tieasy.dao.user;

import com.ieli.tieasy.model.User;

public interface IUserDao {

	User getUserByCred(String username, String password);
}
