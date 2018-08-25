package com.ieli.tieasy.service.drafts;

import java.util.List;

import com.ieli.tieasy.model.Draft;

public interface IDraftsService {

	List<Draft> getTicketDrafts(Integer ticketId);
}
