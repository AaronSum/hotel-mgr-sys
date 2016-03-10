package com.mk.hms.mapper;

import com.mk.hms.model.BillSpecialDetail;
import com.mk.hms.model.BillSpecialDetailExample;
import java.util.List;

public interface BillSpecialDetailMapper {
    int countByExample(BillSpecialDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BillSpecialDetail record);

    int insertSelective(BillSpecialDetail record);

    List<BillSpecialDetail> selectByExample(BillSpecialDetailExample example);

    BillSpecialDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillSpecialDetail record);

    int updateByPrimaryKey(BillSpecialDetail record);
}