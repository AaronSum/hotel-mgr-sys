package com.mk.hms.mapper;

import java.util.List;
import java.util.Map;

import com.mk.hms.model.HmsEBasePriceModel;
import com.mk.hms.model.HmsEPriceModel;
import com.mk.hms.model.HmsEPriceTimeModel;
import com.mk.hms.model.HmsERoomtypeFacilityModel;
import com.mk.hms.model.HmsERoomtypeInfoModel;
import com.mk.hms.model.HmsTBasePriceModel;
import com.mk.hms.model.HmsTRoomtypeFacilityModel;
import com.mk.hms.model.HmsTRoomtypeInfoModel;
import com.mk.hms.model.HmsTRoomtypeModel;

public interface HotelAuditSynToTHotelMapper {
	/**
	 * 
	 * @param hmsTRoomtypeModels
	 * @return
	 */
	List<HmsEBasePriceModel> findTRoomTypeModels(List<HmsTRoomtypeModel> hmsTRoomtypeModels);

	/**
	 * 
	 * @param hmsTRoomtypeModels
	 * @return
	 */
	int deleteTs(List<HmsTRoomtypeModel> hmsTRoomtypeModels);

	List<HmsERoomtypeInfoModel> findRoomtypeByIds(List<HmsTRoomtypeModel> hmsTRoomtypeModels);

	int addTRoomtypeInfos(List<HmsTRoomtypeInfoModel> hmsrttModels);

	int updateTPrices(List<HmsTBasePriceModel> hmspriceModels);

	int addTBasePrices(List<HmsTBasePriceModel> hmspriceModels);

	int addTRoomtypeFacilitys(List<HmsTRoomtypeFacilityModel> hmsTRtFacModels);

	int updateBedNum(Map<Object, Object> map);

	List<HmsERoomtypeFacilityModel> findRoomtypeFacilityById(List<HmsTRoomtypeModel> hmsTRoomtypeModels);

	int deleteTByRoomtypeId(List<HmsTRoomtypeModel> hmsTRoomtypeModels);

	int insertTHotelFacilities(Map<Object, Object> map);

	List<HmsTBasePriceModel> findAllTBasePriceByIds(List<HmsTRoomtypeModel> hmsTRoomtypeModels);

	//
	List<HmsEPriceTimeModel> findTpriceByIds(List<HmsEPriceModel> pricelist);

	List<HmsEPriceModel> findPriceByRoomTypeId(List<HmsTRoomtypeModel> tRoomTypeList);

	// HmsRoomtypeController
	int add(Map<Object, Object> map);

	int delete(Map<Object, Object> map);
}
