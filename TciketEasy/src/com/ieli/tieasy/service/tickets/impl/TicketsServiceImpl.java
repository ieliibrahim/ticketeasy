package com.ieli.tieasy.service.tickets.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ieli.tieasy.dao.tickets.ITicketsDao;
import com.ieli.tieasy.model.Ticket;
import com.ieli.tieasy.service.tickets.ITicketsService;

@Service("iTicketsService")
@Transactional
public class TicketsServiceImpl implements ITicketsService {

	@Autowired
	private ITicketsDao IiTicketsDao;

	@Override
	public Ticket saveTicket(Ticket ticket) {
		return IiTicketsDao.saveTicket(ticket);
	}

}
