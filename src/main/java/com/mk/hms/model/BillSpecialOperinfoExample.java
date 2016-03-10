package com.mk.hms.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillSpecialOperinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BillSpecialOperinfoExample() {
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

        public Criteria andSpecialIdIsNull() {
            addCriterion("special_id is null");
            return (Criteria) this;
        }

        public Criteria andSpecialIdIsNotNull() {
            addCriterion("special_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialIdEqualTo(Long value) {
            addCriterion("special_id =", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdNotEqualTo(Long value) {
            addCriterion("special_id <>", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdGreaterThan(Long value) {
            addCriterion("special_id >", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdGreaterThanOrEqualTo(Long value) {
            addCriterion("special_id >=", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdLessThan(Long value) {
            addCriterion("special_id <", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdLessThanOrEqualTo(Long value) {
            addCriterion("special_id <=", value, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdIn(List<Long> values) {
            addCriterion("special_id in", values, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdNotIn(List<Long> values) {
            addCriterion("special_id not in", values, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdBetween(Long value1, Long value2) {
            addCriterion("special_id between", value1, value2, "specialId");
            return (Criteria) this;
        }

        public Criteria andSpecialIdNotBetween(Long value1, Long value2) {
            addCriterion("special_id not between", value1, value2, "specialId");
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

        public Criteria andCheckstatusIsNull() {
            addCriterion("checkStatus is null");
            return (Criteria) this;
        }

        public Criteria andCheckstatusIsNotNull() {
            addCriterion("checkStatus is not null");
            return (Criteria) this;
        }

        public Criteria andCheckstatusEqualTo(String value) {
            addCriterion("checkStatus =", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusNotEqualTo(String value) {
            addCriterion("checkStatus <>", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusGreaterThan(String value) {
            addCriterion("checkStatus >", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusGreaterThanOrEqualTo(String value) {
            addCriterion("checkStatus >=", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusLessThan(String value) {
            addCriterion("checkStatus <", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusLessThanOrEqualTo(String value) {
            addCriterion("checkStatus <=", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusLike(String value) {
            addCriterion("checkStatus like", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusNotLike(String value) {
            addCriterion("checkStatus not like", value, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusIn(List<String> values) {
            addCriterion("checkStatus in", values, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusNotIn(List<String> values) {
            addCriterion("checkStatus not in", values, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusBetween(String value1, String value2) {
            addCriterion("checkStatus between", value1, value2, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusNotBetween(String value1, String value2) {
            addCriterion("checkStatus not between", value1, value2, "checkstatus");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameIsNull() {
            addCriterion("checkStatusName is null");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameIsNotNull() {
            addCriterion("checkStatusName is not null");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameEqualTo(String value) {
            addCriterion("checkStatusName =", value, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameNotEqualTo(String value) {
            addCriterion("checkStatusName <>", value, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameGreaterThan(String value) {
            addCriterion("checkStatusName >", value, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameGreaterThanOrEqualTo(String value) {
            addCriterion("checkStatusName >=", value, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameLessThan(String value) {
            addCriterion("checkStatusName <", value, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameLessThanOrEqualTo(String value) {
            addCriterion("checkStatusName <=", value, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameLike(String value) {
            addCriterion("checkStatusName like", value, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameNotLike(String value) {
            addCriterion("checkStatusName not like", value, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameIn(List<String> values) {
            addCriterion("checkStatusName in", values, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameNotIn(List<String> values) {
            addCriterion("checkStatusName not in", values, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameBetween(String value1, String value2) {
            addCriterion("checkStatusName between", value1, value2, "checkstatusname");
            return (Criteria) this;
        }

        public Criteria andCheckstatusnameNotBetween(String value1, String value2) {
            addCriterion("checkStatusName not between", value1, value2, "checkstatusname");
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

        public Criteria andFinancestatusEqualTo(String value) {
            addCriterion("financeStatus =", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusNotEqualTo(String value) {
            addCriterion("financeStatus <>", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusGreaterThan(String value) {
            addCriterion("financeStatus >", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusGreaterThanOrEqualTo(String value) {
            addCriterion("financeStatus >=", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusLessThan(String value) {
            addCriterion("financeStatus <", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusLessThanOrEqualTo(String value) {
            addCriterion("financeStatus <=", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusLike(String value) {
            addCriterion("financeStatus like", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusNotLike(String value) {
            addCriterion("financeStatus not like", value, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusIn(List<String> values) {
            addCriterion("financeStatus in", values, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusNotIn(List<String> values) {
            addCriterion("financeStatus not in", values, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusBetween(String value1, String value2) {
            addCriterion("financeStatus between", value1, value2, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusNotBetween(String value1, String value2) {
            addCriterion("financeStatus not between", value1, value2, "financestatus");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameIsNull() {
            addCriterion("financeStatusName is null");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameIsNotNull() {
            addCriterion("financeStatusName is not null");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameEqualTo(String value) {
            addCriterion("financeStatusName =", value, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameNotEqualTo(String value) {
            addCriterion("financeStatusName <>", value, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameGreaterThan(String value) {
            addCriterion("financeStatusName >", value, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameGreaterThanOrEqualTo(String value) {
            addCriterion("financeStatusName >=", value, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameLessThan(String value) {
            addCriterion("financeStatusName <", value, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameLessThanOrEqualTo(String value) {
            addCriterion("financeStatusName <=", value, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameLike(String value) {
            addCriterion("financeStatusName like", value, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameNotLike(String value) {
            addCriterion("financeStatusName not like", value, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameIn(List<String> values) {
            addCriterion("financeStatusName in", values, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameNotIn(List<String> values) {
            addCriterion("financeStatusName not in", values, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameBetween(String value1, String value2) {
            addCriterion("financeStatusName between", value1, value2, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andFinancestatusnameNotBetween(String value1, String value2) {
            addCriterion("financeStatusName not between", value1, value2, "financestatusname");
            return (Criteria) this;
        }

        public Criteria andChangemoneyIsNull() {
            addCriterion("changeMoney is null");
            return (Criteria) this;
        }

        public Criteria andChangemoneyIsNotNull() {
            addCriterion("changeMoney is not null");
            return (Criteria) this;
        }

        public Criteria andChangemoneyEqualTo(BigDecimal value) {
            addCriterion("changeMoney =", value, "changemoney");
            return (Criteria) this;
        }

        public Criteria andChangemoneyNotEqualTo(BigDecimal value) {
            addCriterion("changeMoney <>", value, "changemoney");
            return (Criteria) this;
        }

        public Criteria andChangemoneyGreaterThan(BigDecimal value) {
            addCriterion("changeMoney >", value, "changemoney");
            return (Criteria) this;
        }

        public Criteria andChangemoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("changeMoney >=", value, "changemoney");
            return (Criteria) this;
        }

        public Criteria andChangemoneyLessThan(BigDecimal value) {
            addCriterion("changeMoney <", value, "changemoney");
            return (Criteria) this;
        }

        public Criteria andChangemoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("changeMoney <=", value, "changemoney");
            return (Criteria) this;
        }

        public Criteria andChangemoneyIn(List<BigDecimal> values) {
            addCriterion("changeMoney in", values, "changemoney");
            return (Criteria) this;
        }

        public Criteria andChangemoneyNotIn(List<BigDecimal> values) {
            addCriterion("changeMoney not in", values, "changemoney");
            return (Criteria) this;
        }

        public Criteria andChangemoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("changeMoney between", value1, value2, "changemoney");
            return (Criteria) this;
        }

        public Criteria andChangemoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("changeMoney not between", value1, value2, "changemoney");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andCheckuseridIsNull() {
            addCriterion("checkUserid is null");
            return (Criteria) this;
        }

        public Criteria andCheckuseridIsNotNull() {
            addCriterion("checkUserid is not null");
            return (Criteria) this;
        }

        public Criteria andCheckuseridEqualTo(Long value) {
            addCriterion("checkUserid =", value, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckuseridNotEqualTo(Long value) {
            addCriterion("checkUserid <>", value, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckuseridGreaterThan(Long value) {
            addCriterion("checkUserid >", value, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckuseridGreaterThanOrEqualTo(Long value) {
            addCriterion("checkUserid >=", value, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckuseridLessThan(Long value) {
            addCriterion("checkUserid <", value, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckuseridLessThanOrEqualTo(Long value) {
            addCriterion("checkUserid <=", value, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckuseridIn(List<Long> values) {
            addCriterion("checkUserid in", values, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckuseridNotIn(List<Long> values) {
            addCriterion("checkUserid not in", values, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckuseridBetween(Long value1, Long value2) {
            addCriterion("checkUserid between", value1, value2, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckuseridNotBetween(Long value1, Long value2) {
            addCriterion("checkUserid not between", value1, value2, "checkuserid");
            return (Criteria) this;
        }

        public Criteria andCheckusernameIsNull() {
            addCriterion("checkUsername is null");
            return (Criteria) this;
        }

        public Criteria andCheckusernameIsNotNull() {
            addCriterion("checkUsername is not null");
            return (Criteria) this;
        }

        public Criteria andCheckusernameEqualTo(String value) {
            addCriterion("checkUsername =", value, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameNotEqualTo(String value) {
            addCriterion("checkUsername <>", value, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameGreaterThan(String value) {
            addCriterion("checkUsername >", value, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameGreaterThanOrEqualTo(String value) {
            addCriterion("checkUsername >=", value, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameLessThan(String value) {
            addCriterion("checkUsername <", value, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameLessThanOrEqualTo(String value) {
            addCriterion("checkUsername <=", value, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameLike(String value) {
            addCriterion("checkUsername like", value, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameNotLike(String value) {
            addCriterion("checkUsername not like", value, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameIn(List<String> values) {
            addCriterion("checkUsername in", values, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameNotIn(List<String> values) {
            addCriterion("checkUsername not in", values, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameBetween(String value1, String value2) {
            addCriterion("checkUsername between", value1, value2, "checkusername");
            return (Criteria) this;
        }

        public Criteria andCheckusernameNotBetween(String value1, String value2) {
            addCriterion("checkUsername not between", value1, value2, "checkusername");
            return (Criteria) this;
        }

        public Criteria andChecktimeIsNull() {
            addCriterion("checkTime is null");
            return (Criteria) this;
        }

        public Criteria andChecktimeIsNotNull() {
            addCriterion("checkTime is not null");
            return (Criteria) this;
        }

        public Criteria andChecktimeEqualTo(Date value) {
            addCriterion("checkTime =", value, "checktime");
            return (Criteria) this;
        }

        public Criteria andChecktimeNotEqualTo(Date value) {
            addCriterion("checkTime <>", value, "checktime");
            return (Criteria) this;
        }

        public Criteria andChecktimeGreaterThan(Date value) {
            addCriterion("checkTime >", value, "checktime");
            return (Criteria) this;
        }

        public Criteria andChecktimeGreaterThanOrEqualTo(Date value) {
            addCriterion("checkTime >=", value, "checktime");
            return (Criteria) this;
        }

        public Criteria andChecktimeLessThan(Date value) {
            addCriterion("checkTime <", value, "checktime");
            return (Criteria) this;
        }

        public Criteria andChecktimeLessThanOrEqualTo(Date value) {
            addCriterion("checkTime <=", value, "checktime");
            return (Criteria) this;
        }

        public Criteria andChecktimeIn(List<Date> values) {
            addCriterion("checkTime in", values, "checktime");
            return (Criteria) this;
        }

        public Criteria andChecktimeNotIn(List<Date> values) {
            addCriterion("checkTime not in", values, "checktime");
            return (Criteria) this;
        }

        public Criteria andChecktimeBetween(Date value1, Date value2) {
            addCriterion("checkTime between", value1, value2, "checktime");
            return (Criteria) this;
        }

        public Criteria andChecktimeNotBetween(Date value1, Date value2) {
            addCriterion("checkTime not between", value1, value2, "checktime");
            return (Criteria) this;
        }

        public Criteria andBackuseridIsNull() {
            addCriterion("backUserId is null");
            return (Criteria) this;
        }

        public Criteria andBackuseridIsNotNull() {
            addCriterion("backUserId is not null");
            return (Criteria) this;
        }

        public Criteria andBackuseridEqualTo(Long value) {
            addCriterion("backUserId =", value, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackuseridNotEqualTo(Long value) {
            addCriterion("backUserId <>", value, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackuseridGreaterThan(Long value) {
            addCriterion("backUserId >", value, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackuseridGreaterThanOrEqualTo(Long value) {
            addCriterion("backUserId >=", value, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackuseridLessThan(Long value) {
            addCriterion("backUserId <", value, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackuseridLessThanOrEqualTo(Long value) {
            addCriterion("backUserId <=", value, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackuseridIn(List<Long> values) {
            addCriterion("backUserId in", values, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackuseridNotIn(List<Long> values) {
            addCriterion("backUserId not in", values, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackuseridBetween(Long value1, Long value2) {
            addCriterion("backUserId between", value1, value2, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackuseridNotBetween(Long value1, Long value2) {
            addCriterion("backUserId not between", value1, value2, "backuserid");
            return (Criteria) this;
        }

        public Criteria andBackusernameIsNull() {
            addCriterion("backUserName is null");
            return (Criteria) this;
        }

        public Criteria andBackusernameIsNotNull() {
            addCriterion("backUserName is not null");
            return (Criteria) this;
        }

        public Criteria andBackusernameEqualTo(String value) {
            addCriterion("backUserName =", value, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameNotEqualTo(String value) {
            addCriterion("backUserName <>", value, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameGreaterThan(String value) {
            addCriterion("backUserName >", value, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameGreaterThanOrEqualTo(String value) {
            addCriterion("backUserName >=", value, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameLessThan(String value) {
            addCriterion("backUserName <", value, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameLessThanOrEqualTo(String value) {
            addCriterion("backUserName <=", value, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameLike(String value) {
            addCriterion("backUserName like", value, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameNotLike(String value) {
            addCriterion("backUserName not like", value, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameIn(List<String> values) {
            addCriterion("backUserName in", values, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameNotIn(List<String> values) {
            addCriterion("backUserName not in", values, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameBetween(String value1, String value2) {
            addCriterion("backUserName between", value1, value2, "backusername");
            return (Criteria) this;
        }

        public Criteria andBackusernameNotBetween(String value1, String value2) {
            addCriterion("backUserName not between", value1, value2, "backusername");
            return (Criteria) this;
        }

        public Criteria andBacktimeIsNull() {
            addCriterion("backTime is null");
            return (Criteria) this;
        }

        public Criteria andBacktimeIsNotNull() {
            addCriterion("backTime is not null");
            return (Criteria) this;
        }

        public Criteria andBacktimeEqualTo(Date value) {
            addCriterion("backTime =", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeNotEqualTo(Date value) {
            addCriterion("backTime <>", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeGreaterThan(Date value) {
            addCriterion("backTime >", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeGreaterThanOrEqualTo(Date value) {
            addCriterion("backTime >=", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeLessThan(Date value) {
            addCriterion("backTime <", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeLessThanOrEqualTo(Date value) {
            addCriterion("backTime <=", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeIn(List<Date> values) {
            addCriterion("backTime in", values, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeNotIn(List<Date> values) {
            addCriterion("backTime not in", values, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeBetween(Date value1, Date value2) {
            addCriterion("backTime between", value1, value2, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeNotBetween(Date value1, Date value2) {
            addCriterion("backTime not between", value1, value2, "backtime");
            return (Criteria) this;
        }

        public Criteria andBackmemoIsNull() {
            addCriterion("backMemo is null");
            return (Criteria) this;
        }

        public Criteria andBackmemoIsNotNull() {
            addCriterion("backMemo is not null");
            return (Criteria) this;
        }

        public Criteria andBackmemoEqualTo(String value) {
            addCriterion("backMemo =", value, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoNotEqualTo(String value) {
            addCriterion("backMemo <>", value, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoGreaterThan(String value) {
            addCriterion("backMemo >", value, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoGreaterThanOrEqualTo(String value) {
            addCriterion("backMemo >=", value, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoLessThan(String value) {
            addCriterion("backMemo <", value, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoLessThanOrEqualTo(String value) {
            addCriterion("backMemo <=", value, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoLike(String value) {
            addCriterion("backMemo like", value, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoNotLike(String value) {
            addCriterion("backMemo not like", value, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoIn(List<String> values) {
            addCriterion("backMemo in", values, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoNotIn(List<String> values) {
            addCriterion("backMemo not in", values, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoBetween(String value1, String value2) {
            addCriterion("backMemo between", value1, value2, "backmemo");
            return (Criteria) this;
        }

        public Criteria andBackmemoNotBetween(String value1, String value2) {
            addCriterion("backMemo not between", value1, value2, "backmemo");
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