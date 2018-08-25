package com.ieli.tieasy.dao.drafts.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ieli.tieasy.dao.drafts.IDraftsDao;
import com.ieli.tieasy.dao.util.AbstractDao;
import com.ieli.tieasy.model.Draft;
import com.ieli.tieasy.util.StackTraceHandler;

@Repository("iDraftsDao")
@SuppressWarnings("unchecked")
public class DraftsDaoImpl extends AbstractDao implements IDraftsDao {

	final static Logger logger = Logger.getLogger(DraftsDaoImpl.class);

	@Override
	public Integer getLastDraftNumber(Integer ticketId) {

		Integer lastDraftId = 1;
		Criteria criteria = getSession().createCriteria(Draft.class);
		criteria.add(Restrictions.eq("ticketId", ticketId));
		criteria.setProjection(Projections.max("number"));
		lastDraftId = (Integer) criteria.uniqueResult();

		if (lastDraftId == null) {
			return 1;
		} else {
			return lastDraftId + 1;
		}
	}

	@Override
	public void saveDraft(Draft draft) {
		try {
			save(draft);
		} catch (Exception e) {
			logger.error(StackTraceHandler.getErrString(e));
		}

	}

	@Override
	public List<Draft> getTicketDrafts(Integer ticketId) {
		List<Draft> drafts = null;

		try {

			Criteria criteria = getSession().createCriteria(Draft.class);
			criteria.add(Restrictions.eq("ticketId", ticketId));
			drafts = criteria.list();

		} catch (Exception e) {
			logger.error(StackTraceHandler.getErrString(e));
		}

		return drafts;
	}

}
