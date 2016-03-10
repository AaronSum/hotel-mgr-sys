<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String urlPath = request.getContextPath();
%>
<html ng-app="redisApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html; UTF-8">
		<link href="<%=urlPath%>/main/plug-in/bootstrap/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-1.3.5.js"></script>
		<title>获取redis后门</title>
	</head>
	<body ng-controller="redisController">
		<form class="form-inline" style="margin-top: 10px;">
			<div class="form-group">
				<label>返回值类型</label> <input type="text" class="form-control" ng-model="obj.type" placeholder="返回值类型">
			</div>
			<div class="form-group">
				<label>Redis key</label> <input type="text" class="form-control" ng-model="obj.redisKey" placeholder="请输入Redis key 名称">
			</div>
			<button type="submit" class="btn btn-default" ng-click="submitRedis();" >查询</button>
		</form>
		
		<!-- <table class="table hms-table border-top" style="margin-top: 20px;">
			<tbody>
				<tr ng-repeat="ware in cart.cartlist">
					商品信息
					<td class="text-left clearfloat">
						<div class="cart-item-image" ng-show="ware.wareShopping.imageurlsmall">
							<img class="shopping-cart-image" ng-src="{{ware.wareShopping.imageurlsmall}}"/>
						</div>
						<div class="cart-item-name">
							<span ng-bind="ware.name"></span>
						</div>
					</td>
					单价
					<td class="text-center" ng-bind="ware.price | number:2"></td>
					优惠价
					<td class="text-center" ng-bind="ware.discount | number:2"></td>
					数量
					<td class="text-center">
						<div class="quantity-form">
							<hms-form-counter data="ware" key="num" callback="carts.updateCountCallback"></hms-form-counter>
						</div>
					</td>
					金额
					<td class="text-center"><strong ng-bind="(ware.num * (ware.wareShopping.price - ware.wareShopping.discount)) | number:2"></strong></td>
					运费
					<td class="text-center" ng-bind="ware.wareShopping.fee | number:2"></td>
				</tr>
			</tbody>
		</table> -->
	</body>
</html>
<script type="text/javascript">
	var contentPath = "<%=urlPath %>";
	angular.module("redisApp",[]).controller("redisController", ['$rootScope', '$scope', '$http', function($rootScope,$scope,$http){
		$scope.obj = {
				type:"",
				redisKey:"399_247_HmsShoppingCartData"
		};
		$scope.submitRedis = function(){
			var getResisUrl = contentPath + "/shopping/getRedisData";
			$http.post(getResisUrl, $scope.obj).then(
				function(res) {
					console.log(res);
					//$scope.cart = res.data;
	    			//$scope.cart.cartlist = $scope.cart.cartDetails;
				},
				function(error){
				}
			);
		}
	}])
	.config(["$httpProvider", function($httpProvider) {
		// 使angular $http post提交和jQuery一致
	    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
	    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
	 
	    // Override $http service's default transformRequest
	    $httpProvider.defaults.transformRequest = [function(data) {
	        /**
	         * The workhorse; converts an object to x-www-form-urlencoded serialization.
	         * @param {Object} obj
	         * @return {String}
	         */
	        var param = function(obj) {
	            var query = '';
	            var name, value, fullSubName, subName, subValue, innerObj, i;
	 
	            for (name in obj) {
	                value = obj[name];
	 
	                if (value instanceof Array) {
	                    for (i = 0; i < value.length; ++i) {
	                        subValue = value[i];
	                        fullSubName = name + '[' + i + ']';
	                        innerObj = {};
	                        innerObj[fullSubName] = subValue;
	                        query += param(innerObj) + '&';
	                    }
	                } else if (value instanceof Object) {
	                    for (subName in value) {
	                        subValue = value[subName];
	                        fullSubName = name + '[' + subName + ']';
	                        innerObj = {};
	                        innerObj[fullSubName] = subValue;
	                        query += param(innerObj) + '&';
	                    }
	                } else if (value !== undefined && value !== null) {
	                    query += encodeURIComponent(name) + '='
	                            + encodeURIComponent(value) + '&';
	                }
	            }
	 
	            return query.length ? query.substr(0, query.length - 1) : query;
	        };
	 
	        return angular.isObject(data) && String(data) !== '[object File]'
	                ? param(data)
	                : data;
	    }];
	}]);
</script>