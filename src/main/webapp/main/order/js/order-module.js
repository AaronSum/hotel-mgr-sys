/**order module*/

"use strict";

//module
angular.module("orderApp", ['ui.grid','ui.grid.selection'])

//controller
.controller("orderController", ["$rootScope","$scope", "OrderService","$filter","HmsModalService", function($rootScope,$scope, OrderService,$filter,HmsModalService) {
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
                field: "orderStatusName",
                displayName: "状态"
            }];
    $scope.columns = columns;
    $scope.active = 0;// 默认展示全部订单
    $scope.navOrders = [{
    	'id':'all',
    	'displayName':'全部订单',
    	'action':'findOtsRoomOrders'
    },{
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
        'id':'book',
        'displayName':'预付完成订单',
        'action':'findBookOtsRoomOrderList'        
    }/*,{
        'id':'invalid',
        'displayName':'无效订单',
        'action':'findInvalidOtsRoomOrders'
    }*/];
	$scope.totalItems = 0; //数据总条数
	$scope.currentPage = 1; //当前页
	$scope.pageCount = 10; //每页显示数量
	$scope.numPages = 1; //总页数
	$scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
	//当前页发生变化时操作
	$scope.pageChanged = function(){
		loadData();
	};
	//设置选中页
	$scope.setActive = function(index){
		$scope.active = index;
	};
    $scope.doModify = function(row){
        $rootScope.$broadcast('CHANGE_ORDER_PRICE', {"orderId": row.otaOrderId});
    };
    $scope.showMoreInfo = function(orderId){
        $rootScope.hmsLoading.show();
        OrderService.findOrderMoreInfo(orderId)
            .success(function(data) {
                $rootScope.hmsLoading.hide();
                formatMoreInfoData(data);
                $scope.orderId = orderId;
                $scope.moreInfo = data;
                HmsModalService.show({
                    contentTmpl:contentPath+"/main/order/template/order-moreInfo.html",
                    scope:$scope,
                });
            })
            .error(function(msg) {
                $rootScope.hmsLoading.hide();
                Tip.message("数据加载异常", "error");
            });
    };
    //加载数据
    function loadData(){
    	$rootScope.hmsLoading.show();
		var func = $scope.navOrders[$scope.active]['action'];
		OrderService[func]($scope.currentPage,$scope.pageCount)
			.success(function(data) {
                $rootScope.hmsLoading.hide();
				formatData(data);
	            $scope.rowsData = data.rows; 
				$scope.totalItems = parseInt(data.total);
			})
			.error(function(msg) {
                $rootScope.hmsLoading.hide();
                $scope.rowsData = []; 
                $scope.totalItems = 0;
				Tip.message("数据加载异常", "error");
			});
    };
	//格式化数据
	function formatData(data) {
		var rows = data.rows
        data.orderIds = [];
		_.map(rows, function(row) {
            data.orderIds.push(row.otaOrderId);
			var fmBegintime = DateUtils.formatDate4TimeVal(row.begintime, DateUtils.MM_DD),
                fmEndtime = DateUtils.formatDate4TimeVal(row.endtime, DateUtils.MM_DD);
            row.liveTime = fmBegintime +'/'+fmEndtime;
            row.mName = (row.contacts || '无') 
            if(row.contactsPhone){
               row.mName += '/' + row.contactsPhone;
            }
            row.creattime = DateUtils.formatDate4TimeVal(row.creattime, DateUtils.YYYY_MM_DD_HH_MM_SS);
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
    };
	//初始化调用
	$scope.$watch('active',function(newVal){
		loadData();
	});
}])
/**
 * 订单更多信息 属于orderController子类
 */
.controller('OrderMoreInfoController', ['$scope','HmsModalService',
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
            }, {
                field: "dfje",
                displayName: "预付金额"
            }, {
                field: "orderType",
                displayName: "支付方式"
            }];
    $scope.cancel = function(){
        HmsModalService.hide();
    };
}])
.service('OrderService', ['HmsHttp', function(HmsHttp){  
    var url = contentPath+'/otsroomorder/';
    return{
        /**
         * 全部订单
         * @param  {[int]} pageNum  [当前页]
         * @param  {[int]} pageSize [每页条数]
         * @return {[List<Ojbect>]}          [订单列表]
         */
        findOtsRoomOrders:function(pageNum,pageSize){
            var act = 'findOtsRoomOrders',
                params = {
                    'pageNum': pageNum,
                    'pageSize': pageSize
                };
            return HmsHttp.post(url+act,params);
        },
        /**
         * 今日订单
         * @param  {[int]} pageNum  [当前页]
         * @param  {[int]} pageSize [每页条数]
         * @return {[List<Ojbect>]}          [订单列表]
         */
        findTodayOtsRoomOrders:function(pageNum,pageSize){
            var act = 'findTodayOtsRoomOrders',
                params = {
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
        findCheckinTodayOtsRoomOrders:function(pageNum,pageSize){
            var act = 'findCheckinTodayOtsRoomOrders',
                params = {
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
        findCheckinMonthOtsRoomOrders:function(pageNum,pageSize){
            var act = 'findCheckinMonthOtsRoomOrders',
                params = {
                    'pageNum': pageNum,
                    'pageSize': pageSize
                };
            return HmsHttp.post(url+act,params);
        },
        /**
         * 预付完成订单
         * @param  {[int]} pageNum  [当前页]
         * @param  {[int]} pageSize [每页条数]
         * @return {[List<Ojbect>]}          [订单列表]
         */
        findBookOtsRoomOrderList:function(pageNum,pageSize){
            var act = 'findBookOtsRoomOrderList',
                params = {
                    'pageNum': pageNum,
                    'pageSize': pageSize
                };
            return HmsHttp.post(url+act,params);
        },
        /**
         * 无效订单
         * @param  {[int]} pageNum  [当前页]
         * @param  {[int]} pageSize [每页条数]
         * @return {[List<Ojbect>]}          [订单列表]
         */
        /*findInvalidOtsRoomOrders:function(pageNum,pageSize){
            var act = 'findInvalidOtsRoomOrders',
                params = {
                    'pageNum': pageNum,
                    'pageSize': pageSize
                };
            return HmsHttp.post(url+act,params);
        },*/
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
