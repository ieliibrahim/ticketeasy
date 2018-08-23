package com.ieli.tieasy.dao.drafts;

import java.util.List;

import com.ieli.tieasy.model.Draft;

public interface IDraftsDao {

	List<Draft> getUserDrafts(int userId);
}
