/**user module*/

"use strict";

angular.module("userApp", [])

// controller
.controller("userController", ["$rootScope", "$scope", "HmsHttp", "$timeout","HmsModalService", function($rootScope, $scope, HmsHttp, $timeout,HmsModalService) {
	
	// 默认显示添加按钮
	$scope.isActive = 1;
	
	// 获取可用二维码
	$scope.getAvailableQrcodes = function() {
		HmsHttp.get(contentPath + "/user/qrcodesByUser"+"?userLoginName="+$rootScope.user.loginname)
		.success(function(data) {
			$scope.availableQrcodeList = data;
			$scope.availableQrcodeList.insert(0, {tag: "---请选择---"});
		})
		.error(function(msg) {
			Tip.message("获取可用二维码失败", "error");
		});
	};
	
	// 添加用户
	$scope.addUser = function() {
		$scope.isActive = 2;
	};

	// 是否为店长权限
	$scope.isHotelManager = function() {
		var group = $rootScope.group;
		var user = $rootScope.user;
		var returnFlag = false;
		// 老板，需要旧密码验证
		if (group && group.regphone === user.loginname) {
			returnFlag = true;
		}
		return returnFlag && $scope.isActive === 1 ? true : false;
	};
	
	// 取消添加用户
	$scope.addBack = function() {
		$scope.isActive = 1;
	};
	
	// 保存数据对象
	$scope.saveUserObj = {
		phoneNo: "",
		verifyCode: "",
		password: "",
		name: "",
		qrcodeTag: "",
	};
	
	// 获取验证码
	$scope.sendVerifyCode = function() {
		// 计时器
		$scope.sendVerifyCodeTimer();
		// 获取数据
		HmsHttp.post(contentPath + "/user/verifyCode", {
			phoneNo: $scope.saveUserObj.phoneNo
		})
			.success(function(data) {
				if(data.success) {
					Tip.message("验证码发送成功");
				} else {
					Tip.message(data.errorMsg, "error");
				}
			})
			.error(function(msg) {
				Tip.message("获取验证码异常", "error");
			});
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
	
	// 添加用户
	$scope.saveUserData = function() {
		if($rootScope.RULE_CODE == 'A'){
			$scope.saveUserObj.qrcodeTag.replace("---请选择---", "");
		}else{
			delete $scope.saveUserObj.qrcodeTag;
			delete $scope.saveUserObj.name;
		}
		var isHasValue = _.intersection(_.values($scope.saveUserObj), [""]);
		if (isHasValue.length > 0) {
			Tip.message("请完善信息", "error");
			return;
		}
		$rootScope.hmsLoading.show();
		HmsHttp.post(contentPath + "/user/addHotelUser", $scope.saveUserObj)
			.success(function(data) {
				if (data.success) {
					Tip.message("添加成功！");
					$scope.isActive = 1;
					$scope.sendVerifyCodeFlag = false;
					$scope.sendVerifyCodeTime = 120;
					// 刷新页面
					$scope.finsHotelQrcodeUsers();
				} else {
					Tip.message(data.errorMsg, "error");
				}
				$rootScope.hmsLoading.hide();
			})
			.error(function(msg) {
				$rootScope.hmsLoading.hide();
				Tip.message("添加用户异常", "error");
			});
	};
	// 获取用户列表
	$scope.finsHotelQrcodeUsers = function() {
		HmsHttp.get(contentPath + "/user/qrcodeUsers")
			.success(function(data) {
				$scope.userList = data;
			})
			.error(function(msg) {
				Tip.message("获取二维码用户信息失败", "error");
			});
		// 获取可用二维码
		$scope.getAvailableQrcodes();
	};
	
	// 进入打印页面
	$scope.goToPrintPage = function(content, tag) {
		if (!tag && $rootScope.RULE_CODE == 'A') {
			Tip.message("没有二维码打印信息", "error");
			return;
		}
		Print.goToPrintPage(content, tag, $rootScope.thisHotel.hotelName);
	};
	//解绑手机号
	$scope.unbindPhone = function(user){
		if(!user){
			Tip.message('传入的user为空，不能进行删除操作','error');
			return ;
		}
		$scope.unbind = {
			userid:user.userid,
			tag:user.tag,
			loginname:user.loginname
		};	
    HmsModalService.show({
        contentTmpl:"unbind-phone.tmpl.html",
        scope:$scope,
    });
	};
	
	// 修改密码数据对象
	$scope.modifyPwdObj = {
		// 是否修改标记
		isModifyFlag: false,
		// 旧密码
		oldPwd: "",
		// 新密码
		newPwd: "",
		// 是否修改店长信息
		isModifyHotelManager: false,
		// 但前用户登录名
		userLoginName: "",
		// 重置修改标志
		changeModifyFlag: function(userLoginName) {
			var group = $rootScope.group;
			this.userLoginName = userLoginName;
			// 老板、本人，需要旧密码验证
			if (group && group.regphone === userLoginName || $rootScope.user.loginname === userLoginName) {
				this.isModifyHotelManager = true;
			} else {
				this.isModifyHotelManager = false;
			}
			this.isModifyFlag = this.isModifyFlag ? false : true;
		},
		// 取消修改密码
		cancel: function() {
			this.isModifyFlag = false;
		},
		// 保存密码
		modifyPwd: function() {
			if (this.isModifyHotelManager) {
				if (!$scope.modifyPwdObj.newPwd || !$scope.modifyPwdObj.oldPwd) {
					Tip.message("请完整填写密码！", "error");
					return;
				}
			} else {
				if (!$scope.modifyPwdObj.newPwd) {
					Tip.message("请填写密码！", "error");
					return;
				}
			}
			HmsHttp.post(contentPath + "/user/modifyPwd", {
					oldPwd: $scope.modifyPwdObj.oldPwd,
					newPwd: $scope.modifyPwdObj.newPwd,
					modifyUserLoginName: $scope.modifyPwdObj.userLoginName
				})
				.success(function(data) {
					if (data.success) {
						$scope.modifyPwdObj.isModifyFlag = false;
						Tip.message("密码修改成功，再次登录起效！");
					} else {
						Tip.message(data.errorMsg, "error");
					}
					// 重置变量数据
					$scope.modifyPwdObj.oldPwd = "";
					$scope.modifyPwdObj.newPwd = "";
				})
				.error(function(msg) {
					Tip.message("修改失败！", "error");
				});
		}
	};
	
	// 修改用户信息数据对象
	$scope.modifyHotelUser = {
		// 可用二维码列表
		availableQrcodeList: null,
		// 是否显示
		showModifyHotelUserPanelFlag: false,
		// 获取可用二维码列表
		getAvailableQrcodes: function(userLoginName, tag) {
			var _self = this;
			HmsHttp.post(contentPath + "/user/qrcodes", {
				userLoginName: userLoginName
			})
				.success(function(data) {
					_self.availableQrcodeList = data;
					_self.availableQrcodeList.insert(0, {tag: "---请选择---"});
					_self.availableQrcodeList.insert(1, {tag: tag});
				})
				.error(function(msg) {
					Tip.message("获取可用二维码列表失败", "error");
				});
		},
		// 保存数据对象
		modifyObj: {
			name: "",
			qrcode: "",
			loginName: "",
			isUnbindFlag: false
		},
		// old modifyObj
		oldModifyObj: {},
		// 展示修改面板
		showModifyHotelUserPanel: function(name, userLoginName, tag) {
			this.getAvailableQrcodes(userLoginName, tag);
			$scope.modifyPwdObj.isModifyFlag = true;
			this.showModifyHotelUserPanelFlag = true;
			this.modifyObj.name = name;
			this.modifyObj.qrcode = tag;
			this.modifyObj.loginName = userLoginName;
			this.oldModifyObj = _.clone(this.modifyObj);
		},
		// 取消修改
		cancel: function() {
			$scope.modifyPwdObj.isModifyFlag = false;
			this.showModifyHotelUserPanelFlag = false;
			this.modifyObj.isUnbindFlag = false;
		},
		// 保存数据
		saveModifyData: function() {
			var _self = this;
			var oldDataArray = _.values(this.oldModifyObj);
			var newDataArray = _.values(this.modifyObj);
			var isChangeFlag = _.difference(newDataArray, oldDataArray).length > 0 ? true
					: _.difference(oldDataArray, newDataArray).length > 0 ? true
							: false;
			if (!isChangeFlag) {
				Tip.message("数据没有改变无需保存", "error");
			} else {
				this.modifyObj.qrcode.replace("---请选择---", "");
				HmsHttp.post(contentPath + "/user/modify", this.modifyObj)
					.success(function(data) {
						if (data.success) {
							Tip.message("用户信息修改成功");
							_self.cancel();
							//刷洗页面
							$scope.finsHotelQrcodeUsers();
						} else {
							Tip.message(data.errorMsg, "error");
						}
					})
					.error(function(msg) {
						
					});
			}
		}
	};
	//初始化
	$scope.finsHotelQrcodeUsers();
}])
.controller('UnbindPhoneController', ['$scope','HmsHttp','HmsModalService',
 function($scope,HmsHttp,HmsModalService){
 		// userid: 307, content: "http://wx.imike.com/rt/431/408", tag: "B", loginname: "13817988237"

    $scope.modalConfirm = function(){
        unbind();
    };
    $scope.modalConfirmClose = function(){
        HmsModalService.hide();
    };

   	function unbind(){
   		var params = {
				phoneNo:$scope.unbind.loginname, // 手机号
				tag:$scope.unbind.tag, // 手机号
				pwd: $scope.unbind.password// 当前登录用户确认密码
			}
   		if(!params.pwd){
   			Tip.message('请输入密码','error');
   		}
   		HmsHttp.post(contentPath+'/user/unbindPhone',params)
   		.success(function(result){
   			if(result.success){
   				deleteUser($scope.unbind.userid);
   				Tip.message('删除用户操作成功');
        	$scope.modalConfirmClose();
   			}else{
   				Tip.message(result.errorMsg,'error');   				
   			}
   		})
   		.error(function(error){
   			Tip.message('删除用户操作失败','error');   			
   		});
   	};

   	function deleteUser(userid){
   		angular.forEach($scope.userList,function(item,i){
   			if(item.userid==userid){
   				$scope.userList.splice(i,1);
   			}
   		})
   	};
}])
//directive 二维码图片
.directive("userI2dimcode", function() {
	return {
		restrict: "A",
		link: function(scope, iElement, iAttrs) {
			var src = "";
			if (iAttrs.code) {
				src = contentPath + "/i2dimcodes/userI2DimCode?content="
					+ encodeURIComponent(iAttrs.code) + "&tag=" + iAttrs.tag;
			} else {
				src = contentPath + "/images/erwei-img2.png";
			}
			iElement.attr("src", src);
		}
	}
})