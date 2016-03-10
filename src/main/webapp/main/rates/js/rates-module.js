'use strict';

//module
angular.module('ratesApp', [])

//controller
.controller('ratesController', ['$scope','$rootScope','HmsHttp', function($scope,$rootScope,HmsHttp) {

	var MAX = 10; //显示多少天
	$scope.beginTime =new Date().format('yyyy-MM-dd');
	//后MAX天
	$scope.next = function(){
		$scope.beginTime = DateUtils.addByTransDate($scope.beginTime,MAX);
	};
	//前MAX天
	$scope.prev = function(){
		$scope.beginTime = DateUtils.addByTransDate($scope.beginTime,-MAX);
	};
	//监听开始时间变化
	$scope.$watch('beginTime',function(newVal){
		if(newVal){
			init();
		}
	});
	//显示修改页面
	$scope.doModify = function(roomType){
		$scope.roomType = roomType;
		if(!roomType.basePrice){
			loadBasePrice(roomType);			
		}else{
			$scope.showModify = true;
		}
	}
	$scope.closeModifyPage = function(){
		$scope.showModify = false;
		$scope.roomType = [];
	};	
	$scope.reloadPriceData = function(){
		$scope.closeModifyPage();
		loadPrice();
	};
	//加载时间数据
	function init(){
		$scope.endTime = DateUtils.addByTransDate($scope.beginTime,MAX-1); //当前列表最后一天
		$scope.columns = [];
		for (var i = 0; i < MAX; i++) {
			var dateStr = DateUtils.addByTransDate($scope.beginTime, i);
			$scope.columns.push({
				'date':dateStr,
				'showDate':dateStr.substr(5,5),
				'week':DateUtils.getWeekNum(dateStr)
			});
		}
		if(!$scope.roomTypes)
			loadRoomType();
		loadPrice();
	};
	//加载房型数据
	function loadRoomType(){
		HmsHttp.post(contentPath + '/roomtype/findsByHotelId', {
				hotelId: $rootScope.thisHotel.id,
			})
			.success(function(data) {
				$scope.roomTypes = data;
			})
			.error(function(msg) {
				Tip.message('获取房型数据失败', 'error');
			});
	};
	//加载房价时间列表
	function loadPrice(){
		$rootScope.hmsLoading.show();
		HmsHttp.post(contentPath + '/price/loadPrice', {
				hotelId: $rootScope.thisHotel.id,
				beginTime:$scope.beginTime,
				endTime:$scope.endTime,
			})
			.success(function(data) {
				$rootScope.hmsLoading.hide();
				$scope.priceData = formatPrice(data);
			})
			.error(function(msg) {
				$rootScope.hmsLoading.hide();
				Tip.message('获取房价数据失败', 'error');
			});
	};
	function formatPrice(datas){
		_.each(datas,function(prices,i){
			var myPrice = {};
			_.each(prices,function(price,j){
				var date = new Date();
				date.setTime( price['time'] );
				var time = date.format('yyyy-MM-dd');
				myPrice[time] = price['price'] ;
			})
			datas[i] = myPrice;
		})
		return datas;
	};
	function loadBasePrice(roomType){	
		$rootScope.hmsLoading.show();
		HmsHttp.post(contentPath + '/price/basePrice', {
				roomTypeId: roomType.id
			})
			.success(function(data) {
				roomType.basePrice  = data;
				$rootScope.hmsLoading.hide();
				$scope.showModify = true;
			})
			.error(function(msg) {
				$rootScope.hmsLoading.hide();
				$scope.showModify = true;
				// Tip.message('获取房价数据失败', 'error');
			});
	};
}])
/**
 * [放假维护控制类（ratesController子类）]
 * @param  {[type]} $scope){	}] [description]
 * @return {[type]}               [description]
 */
.controller('ratesModifyController', ['$rootScope','$scope','HmsHttp',
 function($rootScope,$scope,HmsHttp){
 	$scope.basePrice = Math.ceil(!$scope.$parent.roomType.basePrice ? $scope.$parent.roomType.cost : $scope.$parent.roomType.basePrice);
 	$scope.selectWeekType = 'all';
	$scope.priceType = 'price';
	$scope.price = {
		'price':$scope.basePrice,
		'subper':0,
		'subprice':0
	};
	$scope.weeks = [{
		'id':'Mon',
		'name':'星期一',
		'checked':false
	},{
		'id':'Tues',
		'name':'星期二',
		'checked':false
	},{
		'id':'Wed',
		'name':'星期三',
		'checked':false
	},{
		'id':'Thur',
		'name':'星期四',
		'checked':false
	},{
		'id':'Fri',
		'name':'星期五',
		'checked':false
	},{
		'id':'Sat',
		'name':'星期六',
		'checked':false
	},{
		'id':'Sun',
		'name':'星期日',
		'checked':false
	}];
	$scope.weekType = {
		'all':['Mon','Tues','Wed','Thur','Fri','Sat','Sun'],
		'workday':['Mon','Tues','Wed','Thur','Fri'],
		'weekend':['Sat','Sun']
	};
	$scope.save = function(){
		
		if(!_.isNumber($scope.price[$scope.priceType])){
			$scope.price[$scope.priceType] = 0;
			Tip.message('请输入数字','error');
			return;
		}
		var params = {
				roomTypeId:$scope.$parent.roomType.id,
				beginTime:$scope.beginTime,
				endTime:$scope.endTime,
				value:$scope.price[$scope.priceType],
				type:$scope.priceType
			};
		if(!addCheckedDays(params)){
			Tip.message('请选择星期','error');
			return;
		}				
		if(params.value<=0){
			$scope.price[$scope.priceType] = 0;
			Tip.message('请输入大于0的数字','error');
			return;
		}else if(params.type=='subper' && params.value >= 100 ){
			$scope.price.subper = 0;
			Tip.message('下浮比例不能大于100','error');
			return;
		}else if(params.type=='subprice' && params.value > $scope.basePrice ){
			$scope.price.subprice = 0;
			Tip.message('下浮金额不能大于房价','error');
			return;
		}else if(params.type=='price' && params.value > $scope.basePrice ){
			$scope.price.price = 0;
			Tip.message('固定金额不能大于'+$scope.basePrice ,'error');
			return;
		}
		params.value = Math.ceil(params.value);
		$scope.price[params.type] = params.value;
		if(params.type=='subper'){
			params.value /= 100;
		}
		$rootScope.hmsLoading.show();
		HmsHttp.post(contentPath + '/price/addPrice', params)
			.success(function(data) {
				$rootScope.hmsLoading.hide();
				if(data.success){
					Tip.message('修改成功');
					$scope.$parent.reloadPriceData();
				}else{
					Tip.message(data.errorMsg, 'error');					
				}
			})
			.error(function(msg) {
				$rootScope.hmsLoading.hide();
				Tip.message('修改失败', 'error');
			});

	};
	$scope.cancel = function(){
		$scope.$parent.closeModifyPage();
	};

	function addCheckedDays(params){
		var isChecked = false;
		angular.forEach($scope.weeks,function(day){
			if(day.checked){
				isChecked = true;
			}
			params[day.id] = day.checked;
		});
		return isChecked;
	};

	$scope.$watch('selectWeekType',function(type){
		if(type){
			_.forEach($scope.weeks,function(day){
				day.checked = _.contains($scope.weekType[type],day.id);
			});
		}
	});
	
	function setSubper(price){
		price = _.isNumber(price) ? Math.ceil(price) : 0;
		$scope.subperPrice = Math.ceil( ((100-price)*$scope.basePrice)/100 );
	};
	function setSubprice(price){
		price = _.isNumber(price) ? Math.ceil(price) : 0;
		$scope.subperPrice = Math.ceil( $scope.basePrice-price );
	};
	//监听下浮比例变化
	$scope.$watch('price.subper',function(price){
		setSubper(price);
	});
	//监听下浮变化
	$scope.$watch('price.subprice',function(price){
		setSubprice(price);
	});
	//监听下浮变化
	$scope.$watch('priceType',function(priceType){
		if(priceType=='subper'){
			setSubper($scope.price[priceType]);
		}else if(priceType=='subprice'){
			setSubprice($scope.price[priceType]);			
		}
	});
}])