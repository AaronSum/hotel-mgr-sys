package com.mk.hms.service;

import com.mk.hms.enums.BillFeedbackTypeEnum;
import com.mk.hms.enums.HmsSimpleBBillConfirmCheckEnum;
import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.BillSpecialDetailMapper;
import com.mk.hms.mapper.BillSpecialOperinfoMapper;
import com.mk.hms.model.*;
import com.mk.hms.mapper.BillSpecialMapper;
import com.mk.hms.utils.HmsStringUtils;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.LoginUser;
import com.mk.hms.view.Page;
import com.mk.ots.fs.FSException;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Thinkpad
 * Date: 15-10-28
 * Time: 下午9:40
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class BillSpecialService {
    private static final Logger logger = LoggerFactory.getLogger(BillService.class);

    @Autowired
    private BillSpecialMapper billSpecialMapper;
    @Autowired
    private BillSpecialDetailMapper billSpecialDetailMapper;
    @Autowired
    private BillSpecialOperinfoMapper billSpecialOperinfoMapper;

    @Autowired
    private FeedbackService feedbackService;
    /**
     * 分页取对账单
     * @param example
     * @param page
     * @return
     */
    public Map<String, Object> getBillSpecial(BillSpecialExample example, Page page) throws Exception {
        Map<String, Object> outMap = new HashMap<String, Object>();
        int count = billSpecialMapper.countByExample(example);
        outMap.put("totalItems", count);

        example.setLimitStart(page.getStartIndex());
        example.setLimitEnd(page.getPageSize());
        example.setOrderByClause("id");
        List<BillSpecial> list = billSpecialMapper.selectByExample(example);
        for (BillSpecial special : list) {

            //申诉状态
            List<BillFeedback> notSettledList =
                    this.feedbackService.queryNotSettled(special.getId(), BillFeedbackTypeEnum.SPECIAL.getCode());

            if (notSettledList.isEmpty()) {
                special.setFeedbackIng(false);
            } else {
                special.setFeedbackIng(true);
            }
        }
        outMap.put("rows",list);
        return outMap;
    }

    /**
     * 分页取对账单明细
     * @param billSpecialId
     * @param page
     * @return
     */
    public Map<String, Object> getBillSpecialDetail(Long billSpecialId, Page page){
        Map<String, Object> outMap = new HashMap<String, Object>();
        BillSpecialDetailExample example = new BillSpecialDetailExample();
        example.createCriteria().andBillidEqualTo(billSpecialId);

        int count = billSpecialDetailMapper.countByExample(example);
        outMap.put("totalItems",count);

        example.setLimitStart(page.getStartIndex());
        example.setLimitEnd(page.getPageSize());
        List<BillSpecialDetail> list = billSpecialDetailMapper.selectByExample(example);
        outMap.put("rows",list);
        return outMap;
    }

    /**
     * 酒店审核金额操作
     * @return 审核状态
     * @throws java.text.ParseException  异常
     * @throws com.mk.hms.exception.SessionTimeOutException
     */
    public OutModel specialCheckAmount(long billId, String checkText, String changeCost) throws ParseException, SessionTimeOutException {
        // 返回对象
        OutModel out = new OutModel(false);

        // 是否为空
        if (HmsStringUtils.isBlank(checkText)) {
            out.setErrorMsg("该账单反馈内容不可为空");
            return out;
        }
        // 检查调整金额是否为空
        if (changeCost == null) {
            out.setErrorMsg("调整金额不可为空");
            return out;
        }
        BillSpecial billSpecial  = billSpecialMapper.selectByPrimaryKey(billId);
        Integer checkStatus = billSpecial.getCheckstatus();
        Integer financeStatus = billSpecial.getFinancestatus() == null ? 1 : billSpecial.getFinancestatus();
        // 不是【待确认】
        if(HmsSimpleBBillConfirmCheckEnum.Confirming.getValue() != billSpecial.getCheckstatus()){
            out.setErrorMsg("该账单当前状态不是【待确认】");
            return out;
        }
        // 添加确认金额
        billSpecial.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Disagreed.getValue());
        billSpecial.setHotelchangecost(NumberUtils.createBigDecimal(changeCost));
        billSpecialMapper.updateByPrimaryKeySelective(billSpecial);
        //添加hms操作审核纪录
        try {
            addOprInfoRecord(checkText, billSpecial, checkStatus, financeStatus);
        } catch (FSException e) {
            logger.info("specialCheckAmount addOprInfoRecord FSException", e);
            e.printStackTrace();
        }
        out.setSuccess(true);
        return out;
    }

    public OutModel specialConfirmAmount(Long billId) throws SessionTimeOutException {
        // 返回对象
        OutModel out = new OutModel(false);
        // 是否为空
        if (billId == null) {
            out.setErrorMsg("入参billId为空");
            return out;
        }
        BillSpecial billSpecial = billSpecialMapper.selectByPrimaryKey(billId);
        Integer checkStatus = billSpecial.getCheckstatus();
        Integer financeStatus = billSpecial.getFinancestatus() == null ? 1 : billSpecial.getFinancestatus();
        if (billSpecial == null || billSpecial.getId() == null) {
            out.setErrorMsg(String.format("根据入参billId[%s]查询到的记录为空", billId));
            return out;
        }
        // 不是【待确认】
        if (billSpecial.getCheckstatus() != HmsSimpleBBillConfirmCheckEnum.Confirming.getValue()) {
            out.setSuccess(false);
            out.setErrorMsg("该账单当前状态不是【待确认】");
            return out;
        }
        billSpecial.setCheckstatus(HmsSimpleBBillConfirmCheckEnum.Confirmed.getValue());
        billSpecialMapper.updateByPrimaryKey(billSpecial);
        //添加hms操作审核纪录
        try {
            addOprInfoRecord("", billSpecial, checkStatus, financeStatus);
        } catch (FSException e) {
            logger.info("specialCheckAmount addOprInfoRecord FSException", e);
            e.printStackTrace();
        }
        out.setSuccess(true);
        return out;
    }


    /**
     * 添加hms操作审核纪录
     * @param checkText
     * @param billSpecial
     * @throws SessionTimeOutException
     * @throws FSException
     */
    private void addOprInfoRecord(String checkText, BillSpecial billSpecial, Integer operBeforCheckStatus, Integer operBeforFinancesStatus) throws SessionTimeOutException, FSException {
        // 获取用户信息
        LoginUser loginser = SessionUtils.getSessionLoginUser();
        if (null == loginser) {
            throw new FSException("当前登录用户信息为空");
        }
        BillSpecialOperinfo billSpecialOperinfo = new BillSpecialOperinfo();
        User user = loginser.getUser();
        // 写入审核日志纪录
        billSpecialOperinfo.setSpecialId(billSpecial.getId());
        billSpecialOperinfo.setCheckstatus(operBeforCheckStatus.toString());
        billSpecialOperinfo.setCheckstatusname(HmsSimpleBBillConfirmCheckEnum.getEnumByCode(operBeforCheckStatus).getText());
        billSpecialOperinfo.setChangemoney(billSpecial.getChangecost());
        billSpecialOperinfo.setBillcost(billSpecial.getBillcost());
        billSpecialOperinfo.setFinancestatus(operBeforFinancesStatus.toString());
        billSpecialOperinfo.setFinancestatusname(operBeforFinancesStatus == 1 ? "未结算" : "结算");
        billSpecialOperinfo.setNote(checkText);
        billSpecialOperinfo.setChecktime(new Date());
        billSpecialOperinfo.setCheckuserid(user.getId());
        billSpecialOperinfo.setCheckusername(user.getLoginname());
        billSpecialOperinfo.setBacktime(new Date());
        billSpecialOperinfo.setBackuserid(user.getId());
        billSpecialOperinfo.setBackusername(user.getLoginname());
        billSpecialOperinfo.setBackmemo(checkText);
        billSpecialOperinfoMapper.insert(billSpecialOperinfo);
    }
}
