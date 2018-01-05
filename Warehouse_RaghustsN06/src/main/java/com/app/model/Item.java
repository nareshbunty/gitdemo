package com.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="item_tab")
public class Item implements Comparable<Item> {
	@Id
	@Column(name="item_id")
	@GeneratedValue(generator="itmone")
	@GenericGenerator(name="itmone",strategy="increment")
	private long itemId;
	
	@Column(name="item_code",unique=true)
	private String itemCode;
	
	@Column(name="item_wdt")
	private double itemWdth;
	@Column(name="item_len")
	private double itemLen;
	@Column(name="item_hei")
	private double itemheit;
	
	@Column(name="item_base_cost")
	private double baseCost;
	@Column(name="item_base_curr")
	private String baseCurrency;
	
	@ManyToOne
	@JoinColumn(name="item_uom_fk")
	private Uom itemUom=new Uom();
	
	@ManyToOne
	@JoinColumn(name="item_oms_fk")
	private OrderMethod itemSaleOrdMthd=new OrderMethod();
	
	@ManyToOne
	@JoinColumn(name="item_omp_fk")
	private OrderMethod itemPurchaseOrdMthd=new OrderMethod();
	
	@ManyToMany
	@JoinTable(name="item_ven",joinColumns=@JoinColumn(name="item_fk"),
	inverseJoinColumns=@JoinColumn(name="user_ven_fk"))
	private List<WhUserType> itemVendors=new ArrayList<WhUserType>(0);
	
	@ManyToMany
	@JoinTable(name="item_cust",joinColumns=@JoinColumn(name="item_fk"),
	inverseJoinColumns=@JoinColumn(name="user_cust_fk"))
	private List<WhUserType> itemCustomers=new ArrayList<WhUserType>(0);
	
	@Column(name="item_desc")
	private String description;

	@Column(name="item_cdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="item_ldate")
	private Date lastModifiedDate;	
	
	public Item() {
		super();
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public double getItemWdth() {
		return itemWdth;
	}

	public void setItemWdth(double itemWdth) {
		this.itemWdth = itemWdth;
	}

	public double getItemLen() {
		return itemLen;
	}

	public void setItemLen(double itemLen) {
		this.itemLen = itemLen;
	}

	public double getItemheit() {
		return itemheit;
	}

	public void setItemheit(double itemheit) {
		this.itemheit = itemheit;
	}

	public double getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(double baseCost) {
		this.baseCost = baseCost;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Uom getItemUom() {
		return itemUom;
	}

	public void setItemUom(Uom itemUom) {
		this.itemUom = itemUom;
	}

	public OrderMethod getItemSaleOrdMthd() {
		return itemSaleOrdMthd;
	}

	public void setItemSaleOrdMthd(OrderMethod itemSaleOrdMthd) {
		this.itemSaleOrdMthd = itemSaleOrdMthd;
	}

	public OrderMethod getItemPurchaseOrdMthd() {
		return itemPurchaseOrdMthd;
	}

	public void setItemPurchaseOrdMthd(OrderMethod itemPurchaseOrdMthd) {
		this.itemPurchaseOrdMthd = itemPurchaseOrdMthd;
	}

	public List<WhUserType> getItemVendors() {
		return itemVendors;
	}

	public void setItemVendors(List<WhUserType> itemVendors) {
		this.itemVendors = itemVendors;
	}

	public List<WhUserType> getItemCustomers() {
		return itemCustomers;
	}

	public void setItemCustomers(List<WhUserType> itemCustomers) {
		this.itemCustomers = itemCustomers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public int compareTo(Item o) {
		return (int) (o.getItemId()-this.getItemId());
	}

}