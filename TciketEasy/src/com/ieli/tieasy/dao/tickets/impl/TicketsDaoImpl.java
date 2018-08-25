package com.ieli.tieasy.dao.tickets.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ieli.tieasy.dao.tickets.ITicketsDao;
import com.ieli.tieasy.dao.util.AbstractDao;
import com.ieli.tieasy.model.Ticket;
import com.ieli.tieasy.util.StackTraceHandler;

@Repository("iTicketsDao")
public class TicketsDaoImpl extends AbstractDao implements ITicketsDao {

	final static Logger logger = Logger.getLogger(TicketsDaoImpl.class);

	@Override
	public Ticket saveTicket(Ticket ticket) {
		try {
			int ticketId = save(ticket);
			ticket.setTicketId(ticketId);
		} catch (Exception e) {
			logger.error(StackTraceHandler.getErrString(e));
		}

		return ticket;
	}

}
