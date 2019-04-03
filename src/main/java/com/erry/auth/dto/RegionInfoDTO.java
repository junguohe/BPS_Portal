package com.erry.auth.dto;

public class RegionInfoDTO {
	private String id;
	private String provinceid;
	private String province;
	private String city;
	public RegionInfoDTO(){}
	public RegionInfoDTO(RegionInfoDTO dto){
		this.id = dto.getId();
		this.provinceid = dto.getProvinceid();
		this.province = dto.getProvince();
		this.city = dto.getCity();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
