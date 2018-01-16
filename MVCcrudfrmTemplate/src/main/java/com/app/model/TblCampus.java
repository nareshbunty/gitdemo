package com.app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_campus")
public class TblCampus implements java.io.Serializable {
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String campusCode;
    private String campusName;
    private String deletionIndicator;
    private Integer userCreated;
    private Integer userModified;
    private Date dateCreated;
    private Date dateModified;
    
	public TblCampus(Integer id, String campusCode, String campusName, String deletionIndicator, Integer userCreated,
			Integer userModified, Date dateCreated, Date dateModified) {
		super();
		this.id = id;
		this.campusCode = campusCode;
		this.campusName = campusName;
		this.deletionIndicator = deletionIndicator;
		this.userCreated = userCreated;
		this.userModified = userModified;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public TblCampus() {
		super();
	}

	public TblCampus(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCampusCode() {
		return campusCode;
	}

	public void setCampusCode(String campusCode) {
		this.campusCode = campusCode;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public String getDeletionIndicator() {
		return deletionIndicator;
	}

	public void setDeletionIndicator(String deletionIndicator) {
		this.deletionIndicator = deletionIndicator;
	}

	public Integer getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(Integer userCreated) {
		this.userCreated = userCreated;
	}

	public Integer getUserModified() {
		return userModified;
	}

	public void setUserModified(Integer userModified) {
		this.userModified = userModified;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campusCode == null) ? 0 : campusCode.hashCode());
		result = prime * result + ((campusName == null) ? 0 : campusName.hashCode());
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((dateModified == null) ? 0 : dateModified.hashCode());
		result = prime * result + ((deletionIndicator == null) ? 0 : deletionIndicator.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userCreated == null) ? 0 : userCreated.hashCode());
		result = prime * result + ((userModified == null) ? 0 : userModified.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TblCampus other = (TblCampus) obj;
		if (campusCode == null) {
			if (other.campusCode != null)
				return false;
		} else if (!campusCode.equals(other.campusCode))
			return false;
		if (campusName == null) {
			if (other.campusName != null)
				return false;
		} else if (!campusName.equals(other.campusName))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (dateModified == null) {
			if (other.dateModified != null)
				return false;
		} else if (!dateModified.equals(other.dateModified))
			return false;
		if (deletionIndicator == null) {
			if (other.deletionIndicator != null)
				return false;
		} else if (!deletionIndicator.equals(other.deletionIndicator))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userCreated == null) {
			if (other.userCreated != null)
				return false;
		} else if (!userCreated.equals(other.userCreated))
			return false;
		if (userModified == null) {
			if (other.userModified != null)
				return false;
		} else if (!userModified.equals(other.userModified))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TblCampus [id=" + id + ", campusCode=" + campusCode + ", campusName=" + campusName
				+ ", deletionIndicator=" + deletionIndicator + ", userCreated=" + userCreated + ", userModified="
				+ userModified + ", dateCreated=" + dateCreated + ", dateModified=" + dateModified + "]";
	}
    
    
}