package com.mk.hms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.CommentService;
import com.mk.hms.view.Page;

/**
 * 酒店评论控制类
 * @author hdy
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService = null;

	/**
	 * 获取某个酒店的所有未评论内容
	 * @return 评论内容列表
	 * @throws SessionTimeOutException 
	 */
    @RequestMapping("/unReply")
    @ResponseBody
    public Map<String,Object> findsUnReply(Page page) throws SessionTimeOutException{
    	return this.getCommentService().findsUnReplyByHotelId(page);
    }
    
    /**
     * 获取某个酒店的所有已评论内容
     * @return 评论内容列表
     * @throws SessionTimeOutException 
     */
    @RequestMapping("/replied")
    @ResponseBody
    public Map<String,Object> findsReplied(Page page) throws SessionTimeOutException{
    	return this.getCommentService().findsRepliedByHotelId(page);
    }
    /**
     * 根据id获取评价图片
     * @param id
     * @return
     */
    @RequestMapping("/getPics")
    @ResponseBody
    public OutModel getPics(Long id){
    		OutModel out = new OutModel(true);
    		List<String> list = this.getCommentService().getPictures(id);
    		out.setAttribute(list);
    		return out;
    }
	private CommentService getCommentService() {
		return commentService;
	}

}
