/**order module*/

"use strict";

//module
angular.module("borderApp", ['ui.grid','ui.grid.selection'])

//controller
.controller("BOrderController", ["$rootScope","$scope", "BOrderService","$filter","HmsModalService", "$location", "$anchorScroll",function($rootScope,$scope, BOrderService,$filter,HmsModalService,$location,$anchorScroll) {
	//标题头	
    var columns = [{   field: "otaOrderId",
                displayName: "订单号"
            }, {
                field: "roomTypeName",
                displayName: "房型"
            }, {
                field: "mName",
                displayName: "联系人/电话"
            }, {
                field: "liveTime",
                displayName: "入住/离店"
            }, {
                field: "roomNo",
                displayName: "房间号"
            },  {
                field: "creattime",
                displayName: "预定时间"
            }, {
                field: "lzbje",
                displayName: "乐住币金额"
            }, {
                field: "dfbtje",
                displayName: "到付补贴金额"
            }, {
                field: "yfbtje",
                displayName: "预付补贴金额"
            }, {
                field: "orderStatusName",
                displayName: "状态"
            }];
    $scope.columns = columns;
    $scope.active = {
        'left':0,
        'top':0
    };// 默认展示
    $scope.navItems = [{
        'id':'orderType1',
        'displayName':'在线支付',
        'value':1
    },{
        'id':'orderType2',
        'displayName':'前台支付',
        'value':2
    }];
    $scope.navOrders = [{
        'id':'today',
        'displayName':'今日订单',
        'action':'findTodayOtsRoomOrders'
    },{
        'id':'checkinToday',
        'displayName':'今日已入住',
        'action':'findCheckinTodayOtsRoomOrders'
    },{
        'id':'checkinMonth',
        'displayName':'当月已入住',
        'action':'findCheckinMonthOtsRoomOrders'
    },{
    	'id':'all',
    	'displayName':'全部订单',
    	'action':'findOtsRoomOrders'
    }];
    $scope.search = {
        key:'',
        runned:false, //是否运行过
        active:{
            top:3
        },
        backData:{
            active:{},
            navOrders:[],
            currentPage:1,
            currentPageQiantai:1
        },
        enter:function(event){

        },
        focus:function(){
            if($scope.navOrders.length>1){
                this.backData.active.top = $scope.active.top;
                this.backData.navOrders = $scope.navOrders.concat([]);

                this.backData.currentPage = $scope.currentPage;
                this.backData.currentPageQiantai = $scope.currentPageQiantai;

                $scope.navOrders = [{
                    'id':'all',
                    'displayName':'搜索订单',
                    'action':'findOtsRoomOrders'
                }];
                $scope.active.top = 0;
            }
        },
        blur:function(){
            if(!this.key && $scope.navOrders.length==1){
                this.runned = false;
                $scope.navOrders = this.backData.navOrders;
                $scope.active.top = this.backData.active.top;
            }
        },
        del:function(){
            this.key = '';
            if($scope.navOrders.length==1){
                $scope.navOrders = this.backData.navOrders;
                if(this.runned){
                    $scope.currentPage = this.backData.currentPage;
                    $scope.currentPageQiantai = this.backData.currentPageQiantai;
                    $scope.setActive(this.backData.active.top,'top',true);
                }
                else
                    $scope.active.top = this.backData.active.top;

                this.runned = false;
            }
        },
        doAct:function(){
            var key = this.key;
            if(!key){
                Tip.message('请输入订单号','error');
                return;
            }
            /*分页bug处理*/
            $scope.currentPage = 1;
            $scope.currentPageQiantai = 1;
            /**/
            loadData(1,key);
            loadData(2,key);
            this.runned = true;
        }
    };//搜索
	$scope.totalItems = 0; //数据总条数
	$scope.currentPage = 1; //当前页
	$scope.pageCount = 5; //每页显示数量
	$scope.numPages = 1; //总页数
	$scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...

    $scope.totalItemsQiantai = 0; //数据总条数
    $scope.currentPageQiantai = 1; //当前页
    $scope.pageCountQiantai = 5; //每页显示数量
    $scope.numPagesQiantai = 1; //总页数
    $scope.maxSizeQiantai = 5; //当总页数大于多少的时候，分页开始显示...
	//当前页发生变化时操作
	$scope.pageChanged = function(){
		loadData(1);
	};
    $scope.currentGoPage = '';
    $scope.pageGo = function(){
        var type = /^[0-9]*[1-9][0-9]*$/;
        var reg = new RegExp(type);
        if ($scope.currentGoPage.match(reg) == null 
            || $scope.currentGoPage > (Math.ceil($scope.totalItems/$scope.pageCount))) {
            $scope.currentGoPage = "";
            return;
        }
        if(!$scope.currentGoPage)
            return;
        $scope.currentPage = $scope.currentGoPage;
        $scope.currentGoPage = '';
        $scope.pageChanged();
    };
    $scope.pageChangedQiantai = function(){
        loadData(2);
    };
    $scope.currentGoPageQiantai = '';
    $scope.pageGoQiantai = function(){
        var type = /^[0-9]*[1-9][0-9]*$/;
        var reg = new RegExp(type);
        if ($scope.currentGoPageQiantai.match(reg) == null 
            || $scope.currentGoPageQiantai > (Math.ceil($scope.totalItemsQiantai/$scope.pageCountQiantai))) {
            $scope.currentGoPageQiantai = "";
            return;
        }
        if(!$scope.currentGoPageQiantai)
            return;
        $scope.currentPageQiantai = $scope.currentGoPageQiantai;
        $scope.currentGoPageQiantai = '';
        $scope.pageChangedQiantai();
    };
	//设置选中页
	$scope.setActive = function(index,key,noreset){
        if($scope.navOrders.length == 1){ //如果是搜索，则点击标签不刷新
            return;
        }
		if(!noreset && $scope.active[key] != index){
			$scope.currentPage = 1;
			$scope.currentPageQiantai = 1;
		}
		$scope.active[key] = index;
        //调用数据
        loadData(1);
        loadData(2);     
	};
    $scope.doModify = function(row){
        $rootScope.$broadcast('CHANGE_ORDER_PRICE', {"orderId": row.otaOrderId});
    };
    $scope.showMoreInfo = function(orderId){
        $rootScope.hmsLoading.show();
        BOrderService.findOrderMoreInfo(orderId)
            .success(function(data) {
                $rootScope.hmsLoading.hide();
                formatMoreInfoData(data);
                $scope.orderId = orderId;
                $scope.moreInfo = data;
                HmsModalService.show({
                    contentTmpl:contentPath+"/main/order/template/b-order-moreInfo.html",
                    scope:$scope,
                });
            })
            .error(function(msg) {
                $rootScope.hmsLoading.hide();
                Tip.message("数据加载异常", "error");
            });
    };
    //加载数据
    function loadData(activeCode,search){
    	$rootScope.hmsLoading.show();
		var func = $scope.navOrders[$scope.active.top]['action'];
		// BOrderService[func]($scope.navItems[$scope.active.left]['value'],$scope.currentPage,$scope.pageCount)
        var currP = activeCode == 1 ? $scope.currentPage :  $scope.currentPageQiantai;
        var pCount = activeCode == 1 ? $scope.pageCount :  $scope.pageCountQiantai;
        BOrderService[func](activeCode,currP,pCount,search)
			.success(function(data) {
                $rootScope.hmsLoading.hide();
				formatData(data);
                if (activeCode == 1) {
                    $scope.rowsData = data.rows; 
                    $scope.totalItems = parseInt(data.total);
                } else {
                    $scope.rowsDataQiantai = data.rows; 
                    $scope.totalItemsQiantai = parseInt(data.total);
                }
			})
			.error(function(msg) {
                $rootScope.hmsLoading.hide();
                if (activeCode == 1) {
                    $scope.rowsData = []; 
                    $scope.totalItems = 0;
                    Tip.message("在线支付数据加载异常", "error");
                } else {
                    $scope.rowsDataQiantai = []; 
                    $scope.totalItemsQiantai = 0;
                    Tip.message("前台到付数据加载异常", "error");
                }
                
			});
    };
	//格式化数据
	function formatData(data) {
		var rows = data.rows,
            orderType=$scope.navItems[$scope.active.left]['value'];
        data.orderIds = [];
		_.map(rows, function(row) {
            data.orderIds.push(row.otaOrderId);
            var liveTime = '';
            if(row.begintime){
            	var fmBegintime = DateUtils.formatDate4TimeVal(row.begintime, DateUtils.MM_DD);
            	liveTime = fmBegintime+'/';
            }
			var fmBegintime = '';
            if(row.endtime){
            	var fmEndtime = DateUtils.formatDate4TimeVal(row.endtime, DateUtils.MM_DD);
            	liveTime = liveTime + fmEndtime;
            }
            row.liveTime = liveTime;
            row.mName = (row.contacts || '无') 
            if(row.contactsPhone){
               row.mName += '/' + row.contactsPhone;
            }            
            /*//订单状态判断
            if(row.orderStatus== 180 || row.orderStatus==  190||  row.orderStatus==200 ){
	            if(row.spreadUser && !row.invalidreason){
	            	if(row.ordertype==1){ //预付的情况
                        if(row.invalidreason>1){
                            row.lzbje = row.allcost;
                            row.yfbtje = $filter('currency')(20);
                            row.dfbtje = '--';                            
                        }
	                }else{
	                    row.lzbje = row.realotagive;
	                    row.yfbtje = '--';
	                    row.dfbtje = $filter('currency')(10);
					}
				}
			}
            
			//预付订单
			if(row.ordertype == 1){
				//订单状态(入住，190，离店)
				//切客  spreaduser不为空
				if( (row.orderStatus== 180 || row.orderStatus==  190||  row.orderStatus==200 )
						&& row.spreadUser){
					//无失效原因 或者 原因为2,3,4需要显示补贴 金额
					if(!row.invalidreason ||row.invalidreason==2 ||row.invalidreason==3 ||row.invalidreason==4){
						if(row.rulecode == 1002){
							//预付赋值
							row.yfbtje = $filter('currency')(20);
	                    }
					}
				}
			}*/

            // 初始化
            row.lzbje = '--';
            row.yfbtje = '--';
            row.dfbtje = '--';  
            
            //订单状态判断
            if(row.orderStatus== 180 || row.orderStatus==  190||  row.orderStatus==200 ){
    			//预付订单
    			if(row.ordertype == 1){
    				row.yfbtje = row.qiekeIncome ? $filter('currency')(row.qiekeIncome):'--';
    			}else if(row.ordertype == 2){
    				row.dfbtje = row.qiekeIncome ? $filter('currency')(row.qiekeIncome):'--';
    			}
            }
            
            if(row.orderStatus == 513){
                row.lzbje = '--';
            }else{
            	if(row.ordertype==1){
                    row.lzbje = row.allcost;
                }else{
                    row.lzbje = row.realotagive;
                }
                row.lzbje = row.lzbje ? $filter('currency')(row.lzbje) : '--';
            }
            if(row.creattime){
            	row.creattime = DateUtils.formatDate4TimeVal(row.creattime, DateUtils.MM_DD_HH_MM_SS);
            }else{
            	row.creattime = '';
            }
        });
	};
    //格式化数据
    function formatMoreInfoData(data) {
        if(!data){
            return;
        }
        data.exception = data.businessException ? data.businessDesc + ':' + data.businessException : '--';
        data.allCost = data.allCost ? $filter('currency')(data.allCost) : '--';
        data.yhje = data.yhje ? $filter('currency')(data.yhje) : '--';
        data.cjje = data.cjje ? $filter('currency')(data.cjje) : '--';
        data.yfje = data.yfje ? $filter('currency')(data.yfje) : '--';
        data.dfje = data.dfje ? $filter('currency')(data.dfje) : '--';
        switch(data.orderMethod){
            case 1:
                data.orderMethod = 'CRS';
                break;
            case 2:
                data.orderMethod = '网页';
                break;
            case 3:
                data.orderMethod = '微信';
                break;
            case 4:
                data.orderMethod = 'APP(IOS)';
                break;
            case 5:
                data.orderMethod = 'APP(ANDROID)';
                break;
            default:
                data.orderMethod = '--';
                break;
        }
        switch(data.orderType){
            case 1:
                data.orderType = '预付';
                break;
            case 2:
                data.orderType = '到付';
                break;
            case 3:
                data.orderType = '担保';
                break;
            default:
                data.orderType = '--';
        }

        switch(data.invalidreason){
            case 1:
                data.invalidreason = '入住人为常旅客';
                break;
            case 2:
                data.invalidreason = '入住时间未满4小时';
                break;
            case 3:
                data.invalidreason = '此用户本月超过4次切单';
                break;
            case 4:
                data.invalidreason = '此用户今日不是第一次切单';
                break;
            case 5:
                data.invalidreason = '此用户全网每月超过4单';
                break;
            case 6:
                data.invalidreason = '该订单用户的手机设备号对应手机号超过三个';
                break;
            case 9:
                data.invalidreason = '该订单用户不是通过App下单';
                break;
            case 10:
                data.invalidreason = '该订单用户的电话号码不是第一次使用';
                break;
            case 20:
                data.invalidreason = '该订单用户的手机设备号为空';
                break;
            case 30:
                data.invalidreason = '该订单用户的手机设备号不是第一次使用';
                break;
            case 40:
                data.invalidreason = '该订单的身份证号码为空';
                break;
            case 41:
                data.invalidreason = '该订单不是通过PMS扫描二代身份证办理 ';
                break;
            case 50:
                data.invalidreason = '该订单用户的身份证号不是第一次使用';
                break;
            case 60:
                data.invalidreason = '该订单用户使用过优惠券';
                break;
            case 70:
                data.invalidreason = '该订单用户的支付账号不是第一次使用';
                break;
            case 90:
                data.invalidreason = '该订单用户下单时位置不在酒店周边1公里内';
                break;
            case 100:
                data.invalidreason = '该订单的入住时间不满30分钟';
                break;
            case 110:
                data.invalidreason = '本店日拉新限额已满';
                break;
            case 120:
                data.invalidreason = '该订单未入住';
                break;
            default:
                data.invalidreason = '--';
        }
    };
    $scope.goToAnchor = function(key){
        if (key == 0) {
            $location.hash('zaixianzhifu');
            $anchorScroll();
        } else if (key == 1) {
            $location.hash('qiantaizhifu');
            $anchorScroll();
        }
    };     

    $scope.setActive(0,'top');
}])
/**
 * 订单更多信息 属于orderController子类
 */
.controller('BOrderMoreInfoController', ['$scope','HmsModalService',
 function($scope,HmsModalService){
    //标题头   
    $scope.moreInfo_columns = [ {
                field: "orderMethod",
                displayName: "订单来源"
            }, {
                field: "allCost",
                displayName: "原价"
            }, {
                field: "yhje",
                displayName: "眯客优惠券金额"
            }, {
                field: "cjje",
                displayName: "成交价格"
            }, {
                field: "yfje",
                displayName: "现付金额"
            },{
                field: "orderType",
                displayName: "支付方式"
            },{
                field: "invalidreason",
                displayName: "切客失效原因"
            }];
    $scope.cancel = function(){
        HmsModalService.hide();
    };
}])
.service('BOrderService', ['HmsHttp', function(HmsHttp){  
    var url = contentPath+'/brule/';
    return{
        /**
         * 全部订单
         * @param  {[int]} orderType [订单类型]
         * @param  {[int]} pageNum  [当前页]
         * @param  {[int]} pageSize [每页条数]
         * @param  {[string]} search [搜索key]
         * @return {[List<Ojbect>]}          [订单列表]
         */
        findOtsRoomOrders:function(orderType,pageNum,pageSize,search){
            var act = 'findOtsRoomOrders',
                params = {
                    'orderType':orderType,
                    'pageNum': pageNum,
                    'pageSize': pageSize
                };
                if(search)
                    params['search'] = search;
            return HmsHttp.post(url+act,params);
        },
        /**
         * 今日订单
         * @param  {[int]} pageNum  [当前页]
         * @param  {[int]} pageSize [每页条数]
         * @return {[List<Ojbect>]}          [订单列表]
         */
        findTodayOtsRoomOrders:function(orderType,pageNum,pageSize){
            var act = 'findTodayOtsRoomOrders',
                params = {
                    'orderType':orderType,
                    'pageNum': pageNum,
                    'pageSize': pageSize
                };
            return HmsHttp.post(url+act,params);
        },
        /**
         * 今日已入住订单
         * @param  {[int]} pageNum  [当前页]
         * @param  {[int]} pageSize [每页条数]
         * @return {[List<Ojbect>]}          [订单列表]
         */
        findCheckinTodayOtsRoomOrders:function(orderType,pageNum,pageSize){
            var act = 'findCheckinTodayOtsRoomOrders',
                params = {
                    'orderType':orderType,
                    'pageNum': pageNum,
                    'pageSize': pageSize
                };
            return HmsHttp.post(url+act,params);
        },
        /**
         * 当月已入住订单
         * @param  {[int]} pageNum  [当前页]
         * @param  {[int]} pageSize [每页条数]
         * @return {[List<Ojbect>]}          [订单列表]
         */
        findCheckinMonthOtsRoomOrders:function(orderType,pageNum,pageSize){
            var act = 'findCheckinMonthOtsRoomOrders',
                params = {
                    'orderType':orderType,
                    'pageNum': pageNum,
                    'pageSize': pageSize
                };
            return HmsHttp.post(url+act,params);
        },
        /**
         * 获取单个订单详情
         * @param  {[type]} orderId [description]
         * @return {[type]}         [description]
         */
        findOrderMoreInfo:function(orderId){
            var act = 'findOrderMoreInfo',
                params = {
                    orderId:orderId
                };
            return HmsHttp.post(url+act,params);
        }
    };    
}])
