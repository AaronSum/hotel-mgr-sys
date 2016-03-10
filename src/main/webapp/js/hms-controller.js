/**hms controller*/

'use strict';

// hms controller
angular.module("hmsInitApp", ["mgcrea.ngStrap"])

// socket
.controller("socketController", ["$rootScope", "$scope", "HmsHttp", "$timeout", "HmsModalService", function($rootScope, $scope, HmsHttp, $timeout, HmsModalService) {
	// changePrice 消息队列
    $rootScope.changePriceQueue = [];
    // 是否正在处理议价
    $rootScope.isProcessingSocketModal = false;
    // 消息中心未处理队列
    $rootScope.waitingProcessQueue = [];
	// 关闭弹出层
	$scope.closeSocketModal = function() {
		HmsModalService.hide();
		dealChangePriceQueue();
	};
	// 前台议价
	$scope.changeOrderBargainByWaiter = function() {
		if ( $scope.dataObj.changeprice > $scope.dataObj.totalPrice ) {
			Tip.message("房价不能大于平台价", "error");
			return;
		}else if($scope.dataObj.changeprice<10){
			Tip.message("房价不能小于10元", "error");
			return;
		}else if($scope.dataObj.changeprice == $scope.dataObj.totalPrice){ //如果房价没有变化，不调用后台议价接口，直接关闭窗口
			$scope.close();
			return;
		}
		HmsHttp.post(contentPath + "/otsorder/bargainOrder", {
			otaorderid: $scope.dataObj.otaOrderId,
			changeprice: $scope.dataObj.changeprice
		})
		.success(function(data) {
			if (data.success) {
				Tip.message("议价成功");
				// $scope.dataObj.isOk = true;
			} else {
				Tip.message(data.errorMsg, "error");
			}
		}).error(function(msg) {
			Tip.message("议价异常，请联系系统管理员", "error");
		});
	};
	//处理切客队列
	function dealChangePriceQueue() {
		if ($rootScope.changePriceQueue.length > 0) {
			processChangePrice($rootScope.changePriceQueue[0]);
			$timeout(function() {
				$rootScope.changePriceQueue.baoremove(0);
			}, 2000);
		} else {
			$rootScope.isProcessingSocketModal = false;
		}
	}
	// 获取订单信息
	function getOtsOrderInfo(orderId) {
		return HmsHttp.post(contentPath + "/otsorder/findOtsOrderById", {
			otaorderid: orderId
		});
	};
	//显示议价窗口
	function processChangePrice(orderId) {
		$rootScope.isProcessingSocketModal = true;
		getOtsOrderInfo(orderId)
		.success(function(data) {
			if (data.success) {
				$scope.dataObj = data.attribute;
				$scope.dataObj.changeprice = $scope.dataObj.totalPrice;
				$scope.dataObj.isOk = false;
				HmsModalService.show({
					"scope": $scope,
					"title": "您有新订单需要确认",
					"contentTmpl": contentPath + "/main/home/template/socket-modal.html"
				});
			} else {
				Tip.message(data.errorMsg, "error");
			}
		}).error(function(msg) {
			Tip.message("获取订单信息失败", "error");
		});
	}
	//监听议价广播
	$scope.$on('CHANGE_ORDER_PRICE',function(event,msg){
		var orderId;
		if (typeof msg == "string" || typeof msg == "number") {
			orderId = msg;
		} else if (typeof msg == "object") {
			orderId = msg.orderId;
		} else {
			Tip.message("lack of parameters", "error");
			return;
		}
		
		if (!$rootScope.isProcessingSocketModal) {
    		processChangePrice(orderId);
    	} else {
    		$rootScope.changePriceQueue.push(orderId);
    	}	
	});
	//处理收到的订单
	function processReceivedOrder() {
		getOtsOrderInfo(orderId)
		.success(function(data) {
			if (data.success) {
				$scope.dataObj = data.attribute;
			} else {
				Tip.message(data.errorMsg, "error");
			}
		}).error(function(msg) {
			Tip.message("获取订单信息失败", "error");
		});
	}
	//监听接收到订单提醒广播
	$scope.$on('RECEIVE_ORDER',function(event,msg){
		Tip.message("您有一条新消息");
		$rootScope.waitingProcessQueue.push(msg);
	});
	
	
	//获取物品采购菜单权限
	$rootScope.isShowShoppingTab = false;
	var params = {
			menuName: "物品采购",
			hotelId: thisHotel.id
		}
	HmsHttp.post(contentPath + "/shopping/getShoppingCartMenu",params)
				.success(function(data) {
					$rootScope.isShowShoppingTab = data.success;
				}).error(function(mgs) {
					Tip.message("获取物品采购菜单信息失败", "error");
	});
	
}])

// controller
.controller("hmsController", ["$rootScope", "$scope", "HmsHttp", "$timeout","$location", "$popover", '$aside', 'InterfaceService', 'HmsModalService', function($rootScope, $scope, HmsHttp, $timeout, $location, $popover, $aside, InterfaceService, HmsModalService) {
	// 初始化当前选中页签
	$scope.isActive = $location.path() ? $location.path().split('\/')[1] : 'home';

	$scope.homeNavDropDownBtn = (thisHotel.hotelName || '暂无酒店信息') + '<span class="caret"></span>';
	// 获取酒店列表, 放入根上
	$rootScope.hotels = hotelList;
	HmsHttp.get(contentPath + "/hotel/thisHotel")
	.success(function(data) {
		// 格式化当前酒店字段
		data.openTime = data.fmtOpenTime;
		data.repairTime = data.fmtRepairTime;
		
		$rootScope.thisHotel = data;
		//
		$scope.hotelStatus = {};
		$scope.hotelStatus.init = true;
		$scope.hotelTooltip = $popover(jQuery("#hotel-tooltip-label"), {
			title: '酒店当前状态信息',
			contentTemplate: 'tooltip-content.tmpl.html',
			placement: 'bottom',
			prefixEvent: 'hotelTooltip',
			autoClose: true,
			scope: $scope
		});
	});

	// 当前酒店，默认为第一个酒店
	
	//分组信息
	$rootScope.group = group;
	
	// 用户信息
	$rootScope.user = user;
	
	// 登出url
	$scope.loginOutAction = contentPath + "/resources/j_spring_security_logout";	
	//切换路由
	$scope.changeHotel = function(index) {
		$rootScope.thisHotel = $rootScope.hotels[index];
		location.href = contentPath + "/login/main/" + $rootScope.thisHotel.id;
	};
	
	// 新添加酒店
	$scope.addNewHotel = function() {
		location.href = contentPath + "/main/reg/reglogin.jsp#/hotelMsg";
	};

	$scope.getThisHotelRealStatus = function(){
		HmsHttp.get(contentPath + "/hotelmessage/getThisHotelRealStatus").success(function(data) {
			if (!data.success) {
				console.error("获取酒店 信息失败");
				return;
			}
			angular.extend($scope.hotelStatus, data.attribute);
		})
		.error(function(mgs) {
			Tip.message("获取酒店信息失败", "error");
		});
	};

	$scope.showToolBar = function(){
		//
		var toolAside = $aside({
			title: '反馈信息',
			template: 'tool-bar.tpl.html',
			animation:"am-slide-right", 
			placement: 'right',
			show: true,
			scope: $scope,
			backdrop: false
		});

		var close = function(event){
			if (1==1 || $scope.toolBar.locked) {
				return;
			}
			setTimeout(function(){
				showToolBarFlag = false;
			}, 600);
			toolAside.hide();
			jQuery(".tool-bar-start").show();
			document.removeEventListener("click", close);
		};

		document.addEventListener("click", close);
	};

	var showToolBarFlag = false;
	// document.addEventListener("mousemove", function(event){
	// 	if (showToolBarFlag) {
	// 		return;
	// 	}
	// 	var clientWidth = document.documentElement.clientWidth;
	// 	var movX = event.clientX;
	// 	if (clientWidth && movX && (clientWidth - movX < 60) && (clientWidth - movX > 10)) {
	// 		showToolBarFlag = true;
	// 		$scope.showToolBar();
	// 	}
	// }, true);
	jQuery(".tool-bar-start").hover(function(event){
		if (showToolBarFlag) {
			return;
		}
		showToolBarFlag = false;
		//$scope.showToolBar();
		jQuery(this).hide();
	});

	$scope.msg = {
		loading: false,
		list: [],
		currentgPage:1,
		pageCount:10,
		totalItems:0,
		reset:function(){
			this.loading = false;
			this.list = [];
			this.currentgPage = 1;
			this.pageCount = 10;
			this.totalItems = 0;
		},
		msgClick:function(msg){
			var directive = msg.directive;
			if (this[directive] && this[directive].clickCallBack) {
				this[directive].clickCallBack(msg);
			}
		},
		"9":{
			"clickCallBack": function(msg){
				try {
					var cusData = angular.fromJson(msg.data || {});
					if (cusData.orderId) {
						if ($scope.msg.loading) {
							return;
						}
						$scope.msg.loading = true;
			            var url = contentPath + "/otsorder/findOtsOrderById";
			            var params = { 
				            otaorderid: cusData.orderId
				        };
			            HmsHttp.post(url, params).then(
							function(res){
								$scope.msg.loading = false;
								if (res.data) {
									var order = res.data.attribute;
									//弹出窗口
									HmsModalService.show({
										"scope": $scope,
										"title": "您有新的眯客预付订单",
										"content": " 订单ID：" + order.otaOrderId + 
											" 订单ID：" + order.otaOrderId + 
											" 房型：" + order.roomTypeName + 
											" 房间号：" + order.roomNo + 
											" 预计入住天数：" + order.daynumber + 
											" 总价：" + order.totalPrice + 
											" 客人姓名：" + order.memberName
									});

									if (msg.isNew == 2) {
										return;
									}

									/****处理标记**开始**/
									var url = contentPath + "/message/changeFlag";
						            var params = { 
							            id: msg.id
							        };
						            HmsHttp.post(url, params).then(
										function(res){
											if (res.data) {
												msg.isNew = 2;
												$rootScope.waitingProcessQueue.shift();
											}
										}
									);
									/****处理标记**结束**/
								}
							},
							function(msg){
								Tip.message("获取消息失败", "error");
								$scope.msg.loading = false;
							}
						);
					}
				} catch(exception){
					console.log(exception);
				}
			},
			"icon": "fa fa-hotel"
		}
	};

	$scope.toolBar = {
		"locked":Cookies.get('hms-toolbar-fixed') == "true" ? true : false,
		"lock":function(event){
			this.locked = !this.locked;
			if (this.locked) {
				Cookies.set('hms-toolbar-fixed', 'true', { expires: Infinity });
			} else {
				Cookies.set('hms-toolbar-fixed', 'false', { expires: Infinity });
			}
			event.stopPropagation();
		},
		"config":function(){
			$timeout(function(){
				Tip.message("正在开发中。。。");
			});
		},
		"buttons":[
			{
				"name": "房态校准",
				"icon": "building",
				"callBack": function(){
					$timeout(function(){
						Tip.message("正在开发中。。。");
						//InterfaceService.refreshRoomOnlineState($scope, $rootScope.thisHotel.id);
					});
				}
			},
			{
				"name": "乐住币校准",
				"icon": "rmb",
				"callBack": function(){
					$timeout(function(){
						Tip.message("正在开发中。。。");
					});
				}
			},
			{
				"name": "我的消息",
				"icon": "envelope",
				"redPot": $rootScope.waitingProcessQueue,
				"callBack": function(){
					$timeout(function(){
						Tip.message("正在开发中。。。");
						
					});
				}
			},
			{
				"name": "用户设置",
				"icon": "gear",
				"callBack": function(){
					$timeout(function(){
						Tip.message("正在开发中。。。");
					});
				}
			}
		],
		"showTopBtn":false,
		"backToTop":function(){
			if ($scope.toolBar.backing) {
				return false;
			}
			var nowScroll = document.body.scrollTop, flag = "up";
			var funScroll = function() {
				var step = 30;
				var top = document.body.scrollTop;
				if (flag == "up") {
					top -= step;
					if (top <=0) {
						top = 0; 
						flag = "stop";
					}
				} 
				// else if (flag == "down") {
				// 	top += 20;
				// 	if(top >= nowScroll) {
				// 		top = nowScroll;  
				// 		flag = "stop";
				// 	}
				// } 
				else {
					$scope.toolBar.backing = false;
					return; 
				}
				document.body.scrollTop = top;
				requestAnimationFrame(funScroll);
			};
			if (nowScroll) {
				$scope.toolBar.backing = true;
				$scope.toolBar.showTopBtn = false;
				funScroll();
			};
		}
	};
	document.addEventListener("scroll", function(){
		var nowScroll = document.body.scrollTop;console.log(nowScroll);
		if (nowScroll && nowScroll > 40) {
			$scope.toolBar.showTopBtn = true;
		} else {
			$scope.toolBar.showTopBtn = false;
		}
		$scope.$digest();
	}, true);

	if (1==1 || $scope.toolBar.locked) {
		showToolBarFlag = false;
		//$scope.showToolBar();
		jQuery(".tool-bar-start").hide();
	}

	$scope.preview = {
		"show": false,
		"contentTmpl": ""
	};

	if (false && Cookies.get('hms-toolbar-preview') != 'true') {
		$scope.preview.contentTmpl = "preview-tool-bar.tpl.html";
		$scope.preview.show = true;
		$scope.preview.ok = function(){
			$scope.preview.contentTmpl = "";
			if ($scope.preview.shouldHide) {
				Cookies.set('hms-toolbar-preview', 'true', { expires: Infinity });
			}
			$scope.preview.show = false;
		};
	};


	$scope.thisNavIsShow = function(id){
		var isShow = true,
			ruleB = $rootScope.RULE_CODE=='B' && $rootScope.thisHotel.state >= 4,
			ruleA = $rootScope.RULE_CODE=='A';
		switch(id){
			case 'home':
				isShow = ruleB || ruleA;
				break;
			case 'orders':
				isShow = ruleB || ruleA;
				break;
			case 'borders':
				isShow = ruleB || ruleA;
				break;
			case 'rates':
				isShow = ruleB || ruleA;
				break;
			case 'account':
				isShow = ruleB || ruleA;
				break;
			case 'message':
				isShow = true;
				break;
			case 'users':
				isShow = ruleB || ruleA;
				break;
			case 'comment':
				isShow = ruleB || ruleA;
				break;
		}
		return isShow;
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
