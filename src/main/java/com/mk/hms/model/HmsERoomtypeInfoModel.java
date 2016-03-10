package com.mk.hms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 房型
 * @author qhdhiman 2015/5/3
 */
@Entity
@Table(name = "e_roomtype_info")
public class HmsERoomtypeInfoModel implements Serializable{
	
	private long id; //主键
	private long roomTypeId; //房型主键
	private BigDecimal minArea; //最小面积
	private BigDecimal maxArea; //最大面积
	private long bedType; //床型
	private String bedSize; //床尺寸
	private String pics; //图片[{"name":"toilet","pic":[{"url":"http://pic.imike.com/pic/2015/3/30/20150330173345745.jpg"}]},{"name":"def","pic":[{"url":"http://pic.imike.com/pic/2015/3/30/20150330173338026.jpg"}]},{"name":"bed","pic":[{"url":"http://pic.imike.com/pic/2015/3/30/20150330173341742.jpg"}]}]
	private List<HmsERoomtypeFacilityModel> facilities; //设备设施
	@Id
    @Column(name = "id", nullable = false, length = 20)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
    @Column(name = "roomTypeId", nullable = false, length = 20)
	public long getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	@Column(name = "minArea", nullable = true, length = 10)
	public BigDecimal getMinArea() {
		return minArea;
	}
	public void setMinArea(BigDecimal minArea) {
		this.minArea = minArea;
	}
    @Column(name = "maxArea", nullable = true, length = 10)
	public BigDecimal getMaxArea() {
		return maxArea;
	}
	public void setMaxArea(BigDecimal maxArea) {
		this.maxArea = maxArea;
	}
    @Column(name = "bedType", nullable = true, length = 10)
    public long getBedType() {
		return bedType;
	}
	public void setBedType(long bedType) {
		this.bedType = bedType;
	}
    @Column(name = "bedSize", nullable = false, length = 10)
	public String getBedSize() {
		return bedSize;
	}
	public void setBedSize(String bedSize) {
		this.bedSize = bedSize;
	}
    @Column(name = "pics", nullable = true, length = 2000)
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	public List<HmsERoomtypeFacilityModel> getFacilities() {
		return facilities;
	}
	public void setFacilities(List<HmsERoomtypeFacilityModel> facilities) {
		this.facilities = facilities;
	}
}
