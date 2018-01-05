package com.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
/**
 * Model class
 * @author raghu
 *
 */
@Entity
@Table(	name="uom_tab",
uniqueConstraints={@UniqueConstraint(columnNames={"u_type","u_model"})
})
public class Uom implements Comparable<Uom>{

	@Id
	@Column(name="u_id")
	@GeneratedValue(generator="uomgen")
	@GenericGenerator(name="uomgen",strategy="increment")
	private Long uomId;

	@Column(name="u_type")
	private String uomType;

	@Column(name="u_model")
	private String uomModel;

	@Column(name="u_cdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name="u_lmdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@Column(name="u_des")
	private String description;

	//alt+shift+S,O(de-select-all->ok)
	public Uom() {}

	public Uom(Long uomId) {
		this.uomId = uomId;
	}


	public Uom(String uomType, String uomModel, String description,Date createdDate) {
		super();
		this.uomType = uomType;
		this.uomModel = uomModel;
		this.description = description;
		this.createdDate=createdDate;
	}

	//alt+shift+S,R (selectAll->OK)
	public Long getUomId() {
		return uomId;
	}

	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}

	public String getUomType() {
		return uomType;
	}

	public void setUomType(String uomType) {
		this.uomType = uomType;
	}

	public String getUomModel() {
		return uomModel;
	}

	public void setUomModel(String uomModel) {
		this.uomModel = uomModel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	//alt+shift+S,S (OK)

	@Override
	public String toString() {
		return "Uom [uomId=" + uomId + ", uomType=" + uomType + ", uomModel="
				+ uomModel + ", createdDate=" + createdDate
				+ ", lastModifiedDate=" + lastModifiedDate + ", description="
				+ description + "]";
	}


	@Override
	public int compareTo(Uom o) {
		return (int) (o.getUomId()-this.getUomId());
	}


}
