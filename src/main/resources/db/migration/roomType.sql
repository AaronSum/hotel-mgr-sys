/*
   房间类型设置
DROP TABLE IF EXISTS `t_room_setting`;
*/
CREATE TABLE IF not exists `t_room_setting` (
  `id`           BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT 'id',
  `hotelId`      BIGINT(20)  NOT NULL COMMENT '酒店id',
  `roomTypeId`   BIGINT(20)  NOT NULL COMMENT '房间类型',
  `roomTypeName` varchar(50)  NOT NULL COMMENT '房间类型名称',
  `roomNo`       varchar(50) NOT NULL COMMENT '房间号  来源于:t_room.name',
  `bedTypeName`  varchar(50)   COMMENT '床型名称',
  `roomDirection` varchar(50) COMMENT '房间朝向',
  `roomFloor`  varchar(20)       COMMENT '房间楼层',
  `isWindow`   char(1) NOT NULL COMMENT '是否有窗  T-有   F-没有',
  `isStair`    char(1)          COMMENT '是否楼梯口  T-有   F-没有',
  `isElevator` char(1)          COMMENT '电梯口附近  T-有   F-没有',
  `isStreet`   char(1)          COMMENT '是否临街  T-有   F-没有',
  `cretedate`  datetime	        COMMENT '创建时间',
  `updatedate` datetime          COMMENT '更新时间',
  `valid`      char(1) DEFAULT 'T' COMMENT '是否有效 T-有效   F-无效',
  PRIMARY KEY (`id`),
  INDEX(`userId`),
  INDEX(`hotelId`),
  INDEX(`roomTypeId`),
  INDEX(`roomNo`),
  INDEX(`bedTypeName`)
)DEFAULT CHARSET=utf8; 



/*
   房间类型设置
DROP TABLE IF EXISTS `e_room_setting`;
*/
CREATE TABLE IF not exists `e_room_setting` (
  `id`           BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT 'id',
  `hotelId`      BIGINT(20)  NOT NULL COMMENT '酒店id',
  `roomTypeId`   BIGINT(20)  NOT NULL COMMENT '房间类型',
  `roomTypeName` varchar(50)  NOT NULL COMMENT '房间类型名称',
  `roomNo`       varchar(50) NOT NULL COMMENT '房间号  来源于:t_room.name',
  `bedTypeName`  varchar(50)   COMMENT '床型名称',
  `roomDirection` varchar(50) COMMENT '房间朝向',
  `roomFloor`  varchar(20)       COMMENT '房间楼层',
  `isWindow`   char(1) NOT NULL COMMENT '是否有窗  T-有   F-没有',
  `isStair`    char(1)          COMMENT '是否楼梯口  T-有   F-没有',
  `isElevator` char(1)          COMMENT '电梯口附近  T-有   F-没有',
  `isStreet`   char(1)          COMMENT '是否临街  T-有   F-没有',
  `cretedate`  datetime	        COMMENT '创建时间',
  `updatedate` datetime          COMMENT '更新时间',
  `valid`      char(1) DEFAULT 'T' COMMENT '是否有效 T-有效   F-无效',
  PRIMARY KEY (`id`),
  INDEX(`userId`),
  INDEX(`hotelId`),
  INDEX(`roomTypeId`),
  INDEX(`roomNo`),
  INDEX(`bedTypeName`)
)DEFAULT CHARSET=utf8; 
 
 