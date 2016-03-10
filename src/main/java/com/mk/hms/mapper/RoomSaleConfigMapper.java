package com.mk.hms.mapper;


import com.mk.hms.model.TRoomSaleConfig;


public interface RoomSaleConfigMapper {

    public TRoomSaleConfig queryRoomSaleConfig(TRoomSaleConfig bean);

    public TRoomSaleConfig queryRoomSaleConfigInfo(TRoomSaleConfig bean);
}
