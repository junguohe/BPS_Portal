package com.erry.auth.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import ch.ralscha.extdirectspring.generator.ModelField;

import com.erry.ext.serializer.JsonDateSerializer;
import com.erry.util.DateUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "t_bps_dealer_upload_resale")
public class DealerUploadReSale implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue
	@Column(name="id")
	private String id;

	@Column(name="tid")
	private String tid;

	@Column(name="fileid")
	private String fileid;

	@Column(name="region")
	private String region;

	@Column(name="dealername")
	private String dealername;

	@Column(name="period")
	private String period;

	@Column(name="seqno")
	private String seqno;

	@Column(name="custname")
	private String custname;

	@Column(name="currency")
	private String currency;

	@Column(name="materialcode")
	private String materialcode;

	@Column(name="materialname")
	private String materialname;

	@Column(name="batchno")
	private String batchno;

	@Column(name="qty")
	private Integer qty;

	@Column(name="paymenttype")
	private String paymenttype;

	@Column(name="deliverydate")
	private Date deliverydate;;

	@Column(name="unitprice")
	private BigDecimal unitprice;

	@Column(name="istax")
	private String istax;

	@Column(name="isspl")
	private String isspl;

	@Column(name="contractamount")
	private BigDecimal contractamount;

	@Column(name="dealerstdcost")
	private BigDecimal dealerstdcost;

	@Column(name="dealersplcost")
	private BigDecimal dealersplcost;

	@Column(name="dealerstdpo")
	private BigDecimal dealerstdpo;

	@Column(name="dealerstdact")
	private BigDecimal dealerstdact;

	@Column(name="rebateamount")
	private BigDecimal rebateamount;

	@Column(name="bpsstdcost")
	private BigDecimal bpsstdcost;

	@Column(name="bpssplcost")
	private BigDecimal bpssplcost;

	@Column(name="rebatediff")
	private BigDecimal rebatediff;

	@Column(name="remark")
	private String remark;

	@Column(name="active")
	private String active;

	@Column(name="creator")
	private String creator;

	@Column(name="createdate")
	@ModelField(dateFormat = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date createdate;

	@Column(name="updator")
	private String updator;

	@Column(name="updatedate")
	@ModelField(dateFormat = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date updatedate;

	@Column(name="errormsg")
	private String errormsg;

	@Column(name="mdealerid")
	private String mdealerid;

	@Column(name="mcustid")
	private String mcustid;

	@Column(name="mmaterialid")
	private String mmaterialid;

	@Column(name="mregdealerid")
	private String mregdealerid;

	@Column(name="mcpid")
	private String mcpid;

	@Column(name="mseqno")
	private String mseqno;

	@Column(name="materialactive")
	private String materialactive;

	@Column(name="pricecount")
	private Integer pricecount;

	@Column(name="pricepublic")
	private Integer pricepublic;

	@Column(name="specialprice")
	private Integer specialprice;

	@Column(name="isnotrebate")
	private String isnotrebate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDealername() {
		return dealername;
	}

	public void setDealername(String dealername) {
		this.dealername = dealername;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMaterialcode() {
		return materialcode;
	}

	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}

	public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public Date getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(Date deliverydate) {
		this.deliverydate = deliverydate;
	}

	public BigDecimal getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}

	public String getIstax() {
		return istax;
	}

	public void setIstax(String istax) {
		this.istax = istax;
	}

	public String getIsspl() {
		return isspl;
	}

	public void setIsspl(String isspl) {
		this.isspl = isspl;
	}

	public BigDecimal getContractamount() {
		return contractamount;
	}

	public void setContractamount(BigDecimal contractamount) {
		this.contractamount = contractamount;
	}

	public BigDecimal getDealerstdcost() {
		return dealerstdcost;
	}

	public void setDealerstdcost(BigDecimal dealerstdcost) {
		this.dealerstdcost = dealerstdcost;
	}

	public BigDecimal getDealersplcost() {
		return dealersplcost;
	}

	public void setDealersplcost(BigDecimal dealersplcost) {
		this.dealersplcost = dealersplcost;
	}

	public BigDecimal getDealerstdpo() {
		return dealerstdpo;
	}

	public void setDealerstdpo(BigDecimal dealerstdpo) {
		this.dealerstdpo = dealerstdpo;
	}

	public BigDecimal getDealerstdact() {
		return dealerstdact;
	}

	public void setDealerstdact(BigDecimal dealerstdact) {
		this.dealerstdact = dealerstdact;
	}

	public BigDecimal getRebateamount() {
		return rebateamount;
	}

	public void setRebateamount(BigDecimal rebateamount) {
		this.rebateamount = rebateamount;
	}

	public BigDecimal getBpsstdcost() {
		return bpsstdcost;
	}

	public void setBpsstdcost(BigDecimal bpsstdcost) {
		this.bpsstdcost = bpsstdcost;
	}

	public BigDecimal getBpssplcost() {
		return bpssplcost;
	}

	public void setBpssplcost(BigDecimal bpssplcost) {
		this.bpssplcost = bpssplcost;
	}

	public BigDecimal getRebatediff() {
		return rebatediff;
	}

	public void setRebatediff(BigDecimal rebatediff) {
		this.rebatediff = rebatediff;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public String getMdealerid() {
		return mdealerid;
	}

	public void setMdealerid(String mdealerid) {
		this.mdealerid = mdealerid;
	}

	public String getMcustid() {
		return mcustid;
	}

	public void setMcustid(String mcustid) {
		this.mcustid = mcustid;
	}

	public String getMmaterialid() {
		return mmaterialid;
	}

	public void setMmaterialid(String mmaterialid) {
		this.mmaterialid = mmaterialid;
	}

	public String getMregdealerid() {
		return mregdealerid;
	}

	public void setMregdealerid(String mregdealerid) {
		this.mregdealerid = mregdealerid;
	}

	public String getMcpid() {
		return mcpid;
	}

	public void setMcpid(String mcpid) {
		this.mcpid = mcpid;
	}

	public String getMseqno() {
		return mseqno;
	}

	public void setMseqno(String mseqno) {
		this.mseqno = mseqno;
	}

	public String getMaterialactive() {
		return materialactive;
	}

	public void setMaterialactive(String materialactive) {
		this.materialactive = materialactive;
	}

	public Integer getPricecount() {
		return pricecount;
	}

	public void setPricecount(Integer pricecount) {
		this.pricecount = pricecount;
	}

	public Integer getPricepublic() {
		return pricepublic;
	}

	public void setPricepublic(Integer pricepublic) {
		this.pricepublic = pricepublic;
	}

	public Integer getSpecialprice() {
		return specialprice;
	}

	public void setSpecialprice(Integer specialprice) {
		this.specialprice = specialprice;
	}

	public String getIsnotrebate() {
		return isnotrebate;
	}

	public void setIsnotrebate(String isnotrebate) {
		this.isnotrebate = isnotrebate;
	}
}