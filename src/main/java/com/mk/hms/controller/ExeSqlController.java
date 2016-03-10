package com.mk.hms.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.db.HmsJdbcTemplate;
import com.mk.hms.model.OutModel;
 
/**
 * 执行sql
 */
@Controller
@RequestMapping("/exeSqlController")
public class ExeSqlController {
	
	/**
	 * 执行sql
	 * @return OutModel
	 */
	@RequestMapping("/exeSql")
	@ResponseBody
	private OutModel exeSql(String sqlStr,String egg) {
		OutModel out = new OutModel();
		if(null == egg || sqlStr == ""){
			out.setSuccess(false);
			out.setErrorMsg("执行sql失败");
			return out ;
		}
		String[] sqlList = sqlStr.split(";");
		try {
			JdbcTemplate jdbcTemplate = HmsJdbcTemplate.getJdbcTemplate();
			for (int i = 0; i < sqlList.length; i++) {
				String exeSql = sqlList[i].replaceAll("\n", " ");
				if(exeSql.trim().equals("")){
					continue;
				}
				//System.out.println("******" + exeSql + "******");
				jdbcTemplate.update(exeSql);
				 
			}
			out.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			out.setSuccess(false);
			out.setErrorMsg("执行sql失败");
		}
		return out;
	}	
	public static void main(String[] args) {
		 
	}
}
