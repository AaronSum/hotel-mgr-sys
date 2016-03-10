package com.mk.hms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.mk.hms.model.HmsBAreaRule;

/**
 * 规则表mapper接口
 * @author hao.jiang
 *
 */
public interface HmsBAreaRuleMapper {
	
	@Select("select id,rulecode,rulename from b_area_rule")
	List<HmsBAreaRule> findAreaRule();

}
