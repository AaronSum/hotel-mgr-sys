/**login module*/

"use strict";

//module
angular.module("loginJinApp", [])

//controller
.controller("loginJinController", ["$rootScope", "$scope", "$http", function($rootScope, $scope, $http) {
	if (!isWebkitForHms()) {
		jQuery('#myModal').modal({});
	}

	// 返回酒店登录
	$scope.hotelLogin = contentPath + "/login/main";
	
	$scope.passwordEnter = function(event){
		if (event.keyCode == 13) {
			$scope.isExitLoginUser();
		}
	};

	/**
	 * 是否存在登录用户
	 */
	$scope.isExitLoginUser = function() {
		if (!isWebkitForHms()) {
			jQuery('#myModal').modal({});
			return;
		}
		$scope.success = true;
		$scope.errorMsg = "正在验证，请稍后……";
		// 请求
		$http({
			method: 'POST',
		    url: contentPath + "/resources/j_spring_security_check",
		    data: $.param({
		    	// 加入酒店登录名后缀，防止后端获取数据出现酒店、管理端都有此用户现象
		    	"j_username": jQuery("#loginName").val().trim() + "_pms",
				"j_password": jQuery("#password").val()
		    }),
		    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).success(function(data) {
			$http.post(contentPath + "/loginJin/isExit", {
				loginName: jQuery("#loginName").val().trim(),
				password: jQuery("#password").val()
			})
				.success(function(data){
					$scope.success = data.success;
					$scope.errorMsg = data.errorMsg || "验证成功，正在登录……";
					if ($scope.success) {
						location.href = contentPath + "/loginJin/main";
					}
				})
				.error(function(msg){
					$scope.success = false;
					$scope.errorMsg = "登录异常，请稍后重试";
				});
		});
	};
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