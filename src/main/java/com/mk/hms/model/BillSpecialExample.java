package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillSpecialExample extends BasePageExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BillSpecialExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPromotypeIsNull() {
            addCriterion("promoType is null");
            return (Criteria) this;
        }

        public Criteria andPromotypeIsNotNull() {
            addCriterion("promoType is not null");
            return (Criteria) this;
        }

        public Criteria andPromotypeEqualTo(Integer value) {
            addCriterion("promoType =", value, "promotype");
            return (Criteria) this;
        }

        public Criteria andPromotypeNotEqualTo(Integer value) {
            addCriterion("promoType <>", value, "promotype");
            return (Criteria) this;
        }

        public Criteria andPromotypeGreaterThan(Integer value) {
            addCriterion("promoType >", value, "promotype");
            return (Criteria) this;
        }

        public Criteria andPromotypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("promoType >=", value, "promotype");
            return (Criteria) this;
        }

        public Criteria andPromotypeLessThan(Integer value) {
            addCriterion("promoType <", value, "promotype");
            return (Criteria) this;
        }

        public Criteria andPromotypeLessThanOrEqualTo(Integer value) {
            addCriterion("promoType <=", value, "promotype");
            return (Criteria) this;
        }

        public Criteria andPromotypeIn(List<Integer> values) {
            addCriterion("promoType in", values, "promotype");
            return (Criteria) this;
        }

        public Criteria andPromotypeNotIn(List<Integer> values) {
            addCriterion("promoType not in", values, "promotype");
            return (Criteria) this;
        }

        public Criteria andPromotypeBetween(Integer value1, Integer value2) {
            addCriterion("promoType between", value1, value2, "promotype");
            return (Criteria) this;
        }

        public Criteria andPromotypeNotBetween(Integer value1, Integer value2) {
            addCriterion("promoType not between", value1, value2, "promotype");
            return (Criteria) this;
        }

        public Criteria andHotelidIsNull() {
            addCriterion("hotelId is null");
            return (Criteria) this;
        }

        public Criteria andHotelidIsNotNull() {
            addCriterion("hotelId is not null");
            return (Criteria) this;
        }

        public Criteria andHotelidEqualTo(Long value) {
            addCriterion("hotelId =", value, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelidNotEqualTo(Long value) {
            addCriterion("hotelId <>", value, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelidGreaterThan(Long value) {
            addCriterion("hotelId >", value, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelidGreaterThanOrEqualTo(Long value) {
            addCriterion("hotelId >=", value, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelidLessThan(Long value) {
            addCriterion("hotelId <", value, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelidLessThanOrEqualTo(Long value) {
            addCriterion("hotelId <=", value, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelidIn(List<Long> values) {
            addCriterion("hotelId in", values, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelidNotIn(List<Long> values) {
            addCriterion("hotelId not in", values, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelidBetween(Long value1, Long value2) {
            addCriterion("hotelId between", value1, value2, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelidNotBetween(Long value1, Long value2) {
            addCriterion("hotelId not between", value1, value2, "hotelid");
            return (Criteria) this;
        }

        public Criteria andHotelnameIsNull() {
            addCriterion("hotelName is null");
            return (Criteria) this;
        }

        public Criteria andHotelnameIsNotNull() {
            addCriterion("hotelName is not null");
            return (Criteria) this;
        }

        public Criteria andHotelnameEqualTo(String value) {
            addCriterion("hotelName =", value, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameNotEqualTo(String value) {
            addCriterion("hotelName <>", value, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameGreaterThan(String value) {
            addCriterion("hotelName >", value, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameGreaterThanOrEqualTo(String value) {
            addCriterion("hotelName >=", value, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameLessThan(String value) {
            addCriterion("hotelName <", value, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameLessThanOrEqualTo(String value) {
            addCriterion("hotelName <=", value, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameLike(String value) {
            addCriterion("hotelName like", value, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameNotLike(String value) {
            addCriterion("hotelName not like", value, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameIn(List<String> values) {
            addCriterion("hotelName in", values, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameNotIn(List<String> values) {
            addCriterion("hotelName not in", values, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameBetween(String value1, String value2) {
            addCriterion("hotelName between", value1, value2, "hotelname");
            return (Criteria) this;
        }

        public Criteria andHotelnameNotBetween(String value1, String value2) {
            addCriterion("hotelName not between", value1, value2, "hotelname");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNull() {
            addCriterion("cityCode is null");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNotNull() {
            addCriterion("cityCode is not null");
            return (Criteria) this;
        }

        public Criteria andCitycodeEqualTo(String value) {
            addCriterion("cityCode =", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotEqualTo(String value) {
            addCriterion("cityCode <>", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThan(String value) {
            addCriterion("cityCode >", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThanOrEqualTo(String value) {
            addCriterion("cityCode >=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThan(String value) {
            addCriterion("cityCode <", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThanOrEqualTo(String value) {
            addCriterion("cityCode <=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLike(String value) {
            addCriterion("cityCode like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotLike(String value) {
            addCriterion("cityCode not like", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeIn(List<String> values) {
            addCriterion("cityCode in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotIn(List<String> values) {
            addCriterion("cityCode not in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeBetween(String value1, String value2) {
            addCriterion("cityCode between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotBetween(String value1, String value2) {
            addCriterion("cityCode not between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitynameIsNull() {
            addCriterion("cityName is null");
            return (Criteria) this;
        }

        public Criteria andCitynameIsNotNull() {
            addCriterion("cityName is not null");
            return (Criteria) this;
        }

        public Criteria andCitynameEqualTo(String value) {
            addCriterion("cityName =", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotEqualTo(String value) {
            addCriterion("cityName <>", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameGreaterThan(String value) {
            addCriterion("cityName >", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameGreaterThanOrEqualTo(String value) {
            addCriterion("cityName >=", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameLessThan(String value) {
            addCriterion("cityName <", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameLessThanOrEqualTo(String value) {
            addCriterion("cityName <=", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameLike(String value) {
            addCriterion("cityName like", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotLike(String value) {
            addCriterion("cityName not like", value, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameIn(List<String> values) {
            addCriterion("cityName in", values, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotIn(List<String> values) {
            addCriterion("cityName not in", values, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameBetween(String value1, String value2) {
            addCriterion("cityName between", value1, value2, "cityname");
            return (Criteria) this;
        }

        public Criteria andCitynameNotBetween(String value1, String value2) {
            addCriterion("cityName not between", value1, value2, "cityname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusIsNull() {
            addCriterion("checkStatus is null");
            return (Criteria) this;
        }

        public Criteria andCheckstatusIsNotNull() {
            addCriterion("checkStatus is not null");
            return (Criteria) this;
        }

        public Criteria andCheckstatusEqualTo(Integer value) {
            addCriterion("checkStatus =", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusNotEqualTo(Integer value) {
            addCriterion("checkStatus <>", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusGreaterThan(Integer value) {
            addCriterion("checkStatus >", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("checkStatus >=", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusLessThan(Integer value) {
            addCriterion("checkStatus <", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusLessThanOrEqualTo(Integer value) {
            addCriterion("checkStatus <=", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusIn(List<Integer> values) {
            addCriterion("checkStatus in", values, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusNotIn(List<Integer> values) {
            addCriterion("checkStatus not in", values, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusBetween(Integer value1, Integer value2) {
            addCriterion("checkStatus between", value1, value2, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusNotBetween(Integer value1, Integer value2) {
            addCriterion("checkStatus not between", value1, value2, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andBilltimeIsNull() {
            addCriterion("billTime is null");
            return (Criteria) this;
        }

        public Criteria andBilltimeIsNotNull() {
            addCriterion("billTime is not null");
            return (Criteria) this;
        }

        public Criteria andBilltimeEqualTo(String value) {
            addCriterion("billTime =", value, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeNotEqualTo(String value) {
            addCriterion("billTime <>", value, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeGreaterThan(String value) {
            addCriterion("billTime >", value, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeGreaterThanOrEqualTo(String value) {
            addCriterion("billTime >=", value, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeLessThan(String value) {
            addCriterion("billTime <", value, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeLessThanOrEqualTo(String value) {
            addCriterion("billTime <=", value, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeLike(String value) {
            addCriterion("billTime like", value, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeNotLike(String value) {
            addCriterion("billTime not like", value, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeIn(List<String> values) {
            addCriterion("billTime in", values, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeNotIn(List<String> values) {
            addCriterion("billTime not in", values, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeBetween(String value1, String value2) {
            addCriterion("billTime between", value1, value2, "billtime");
            return (Criteria) this;
        }

        public Criteria andBilltimeNotBetween(String value1, String value2) {
            addCriterion("billTime not between", value1, value2, "billtime");
            return (Criteria) this;
        }

        public Criteria andBegintimeIsNull() {
            addCriterion("beginTime is null");
            return (Criteria) this;
        }

        public Criteria andBegintimeIsNotNull() {
            addCriterion("beginTime is not null");
            return (Criteria) this;
        }

        public Criteria andBegintimeEqualTo(Date value) {
            addCriterion("beginTime =", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeNotEqualTo(Date value) {
            addCriterion("beginTime <>", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeGreaterThan(Date value) {
            addCriterion("beginTime >", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("beginTime >=", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeLessThan(Date value) {
            addCriterion("beginTime <", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeLessThanOrEqualTo(Date value) {
            addCriterion("beginTime <=", value, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeIn(List<Date> values) {
            addCriterion("beginTime in", values, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeNotIn(List<Date> values) {
            addCriterion("beginTime not in", values, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeBetween(Date value1, Date value2) {
            addCriterion("beginTime between", value1, value2, "begintime");
            return (Criteria) this;
        }

        public Criteria andBegintimeNotBetween(Date value1, Date value2) {
            addCriterion("beginTime not between", value1, value2, "begintime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNull() {
            addCriterion("endTime is null");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNotNull() {
            addCriterion("endTime is not null");
            return (Criteria) this;
        }

        public Criteria andEndtimeEqualTo(Date value) {
            addCriterion("endTime =", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotEqualTo(Date value) {
            addCriterion("endTime <>", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThan(Date value) {
            addCriterion("endTime >", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("endTime >=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThan(Date value) {
            addCriterion("endTime <", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThanOrEqualTo(Date value) {
            addCriterion("endTime <=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIn(List<Date> values) {
            addCriterion("endTime in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotIn(List<Date> values) {
            addCriterion("endTime not in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeBetween(Date value1, Date value2) {
            addCriterion("endTime between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotBetween(Date value1, Date value2) {
            addCriterion("endTime not between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andOrdernumIsNull() {
            addCriterion("orderNum is null");
            return (Criteria) this;
        }

        public Criteria andOrdernumIsNotNull() {
            addCriterion("orderNum is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernumEqualTo(Integer value) {
            addCriterion("orderNum =", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumNotEqualTo(Integer value) {
            addCriterion("orderNum <>", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumGreaterThan(Integer value) {
            addCriterion("orderNum >", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumGreaterThanOrEqualTo(Integer value) {
            addCriterion("orderNum >=", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumLessThan(Integer value) {
            addCriterion("orderNum <", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumLessThanOrEqualTo(Integer value) {
            addCriterion("orderNum <=", value, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumIn(List<Integer> values) {
            addCriterion("orderNum in", values, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumNotIn(List<Integer> values) {
            addCriterion("orderNum not in", values, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumBetween(Integer value1, Integer value2) {
            addCriterion("orderNum between", value1, value2, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOrdernumNotBetween(Integer value1, Integer value2) {
            addCriterion("orderNum not between", value1, value2, "ordernum");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidIsNull() {
            addCriterion("onlinePaid is null");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidIsNotNull() {
            addCriterion("onlinePaid is not null");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidEqualTo(BigDecimal value) {
            addCriterion("onlinePaid =", value, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidNotEqualTo(BigDecimal value) {
            addCriterion("onlinePaid <>", value, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidGreaterThan(BigDecimal value) {
            addCriterion("onlinePaid >", value, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("onlinePaid >=", value, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidLessThan(BigDecimal value) {
            addCriterion("onlinePaid <", value, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("onlinePaid <=", value, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidIn(List<BigDecimal> values) {
            addCriterion("onlinePaid in", values, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidNotIn(List<BigDecimal> values) {
            addCriterion("onlinePaid not in", values, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("onlinePaid between", value1, value2, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andOnlinepaidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("onlinePaid not between", value1, value2, "onlinepaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidIsNull() {
            addCriterion("aliPaid is null");
            return (Criteria) this;
        }

        public Criteria andAlipaidIsNotNull() {
            addCriterion("aliPaid is not null");
            return (Criteria) this;
        }

        public Criteria andAlipaidEqualTo(BigDecimal value) {
            addCriterion("aliPaid =", value, "alipaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidNotEqualTo(BigDecimal value) {
            addCriterion("aliPaid <>", value, "alipaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidGreaterThan(BigDecimal value) {
            addCriterion("aliPaid >", value, "alipaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("aliPaid >=", value, "alipaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidLessThan(BigDecimal value) {
            addCriterion("aliPaid <", value, "alipaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("aliPaid <=", value, "alipaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidIn(List<BigDecimal> values) {
            addCriterion("aliPaid in", values, "alipaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidNotIn(List<BigDecimal> values) {
            addCriterion("aliPaid not in", values, "alipaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("aliPaid between", value1, value2, "alipaid");
            return (Criteria) this;
        }

        public Criteria andAlipaidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("aliPaid not between", value1, value2, "alipaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidIsNull() {
            addCriterion("wechatPaid is null");
            return (Criteria) this;
        }

        public Criteria andWechatpaidIsNotNull() {
            addCriterion("wechatPaid is not null");
            return (Criteria) this;
        }

        public Criteria andWechatpaidEqualTo(BigDecimal value) {
            addCriterion("wechatPaid =", value, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidNotEqualTo(BigDecimal value) {
            addCriterion("wechatPaid <>", value, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidGreaterThan(BigDecimal value) {
            addCriterion("wechatPaid >", value, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("wechatPaid >=", value, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidLessThan(BigDecimal value) {
            addCriterion("wechatPaid <", value, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("wechatPaid <=", value, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidIn(List<BigDecimal> values) {
            addCriterion("wechatPaid in", values, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidNotIn(List<BigDecimal> values) {
            addCriterion("wechatPaid not in", values, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wechatPaid between", value1, value2, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andWechatpaidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("wechatPaid not between", value1, value2, "wechatpaid");
            return (Criteria) this;
        }

        public Criteria andBillcostIsNull() {
            addCriterion("billCost is null");
            return (Criteria) this;
        }

        public Criteria andBillcostIsNotNull() {
            addCriterion("billCost is not null");
            return (Criteria) this;
        }

        public Criteria andBillcostEqualTo(BigDecimal value) {
            addCriterion("billCost =", value, "billcost");
            return (Criteria) this;
        }

        public Criteria andBillcostNotEqualTo(BigDecimal value) {
            addCriterion("billCost <>", value, "billcost");
            return (Criteria) this;
        }

        public Criteria andBillcostGreaterThan(BigDecimal value) {
            addCriterion("billCost >", value, "billcost");
            return (Criteria) this;
        }

        public Criteria andBillcostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("billCost >=", value, "billcost");
            return (Criteria) this;
        }

        public Criteria andBillcostLessThan(BigDecimal value) {
            addCriterion("billCost <", value, "billcost");
            return (Criteria) this;
        }

        public Criteria andBillcostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("billCost <=", value, "billcost");
            return (Criteria) this;
        }

        public Criteria andBillcostIn(List<BigDecimal> values) {
            addCriterion("billCost in", values, "billcost");
            return (Criteria) this;
        }

        public Criteria andBillcostNotIn(List<BigDecimal> values) {
            addCriterion("billCost not in", values, "billcost");
            return (Criteria) this;
        }

        public Criteria andBillcostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("billCost between", value1, value2, "billcost");
            return (Criteria) this;
        }

        public Criteria andBillcostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("billCost not between", value1, value2, "billcost");
            return (Criteria) this;
        }

        public Criteria andChangecostIsNull() {
            addCriterion("changeCost is null");
            return (Criteria) this;
        }

        public Criteria andChangecostIsNotNull() {
            addCriterion("changeCost is not null");
            return (Criteria) this;
        }

        public Criteria andChangecostEqualTo(BigDecimal value) {
            addCriterion("changeCost =", value, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecostNotEqualTo(BigDecimal value) {
            addCriterion("changeCost <>", value, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecostGreaterThan(BigDecimal value) {
            addCriterion("changeCost >", value, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("changeCost >=", value, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecostLessThan(BigDecimal value) {
            addCriterion("changeCost <", value, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("changeCost <=", value, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecostIn(List<BigDecimal> values) {
            addCriterion("changeCost in", values, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecostNotIn(List<BigDecimal> values) {
            addCriterion("changeCost not in", values, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("changeCost between", value1, value2, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("changeCost not between", value1, value2, "changecost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostIsNull() {
            addCriterion("changeCorrectCost is null");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostIsNotNull() {
            addCriterion("changeCorrectCost is not null");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostEqualTo(BigDecimal value) {
            addCriterion("changeCorrectCost =", value, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostNotEqualTo(BigDecimal value) {
            addCriterion("changeCorrectCost <>", value, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostGreaterThan(BigDecimal value) {
            addCriterion("changeCorrectCost >", value, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("changeCorrectCost >=", value, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostLessThan(BigDecimal value) {
            addCriterion("changeCorrectCost <", value, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("changeCorrectCost <=", value, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostIn(List<BigDecimal> values) {
            addCriterion("changeCorrectCost in", values, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostNotIn(List<BigDecimal> values) {
            addCriterion("changeCorrectCost not in", values, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("changeCorrectCost between", value1, value2, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andChangecorrectcostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("changeCorrectCost not between", value1, value2, "changecorrectcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostIsNull() {
            addCriterion("finalCost is null");
            return (Criteria) this;
        }

        public Criteria andFinalcostIsNotNull() {
            addCriterion("finalCost is not null");
            return (Criteria) this;
        }

        public Criteria andFinalcostEqualTo(BigDecimal value) {
            addCriterion("finalCost =", value, "finalcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostNotEqualTo(BigDecimal value) {
            addCriterion("finalCost <>", value, "finalcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostGreaterThan(BigDecimal value) {
            addCriterion("finalCost >", value, "finalcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("finalCost >=", value, "finalcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostLessThan(BigDecimal value) {
            addCriterion("finalCost <", value, "finalcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("finalCost <=", value, "finalcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostIn(List<BigDecimal> values) {
            addCriterion("finalCost in", values, "finalcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostNotIn(List<BigDecimal> values) {
            addCriterion("finalCost not in", values, "finalcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finalCost between", value1, value2, "finalcost");
            return (Criteria) this;
        }

        public Criteria andFinalcostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("finalCost not between", value1, value2, "finalcost");
            return (Criteria) this;
        }

        public Criteria andIncomeIsNull() {
            addCriterion("income is null");
            return (Criteria) this;
        }

        public Criteria andIncomeIsNotNull() {
            addCriterion("income is not null");
            return (Criteria) this;
        }

        public Criteria andIncomeEqualTo(BigDecimal value) {
            addCriterion("income =", value, "income");
            return (Criteria) this;
        }

        public Criteria andIncomeNotEqualTo(BigDecimal value) {
            addCriterion("income <>", value, "income");
            return (Criteria) this;
        }

        public Criteria andIncomeGreaterThan(BigDecimal value) {
            addCriterion("income >", value, "income");
            return (Criteria) this;
        }

        public Criteria andIncomeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("income >=", value, "income");
            return (Criteria) this;
        }

        public Criteria andIncomeLessThan(BigDecimal value) {
            addCriterion("income <", value, "income");
            return (Criteria) this;
        }

        public Criteria andIncomeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("income <=", value, "income");
            return (Criteria) this;
        }

        public Criteria andIncomeIn(List<BigDecimal> values) {
            addCriterion("income in", values, "income");
            return (Criteria) this;
        }

        public Criteria andIncomeNotIn(List<BigDecimal> values) {
            addCriterion("income not in", values, "income");
            return (Criteria) this;
        }

        public Criteria andIncomeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("income between", value1, value2, "income");
            return (Criteria) this;
        }

        public Criteria andIncomeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("income not between", value1, value2, "income");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andFinancestatusIsNull() {
            addCriterion("financeStatus is null");
            return (Criteria) this;
        }

        public Criteria andFinancestatusIsNotNull() {
            addCriterion("financeStatus is not null");
            return (Criteria) this;
        }

        public Criteria andFinancestatusEqualTo(Integer value) {
            addCriterion("financeStatus =", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusNotEqualTo(Integer value) {
            addCriterion("financeStatus <>", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusGreaterThan(Integer value) {
            addCriterion("financeStatus >", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("financeStatus >=", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusLessThan(Integer value) {
            addCriterion("financeStatus <", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusLessThanOrEqualTo(Integer value) {
            addCriterion("financeStatus <=", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusIn(List<Integer> values) {
            addCriterion("financeStatus in", values, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusNotIn(List<Integer> values) {
            addCriterion("financeStatus not in", values, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusBetween(Integer value1, Integer value2) {
            addCriterion("financeStatus between", value1, value2, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusNotBetween(Integer value1, Integer value2) {
            addCriterion("financeStatus not between", value1, value2, "financestatus");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyIsNull() {
            addCriterion("availableMoney is null");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyIsNotNull() {
            addCriterion("availableMoney is not null");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyEqualTo(BigDecimal value) {
            addCriterion("availableMoney =", value, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyNotEqualTo(BigDecimal value) {
            addCriterion("availableMoney <>", value, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyGreaterThan(BigDecimal value) {
            addCriterion("availableMoney >", value, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("availableMoney >=", value, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyLessThan(BigDecimal value) {
            addCriterion("availableMoney <", value, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("availableMoney <=", value, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyIn(List<BigDecimal> values) {
            addCriterion("availableMoney in", values, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyNotIn(List<BigDecimal> values) {
            addCriterion("availableMoney not in", values, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("availableMoney between", value1, value2, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andAvailablemoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("availableMoney not between", value1, value2, "availablemoney");
            return (Criteria) this;
        }

        public Criteria andIsfreezeIsNull() {
            addCriterion("isFreeze is null");
            return (Criteria) this;
        }

        public Criteria andIsfreezeIsNotNull() {
            addCriterion("isFreeze is not null");
            return (Criteria) this;
        }

        public Criteria andIsfreezeEqualTo(String value) {
            addCriterion("isFreeze =", value, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeNotEqualTo(String value) {
            addCriterion("isFreeze <>", value, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeGreaterThan(String value) {
            addCriterion("isFreeze >", value, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeGreaterThanOrEqualTo(String value) {
            addCriterion("isFreeze >=", value, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeLessThan(String value) {
            addCriterion("isFreeze <", value, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeLessThanOrEqualTo(String value) {
            addCriterion("isFreeze <=", value, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeLike(String value) {
            addCriterion("isFreeze like", value, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeNotLike(String value) {
            addCriterion("isFreeze not like", value, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeIn(List<String> values) {
            addCriterion("isFreeze in", values, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeNotIn(List<String> values) {
            addCriterion("isFreeze not in", values, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeBetween(String value1, String value2) {
            addCriterion("isFreeze between", value1, value2, "isfreeze");
            return (Criteria) this;
        }

        public Criteria andIsfreezeNotBetween(String value1, String value2) {
            addCriterion("isFreeze not between", value1, value2, "isfreeze");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}