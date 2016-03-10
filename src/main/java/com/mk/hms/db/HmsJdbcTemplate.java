package com.mk.hms.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * jdbcTimplate
 * @author hdy
 *
 */
@Component
public class HmsJdbcTemplate {
	
	/**jdbcTimplate对象*/
	private static JdbcTemplate jdbcTemplate;
	
	/**namedParameterJdbcTemplate对象*/
	private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		HmsJdbcTemplate.jdbcTemplate = jdbcTemplate;
	}
	
	@Autowired
	public void setJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
		HmsJdbcTemplate.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	/**
	 * 获取jdbcTemplate
	 * @return jdbcTemplate
	 */
	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	/**
	 * 获取jdbcTemplate
	 * @return jdbcTemplate
	 */
	public static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
}
