/*
   商品分类数据表
*/
DROP TABLE IF EXISTS `h_shopping_ware_category`;
CREATE TABLE `h_shopping_ware_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `categoryname` varchar(30) DEFAULT NULL COMMENT '分类名称',
  `orderby` int(11) DEFAULT '0' COMMENT '排序字段',
  `valid` int(11) DEFAULT '1' COMMENT '是否有效 1-有效   2-无效',
  PRIMARY KEY (`id`),
  KEY `categoryname` (`categoryname`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of h_shopping_ware_category
-- ----------------------------
INSERT INTO h_shopping_ware_category VALUES ('1', '日用品', '20', '1');
INSERT INTO h_shopping_ware_category VALUES ('2', '食品饮料', '10', '1');
 
/*
   商品信息表
*/
DROP TABLE IF EXISTS `h_shopping_ware`;
CREATE TABLE `h_shopping_ware` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `categoryid` bigint(20) DEFAULT NULL COMMENT '分类编号',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品原价',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
  `fee` decimal(10,2) DEFAULT NULL COMMENT '运费',
  `imageurllarge` varchar(200) DEFAULT NULL COMMENT '大图片默认url地址',
  `imagedesclarge` varchar(200) DEFAULT NULL COMMENT '大图片默认描述',
  `imageurlmiddle` varchar(200) DEFAULT NULL COMMENT '中图片默认url地址',
  `imagedescmiddle` varchar(200) DEFAULT NULL COMMENT '中图片默认描述',
  `imageurlsmall` varchar(200) DEFAULT NULL COMMENT '小图片默认url地址',
  `imagedescsmall` varchar(200) DEFAULT NULL COMMENT '小图片默认描述',
  `adlanguage` varchar(200) DEFAULT NULL COMMENT '广告语',
  `issg` int(11) DEFAULT '1' COMMENT '是否上柜 1-上柜   2-下柜',
  `stockNum` int(11) DEFAULT NULL COMMENT '库存数量',
  `promotionAdLanguage` varchar(200) DEFAULT NULL COMMENT '促销广告语',
  `orderby` int(11) DEFAULT '0' COMMENT '排序字段',
  `srouceprice` decimal(10,2) DEFAULT '0.00' COMMENT '原价',
  PRIMARY KEY (`id`),
  KEY `categoryid` (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h_shopping_ware
-- ----------------------------
INSERT INTO h_shopping_ware VALUES ('1', '冰露 矿物质饮用水550ml*24瓶/箱', '2', '19.20', '0.00', '0.00', 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_d1.jpg', null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_z1.jpg', null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_x1.jpg', null, '口感甘冽，清澈透明', '1', '8', '十箱更优惠', '0', '24.00');
INSERT INTO h_shopping_ware VALUES ('2', '统一冰红茶500ml（快速配送）', '2', '28.90', '0.00', '0.00', 'http://7xitp4.com2.z0.glb.qiniucdn.com/1_1_d.jpg', null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/1_1_z.jpg', null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/1_1_x.jpg', null, '', '1', '0', null, '0', '30.00');
INSERT INTO h_shopping_ware VALUES ('3', '加多宝凉茶310ml*24罐 整箱', '2', '76.90', '0.00', '0.00', 'http://7xitp4.com2.z0.glb.qiniucdn.com/2_1_d.jpg', null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/2_1_z.jpg', null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/2_1_x.jpg', null, '缤纷夏季，清凉一夏，鹊桥相会，与水之欢', '1', '0', null, '0', '80.00');


/*
   商品图片信息表
*/
DROP TABLE IF EXISTS `h_shopping_ware_image`;
CREATE TABLE `h_shopping_ware_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wareid` bigint(20) DEFAULT NULL COMMENT '商品id',
  `imageurl1` varchar(200) DEFAULT NULL COMMENT '图片url地址',
  `imagedesc1` varchar(200) DEFAULT NULL COMMENT '图片描述',
  `imageurl2` varchar(200) DEFAULT NULL COMMENT '图片url地址',
  `imagedesc2` varchar(200) DEFAULT NULL COMMENT '图片描述',
  `imageurl3` varchar(200) DEFAULT NULL COMMENT '图片url地址',
  `imagedesc3` varchar(200) DEFAULT NULL COMMENT '图片描述',
  `orderby` int(11) DEFAULT '0' COMMENT '排序字段',
  `valid` int(11) DEFAULT '1' COMMENT '是否有效 1-有效   2-无效',
  `imageType` varchar(1) DEFAULT NULL COMMENT '图片类型  1-商品展示用  2-商品介绍用',
  PRIMARY KEY (`id`),
  KEY `wareid` (`wareid`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h_shopping_ware_image
-- ----------------------------
INSERT INTO h_shopping_ware_image VALUES ('1', '2', 'http://7xitp4.com2.z0.glb.qiniucdn.com/1_1_d.jpg', null, '', null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/1_1_x.jpg', null, '0', '1', '0');
INSERT INTO h_shopping_ware_image VALUES ('2', '2', 'http://7xitp4.com2.z0.glb.qiniucdn.com/1_2_d.jpg', null, '', null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/1_2_x.jpg', null, '0', '1', '0');
INSERT INTO h_shopping_ware_image VALUES ('4', '3', 'http://7xitp4.com2.z0.glb.qiniucdn.com/2_1_d.jpg', null, null, null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/2_1_x.jpg', null, '0', '1', '0');
INSERT INTO h_shopping_ware_image VALUES ('5', '3', 'http://7xitp4.com2.z0.glb.qiniucdn.com/2_3_d.jpg', null, null, null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/2_3_x.jpg', null, '0', '1', '0');
INSERT INTO h_shopping_ware_image VALUES ('6', '3', 'http://7xitp4.com2.z0.glb.qiniucdn.com/2_2_d.jpg', null, null, null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/2_2_x.jpg', null, '0', '1', '0');
INSERT INTO h_shopping_ware_image VALUES ('7', '1', 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_d1.jpg', '', '', '', 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_x1.jpg', '', '0', '1', '1');
INSERT INTO h_shopping_ware_image VALUES ('8', '1', 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_d2.jpg', '', '', '', 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_x2.jpg', '', '0', '1', '1');
INSERT INTO h_shopping_ware_image VALUES ('28', '1', 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_7_d3.jpg', null, null, null, null, null, '0', '1', '2');
INSERT INTO h_shopping_ware_image VALUES ('29', '1', 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_d3.jpg', null, null, null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_x3.jpg', null, '0', '1', '1');
INSERT INTO h_shopping_ware_image VALUES ('31', '1', 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_d4.jpg', null, null, null, 'http://7xitp4.com2.z0.glb.qiniucdn.com/3_1_x4.jpg', null, '0', '1', '1');


 /*
   商品属性
*/
DROP TABLE IF EXISTS `h_shopping_ware_attr`;
CREATE TABLE `h_shopping_ware_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wareId` bigint(20) DEFAULT NULL COMMENT '商品编码',
  `attr` varchar(100) DEFAULT NULL COMMENT '商品属性',
  `attrValue` varchar(100) DEFAULT NULL COMMENT '商品属性值',
  `orderby` int(11) DEFAULT '0' COMMENT '排序字段',
  `valid` int(11) DEFAULT '1' COMMENT '是否有效 1-有效   2-无效',
  PRIMARY KEY (`id`),
  KEY `wareId` (`wareId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h_shopping_ware_attr
-- ----------------------------
INSERT INTO h_shopping_ware_attr VALUES ('1', '1', '商品名称', '冰露 矿物质饮用水550ml', '1', '1');
INSERT INTO h_shopping_ware_attr VALUES ('2', '1', '商品编号', '1', '2', '1');
INSERT INTO h_shopping_ware_attr VALUES ('4', '1', '上架时间', '2015-08-17', '3', '1');
INSERT INTO h_shopping_ware_attr VALUES ('5', '1', '商品毛重', '13.2kg', '4', '1');
INSERT INTO h_shopping_ware_attr VALUES ('6', '1', '类别', '纯净水', '5', '1');
INSERT INTO h_shopping_ware_attr VALUES ('7', '1', '包装', ' 箱装', '6', '1');
INSERT INTO h_shopping_ware_attr VALUES ('8', '1', '特性', '无汽', '7', '1');

/*
   促销信息
*/
DROP TABLE IF EXISTS `h_shopping_ware_promotion`;
CREATE TABLE `h_shopping_ware_promotion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `baseData` decimal(10,4) DEFAULT NULL COMMENT '基础数据(金额)',
  `promotionAData` decimal(10,4) DEFAULT NULL COMMENT '促销数据(金额or折扣比)',
  `promotionDesc` varchar(200) DEFAULT NULL COMMENT '促销描述',
  `startdate` datetime DEFAULT '2010-01-01 00:00:00' COMMENT '促销开始时间',
  `enddate` datetime DEFAULT '2010-01-01 00:00:00' COMMENT '促销结束时间',
  `promotionType` int(3) DEFAULT '1' COMMENT '促销类型 1-满减   2-满折  3-满赠',
  `isCyclePromotion` int(3) DEFAULT '1' COMMENT '是否循环 1-是   2-不是',
  `valid` int(3) DEFAULT '1' COMMENT '是否有效 1-有效   2-无效',
  `cretedate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  `num` int(20) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h_shopping_ware_promotion
-- ----------------------------
INSERT INTO h_shopping_ware_promotion VALUES ('1', '192.0000', '0.8750', '19.2元/箱（0.8元/瓶），十箱以上（含十箱）价格为16.8元/箱（0.7元/瓶）', '1970-01-01 00:00:00', '2099-01-01 00:00:00', '2', '1', '1', null, null, '10');
INSERT INTO h_shopping_ware_promotion VALUES ('2', '192.0000', '24.0000', '19.2元/箱（0.8元/瓶），十箱以上（含十箱）价格为16.8元/箱（0.7元/瓶）', '1970-01-01 00:00:00', '2099-01-01 00:00:00', '1', '1', '1', null, null, null);


/*
   商品和促销信息映射表
*/
DROP TABLE IF EXISTS `h_shopping_ware_promotion_mapping`;
CREATE TABLE `h_shopping_ware_promotion_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `promotionId` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `wareId` bigint(20) DEFAULT NULL COMMENT '促销ID',
  `startdate` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '促销开始时间',
  `enddate` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '促销结束时间',
  `valid` int(11) DEFAULT '1' COMMENT '是否有效 1-有效   2-无效',
  PRIMARY KEY (`id`),
  KEY `promotionId` (`promotionId`),
  KEY `wareId` (`wareId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h_shopping_ware_promotion_mapping
-- ----------------------------
INSERT INTO h_shopping_ware_promotion_mapping VALUES ('1', '1', '1', '1970-01-01 00:00:00', '2095-07-22 00:00:00', '1');

/*
    运费
*/
DROP TABLE IF EXISTS `h_shopping_ware_freight`;
CREATE TABLE IF not exists `h_shopping_ware_freight` (
    `id`            BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT 'id',
	`deliveryInfo`	    varchar(200) COMMENT '送货信息',
	`baseFreight`	    decimal(10,2)	 COMMENT '基础运费',
	`actualFreight`	    decimal(10,2)	 COMMENT '实际运费',
	`freightDesc`	    varchar(200) COMMENT '运费描述',
    PRIMARY KEY (`id`)
)DEFAULT CHARSET=utf8;
 
/*
   商品订单    修改
*/
DROP TABLE IF EXISTS `h_shopping_order`;
CREATE TABLE IF not exists `h_shopping_order` (
    `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `orderid`     varchar(50) NOT NULL COMMENT '订单编号',
    `userid`      BIGINT(20)   	COMMENT '用户id	h_user表的id',
	`loginuser`	  varchar(30)	COMMENT '注册用户名	酒店老板账号名',
	`hotelid`     BIGINT(20)	COMMENT '酒店id',
	`orderamount` decimal(10,2) COMMENT '订单金额',
	`orderdate`	  datetime	COMMENT '订单创建时间（年月日时分秒）',
	`cretedate`	  datetime	COMMENT '创建时间',
	`updatedate`  datetime COMMENT '更新时间',
	`paydate`	  datetime	COMMENT '购买日期（年月日时分秒）',
	`payType`	  int DEFAULT 10 COMMENT '支付方式  10-在线支付 	 20-货到付款    30-公司转账   40-邮局汇款 ',
	`istype`      int DEFAULT 20 COMMENT '订单状态  10-已确认 20-已开始配送    30-已收到   40-款项已结清',
    PRIMARY KEY (`id`)
)DEFAULT CHARSET=utf8;

/*
   订单明细
*/
DROP TABLE IF EXISTS `h_shopping_order_detail`;
CREATE TABLE IF not exists `h_shopping_order_detail` (
    `id`            BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
	`orderid`	    varchar(50)	COMMENT '订单编号',
    `wareid`	    BIGINT(20)	COMMENT '商品编号',
    `warename`	      varchar(200)	COMMENT '商品名称',
    `price`	        decimal(10,2)	COMMENT '商品原价',
	`num`           BIGINT(10)   	COMMENT '购买商品数量',
	`discount`	    decimal(10,2)	COMMENT '优惠金额',
	`fee`	        decimal(10,2)	COMMENT '运费',
	`imageurlsmall` varchar(200)	COMMENT '小图片默认url地址',
	`cretedate`	    datetime	COMMENT '创建时间',
	`updatedate`    datetime COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX(`orderid`)
)DEFAULT CHARSET=utf8;

/*
    试点酒店配置表
*/
DROP TABLE IF EXISTS `h_shopping_hotel`;
CREATE TABLE `h_shopping_hotel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menuname` varchar(200) DEFAULT NULL COMMENT '菜单名称',
  `hotelId` varchar(200) DEFAULT NULL COMMENT '酒店ID',
  PRIMARY KEY (`id`),
  KEY `menuname` (`menuname`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h_shopping_hotel
-- ----------------------------
INSERT INTO h_shopping_hotel VALUES ('1', '物品采购', '399');
INSERT INTO h_shopping_hotel VALUES ('2', '物品采购', '1417');
