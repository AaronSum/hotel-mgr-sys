'use strict';

//module
angular.module("homeApp", [])

//controller
.controller("homeController", ["$rootScope","$scope", "HmsHttp","QrCodeService","$location",
 function($rootScope,$scope, HmsHttp,QrCodeService,$location) {

 	//如果是B规则，并且酒店还没有进行过第一次审核
 	if($rootScope.RULE_CODE=='B' && $rootScope.thisHotel.state < 4){
 		$location.path('/message');
 	}

	jQuery("#hotel_home_last_row_right").height(jQuery("#hotel_home_last_row_hei").height() - 13);
	//headerMark(0);
	bindInputs();
	loadHomeDatas(function(){
		$rootScope.hmsLoading.hide();
		$rootScope.$digest();
	});
	$rootScope.hmsLoading.show();

	$scope.showQrCode = function(){
		HmsHttp.post(contentPath + "/home/getID2code")
		.success(function(data) {
			if(data){
				QrCodeService.show(data,'',$scope);
			}else{
				QrCodeService.show('weixin.imike.com','',$scope);
			}
		})
		.error(function() {
			QrCodeService.show('weixin.imike.com','',$scope);
		});
	};
	
	if(ruleCode == 1002){
		HmsHttp.post(contentPath + "/home/getID2code")
		.success(function(data) {
			if(data){
				$scope.qrCodeSrc = QrCodeService.getSrc(data);
			}else{
				$scope.qrCodeSrc = QrCodeService.getSrc('wechat.imike.com');
			}
		})
		.error(function() {
			$scope.qrCodeSrc = QrCodeService.getSrc('wechat.imike.com');
		});
	}
}]).controller('menuController', ['$scope','$templateCache', 
    function($scope,$templateCache){
		var tmp = "<ul class=\"nav nav-tabs hms-top-nav pull-right\" role=\"tablist\">"+
							"	 <li ng-if=\"thisNavIsShow('home')\" role=\"presentation\" ng-class=\"{'active':isActive=='home'}\"><a href=\"#home\" aria-controls=\"home\" role=\"tab\" data-toggle=\"tab\"><i class=\"fa fa-home\"></i>&nbsp;首页</a></li>"+
							"    <li ng-if=\"isShowShoppingTab\" role=\"presentation\" ng-class=\"{'active':isActive=='shopping'}\"><a href=\"#goods\" aria-controls=\"shopping\" role=\"tab\" data-toggle=\"tab\" ><i class=\"fa fa-truck\"></i>&nbsp;物品采购</a></li>"+
							"	  <li  ng-if=\"thisNavIsShow('orders')\" role=\"presentation\" ng-class=\"{'active':isActive=='orders'}\"><a href=\"#orders\" aria-controls=\"orders\" role=\"tab\" data-toggle=\"tab\" ng-if=\"RULE_CODE=='A'\"><i class=\"fa fa-book\"></i>&nbsp;订单查询</a></li>"+
							"	  <li ng-if=\"thisNavIsShow('borders')\" role=\"presentation\" ng-class=\"{'active':isActive=='borders'}\"><a href=\"#borders\" aria-controls=\"borders\" role=\"tab\" data-toggle=\"tab\" ng-if=\"RULE_CODE=='B'\"><i class=\"fa fa-book\"></i>&nbsp;订单查询</a></li>"+
							"	  <li ng-if=\"thisNavIsShow('rates')\" role=\"presentation\" ng-class=\"{'active':isActive=='rates'}\"><a href=\"#rates\" aria-controls=\"rates\" role=\"tab\" data-toggle=\"tab\"><i class=\"fa fa-calendar\"></i>&nbsp;房价维护</a></li>"+
							"	  <li ng-if=\"thisNavIsShow('account')\" role=\"presentation\" ng-class=\"{'active':isActive=='account'}\"><a href=\"#account\" aria-controls=\"account\" role=\"tab\" data-toggle=\"tab\"><i class=\"fa fa-cny\"></i>&nbsp;财务结算</a></li>"+
							"	  <li ng-if=\"thisNavIsShow('message')\" role=\"presentation\" ng-class=\"{'active':isActive=='message'}\"><a href=\"#message\" aria-controls=\"message\" role=\"tab\" data-toggle=\"tab\"><i class=\"fa fa-info\"></i>&nbsp;酒店讯息</a></li>"+
							"	  <li ng-if=\"thisNavIsShow('users')\" role=\"presentation\" ng-class=\"{'active':isActive=='	'}\"><a href=\"#users\" aria-controls=\"users\" role=\"tab\" data-toggle=\"tab\"><i class=\"fa fa-user\"></i>&nbsp;用户管理</a></li>"+
							"	  <li ng-if=\"thisNavIsShow('comment')\" role=\"presentation\" ng-class=\"{'active':isActive=='comment'}\"><a href=\"#comment\" aria-controls=\"comment\" role=\"tab\" data-toggle=\"tab\"><i class=\"fa fa-comments\"></i>&nbsp;评论管理</a></li> "+
							"</ul>";
	    $templateCache.put('menu.html',tmp);               
}])

//directive hmsSelect
.directive("hmsSelect", function() {
	return {
		restrict: "A",
		link: function(scope, iElement, iAttrs) {
			//年份
			if (iAttrs.type === "year") {
				var yearList = DateUtils.getYearList();
				var years = [];
				years.push("<option>---请选择---</option>");
				for (var i = 0; i < yearList.length; i++) {
					years.push("<option value='", yearList[i], "'>", yearList[i], "年</option>");
				}
				iElement.append(years.join(""));
			//时间
			} else if (iAttrs.type === "time") {
				var timeList = DateUtils.getTimeList();
				var times = [];
				times.push("<option>---请选择---</option>");
				for (var i = 0; i < timeList.length; i++) {
					times.push("<option value='", timeList[i], "'>", timeList[i], "</option>");
				}
				iElement.append(times.join(""));
			}
			iElement.selectpicker({
				style: iAttrs["class"] || "btn-success"
			});
		}
	}
})
.service('QrCodeService', ['HmsModalService','$rootScope', function(HmsModalService,$rootScope){
	return {
		show:function(content,tag,$scope){
			$scope.printI2dimCodes = [{
				'content':content,
				'tag':tag
			}];		
			// 获取生成二维码链接
			$scope.getPrintI2dimCodeUrl = function(content, tag) {
				return Print.getPrintI2dimCodeUrl(content, tag);
			};
			// 进入打印页面
			$scope.goToPrintPage = function(content, tag) {
				Print.goToPrintPage(content, tag, $rootScope.thisHotel.hotelName, $scope.printI2dimCodes);
			};
			$scope.cancle = function(){
				HmsModalService.hide();
			};
            HmsModalService.show({
                contentTmpl: contentPath + "/main/message/template/qr-code.html",
                scope:$scope,
            });
		},
		getSrc:function(content,tag){
			return Print.getPrintI2dimCodeUrl(content, tag);
		}
	}
}])