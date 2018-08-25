package com.ieli.tieasy.dao.drafts;

import java.util.List;

import com.ieli.tieasy.model.Draft;

public interface IDraftsDao {

	Integer getLastDraftNumber(Integer ticketId);

	void saveDraft(Draft draft);

	List<Draft> getTicketDrafts(Integer ticketId);
}
