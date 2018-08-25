package com.ieli.tieasy.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drafts", catalog = "easyticketdb")
public class Draft implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer draftId;
	private Integer ticketId;
	private String imagePath;
	private String imageThumb;
	private String draftDateTime;
	private Integer number;
	private Integer enabled;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "draft_id", unique = true, nullable = false)
	public Integer getDraftId() {
		return draftId;
	}

	public void setDraftId(Integer draftId) {
		this.draftId = draftId;
	}

	@Column(name = "ticket_id")
	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	@Column(name = "image_path")
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name = "draft_date_time")
	public String getDraftDateTime() {
		return draftDateTime;
	}

	public void setDraftDateTime(String draftDateTime) {
		this.draftDateTime = draftDateTime;
	}

	@Column(name = "number")
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "enabled")
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	@Column(name = "image_thumb")
	public String getImageThumb() {
		return imageThumb;
	}

	public void setImageThumb(String imageThumb) {
		this.imageThumb = imageThumb;
	}

}
