package com.mk.hms.mapper;

import com.mk.hms.model.BillOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BillOrderDetailMapper {

    public List<BillOrderDetail> queryByBillOrderWeekId(Map param);

    public Integer countByBillOrderWeekId(@Param(value="billOrderWeekId") Long billOrderWeekId);
}
