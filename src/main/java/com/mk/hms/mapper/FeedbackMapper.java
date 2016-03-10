package com.mk.hms.mapper;

import com.mk.hms.model.BillFeedback;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/12/24.
 */
public interface FeedbackMapper {
    public int save(BillFeedback billFeedback);

    public List<BillFeedback> query(Map param);

    public Integer count(Map param);
}
