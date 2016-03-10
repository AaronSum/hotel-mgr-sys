-- 新建推送消息日志表
CREATE TABLE e_instant_message (
`id`  bigint(20) NOT NULL COMMENT '主键' ,
`createtime`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`updatetime`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`directive`  int(3) NULL DEFAULT NULL COMMENT '指令' ,
`title`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '消息标题' ,
`data`  varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '数据' ,
`username`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称' ,
`userid`  bigint(20) NULL DEFAULT NULL COMMENT '用户ID' ,
`isnew`  int(1) NULL DEFAULT NULL COMMENT '是否处理 1、未处理 2、已处理' ,
`hotelid`  bigint(20) NULL DEFAULT NULL COMMENT '酒店ID' ,
PRIMARY KEY (`id`)
);
ALTER TABLE `e_instant_message`
MODIFY COLUMN `id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' FIRST ;
