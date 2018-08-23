package com.ieli.tieasy.dao.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void saveOrUpdate(Object entity) {
		try {
			getSession().saveOrUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int save(Object entity) {
		int newID = -1;
		try {
			newID = ((Integer) getSession().save(entity)).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newID;
	}

	public void persist(Object entity) {
		try {
			getSession().persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(Object entity) {
		try {
			getSession().delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Object entity) {
		try {
			getSession().update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}