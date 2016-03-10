package com.mk.hms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.HotelScoreMapper;
import com.mk.hms.mapper.OtaOrderMapper;
import com.mk.hms.mapper.UMemberMapper;
import com.mk.hms.model.EHotelWithBLOBs;
import com.mk.hms.model.HotelScore;
import com.mk.hms.model.HotelScoreCriteria;
import com.mk.hms.model.OtaOrder;
import com.mk.hms.model.OtaOrderCriteria;
import com.mk.hms.model.UMember;
import com.mk.hms.model.UMemberCriteria;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.HotelScoreWithLoginName;
import com.mk.hms.view.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 酒店评论服务类
 * @author hdy
 *
 */
@Service
@Transactional
public class CommentService {
	
	/**评论类型*/
	private final int type1 = 1;
	
	/**未评论*/
	private final int status4 = 4;
	
	/**已评论*/
	private final int status5 = 5;
	
	/**所有评论*/
	private final int status6 = 6;
	/**1：待审核
	2：审核不通过待回复
	3：已删除
	4：审核通过待回复
	5：审核不通过已回复
	6：有效回复
	7：审核通过已回复*/
	private final int status7 = 7;
    @Autowired
    private HotelScoreMapper hotelScoreMapper = null;
    
    @Autowired
    private OtaOrderMapper otaOrderMapper = null;
    
    @Autowired
    private UMemberMapper uMemberMapper = null;
    
    
    
    /**
     * 获取某个酒店的所有已评论内容
     * @return 评论内容列表
     * @throws SessionTimeOutException 
     */
    public Map<String,Object> findsUnReplyByHotelId(Page page) throws SessionTimeOutException{
    	long hotelId1 = -1;
		EHotelWithBLOBs thisHotel = SessionUtils.getThisHotel();
		if(null!=thisHotel){
			hotelId1 = thisHotel.getId();
		}
		long hotelId = hotelId1;
		if (hotelId <= 0) {
			return null;
		}
		Map<String,Object> out = new HashMap<String,Object>();
		out.put("rows", this.resetHotelScoreList(hotelId, type1, status4, page));
		out.put("total", this.getHotelScoreCount(hotelId, type1, status4));
		return out;
    }
    
    /**
     * 获取某个酒店的所有已评论内容
     * @return 评论内容列表
     * @throws SessionTimeOutException 
     */
    public Map<String,Object> findsRepliedByHotelId(Page page) throws SessionTimeOutException{
    	long hotelId = SessionUtils.getThisHotelId();
		if (hotelId <= 0) {
			return null;
		}
		Map<String,Object> out = new HashMap<String,Object>();
		out.put("rows", this.getReply(this.resetHotelScoreList(hotelId, type1, status7, page)));
		out.put("total", this.getHotelScoreCount(hotelId, type1, status7));
		return out;
    }
    
    /**
     * 把酒店回复内容融合到评价列表，并返回
     * @param unreplyList 未加入回复内容的评价列表
     * @return
     */
    private List<HotelScoreWithLoginName> getReply(List<HotelScoreWithLoginName> unreplyList){
    	//获取回复内容列表
    	if(unreplyList != null && unreplyList.size() > 0){
    		List<HotelScore> replyList = this.findsReplyWithLoginName(unreplyList);
        	//把回复内容糅合到评价内容中
    		for (HotelScoreWithLoginName unReplyModel : unreplyList) {
    			for (HotelScore replyModel : replyList) {
    				if (unReplyModel.getId().equals(replyModel.getParentID())) {
    					unReplyModel.setServiceScore(replyModel.getScore());
    				}
    			}
    		}    	
        	return unreplyList;
    	}
    	return new ArrayList<HotelScoreWithLoginName>();	
    }
    
    /**
     * 获取全部评论内容
     * @param unreplyList 评论列表
     * @return 全部评论内容
     */
    /*private List<HotelScore> findsReply(List<HotelScore> unreplyList) {
    	List<Long> parentsIds = new ArrayList<Long>();
    	if (unreplyList.size() > 0) {
    		for (HotelScore hs : unreplyList) {
    			parentsIds.add(hs.getId());
        	}
    		HotelScoreCriteria example = new HotelScoreCriteria();
    		example.createCriteria().andStatusEqualTo(status6).andParentIDIn(parentsIds);
    		return this.getHotelScoreMapper().selectByExample(example);
    	}
    	return new ArrayList<HotelScore>();
    }*/
    
    /**
     * 获取全部评论内容
     * @param unreplyList 评论列表
     * @return 全部评论内容
     */
    private List<HotelScore> findsReplyWithLoginName(List<HotelScoreWithLoginName> unreplyList) {
    	List<Long> parentsIds = new ArrayList<Long>();
    	if (unreplyList.size() > 0) {
    		for (HotelScoreWithLoginName hs : unreplyList) {
    			parentsIds.add(hs.getId());
        	}
    		HotelScoreCriteria example = new HotelScoreCriteria();
    		example.createCriteria().andStatusEqualTo(status6).andParentIDIn(parentsIds);
    		return this.getHotelScoreMapper().selectByExample(example);
    	}
    	return new ArrayList<HotelScore>();
    }
    
    /**
     * 重组评论数据
     * @return 重组之后评论数据
     */
    private List<HotelScoreWithLoginName> resetHotelScoreList (long hotelId, int type, int status, Page page) {
    	List<HotelScoreWithLoginName> hswnList = new ArrayList<HotelScoreWithLoginName>();
    	List<HotelScore> scoreList = this.getHotelScoreByPage(hotelId, type, status, page);
    	List<OtaOrder> orderList = this.getOtaOrderByIds(scoreList);
    	List<UMember> mUserList = this.getUMemberByMid(orderList);
    	if(scoreList != null){
	    	for (HotelScore hs : scoreList ) {
	    		HotelScoreWithLoginName hswn = new HotelScoreWithLoginName();
	            BeanCopier beanCopier = BeanCopier.create(HotelScore.class, HotelScoreWithLoginName.class, false);
	            beanCopier.copy(hs, hswn, null);
	    		for (OtaOrder oo : orderList) {
	    			if (hs.getOrderId().equals(oo.getId())) {
	    				hswn.setmId(oo.getMid());
	    				for (UMember mu : mUserList ) {
	        				if (oo.getMid().equals(mu.getMid())) {
	        					hswn.setLoginName(mu.getLoginname());
	        					break;
	        				}
	        			}
	    			}
	    		}
	    		hswnList.add(hswn);
	    	}
    	}
    	return hswnList;
    }
    
    /**
     * 获取评论数据条数
     * @param hotelId 酒店id
     * @return 数据条数
     */
    private int getHotelScoreCount(long hotelId, int type, int status) {
    	HotelScoreCriteria example = new HotelScoreCriteria();
    	example.createCriteria().andHotelIdEqualTo(hotelId).andTypeEqualTo(type).andStatusEqualTo(status);
    	return this.getHotelScoreMapper().countByExample(example);
    }
    
    /**
     * 获取酒店评论列表
     * @param hotelId 酒店id
     * @param page 分页对象
     * @return 评论列表
     */
    private List<HotelScore> getHotelScoreByPage (long hotelId, int type, int status, Page page) {
    	HotelScoreCriteria example = new HotelScoreCriteria();
    	example.setLimitStart(page.getStartIndex());
    	example.setLimitEnd(page.getPageSize());
    	example.setOrderByClause("id desc");
    	example.createCriteria().andHotelIdEqualTo(hotelId).andTypeEqualTo(type).andStatusEqualTo(status);
    	return this.getHotelScoreMapper().selectByExample(example);
    } 
    
    /**
     * 获取订单列表
     * @param list 评论列表
     * @return 订单列表
     */
    private List<OtaOrder> getOtaOrderByIds(List<HotelScore> list) {
    	if (list != null && list.size() > 0) {
    		List<Long> orderIds = new ArrayList<Long>();
    		for (HotelScore hs : list) {
    			orderIds.add(hs.getOrderId());
    		}
    		OtaOrderCriteria example = new OtaOrderCriteria();
    		example.createCriteria().andIdIn(orderIds);
    		return this.getOtaOrderMapper().selectByExample(example);
    	}
		return new ArrayList<OtaOrder>();
    }
    
    /**
     * 获取用户列表
     * @param list 订单列表
     * @return 用户列表
     */
    private List<UMember> getUMemberByMid(List<OtaOrder> list) {
    	if (list != null && list.size() > 0) {
    		List<Long> mIds = new ArrayList<Long>();
    		for (OtaOrder oo : list) {
    			mIds.add(oo.getMid());
    		}
    		UMemberCriteria example = new UMemberCriteria();
    		example.createCriteria().andMidIn(mIds);
    		return this.getuMemberMapper().selectByExample(example);
    	}
    	return new ArrayList<UMember>();
    }
    /**
     * 根据id获得评论的4张图片
     * @return
     */
    public List<String> getPictures(Long id){
    		String pics = this.getHotelScoreMapper().getPicture(id);
    		List<String> picList = new ArrayList<String>();
	    	if(pics!=null){
	    		JSONArray  jsonArray = JSONArray.fromObject(pics);
	    		int size = jsonArray.size();
	    		for(int i=0;i<size;i++){
	    			JSONObject obj = jsonArray.getJSONObject(i);
	    			picList.add(obj.get("scorepicurl").toString());
	    		}
    		}
    		return picList;
    }
	private HotelScoreMapper getHotelScoreMapper() {
		return hotelScoreMapper;
	}

	private OtaOrderMapper getOtaOrderMapper() {
		return otaOrderMapper;
	}

	private UMemberMapper getuMemberMapper() {
		return uMemberMapper;
	}
}
