package com.ieli.tieasy.service.drafts.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ieli.tieasy.dao.drafts.IDraftsDao;
import com.ieli.tieasy.model.Draft;
import com.ieli.tieasy.service.drafts.IDraftsService;

@Service("iDraftsService")
@Transactional
public class DraftsServiceImpl implements IDraftsService {

	@Autowired
	private IDraftsDao iDraftsDao;

	@Override
	public List<Draft> getTicketDrafts(Integer ticketId) {
		return iDraftsDao.getTicketDrafts(ticketId);
	}

}
