package com.mk.hms.mapper;

import com.mk.hms.model.BillSpecialDay;

public interface BillSpecialDayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillSpecialDay record);

    int insertSelective(BillSpecialDay record);

    BillSpecialDay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillSpecialDay record);

    int updateByPrimaryKey(BillSpecialDay record);
}