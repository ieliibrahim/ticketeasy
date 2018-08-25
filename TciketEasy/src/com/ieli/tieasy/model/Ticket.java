package com.ieli.tieasy.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tickets", catalog = "easyticketdb")
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer ticketId;
	private Integer userId;
	private String title;
	private String description;
	private String ticketDateTime;
	private Integer enabled;

	public Ticket() {

	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ticket_id", unique = true, nullable = false)
	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	@Column(name = "ticket_date_time")
	public String getTicketDateTime() {
		return ticketDateTime;
	}

	public void setTicketDateTime(String ticketDateTime) {
		this.ticketDateTime = ticketDateTime;
	}

	@Column(name = "enabled")
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

}
