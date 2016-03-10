/**hms controller*/

'use strict';

// hms controller
angular.module("hmsService", [])
.service('HmsHttp', ['$http','$q','$location', function($http,$q,$location){
	var rtnLogin = function(result) {
		var msg = "";
		// 如果是系统提示信息，则输出系统提示信息
		if (result.system) {
			layer.alert(result.errorMsg, {
				title: "系统提示",
				closeBtn: false
			}, function(){
				if (result.attribute && result.attribute.isToken == "true") {
					top.close();
				}
				top.location.href = contentPath + "/resources/j_spring_security_logout";
			});
		}
	};
	return{
		post:function(url,params){
			var deferred = $q.defer();
			return $http.post(url,params)
			.success(function(result){
				rtnLogin(result);
				deferred.resolve(result);
			})
			.error(function(error){
				deferred.reject(error);
			})
			return deferred.promise;
		},
		get:function(url){
			var deferred = $q.defer();
			return $http.get(url)
			.success(function(result){
				rtnLogin(result);
				deferred.resolve(result);
			})
			.error(function(error){
				deferred.reject(error);
			})
			return deferred.promise;
		}
	}
}])