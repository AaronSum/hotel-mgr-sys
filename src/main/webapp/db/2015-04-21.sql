--添加字段，用于标示类型；防止与主键冲突 as fixed
ALTER TABLE t_businesszonetype ADD COLUMN businesszone_type int(11);

--给添加字段赋值； as fixed
update t_businesszonetype set businesszone_type=10 where id=10;
update t_businesszonetype set businesszone_type=11 where id=11;
update t_businesszonetype set businesszone_type=12 where id=12;
update t_businesszonetype set businesszone_type=13 where id=13;

--添加是否新PMS接口调用安装标示（T、F） as fixed
ALTER TABLE e_hotel ADD COLUMN isNewPms char(1);

ALTER TABLE t_hotel ADD COLUMN isNewPms char(1);

--用户角色添加角色类型，便于以后业务增加好扩展  as fixed
--1、管理员；2、店长；3、前台；
--后期可以扩展添加  as fixed
ALTER TABLE h_role ADD COLUMN type int(11);

-- 更新数据  as fixed
update h_role set type = 1 where name = '创始人';

update h_role set type = 2 where name = '前台录入';

update h_role set type = 3 where name = '管理员';

--添加日志记录信息列表  as fixed
CREATE TABLE `h_operate_log` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`tatablename` varchar(40) DEFAULT '' COMMENT '操作表名',
	`usercode` varchar(20) DEFAULT NULL COMMENT '操作用户登录名',
	`username` varchar(40) DEFAULT '' COMMENT '操作用户名',
	`ip` varchar(50) DEFAULT '' COMMENT 'ip地址',
	`functioncode` varchar(20) DEFAULT '' COMMENT '操作功能编码',
	`functionname` varchar(40) DEFAULT '' COMMENT '操作功能名称',
	`operatetime` datetime DEFAULT NULL COMMENT '操作时间',
	`usertype` varchar(10) DEFAULT NULL COMMENT '操作应用类型（店长、pms用户）',
	PRIMARY KEY (`id`)
) ENGINE=`InnoDB` AUTO_INCREMENT=478 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ROW_FORMAT=COMPACT CHECKSUM=0 DELAY_KEY_WRITE=0;

-- 添加pms客服人员角色
--insert into 'hms_new'.'m_role' ( 'id', 'name', 'desction') values ( '1017', '客服人员（勿删）', '客服人员角色');

--insert into 'hms_new'.'m_user' ( 'id', 'nextchangepswtime', 'name', 'errorlogin', 'loginname', 'psw', 'regtime')
	--values ( '1079', '20150514225400', '客服人员', '0', 'attendantUser', 'B2080ABCF73007F43F756DE0C5BB3808', '20150514225400');
	
--insert into 'hms_new'.'m_role_user' ( 'id', 'userid', 'roleid') values ( '1074', '1079', '1017')

-- 添加t表酒店审核log数据  as fixed
CREATE TABLE `t_hotel_operate_log` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`hotelid` bigint(20) DEFAULT NULL COMMENT '酒店id',
	`hotelname` varchar(100) DEFAULT NULL COMMENT '酒店名称',
	`usercode` bigint(20) DEFAULT NULL COMMENT '用户id',
	`username` varchar(50) DEFAULT NULL COMMENT '用户姓名',
	`checktime` datetime DEFAULT NULL COMMENT '审核时间',
	`checktype` int(11) DEFAULT NULL COMMENT '审核类型（1，初次审核；2，更新审核）',
	`checktypename` varchar(50) DEFAULT NULL COMMENT '审核类型名称',
	PRIMARY KEY (`id`)
) ENGINE=`InnoDB` AUTO_INCREMENT=56 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ROW_FORMAT=COMPACT CHECKSUM=0 DELAY_KEY_WRITE=0;

-------*******未执行开始*******-------
-- 添加用户是否可用字段
ALTER TABLE h_user
ADD COLUMN visible VARCHAR(1) NULL COMMENT '是否可以用；T，可用；F，不可用',
ADD COLUMN `begintime` DATETIME NULL COMMENT '开始时间',
ADD COLUMN `endtime` DATETIME NULL COMMENT '结束时间',
DROP INDEX `IX_h_user_loginname` ;

-- 修改数据值
update h_user set visible = 'T';
-------*******未执行结束*******-------


