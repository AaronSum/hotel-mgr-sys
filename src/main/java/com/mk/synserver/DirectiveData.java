package com.mk.synserver;

import java.io.Serializable;
import java.util.Map;

import com.mk.hms.server.DirectiveSet;

/**
 *
 * 指令数据.
 *
 * @author zhaoshb
 *
 */
public class DirectiveData implements Serializable {

	private static final long serialVersionUID = -9115453946169389011L;

	private int directive = DirectiveSet.DIRECTIVE_NULL;

	private Map<String, Object> data = null;

	public int getDirective() {
		return this.directive;
	}

	public void setDirective(int directive) {
		this.directive = directive;
	}

	public Map<String, Object> getData() {
		return this.data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
