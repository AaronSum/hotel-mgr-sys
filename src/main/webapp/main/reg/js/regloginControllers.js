
'use strict';

/**
 * controllers module
 */
var controllers = angular.module('regLoginControllers', [])
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

controllers.controller('controllers.regLoginCtrl',['$rootScope','$scope', '$location', '$timeout','$http', function($rootScope,$scope,$location,$timeout,$http){
	$scope.login = {
		//
		MIKE_PNG:contentPath + "/images/index-log.png",
		MFKD_PNG:contentPath + "/images/icn_03.png",
		PHONE:contentPath + "/images/phone.png",
		//
	    phoneNum:"",
	    phoneYzm:"",
	    aPsd:"",
	    vPsd:"",
	    //
		phoneNumMsg:"",
		phoneYzmMsg:"",
		aPsdMsg:"",
		vPsdMsg:"",
		//
		phoneYzmBtnMsg:"获取手机验证码",
		//
		phoneNumCheck:function(){
			var regExp= /^1\d{10}$/;
			if (!this.phoneNum) {
				this.phoneNumMsg = "输入不能为空";
				return false;
			}
			if (regExp.test(this.phoneNum)) {
				this.phoneNumMsg = "";
				return true;
			} else {
				this.phoneNumMsg = "请正确输入手机号";
				return false;
			}
		},
		//
		phoneYzmCheck:function(){
			var regExp= /\d$/;
			// if (!this.phoneYzm) {
			// 	this.phoneYzmMsg = "输入不能为空";
			// 	return false;
			// }
			if (this.phoneYzm) {
				this.phoneYzmMsg = "";
				return true;
			} else {
				this.phoneYzmMsg = "请输入验证码";
				return false;
			}
		},
		//
		receivePhoneYzm:function(){
			var self = this;
			if (self.phoneNumCheck()) {
				self.yzmLoading = true;
				var yzmUrl = contentPath + "/sms/verifyCode";
				$http.post(yzmUrl, {"phoneNum":self.phoneNum}).then(
					function(res){
						var msg = res.data;
						if (!msg.success) {
							self.yzmLoading = false;
							self.phoneNumMsg = msg.errorMsg;
						} else {
							self.phoneNumMsg = "验证码发送成功";
							self.yzmCountDown();
						}
					},
					function(res){
						//call messenger
						self.yzmLoading = false;
						Tip.message("获取验证码异常", "error");
					}
				);
			} else {
				self.yzmLoading = false;
				self.phoneNumMsg = "请再次确认手机号";
			}
		},
		//
		yzmCountDown:function(){
			var self = this;
			var count = 60;
			var _countDown = function(){
				self.phoneYzmBtnMsg = "重新获取(" + parseInt(count) + ")";
				if (count > 0) {
					$timeout(_countDown,1000);
					count--;
				} else {
					self.yzmLoading = false;
					self.phoneYzmBtnMsg = "获取手机验证码";
				}
			};
			$timeout(_countDown,0);
		},
		//
		aPsdCheck:function(){
			var aPsd = this.aPsd;
			if (!aPsd) {
				this.aPsdMsg = "输入不能为空";
				return false;
			}
			if (/.*[\u4e00-\u9fa5]+.*$/.test(aPsd)) {
				this.aPsdMsg = "密码不能含有汉字";
				return false;
			}
			this.aPsdMsg = "";
			return true;
		},
		//
		vPsdCheck:function(){
			var vPsd = this.vPsd;
			var aPsd = this.aPsd;
			if(!vPsd){
				this.vPsdMsg="输入不能为空";
				return false;
			}
			if(/.*[\u4e00-\u9fa5]+.*$/.test(vPsd)){
				this.vPsdMsg="密码不能含有汉字";
				return false;
			}
			if(aPsd != vPsd){
				this.vPsdMsg="密码不一致";
				return false;
			}
			this.vPsdMsg = "";
			return true;
		},
		//
		canSubmit:function(){
			if (this.phoneNum 
				&& this.phoneYzm 
				&& this.aPsd 
				&& this.vPsd
				&& !this.submitLock) {
				return "";
			}
			return "disabled";
		},
		//
		submit:function(){
			var self = this;
			if(this.phoneNumCheck() 
				&& this.phoneYzmCheck() 
				&& this.aPsdCheck() 
				&& this.vPsdCheck()){
				if (self.submitLock) {
					return;
				}
				self.submitLock = true;
				var regLoginUrl = contentPath + "/user/regUser";
				$http.post(regLoginUrl, {
					"phoneNum":self.phoneNum,
					"phoneYzm":self.phoneYzm,
					"aPsd":self.aPsd //???待做base64或des等或用https
				}).then(
					function(res){
						var msg = res.data;
						if (!msg.success) {
							Tip.message(msg.errorMsg, "error");
						} else {
							$location.path('/hotelMsg');
						}
						self.submitLock = false;
					},
					function(res){
						Tip.message("注册用户异常", "error");
						self.submitLock = false;
					}
				);
			}
		},
		backLogin:function(){
			location.href = contentPath + "/";
		}
	}
}]);
controllers.controller('controllers.regHotelMsgCtrl',['$scope', '$location','$http','$timeout', '$anchorScroll', function($scope,$location,$http,$timeout,$anchorScroll){
	jQuery("iframe").load(function(){
		var iframe = jQuery("#a-map");
		iframe[0].contentWindow && iframe[0].contentWindow.parentH && (iframe[0].contentWindow.parentH = $scope);
		$scope.hotel.getPros().then(function(){
			// $scope.hotel.proChange().then(function(){
			// 	$scope.hotel.cityChange();
			// });
			$scope.hotel.lockOrUnlockMapAddr();
		});
	});

	$scope.position = {
		detailAddr: "",
		longitude: "",
		latitude: "",
		_proObj: {
			code: "",
		},
		_cityObj: {
			code: "",
		},
		_disObj: {
			code: "",
			id: "",
		},
		_areaObj:{
			areacode: "",
			name: "",
		}
	};
	
	$scope.hotel = {
		MIKE_PNG:contentPath + "/images/index-log.png",
		PHONE:contentPath + "/images/phone2.png",
		HOTELBACK:contentPath + "/images/reg2-bg_01.png",
		A_MAP:contentPath + "/main/reg/aMap.html",
		//
		hotelName:"",
		managerName:"",
		hotelPhone:"",
		pmsCode:"",
		pmsUserList:[],
		qtPhone:"",
		openTime:"",
		repairTime:"",
		introduction:"",
		//
		pmsExist:false,
		//
		hotelNameCheck:function(){
			if(!this.hotelName){
				this.hotelNameMsg="输入不能为空";
				$location.hash('hotel-name-con');
            	$anchorScroll();
				return false;
			}
			this.hotelNameMsg="";
			return true;
		},
		managerNameCheck:function(){
			if(!this.managerName){
				this.managerNameMsg="输入不能为空";
				$location.hash('manager-name-con');
            	$anchorScroll();
				return false;
			}
			this.managerNameMsg="";
			return true;
		},
		hotelPhoneCheck:function(){
			if(!this.hotelPhone){
				this.hotelPhoneMsg="输入不能为空";
				$location.hash('hotel-phone-con');
            	$anchorScroll();
				return false;
			}
			var regExp= /\d$/;
			if (!regExp.test(this.hotelPhone)) {
				this.hotelPhoneMsg = "请正确输入电话号码";
				$location.hash('hotel-phone-con');
            	$anchorScroll();
				return false;
			}
			this.hotelPhoneMsg="";
			return true;
		},
		pmsCodeCheck:function(){
			if(!this.pmsCode){
				this.pmsCodeMsg="输入不能为空";
				$location.hash('pms-code-con');
            	$anchorScroll();
				return false;
			}
			this.isPmsCodeExist();
			this.pmsCodeMsg="";
			return true;
		},
		pmsCodeExistCheck:function(){
			if(!this.pmsCode){
				this.pmsCodeMsg="输入不能为空";
				$location.hash('pms-code-con');
            	$anchorScroll();
				return false;
			}
			if(!this.pmsExist){
				this.pmsCodeMsg="账号验证未通过";
				$location.hash('pms-code-con');
            	$anchorScroll();
				return false;
			}
			return true;
		},
		//
		isPmsCodeExist:function(){
			var self = this;
			self.pmsExist = false;
			$http.post(contentPath + "/hotel/pmsUserByCode",{
				"pmsCode": self.pmsCode
			}).then(
				function(res){
					if (res.data) {
						if (res.data.success) {
							self.pmsCodeMsg = "";
							self.pmsExist = true;
						} else {
							self.pmsCodeMsg = "账号验证未通过";
							self.pmsExist = false;
						}
					} else {
						Tip.message("验证销售人员账号异常，请稍后再试", "error");
						self.pmsCodeMsg = "账号验证异常";
						self.pmsExist = false;
					}
				},
				function(){
					Tip.message("验证销售人员账号异常，请稍后再试", "error");
					self.pmsExist = false;
				}
			);
		},
		qtPhoneCheck:function(){
			if(!this.qtPhone){
				this.qtPhoneMsg="输入不能为空";
				$location.hash('qt-phone-con');
            	$anchorScroll();
				return false;
			}
			var regExp= /\d$/;
			if (!regExp.test(this.qtPhone)) {
				this.qtPhoneMsg = "请正确输入电话号码";
				$location.hash('qt-phone-con');
            	$anchorScroll();
				return false;
			}
			this.qtPhoneMsg="";
			return true;
		},
		positionCheck:function(){
			if(!$scope.position._proObj.code){
				this.positionMsg="请选择所在省";
				$location.hash('hotel-select-con');
            	$anchorScroll();
				return false;
			}
			if(!$scope.position._cityObj.code){
				this.positionMsg="请选择所在市";
				$location.hash('hotel-select-con');
            	$anchorScroll();
				return false;
			}
			if(!$scope.position._disObj.code){
				this.positionMsg="请选择所在区县";
				$location.hash('hotel-select-con');
            	$anchorScroll();
				return false;
			}
			if(!$scope.position._areaObj.areacode){
				this.positionMsg="请选择所在商圈";
				$location.hash('hotel-select-con');
            	$anchorScroll();
				return false;
			}
			if(!$scope.position.detailAddr){
				this.positionMsg="请输入详细地址";
				$location.hash('hotel-select-con');
            	$anchorScroll();
				return false;
			}
			if(!$scope.position.longitude || !$scope.position.latitude){
				this.positionMsg="请输入正确的详细地址";
				$location.hash('hotel-select-con');
            	$anchorScroll();
				return false;
			}
			this.positionMsg="";
			return true;
		},
		introductionCheck:function(){
			if(!this.introduction){
				this.introductionMsg="输入不能为空";
				return false;
			}
			this.introductionMsg="";
			return true;
		},
		openTimeCheck:function(){
			if(!this.openTime){
				this.openTimeMsg="输入不能为空";
				$location.hash('open-time-con');
            	$anchorScroll();
				return false;
			}
			this.openTimeMsg="";
			return true;
		},
		repairTimeCheck:function(){
			if(!this.repairTime){
				this.repairTimeMsg="输入不能为空";
				$location.hash('repair-time-con');
            	$anchorScroll();
				return false;
			}
			this.repairTimeMsg="";
			return true;
		},
		//
		getPmsUserList:function(){
			var self = this;
			$http.get(contentPath + "/hotel/pmsUsers").then(
				function(res){
					if (res.data) {
						self.pmsUserList = res.data;
					}
				},
				function(){
					//
				}
			);
		},
		//
		canSubmit:function(){
			if (this.hotelName 
				&& this.managerName 
				&& this.hotelPhone 
				&& this.pmsCode 
				&& !this.pmsCodeMsg 
				&& this.qtPhone 
				&& $scope.position._proObj.code 
				&& $scope.position._cityObj.code 
				&& $scope.position._disObj.code 
				&& $scope.position._disObj.id 
				&& $scope.position._areaObj.areacode 
				&& $scope.position.detailAddr 
				&& $scope.position.longitude 
				&& $scope.position.latitude 
				&& this.pmsExist 
				&& this.openTime 
				&& this.repairTime 
				&& this.introduction) {
				return "";
			}
			return "disabled";
		},
		//
		submit:function(){
			var self = this;
			if(self.hotelNameCheck() 
				&& self.managerNameCheck() 
				&& self.hotelPhoneCheck() 
				&& self.pmsCodeExistCheck() 
				&& self.qtPhoneCheck() 
				&& this.positionCheck() 
				&& this.openTimeCheck() 
				&& this.repairTimeCheck() 
				//&& this.introduction
				){
				if (self.submitLock) {
					return;
				}
				self.submitLock = true;
				$http.post(contentPath + "/hotel/regHotel", {
					hotelName: self.hotelName,
					managerName: self.managerName,
					hotelPhone: self.hotelPhone,
					pmsCode: self.pmsCode,
					qtPhone: self.qtPhone,
					provcode: $scope.position._proObj.code,
					citycode: $scope.position._cityObj.code,
					discode: $scope.position._disObj.code,
					disId: $scope.position._disObj.id,
					areacode: $scope.position._areaObj.areacode,
					areaname: $scope.position._areaObj.name,
					detailAddr: $scope.position.detailAddr,
					longitude: $scope.position.longitude,
					latitude: $scope.position.latitude,
					openTime: self.openTime,
					repairTime: self.repairTime,
					introduction: self.introduction
				}).then(
					function(res){
						var msg = res.data;
						if (!msg.success) {
							Tip.message(msg.errorMsg, "error");
						} else {
							Tip.message("酒店添加成功");
							$location.path('/photoMsg');
						}
						self.submitLock = false;
					}, 
					function(msg){
						Tip.message("酒店注册异常", "error");
						self.submitLock = false;
					}
				);
			}
		},
		logout:function(){
			location.href = contentPath + "/resources/j_spring_security_logout";
		},
		enterHome:function(){
			location.href = contentPath + "/";
		}
	}
	$scope.hotel.getPmsUserList();
}]);
controllers.controller('controllers.regPhotoMsgCtrl',['$scope', '$location', 'FileUploader', '$modal', '$http', '$q', 'HmsModalService', function($scope,$location,FileUploader,$modal,$http,$q,HmsModalService){
	$scope.photo = {
		MIKE_PNG:contentPath + "/images/index-log.png",
		PHONE:contentPath + "/images/phone3.png",
		canSubmit:function(){
			var self = this;
			if ($scope.yyzzImg.previews[0] 
				|| ($scope.sfzzmImg.previews[0] 
				&& $scope.sfzfmImg.previews[0])) {
				if (!self.submitLock) {
					return "";
				}
			}
			return "disabled";
		},
		submit:function(){
			var self = this;
			if($scope.yyzzImg.previews[0] 
				|| ($scope.sfzzmImg.previews[0] 
				&& $scope.sfzfmImg.previews[0])){
				if (self.submitLock) {
					return;
				}
				self.submitLock = true;
				$http.post(contentPath + "/qiniu/saveHotelCredentialsPic", {
					businessLicenseFront: $scope.yyzzImg.previews[0] || "",
					idCardFront: $scope.sfzzmImg.previews[0] || "",
					idCardBack: $scope.sfzfmImg.previews[0] || ""
				}).then(
					function(res){
						var msg = res.data;
						if (!msg.success) {
							Tip.message(msg.errorMsg, "error");
						} else {
							Tip.message("酒店证照添加成功");
							$location.path('/successMsg');
						}
						self.submitLock = false;
					}, 
					function(msg){
						Tip.message("酒店证照添加异常", "error");
						self.submitLock = false;
					}
				);
			}
		},
		logout:function(){
			location.href = contentPath + "/resources/j_spring_security_logout";
		},
		enterHome:function(){
			location.href = contentPath + "/";
		}
	}

	$scope.yyzzImg = {
		"btnName": "上传我的营业执照",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 1,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|"
	};
	$scope.sfzzmImg = {
		"btnName": "上传我的身份证正面",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 1,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|"
	};
	$scope.sfzfmImg = {
		"btnName": "上传我的身份证反面",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 1,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|"
	};
	$scope.delImg  = function(img, id){
		if (!confirm("确认删除照片？")) {
			return;
		}
		// for(var i=0;i<img.keys.length;i++) {
		// 	if (img.keys[i] == key) {
		// 		img.keys.baoremove(i);
		// 		img.previews.baoremove(i);
		// 	}
		// }
		img.keys = [];
		img.previews = [];
		jQuery("#" + id).removeAttr("src");
	};

	/**TAB*/
	$scope.tabs = [
		{
	    	"title": "营业执照"
		},
		{
		    "title": "身份证"
		}
	];
	$scope.tabs.activeTab = 0;
}]);
controllers.controller('controllers.regSuccessMsgCtrl',['$scope', '$location', function($scope,$location){
	$scope.succ = {
		WAIT_PNG:contentPath + "/images/reg4_02.png",
		outback:function(){
			location.href = contentPath + "/resources/j_spring_security_logout";
		},
		enterHome:function(){
			location.href = contentPath + "/";
		},
		add:function(){
	 		$location.path('/hotelMsg');
		}
	}
}]);