package com.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="whusr_tab",
	uniqueConstraints=@UniqueConstraint(columnNames={"whusr_typ","whusr_cde"}))
public class WhUserType implements Comparable<WhUserType>{
	@Id
	@Column(name="whusr_id")
	@GeneratedValue(generator="whusr")
	@GenericGenerator(name="whusr",strategy="increment")
	private Long whUserTypeId;
	
	@Column(name="whusr_typ")
	private String userType;
	@Column(name="whusr_cde")
	private String whUserCode;
	@Column(name="whusr_fr")
	private String whUserFor;
	
	@Column(name="whusr_mail")
	private String whUserEmail;
	@Column(name="whusr_contact")
	private String whUserContact;
	@Column(name="whusr_id_typ")
	private String whUserIdType;
	
	@Column(name="whusr_idtyp_othr")
	private String whUserIdTypeOther;
	@Column(name="whusr_id_num")
	private String whUserIdNumber;
	
	@ManyToMany(mappedBy="itemVendors")
	private List<Item> venItems=new ArrayList<Item>(0);
	
	@ManyToMany(mappedBy="itemCustomers")
	private List<Item> custItems=new ArrayList<Item>(0);
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="whusr_cdat")
	private Date createdDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="whusr_ldat")
	private Date lasModifiedDate;
	
	public WhUserType() {
		super();
	}

	public Long getWhUserTypeId() {
		return whUserTypeId;
	}

	public void setWhUserTypeId(Long whUserTypeId) {
		this.whUserTypeId = whUserTypeId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getWhUserCode() {
		return whUserCode;
	}

	public void setWhUserCode(String whUserCode) {
		this.whUserCode = whUserCode;
	}

	public String getWhUserFor() {
		return whUserFor;
	}

	public void setWhUserFor(String whUserFor) {
		this.whUserFor = whUserFor;
	}

	public String getWhUserEmail() {
		return whUserEmail;
	}

	public void setWhUserEmail(String whUserEmail) {
		this.whUserEmail = whUserEmail;
	}

	public String getWhUserContact() {
		return whUserContact;
	}

	public void setWhUserContact(String whUserContact) {
		this.whUserContact = whUserContact;
	}

	public String getWhUserIdType() {
		return whUserIdType;
	}

	public void setWhUserIdType(String whUserIdType) {
		this.whUserIdType = whUserIdType;
	}

	public String getWhUserIdTypeOther() {
		return whUserIdTypeOther;
	}

	public void setWhUserIdTypeOther(String whUserIdTypeOther) {
		this.whUserIdTypeOther = whUserIdTypeOther;
	}

	public String getWhUserIdNumber() {
		return whUserIdNumber;
	}

	public void setWhUserIdNumber(String whUserIdNumber) {
		this.whUserIdNumber = whUserIdNumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLasModifiedDate() {
		return lasModifiedDate;
	}

	public void setLasModifiedDate(Date lasModifiedDate) {
		this.lasModifiedDate = lasModifiedDate;
	}

	
	public List<Item> getVenItems() {
		return venItems;
	}

	public void setVenItems(List<Item> venItems) {
		this.venItems = venItems;
	}

	public List<Item> getCustItems() {
		return custItems;
	}

	public void setCustItems(List<Item> custItems) {
		this.custItems = custItems;
	}

	
	
	@Override
	public int compareTo(WhUserType o) {
		return (int) (o.getWhUserTypeId()-this.getWhUserTypeId());
	}
}