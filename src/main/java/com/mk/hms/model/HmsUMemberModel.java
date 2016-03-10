package com.mk.hms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 会员模型实体
 * @author hdy
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "u_member")
public class HmsUMemberModel implements Serializable{

	private long mid;
	private String loginName;
	private String password;
	private Date passwordTime;
	private String payPassword;
	private String email;
	private String phone;
	private String cpName;
	private String name;
	private String sex;
	private String birthday;
	private int birthdayYear;
	private String avatar;
	private String phonePic;
	private String personPic;
	private String occupation;
	private long disId;
	private String company;
	private String school;
	private String birthland;
	private String selfIntroduction;
	private int affectiveState;
	private String interests;
	private String cardtype;
	private String Idcard;
	private Date Idcardapplytime;
	private String source;
	private String regcode;
	private String cardorg;
	private double score1;
	private double score2;
	private double score3;
	private long level;
	private String address;
	private double longitude;
	private double latitude;
	private long lastpostime;
	private int footprint;
	private long version;
	private String enable;
	private Date createtime;
	private double givescore3;
	private String openid;
	private String channelid;
	private String devicetype;
	private String marketsource;
	private String appversion;
	private String ostype;
	private String osver;
	private String weixinname;
	private String comefrom;
	private String comefromtype;
	private long hotelid;

	@Id
	@Column(name = "mid")
	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	@Column(name = "loginName")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "passwordTime")
	public Date getPasswordTime() {
		return passwordTime;
	}

	public void setPasswordTime(Date passwordTime) {
		this.passwordTime = passwordTime;
	}

	@Column(name = "payPassword")
	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "cpName")
	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "birthday")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "birthdayYear")
	public int getBirthdayYear() {
		return birthdayYear;
	}

	public void setBirthdayYear(int birthdayYear) {
		this.birthdayYear = birthdayYear;
	}

	@Column(name = "avatar")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name = "phonePic")
	public String getPhonePic() {
		return phonePic;
	}

	public void setPhonePic(String phonePic) {
		this.phonePic = phonePic;
	}

	@Column(name = "personPic")
	public String getPersonPic() {
		return personPic;
	}

	public void setPersonPic(String personPic) {
		this.personPic = personPic;
	}

	@Column(name = "occupation")
	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Column(name = "disId")
	public long getDisId() {
		return disId;
	}

	public void setDisId(long disId) {
		this.disId = disId;
	}

	@Column(name = "company")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "school")
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "birthland")
	public String getBirthland() {
		return birthland;
	}

	public void setBirthland(String birthland) {
		this.birthland = birthland;
	}

	@Column(name = "selfIntroduction")
	public String getSelfIntroduction() {
		return selfIntroduction;
	}

	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}

	@Column(name = "affectiveState")
	public int getAffectiveState() {
		return affectiveState;
	}

	public void setAffectiveState(int affectiveState) {
		this.affectiveState = affectiveState;
	}

	@Column(name = "interests")
	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	@Column(name = "cardtype")
	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	@Column(name = "Idcard")
	public String getIdcard() {
		return Idcard;
	}

	public void setIdcard(String idcard) {
		Idcard = idcard;
	}

	@Column(name = "Idcardapplytime")
	public Date getIdcardapplytime() {
		return Idcardapplytime;
	}

	public void setIdcardapplytime(Date idcardapplytime) {
		Idcardapplytime = idcardapplytime;
	}

	@Column(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "regcode")
	public String getRegcode() {
		return regcode;
	}

	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}

	@Column(name = "cardorg")
	public String getCardorg() {
		return cardorg;
	}

	public void setCardorg(String cardorg) {
		this.cardorg = cardorg;
	}

	@Column(name = "score1")
	public double getScore1() {
		return score1;
	}

	public void setScore1(double score1) {
		this.score1 = score1;
	}

	@Column(name = "score2")
	public double getScore2() {
		return score2;
	}

	public void setScore2(double score2) {
		this.score2 = score2;
	}

	@Column(name = "score3")
	public double getScore3() {
		return score3;
	}

	public void setScore3(double score3) {
		this.score3 = score3;
	}

	@Column(name = "level")
	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "longitude")
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude")
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "lastpostime")
	public long getLastpostime() {
		return lastpostime;
	}

	public void setLastpostime(long lastpostime) {
		this.lastpostime = lastpostime;
	}

	@Column(name = "footprint")
	public int getFootprint() {
		return footprint;
	}

	public void setFootprint(int footprint) {
		this.footprint = footprint;
	}

	@Column(name = "version")
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Column(name = "enable")
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Column(name = "createtime")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "givescore3")
	public double getGivescore3() {
		return givescore3;
	}

	public void setGivescore3(double givescore3) {
		this.givescore3 = givescore3;
	}

	@Column(name = "openid")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "channelid")
	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	@Column(name = "devicetype")
	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	@Column(name = "marketsource")
	public String getMarketsource() {
		return marketsource;
	}

	public void setMarketsource(String marketsource) {
		this.marketsource = marketsource;
	}

	@Column(name = "appversion")
	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	@Column(name = "ostype")
	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}
	
	@Column(name = "osver")
	public String getOsver() {
		return osver;
	}

	public void setOsver(String osver) {
		this.osver = osver;
	}

	@Column(name = "weixinname")
	public String getWeixinname() {
		return weixinname;
	}

	public void setWeixinname(String weixinname) {
		this.weixinname = weixinname;
	}

	@Column(name = "comefrom")
	public String getComefrom() {
		return comefrom;
	}

	public void setComefrom(String comefrom) {
		this.comefrom = comefrom;
	}

	@Column(name = "comefromtype")
	public String getComefromtype() {
		return comefromtype;
	}

	public void setComefromtype(String comefromtype) {
		this.comefromtype = comefromtype;
	}

	@Column(name = "hotelid")
	public long getHotelid() {
		return hotelid;
	}

	public void setHotelid(long hotelid) {
		this.hotelid = hotelid;
	}
}
