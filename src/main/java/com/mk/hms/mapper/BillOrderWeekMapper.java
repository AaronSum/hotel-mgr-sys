package com.mk.hms.mapper;

import com.mk.hms.model.BillOrderWeek;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BillOrderWeekMapper {

    public List<BillOrderWeek> query(Map param);
    public Integer count(Map param);

    public BillOrderWeek queryById(@Param(value="id") Long id);
}
