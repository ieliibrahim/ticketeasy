package com.ieli.tieasy.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "drafts", catalog = "easyticketdb")
public class Draft implements Serializable {

	private static final long serialVersionUID = 1L;

	private int draftId;
	private int userId;
	private String imagePath;
	private String title;
	private String description;
	private String draftDateTime;
	private int enabled;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "draft_id", unique = true, nullable = false)
	public int getDraftId() {
		return draftId;
	}

	public void setDraftId(int draftId) {
		this.draftId = draftId;
	}

	@Column(name = "user_id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "image_path")
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "draft_date_time")
	public String getDraftDateTime() {
		return draftDateTime;
	}

	public void setDraftDateTime(String draftDateTime) {
		this.draftDateTime = draftDateTime;
	}

	@Column(name = "enabled")
	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

}
