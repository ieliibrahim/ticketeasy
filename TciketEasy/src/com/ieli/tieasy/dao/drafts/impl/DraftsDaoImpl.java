package com.ieli.tieasy.dao.drafts.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ieli.tieasy.dao.drafts.IDraftsDao;
import com.ieli.tieasy.dao.util.AbstractDao;
import com.ieli.tieasy.model.Draft;
import com.ieli.tieasy.util.StackTraceHandler;

@Repository("iDraftsDao")
public class DraftsDaoImpl extends AbstractDao implements IDraftsDao {

	final static Logger logger = Logger.getLogger(DraftsDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Draft> getUserDrafts(int userId) {

		List<Draft> drafts = null;

		try {

			Criteria criteria = getSession().createCriteria(Draft.class);
			criteria.add(Restrictions.eq("userId", userId));
			drafts = criteria.list();

		} catch (Exception e) {
			logger.error(StackTraceHandler.getErrString(e));
		}

		return drafts;
	}

}
