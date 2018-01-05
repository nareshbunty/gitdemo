package com.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="po_tab")
public class PurchaseOrder implements Comparable<PurchaseOrder>{
	@Id
	@GeneratedValue(generator="pogen")
	@GenericGenerator(name="pogen",strategy="increment")
	@Column(name="po_id")
	private Long orderId;
	
	@Column(name="po_code",unique=true)
	private String orderCode;
	
	@ManyToOne
	@JoinColumn(name="ship_id")
	private ShipmentType shipmentMode=new ShipmentType();

	@ManyToOne
	@JoinColumn(name="ven_id")
	private WhUserType vendor=new WhUserType();
	
	@Column(name="po_refe_num")
	private String refernceNumber;
	@Column(name="po_qlty_chk")
	private String qualityCheck;
	@Column(name="po_status")
	private String status;
	@Column(name="po_desc")
	private String description;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="po_id")
	private List<PurchaseOrderDetails> details=new ArrayList<PurchaseOrderDetails>(0);
	
	@Column(name="po_cdate")
	private Date createdDate;
	@Column(name="po_ldate")
	private Date lastModifiedDate;
	
	public PurchaseOrder() {
		super();
	}
	public PurchaseOrder(String status){
		this.status=status;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public ShipmentType getShipmentMode() {
		return shipmentMode;
	}
	public void setShipmentMode(ShipmentType shipmentMode) {
		this.shipmentMode = shipmentMode;
	}
	public WhUserType getVendor() {
		return vendor;
	}
	public void setVendor(WhUserType vendor) {
		this.vendor = vendor;
	}
	public String getRefernceNumber() {
		return refernceNumber;
	}
	public void setRefernceNumber(String refernceNumber) {
		this.refernceNumber = refernceNumber;
	}
	public String getQualityCheck() {
		return qualityCheck;
	}
	public void setQualityCheck(String qualityCheck) {
		this.qualityCheck = qualityCheck;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<PurchaseOrderDetails> getDetails() {
		return details;
	}
	public void setDetails(List<PurchaseOrderDetails> details) {
		this.details = details;
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
	@Override
	public String toString() {
		return "PurchaseOrder [orderId=" + orderId + ", orderCode=" + orderCode + ", shipmentMode=" + shipmentMode
				+ ", vendor=" + vendor + ", refernceNumber=" + refernceNumber + ", qualityCheck=" + qualityCheck
				+ ", status=" + status + ", description=" + description + ", details=" + details + ", createdDate="
				+ createdDate + ", lastModifiedDate=" + lastModifiedDate + "]";
	}

	@Override
	public int compareTo(PurchaseOrder o) {
		return (int) (o.getOrderId()-this.getOrderId());
	}
	
}
