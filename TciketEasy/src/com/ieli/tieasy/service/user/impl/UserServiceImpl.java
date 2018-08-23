package com.ieli.tieasy.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ieli.tieasy.dao.user.IUserDao;
import com.ieli.tieasy.model.User;
import com.ieli.tieasy.service.user.IUserService;

@Service("iUserService")
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao iUserDao;

	@Override
	public User getUserByCred(String username, String password) {
		return iUserDao.getUserByCred(username, password);
	}

}
