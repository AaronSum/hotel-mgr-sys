package com.mk.hms.model;

import java.util.ArrayList;
import java.util.List;

public class WareImageCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    protected int limitStart = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    protected int limitEnd = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public WareImageCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
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

        public Criteria andWareidIsNull() {
            addCriterion("wareid is null");
            return (Criteria) this;
        }

        public Criteria andWareidIsNotNull() {
            addCriterion("wareid is not null");
            return (Criteria) this;
        }

        public Criteria andWareidEqualTo(Long value) {
            addCriterion("wareid =", value, "wareid");
            return (Criteria) this;
        }

        public Criteria andWareidNotEqualTo(Long value) {
            addCriterion("wareid <>", value, "wareid");
            return (Criteria) this;
        }

        public Criteria andWareidGreaterThan(Long value) {
            addCriterion("wareid >", value, "wareid");
            return (Criteria) this;
        }

        public Criteria andWareidGreaterThanOrEqualTo(Long value) {
            addCriterion("wareid >=", value, "wareid");
            return (Criteria) this;
        }

        public Criteria andWareidLessThan(Long value) {
            addCriterion("wareid <", value, "wareid");
            return (Criteria) this;
        }

        public Criteria andWareidLessThanOrEqualTo(Long value) {
            addCriterion("wareid <=", value, "wareid");
            return (Criteria) this;
        }

        public Criteria andWareidIn(List<Long> values) {
            addCriterion("wareid in", values, "wareid");
            return (Criteria) this;
        }

        public Criteria andWareidNotIn(List<Long> values) {
            addCriterion("wareid not in", values, "wareid");
            return (Criteria) this;
        }

        public Criteria andWareidBetween(Long value1, Long value2) {
            addCriterion("wareid between", value1, value2, "wareid");
            return (Criteria) this;
        }

        public Criteria andWareidNotBetween(Long value1, Long value2) {
            addCriterion("wareid not between", value1, value2, "wareid");
            return (Criteria) this;
        }

        public Criteria andImageurl1IsNull() {
            addCriterion("imageurl1 is null");
            return (Criteria) this;
        }

        public Criteria andImageurl1IsNotNull() {
            addCriterion("imageurl1 is not null");
            return (Criteria) this;
        }

        public Criteria andImageurl1EqualTo(String value) {
            addCriterion("imageurl1 =", value, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1NotEqualTo(String value) {
            addCriterion("imageurl1 <>", value, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1GreaterThan(String value) {
            addCriterion("imageurl1 >", value, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1GreaterThanOrEqualTo(String value) {
            addCriterion("imageurl1 >=", value, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1LessThan(String value) {
            addCriterion("imageurl1 <", value, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1LessThanOrEqualTo(String value) {
            addCriterion("imageurl1 <=", value, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1Like(String value) {
            addCriterion("imageurl1 like", value, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1NotLike(String value) {
            addCriterion("imageurl1 not like", value, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1In(List<String> values) {
            addCriterion("imageurl1 in", values, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1NotIn(List<String> values) {
            addCriterion("imageurl1 not in", values, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1Between(String value1, String value2) {
            addCriterion("imageurl1 between", value1, value2, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImageurl1NotBetween(String value1, String value2) {
            addCriterion("imageurl1 not between", value1, value2, "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1IsNull() {
            addCriterion("imagedesc1 is null");
            return (Criteria) this;
        }

        public Criteria andImagedesc1IsNotNull() {
            addCriterion("imagedesc1 is not null");
            return (Criteria) this;
        }

        public Criteria andImagedesc1EqualTo(String value) {
            addCriterion("imagedesc1 =", value, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1NotEqualTo(String value) {
            addCriterion("imagedesc1 <>", value, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1GreaterThan(String value) {
            addCriterion("imagedesc1 >", value, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1GreaterThanOrEqualTo(String value) {
            addCriterion("imagedesc1 >=", value, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1LessThan(String value) {
            addCriterion("imagedesc1 <", value, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1LessThanOrEqualTo(String value) {
            addCriterion("imagedesc1 <=", value, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1Like(String value) {
            addCriterion("imagedesc1 like", value, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1NotLike(String value) {
            addCriterion("imagedesc1 not like", value, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1In(List<String> values) {
            addCriterion("imagedesc1 in", values, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1NotIn(List<String> values) {
            addCriterion("imagedesc1 not in", values, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1Between(String value1, String value2) {
            addCriterion("imagedesc1 between", value1, value2, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1NotBetween(String value1, String value2) {
            addCriterion("imagedesc1 not between", value1, value2, "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImageurl2IsNull() {
            addCriterion("imageurl2 is null");
            return (Criteria) this;
        }

        public Criteria andImageurl2IsNotNull() {
            addCriterion("imageurl2 is not null");
            return (Criteria) this;
        }

        public Criteria andImageurl2EqualTo(String value) {
            addCriterion("imageurl2 =", value, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2NotEqualTo(String value) {
            addCriterion("imageurl2 <>", value, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2GreaterThan(String value) {
            addCriterion("imageurl2 >", value, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2GreaterThanOrEqualTo(String value) {
            addCriterion("imageurl2 >=", value, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2LessThan(String value) {
            addCriterion("imageurl2 <", value, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2LessThanOrEqualTo(String value) {
            addCriterion("imageurl2 <=", value, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2Like(String value) {
            addCriterion("imageurl2 like", value, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2NotLike(String value) {
            addCriterion("imageurl2 not like", value, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2In(List<String> values) {
            addCriterion("imageurl2 in", values, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2NotIn(List<String> values) {
            addCriterion("imageurl2 not in", values, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2Between(String value1, String value2) {
            addCriterion("imageurl2 between", value1, value2, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImageurl2NotBetween(String value1, String value2) {
            addCriterion("imageurl2 not between", value1, value2, "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2IsNull() {
            addCriterion("imagedesc2 is null");
            return (Criteria) this;
        }

        public Criteria andImagedesc2IsNotNull() {
            addCriterion("imagedesc2 is not null");
            return (Criteria) this;
        }

        public Criteria andImagedesc2EqualTo(String value) {
            addCriterion("imagedesc2 =", value, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2NotEqualTo(String value) {
            addCriterion("imagedesc2 <>", value, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2GreaterThan(String value) {
            addCriterion("imagedesc2 >", value, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2GreaterThanOrEqualTo(String value) {
            addCriterion("imagedesc2 >=", value, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2LessThan(String value) {
            addCriterion("imagedesc2 <", value, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2LessThanOrEqualTo(String value) {
            addCriterion("imagedesc2 <=", value, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2Like(String value) {
            addCriterion("imagedesc2 like", value, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2NotLike(String value) {
            addCriterion("imagedesc2 not like", value, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2In(List<String> values) {
            addCriterion("imagedesc2 in", values, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2NotIn(List<String> values) {
            addCriterion("imagedesc2 not in", values, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2Between(String value1, String value2) {
            addCriterion("imagedesc2 between", value1, value2, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2NotBetween(String value1, String value2) {
            addCriterion("imagedesc2 not between", value1, value2, "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImageurl3IsNull() {
            addCriterion("imageurl3 is null");
            return (Criteria) this;
        }

        public Criteria andImageurl3IsNotNull() {
            addCriterion("imageurl3 is not null");
            return (Criteria) this;
        }

        public Criteria andImageurl3EqualTo(String value) {
            addCriterion("imageurl3 =", value, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3NotEqualTo(String value) {
            addCriterion("imageurl3 <>", value, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3GreaterThan(String value) {
            addCriterion("imageurl3 >", value, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3GreaterThanOrEqualTo(String value) {
            addCriterion("imageurl3 >=", value, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3LessThan(String value) {
            addCriterion("imageurl3 <", value, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3LessThanOrEqualTo(String value) {
            addCriterion("imageurl3 <=", value, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3Like(String value) {
            addCriterion("imageurl3 like", value, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3NotLike(String value) {
            addCriterion("imageurl3 not like", value, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3In(List<String> values) {
            addCriterion("imageurl3 in", values, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3NotIn(List<String> values) {
            addCriterion("imageurl3 not in", values, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3Between(String value1, String value2) {
            addCriterion("imageurl3 between", value1, value2, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImageurl3NotBetween(String value1, String value2) {
            addCriterion("imageurl3 not between", value1, value2, "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3IsNull() {
            addCriterion("imagedesc3 is null");
            return (Criteria) this;
        }

        public Criteria andImagedesc3IsNotNull() {
            addCriterion("imagedesc3 is not null");
            return (Criteria) this;
        }

        public Criteria andImagedesc3EqualTo(String value) {
            addCriterion("imagedesc3 =", value, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3NotEqualTo(String value) {
            addCriterion("imagedesc3 <>", value, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3GreaterThan(String value) {
            addCriterion("imagedesc3 >", value, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3GreaterThanOrEqualTo(String value) {
            addCriterion("imagedesc3 >=", value, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3LessThan(String value) {
            addCriterion("imagedesc3 <", value, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3LessThanOrEqualTo(String value) {
            addCriterion("imagedesc3 <=", value, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3Like(String value) {
            addCriterion("imagedesc3 like", value, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3NotLike(String value) {
            addCriterion("imagedesc3 not like", value, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3In(List<String> values) {
            addCriterion("imagedesc3 in", values, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3NotIn(List<String> values) {
            addCriterion("imagedesc3 not in", values, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3Between(String value1, String value2) {
            addCriterion("imagedesc3 between", value1, value2, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3NotBetween(String value1, String value2) {
            addCriterion("imagedesc3 not between", value1, value2, "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andOrderbyIsNull() {
            addCriterion("orderby is null");
            return (Criteria) this;
        }

        public Criteria andOrderbyIsNotNull() {
            addCriterion("orderby is not null");
            return (Criteria) this;
        }

        public Criteria andOrderbyEqualTo(Integer value) {
            addCriterion("orderby =", value, "orderby");
            return (Criteria) this;
        }

        public Criteria andOrderbyNotEqualTo(Integer value) {
            addCriterion("orderby <>", value, "orderby");
            return (Criteria) this;
        }

        public Criteria andOrderbyGreaterThan(Integer value) {
            addCriterion("orderby >", value, "orderby");
            return (Criteria) this;
        }

        public Criteria andOrderbyGreaterThanOrEqualTo(Integer value) {
            addCriterion("orderby >=", value, "orderby");
            return (Criteria) this;
        }

        public Criteria andOrderbyLessThan(Integer value) {
            addCriterion("orderby <", value, "orderby");
            return (Criteria) this;
        }

        public Criteria andOrderbyLessThanOrEqualTo(Integer value) {
            addCriterion("orderby <=", value, "orderby");
            return (Criteria) this;
        }

        public Criteria andOrderbyIn(List<Integer> values) {
            addCriterion("orderby in", values, "orderby");
            return (Criteria) this;
        }

        public Criteria andOrderbyNotIn(List<Integer> values) {
            addCriterion("orderby not in", values, "orderby");
            return (Criteria) this;
        }

        public Criteria andOrderbyBetween(Integer value1, Integer value2) {
            addCriterion("orderby between", value1, value2, "orderby");
            return (Criteria) this;
        }

        public Criteria andOrderbyNotBetween(Integer value1, Integer value2) {
            addCriterion("orderby not between", value1, value2, "orderby");
            return (Criteria) this;
        }

        public Criteria andValidIsNull() {
            addCriterion("valid is null");
            return (Criteria) this;
        }

        public Criteria andValidIsNotNull() {
            addCriterion("valid is not null");
            return (Criteria) this;
        }

        public Criteria andValidEqualTo(Integer value) {
            addCriterion("valid =", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotEqualTo(Integer value) {
            addCriterion("valid <>", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThan(Integer value) {
            addCriterion("valid >", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThanOrEqualTo(Integer value) {
            addCriterion("valid >=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThan(Integer value) {
            addCriterion("valid <", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThanOrEqualTo(Integer value) {
            addCriterion("valid <=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidIn(List<Integer> values) {
            addCriterion("valid in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotIn(List<Integer> values) {
            addCriterion("valid not in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidBetween(Integer value1, Integer value2) {
            addCriterion("valid between", value1, value2, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotBetween(Integer value1, Integer value2) {
            addCriterion("valid not between", value1, value2, "valid");
            return (Criteria) this;
        }

        public Criteria andImagetypeIsNull() {
            addCriterion("imageType is null");
            return (Criteria) this;
        }

        public Criteria andImagetypeIsNotNull() {
            addCriterion("imageType is not null");
            return (Criteria) this;
        }

        public Criteria andImagetypeEqualTo(String value) {
            addCriterion("imageType =", value, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeNotEqualTo(String value) {
            addCriterion("imageType <>", value, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeGreaterThan(String value) {
            addCriterion("imageType >", value, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeGreaterThanOrEqualTo(String value) {
            addCriterion("imageType >=", value, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeLessThan(String value) {
            addCriterion("imageType <", value, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeLessThanOrEqualTo(String value) {
            addCriterion("imageType <=", value, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeLike(String value) {
            addCriterion("imageType like", value, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeNotLike(String value) {
            addCriterion("imageType not like", value, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeIn(List<String> values) {
            addCriterion("imageType in", values, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeNotIn(List<String> values) {
            addCriterion("imageType not in", values, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeBetween(String value1, String value2) {
            addCriterion("imageType between", value1, value2, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImagetypeNotBetween(String value1, String value2) {
            addCriterion("imageType not between", value1, value2, "imagetype");
            return (Criteria) this;
        }

        public Criteria andImageurl1LikeInsensitive(String value) {
            addCriterion("upper(imageurl1) like", value.toUpperCase(), "imageurl1");
            return (Criteria) this;
        }

        public Criteria andImagedesc1LikeInsensitive(String value) {
            addCriterion("upper(imagedesc1) like", value.toUpperCase(), "imagedesc1");
            return (Criteria) this;
        }

        public Criteria andImageurl2LikeInsensitive(String value) {
            addCriterion("upper(imageurl2) like", value.toUpperCase(), "imageurl2");
            return (Criteria) this;
        }

        public Criteria andImagedesc2LikeInsensitive(String value) {
            addCriterion("upper(imagedesc2) like", value.toUpperCase(), "imagedesc2");
            return (Criteria) this;
        }

        public Criteria andImageurl3LikeInsensitive(String value) {
            addCriterion("upper(imageurl3) like", value.toUpperCase(), "imageurl3");
            return (Criteria) this;
        }

        public Criteria andImagedesc3LikeInsensitive(String value) {
            addCriterion("upper(imagedesc3) like", value.toUpperCase(), "imagedesc3");
            return (Criteria) this;
        }

        public Criteria andImagetypeLikeInsensitive(String value) {
            addCriterion("upper(imageType) like", value.toUpperCase(), "imagetype");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated do_not_delete_during_merge Tue Aug 18 17:13:50 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table h_shopping_ware_image
     *
     * @mbggenerated Tue Aug 18 17:13:50 CST 2015
     */
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