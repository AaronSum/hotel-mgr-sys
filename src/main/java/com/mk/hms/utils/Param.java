package com.mk.hms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mk.hms.model.HotelRule;

public class Param {
	private int id;
	private long hotelid;
	private int rulecode;
	private String isthreshold;

	
	public long getHotelid() {
		return hotelid;
	}

	public void setHotelid(long hotelid) {
		this.hotelid = hotelid;
	}

	public int getRulecode() {
		return rulecode;
	}

	public void setRulecode(int rulecode) {
		this.rulecode = rulecode;
	}

	public String getIsthreshold() {
		return isthreshold;
	}

	public void setIsthreshold(String isthreshold) {
		this.isthreshold = isthreshold;
	}
    public static Map<Long,List<HotelRule>> init(List<HotelRule> rules){
//    	List<Map<Object,List<HotelRule>>> list = new ArrayList<Map<Object,Object>>();
    	List<HotelRule> tmp = null;
    	Map<Long,List<HotelRule>> map = new HashMap<Long,List<HotelRule>>();
    	for(int i=0;i<rules.size();i++){
    		HotelRule rule = rules.get(i);
    		long hotelid = rule.getHotelid();
    		if(!map.containsKey(hotelid)){
    			tmp = new ArrayList<HotelRule>();
    			tmp.add(rule);
    		}else{
    			tmp = map.get(hotelid);
    			tmp.add(rule);
    		}
    		map.put(hotelid, tmp);
    	}
    	return map;
    }
	public static List<Param> convert(Map<Long,List<HotelRule>> map){
		List<Param> params = new ArrayList<Param>();
		Param p = new Param();
		Set<Long> set = map.keySet();
		for(long key:set){
			List<HotelRule> list = map.get(key);
			p.setHotelid(list.get(0).getHotelid());
			for(HotelRule rule:list){
				if(rule.getType()==1){
					p.setRulecode(1002);
				}
				if(rule.getType()==2){
					p.setIsthreshold(rule.getValue());
				}
			}
			params.add(p);
		}
		return params;
	}
	public static void main(String[] args) {

	}

}
