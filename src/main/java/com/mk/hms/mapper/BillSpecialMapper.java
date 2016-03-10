package com.mk.hms.mapper;

import com.mk.hms.model.BillSpecial;
import com.mk.hms.model.BillSpecialExample;

import java.util.List;

public interface BillSpecialMapper {
    int countByExample(BillSpecialExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BillSpecial record);

    int insertSelective(BillSpecial record);

    List<BillSpecial> selectByExample(BillSpecialExample example);

    BillSpecial selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillSpecial record);

    int updateByPrimaryKey(BillSpecial record);
}