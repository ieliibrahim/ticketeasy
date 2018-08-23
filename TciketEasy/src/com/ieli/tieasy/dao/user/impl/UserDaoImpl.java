package com.ieli.tieasy.dao.user.impl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ieli.tieasy.dao.user.IUserDao;
import com.ieli.tieasy.dao.util.AbstractDao;
import com.ieli.tieasy.model.User;
import com.ieli.tieasy.util.StackTraceHandler;

@Repository("iUserDao")
public class UserDaoImpl extends AbstractDao implements IUserDao {

	final static Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public User getUserByCred(String username, String password) {
		User user = null;

		try {

			Criteria criteria = getSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("username", username));
			criteria.add(Restrictions.eq("password", password));
			user = (User) criteria.uniqueResult();

		} catch (Exception e) {
			logger.error(StackTraceHandler.getErrString(e));
		}

		return user;
	}

}
