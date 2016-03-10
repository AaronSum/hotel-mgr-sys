package com.mk.hms.model;

import java.util.List;

public interface BillSpecialOperinfoMapper {
    int countByExample(BillSpecialOperinfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BillSpecialOperinfo record);

    int insertSelective(BillSpecialOperinfo record);

    List<BillSpecialOperinfo> selectByExample(BillSpecialOperinfoExample example);

    BillSpecialOperinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillSpecialOperinfo record);

    int updateByPrimaryKey(BillSpecialOperinfo record);
}