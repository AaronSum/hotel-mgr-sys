package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillSpecialDetailExample extends BasePageExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BillSpecialDetailExample() {
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

        public Criteria andBillidIsNull() {
            addCriterion("billId is null");
            return (Criteria) this;
        }

        public Criteria andBillidIsNotNull() {
            addCriterion("billId is not null");
            return (Criteria) this;
        }

        public Criteria andBillidEqualTo(Long value) {
            addCriterion("billId =", value, "billid");
            return (Criteria) this;
        }

        public Criteria andBillidNotEqualTo(Long value) {
            addCriterion("billId <>", value, "billid");
            return (Criteria) this;
        }

        public Criteria andBillidGreaterThan(Long value) {
            addCriterion("billId >", value, "billid");
            return (Criteria) this;
        }

        public Criteria andBillidGreaterThanOrEqualTo(Long value) {
            addCriterion("billId >=", value, "billid");
            return (Criteria) this;
        }

        public Criteria andBillidLessThan(Long value) {
            addCriterion("billId <", value, "billid");
            return (Criteria) this;
        }

        public Criteria andBillidLessThanOrEqualTo(Long value) {
            addCriterion("billId <=", value, "billid");
            return (Criteria) this;
        }

        public Criteria andBillidIn(List<Long> values) {
            addCriterion("billId in", values, "billid");
            return (Criteria) this;
        }

        public Criteria andBillidNotIn(List<Long> values) {
            addCriterion("billId not in", values, "billid");
            return (Criteria) this;
        }

        public Criteria andBillidBetween(Long value1, Long value2) {
            addCriterion("billId between", value1, value2, "billid");
            return (Criteria) this;
        }

        public Criteria andBillidNotBetween(Long value1, Long value2) {
            addCriterion("billId not between", value1, value2, "billid");
            return (Criteria) this;
        }

        public Criteria andOrderidIsNull() {
            addCriterion("orderId is null");
            return (Criteria) this;
        }

        public Criteria andOrderidIsNotNull() {
            addCriterion("orderId is not null");
            return (Criteria) this;
        }

        public Criteria andOrderidEqualTo(Long value) {
            addCriterion("orderId =", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotEqualTo(Long value) {
            addCriterion("orderId <>", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThan(Long value) {
            addCriterion("orderId >", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThanOrEqualTo(Long value) {
            addCriterion("orderId >=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThan(Long value) {
            addCriterion("orderId <", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThanOrEqualTo(Long value) {
            addCriterion("orderId <=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidIn(List<Long> values) {
            addCriterion("orderId in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotIn(List<Long> values) {
            addCriterion("orderId not in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidBetween(Long value1, Long value2) {
            addCriterion("orderId between", value1, value2, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotBetween(Long value1, Long value2) {
            addCriterion("orderId not between", value1, value2, "orderid");
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

        public Criteria andCheckintimeIsNull() {
            addCriterion("checkinTime is null");
            return (Criteria) this;
        }

        public Criteria andCheckintimeIsNotNull() {
            addCriterion("checkinTime is not null");
            return (Criteria) this;
        }

        public Criteria andCheckintimeEqualTo(Date value) {
            addCriterion("checkinTime =", value, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckintimeNotEqualTo(Date value) {
            addCriterion("checkinTime <>", value, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckintimeGreaterThan(Date value) {
            addCriterion("checkinTime >", value, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("checkinTime >=", value, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckintimeLessThan(Date value) {
            addCriterion("checkinTime <", value, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckintimeLessThanOrEqualTo(Date value) {
            addCriterion("checkinTime <=", value, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckintimeIn(List<Date> values) {
            addCriterion("checkinTime in", values, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckintimeNotIn(List<Date> values) {
            addCriterion("checkinTime not in", values, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckintimeBetween(Date value1, Date value2) {
            addCriterion("checkinTime between", value1, value2, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckintimeNotBetween(Date value1, Date value2) {
            addCriterion("checkinTime not between", value1, value2, "checkintime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeIsNull() {
            addCriterion("checkoutTime is null");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeIsNotNull() {
            addCriterion("checkoutTime is not null");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeEqualTo(Date value) {
            addCriterion("checkoutTime =", value, "checkouttime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeNotEqualTo(Date value) {
            addCriterion("checkoutTime <>", value, "checkouttime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeGreaterThan(Date value) {
            addCriterion("checkoutTime >", value, "checkouttime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("checkoutTime >=", value, "checkouttime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeLessThan(Date value) {
            addCriterion("checkoutTime <", value, "checkouttime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeLessThanOrEqualTo(Date value) {
            addCriterion("checkoutTime <=", value, "checkouttime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeIn(List<Date> values) {
            addCriterion("checkoutTime in", values, "checkouttime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeNotIn(List<Date> values) {
            addCriterion("checkoutTime not in", values, "checkouttime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeBetween(Date value1, Date value2) {
            addCriterion("checkoutTime between", value1, value2, "checkouttime");
            return (Criteria) this;
        }

        public Criteria andCheckouttimeNotBetween(Date value1, Date value2) {
            addCriterion("checkoutTime not between", value1, value2, "checkouttime");
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

        public Criteria andOrdertimeIsNull() {
            addCriterion("orderTime is null");
            return (Criteria) this;
        }

        public Criteria andOrdertimeIsNotNull() {
            addCriterion("orderTime is not null");
            return (Criteria) this;
        }

        public Criteria andOrdertimeEqualTo(Date value) {
            addCriterion("orderTime =", value, "ordertime");
            return (Criteria) this;
        }

        public Criteria andOrdertimeNotEqualTo(Date value) {
            addCriterion("orderTime <>", value, "ordertime");
            return (Criteria) this;
        }

        public Criteria andOrdertimeGreaterThan(Date value) {
            addCriterion("orderTime >", value, "ordertime");
            return (Criteria) this;
        }

        public Criteria andOrdertimeGreaterThanOrEqualTo(Date value) {
            addCriterion("orderTime >=", value, "ordertime");
            return (Criteria) this;
        }

        public Criteria andOrdertimeLessThan(Date value) {
            addCriterion("orderTime <", value, "ordertime");
            return (Criteria) this;
        }

        public Criteria andOrdertimeLessThanOrEqualTo(Date value) {
            addCriterion("orderTime <=", value, "ordertime");
            return (Criteria) this;
        }

        public Criteria andOrdertimeIn(List<Date> values) {
            addCriterion("orderTime in", values, "ordertime");
            return (Criteria) this;
        }

        public Criteria andOrdertimeNotIn(List<Date> values) {
            addCriterion("orderTime not in", values, "ordertime");
            return (Criteria) this;
        }

        public Criteria andOrdertimeBetween(Date value1, Date value2) {
            addCriterion("orderTime between", value1, value2, "ordertime");
            return (Criteria) this;
        }

        public Criteria andOrdertimeNotBetween(Date value1, Date value2) {
            addCriterion("orderTime not between", value1, value2, "ordertime");
            return (Criteria) this;
        }

        public Criteria andDaynumberIsNull() {
            addCriterion("dayNumber is null");
            return (Criteria) this;
        }

        public Criteria andDaynumberIsNotNull() {
            addCriterion("dayNumber is not null");
            return (Criteria) this;
        }

        public Criteria andDaynumberEqualTo(Integer value) {
            addCriterion("dayNumber =", value, "daynumber");
            return (Criteria) this;
        }

        public Criteria andDaynumberNotEqualTo(Integer value) {
            addCriterion("dayNumber <>", value, "daynumber");
            return (Criteria) this;
        }

        public Criteria andDaynumberGreaterThan(Integer value) {
            addCriterion("dayNumber >", value, "daynumber");
            return (Criteria) this;
        }

        public Criteria andDaynumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("dayNumber >=", value, "daynumber");
            return (Criteria) this;
        }

        public Criteria andDaynumberLessThan(Integer value) {
            addCriterion("dayNumber <", value, "daynumber");
            return (Criteria) this;
        }

        public Criteria andDaynumberLessThanOrEqualTo(Integer value) {
            addCriterion("dayNumber <=", value, "daynumber");
            return (Criteria) this;
        }

        public Criteria andDaynumberIn(List<Integer> values) {
            addCriterion("dayNumber in", values, "daynumber");
            return (Criteria) this;
        }

        public Criteria andDaynumberNotIn(List<Integer> values) {
            addCriterion("dayNumber not in", values, "daynumber");
            return (Criteria) this;
        }

        public Criteria andDaynumberBetween(Integer value1, Integer value2) {
            addCriterion("dayNumber between", value1, value2, "daynumber");
            return (Criteria) this;
        }

        public Criteria andDaynumberNotBetween(Integer value1, Integer value2) {
            addCriterion("dayNumber not between", value1, value2, "daynumber");
            return (Criteria) this;
        }

        public Criteria andMikepriceIsNull() {
            addCriterion("mikePrice is null");
            return (Criteria) this;
        }

        public Criteria andMikepriceIsNotNull() {
            addCriterion("mikePrice is not null");
            return (Criteria) this;
        }

        public Criteria andMikepriceEqualTo(BigDecimal value) {
            addCriterion("mikePrice =", value, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andMikepriceNotEqualTo(BigDecimal value) {
            addCriterion("mikePrice <>", value, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andMikepriceGreaterThan(BigDecimal value) {
            addCriterion("mikePrice >", value, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andMikepriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("mikePrice >=", value, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andMikepriceLessThan(BigDecimal value) {
            addCriterion("mikePrice <", value, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andMikepriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("mikePrice <=", value, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andMikepriceIn(List<BigDecimal> values) {
            addCriterion("mikePrice in", values, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andMikepriceNotIn(List<BigDecimal> values) {
            addCriterion("mikePrice not in", values, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andMikepriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("mikePrice between", value1, value2, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andMikepriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("mikePrice not between", value1, value2, "mikeprice");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNull() {
            addCriterion("discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(BigDecimal value) {
            addCriterion("discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(BigDecimal value) {
            addCriterion("discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(BigDecimal value) {
            addCriterion("discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(BigDecimal value) {
            addCriterion("discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<BigDecimal> values) {
            addCriterion("discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<BigDecimal> values) {
            addCriterion("discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount not between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsIsNull() {
            addCriterion("lezhuCoins is null");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsIsNotNull() {
            addCriterion("lezhuCoins is not null");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsEqualTo(BigDecimal value) {
            addCriterion("lezhuCoins =", value, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsNotEqualTo(BigDecimal value) {
            addCriterion("lezhuCoins <>", value, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsGreaterThan(BigDecimal value) {
            addCriterion("lezhuCoins >", value, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lezhuCoins >=", value, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsLessThan(BigDecimal value) {
            addCriterion("lezhuCoins <", value, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lezhuCoins <=", value, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsIn(List<BigDecimal> values) {
            addCriterion("lezhuCoins in", values, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsNotIn(List<BigDecimal> values) {
            addCriterion("lezhuCoins not in", values, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lezhuCoins between", value1, value2, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andLezhucoinsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lezhuCoins not between", value1, value2, "lezhucoins");
            return (Criteria) this;
        }

        public Criteria andOrderpriceIsNull() {
            addCriterion("orderPrice is null");
            return (Criteria) this;
        }

        public Criteria andOrderpriceIsNotNull() {
            addCriterion("orderPrice is not null");
            return (Criteria) this;
        }

        public Criteria andOrderpriceEqualTo(BigDecimal value) {
            addCriterion("orderPrice =", value, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrderpriceNotEqualTo(BigDecimal value) {
            addCriterion("orderPrice <>", value, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrderpriceGreaterThan(BigDecimal value) {
            addCriterion("orderPrice >", value, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrderpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("orderPrice >=", value, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrderpriceLessThan(BigDecimal value) {
            addCriterion("orderPrice <", value, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrderpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("orderPrice <=", value, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrderpriceIn(List<BigDecimal> values) {
            addCriterion("orderPrice in", values, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrderpriceNotIn(List<BigDecimal> values) {
            addCriterion("orderPrice not in", values, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrderpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("orderPrice between", value1, value2, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrderpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("orderPrice not between", value1, value2, "orderprice");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNull() {
            addCriterion("orderType is null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIsNotNull() {
            addCriterion("orderType is not null");
            return (Criteria) this;
        }

        public Criteria andOrdertypeEqualTo(Integer value) {
            addCriterion("orderType =", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotEqualTo(Integer value) {
            addCriterion("orderType <>", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThan(Integer value) {
            addCriterion("orderType >", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("orderType >=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThan(Integer value) {
            addCriterion("orderType <", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeLessThanOrEqualTo(Integer value) {
            addCriterion("orderType <=", value, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeIn(List<Integer> values) {
            addCriterion("orderType in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotIn(List<Integer> values) {
            addCriterion("orderType not in", values, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeBetween(Integer value1, Integer value2) {
            addCriterion("orderType between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andOrdertypeNotBetween(Integer value1, Integer value2) {
            addCriterion("orderType not between", value1, value2, "ordertype");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameIsNull() {
            addCriterion("roomTypeName is null");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameIsNotNull() {
            addCriterion("roomTypeName is not null");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameEqualTo(String value) {
            addCriterion("roomTypeName =", value, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameNotEqualTo(String value) {
            addCriterion("roomTypeName <>", value, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameGreaterThan(String value) {
            addCriterion("roomTypeName >", value, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameGreaterThanOrEqualTo(String value) {
            addCriterion("roomTypeName >=", value, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameLessThan(String value) {
            addCriterion("roomTypeName <", value, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameLessThanOrEqualTo(String value) {
            addCriterion("roomTypeName <=", value, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameLike(String value) {
            addCriterion("roomTypeName like", value, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameNotLike(String value) {
            addCriterion("roomTypeName not like", value, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameIn(List<String> values) {
            addCriterion("roomTypeName in", values, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameNotIn(List<String> values) {
            addCriterion("roomTypeName not in", values, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameBetween(String value1, String value2) {
            addCriterion("roomTypeName between", value1, value2, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomtypenameNotBetween(String value1, String value2) {
            addCriterion("roomTypeName not between", value1, value2, "roomtypename");
            return (Criteria) this;
        }

        public Criteria andRoomnoIsNull() {
            addCriterion("roomNo is null");
            return (Criteria) this;
        }

        public Criteria andRoomnoIsNotNull() {
            addCriterion("roomNo is not null");
            return (Criteria) this;
        }

        public Criteria andRoomnoEqualTo(String value) {
            addCriterion("roomNo =", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoNotEqualTo(String value) {
            addCriterion("roomNo <>", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoGreaterThan(String value) {
            addCriterion("roomNo >", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoGreaterThanOrEqualTo(String value) {
            addCriterion("roomNo >=", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoLessThan(String value) {
            addCriterion("roomNo <", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoLessThanOrEqualTo(String value) {
            addCriterion("roomNo <=", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoLike(String value) {
            addCriterion("roomNo like", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoNotLike(String value) {
            addCriterion("roomNo not like", value, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoIn(List<String> values) {
            addCriterion("roomNo in", values, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoNotIn(List<String> values) {
            addCriterion("roomNo not in", values, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoBetween(String value1, String value2) {
            addCriterion("roomNo between", value1, value2, "roomno");
            return (Criteria) this;
        }

        public Criteria andRoomnoNotBetween(String value1, String value2) {
            addCriterion("roomNo not between", value1, value2, "roomno");
            return (Criteria) this;
        }

        public Criteria andPaymethodIsNull() {
            addCriterion("payMethod is null");
            return (Criteria) this;
        }

        public Criteria andPaymethodIsNotNull() {
            addCriterion("payMethod is not null");
            return (Criteria) this;
        }

        public Criteria andPaymethodEqualTo(String value) {
            addCriterion("payMethod =", value, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodNotEqualTo(String value) {
            addCriterion("payMethod <>", value, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodGreaterThan(String value) {
            addCriterion("payMethod >", value, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodGreaterThanOrEqualTo(String value) {
            addCriterion("payMethod >=", value, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodLessThan(String value) {
            addCriterion("payMethod <", value, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodLessThanOrEqualTo(String value) {
            addCriterion("payMethod <=", value, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodLike(String value) {
            addCriterion("payMethod like", value, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodNotLike(String value) {
            addCriterion("payMethod not like", value, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodIn(List<String> values) {
            addCriterion("payMethod in", values, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodNotIn(List<String> values) {
            addCriterion("payMethod not in", values, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodBetween(String value1, String value2) {
            addCriterion("payMethod between", value1, value2, "paymethod");
            return (Criteria) this;
        }

        public Criteria andPaymethodNotBetween(String value1, String value2) {
            addCriterion("payMethod not between", value1, value2, "paymethod");
            return (Criteria) this;
        }

        public Criteria andUsercostIsNull() {
            addCriterion("userCost is null");
            return (Criteria) this;
        }

        public Criteria andUsercostIsNotNull() {
            addCriterion("userCost is not null");
            return (Criteria) this;
        }

        public Criteria andUsercostEqualTo(BigDecimal value) {
            addCriterion("userCost =", value, "usercost");
            return (Criteria) this;
        }

        public Criteria andUsercostNotEqualTo(BigDecimal value) {
            addCriterion("userCost <>", value, "usercost");
            return (Criteria) this;
        }

        public Criteria andUsercostGreaterThan(BigDecimal value) {
            addCriterion("userCost >", value, "usercost");
            return (Criteria) this;
        }

        public Criteria andUsercostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("userCost >=", value, "usercost");
            return (Criteria) this;
        }

        public Criteria andUsercostLessThan(BigDecimal value) {
            addCriterion("userCost <", value, "usercost");
            return (Criteria) this;
        }

        public Criteria andUsercostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("userCost <=", value, "usercost");
            return (Criteria) this;
        }

        public Criteria andUsercostIn(List<BigDecimal> values) {
            addCriterion("userCost in", values, "usercost");
            return (Criteria) this;
        }

        public Criteria andUsercostNotIn(List<BigDecimal> values) {
            addCriterion("userCost not in", values, "usercost");
            return (Criteria) this;
        }

        public Criteria andUsercostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("userCost between", value1, value2, "usercost");
            return (Criteria) this;
        }

        public Criteria andUsercostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("userCost not between", value1, value2, "usercost");
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