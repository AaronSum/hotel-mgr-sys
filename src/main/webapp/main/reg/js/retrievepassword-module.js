/**
 * 忘记密码 module
 */

// module
angular.module("retrievePasswordApp", ["ui.router"])

/**
 * angularJs启动的时候只会执行一次，这里将state、stateParams放入rootScope中，目的是为了在
 * 后面可以方便的引用和注入
 */
.run(["$rootScope", "$state", "$stateParams", function($rootScope, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
}])

/**
 * 路由配置
 * 注意：这里使用的是ui-router，而不是angularJs原生的
 * 原生的不支持路由嵌套，而ui-router支持路由嵌套
 *
  */
.config(["$stateProvider", "$urlRouterProvider", function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/index");
    $stateProvider.state("index", {
        url: "/index",
        templateUrl: contentPath + "/main/reg/template/rp-index.html"
    })
    .state("reset", {
        url: "/reset",
        templateUrl: contentPath + "/main/reg/template/rp-reset.html"
    })
}])

// controller 发送验证码
.controller("retrievePasswordIndexController", ["$scope", "$http", "$location", "$timeout", function($scope, $http, $location, $timeout) {
	$scope.phoneNum = "";
	$scope.verifyCode = "";
	$scope.isOk = false;
	// 发送手机验证码
	$scope.sendVerifyCode = function() {
		// 手机号密码不能为空
		if (!$scope.phoneNum) {
			Tip.message("手机号不能为空", "error");
			return;
		}
		$http.post(contentPath + "/sms/retrievePassword", {
			phoneNum: $scope.phoneNum,
			phoneNumJin: ""
		})
			.success(function(data) {
				if (data.success) {
					Tip.message("发送成功");
					// 启动定时器
					$scope.sendVerifyCodeTimer();
				} else {
					Tip.message(data.errorMsg, "error");
				}
			}).error(function(mgs) {
				Tip.message("发送验证码异常", "error")
			});
	};
	// 下一步
	$scope.next = function() {
		// 手机号密码不能为空
		if (!$scope.phoneNum || !$scope.verifyCode) {
			Tip.message("手机号或验证码不能为空", "error");
			return;
		}
		$http.post(contentPath + "/hotel/retrievePasswordNext", {
			phoneNum: $scope.phoneNum,
			verifyCode: $scope.verifyCode
		})
			.success(function(data) {
				if (data.success) {
					$location.path("/reset");
				} else {
					Tip.message(data.errorMsg, "error");
				}
			}).error(function(mgs) {
				Tip.message("校验验证码异常", "error");
			});
	};
	// 返回登录页面
	$scope.backLogin = function() {
		location.href = contentPath;
	};
	//验证码按钮定时器
	$scope.sendVerifyCodeTime = 120;
	$scope.sendVerifyCodeFlag = false;
	$scope.sendVerifyCodeTimer = function() {
		$scope.sendVerifyCodeFlag = true;
		$scope.sendVerifyCodeTime -= 1;
		$timeout(function(){
			if ($scope.sendVerifyCodeTime > 0 && $scope.sendVerifyCodeFlag) {
				$scope.sendVerifyCodeTimer();
			} else {
				$scope.sendVerifyCodeFlag = false;
				$scope.sendVerifyCodeTime = 120;
			}
		}, 1000);
	};
}])

// controller 输入新密码
.controller("retrievePasswordResetController", ["$scope", "$location", "$http",  function($scope, $location, $http) {
	
	// 修改密码
	$scope.save = function() {
		if (!$scope.newPassword) {
			Tip.message("新密码不能为空", "error");
			return;
		}
		$http.post(contentPath + "/hotel/modifyPassword", {
			newPassword: $scope.newPassword,
			newPasswordJin: ""
		})
			.success(function(data) {
				if (data.success) {
					$scope.isOk = true;
					Tip.message("秘密修改完成");
				} else {
					Tip.message(data.errorMsg, "error");
				}
			}).error(function(mgs) {
				Tip.message("密码修改异常", "error");
			});
	};
	// 返回登录页面
	$scope.backLogin = function() {
		location.href = contentPath;
	};
	//返回从新回验证码
	$scope.backRetrievePassword = function() {
		$location.path("/index");
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







