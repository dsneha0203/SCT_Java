package com.simpsoft.salesCommission.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Address")
public class Address {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "addrslinen1")
	private String addrslinen1;
	
	@Column(name = "addrslinen2")
	private String addrslinen2;
	
	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "STATE_ID")
	private State state;
	
	public Address() {
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the addrslinen1
	 */
	public String getAddrslinen1() {
		return addrslinen1;
	}

	/**
	 * @param addrslinen1 the addrslinen1 to set
	 */
	public void setAddrslinen1(String addrslinen1) {
		this.addrslinen1 = addrslinen1;
	}

	/**
	 * @return the addrslinen2
	 */
	public String getAddrslinen2() {
		return addrslinen2;
	}

	/**
	 * @param addrslinen2 the addrslinen2 to set
	 */
	public void setAddrslinen2(String addrslinen2) {
		this.addrslinen2 = addrslinen2;
	}
	
	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}
}
