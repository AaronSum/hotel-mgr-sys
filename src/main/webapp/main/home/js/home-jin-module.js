/**home-jin module*/

"use strict";

// module
angular.module("homeJinApp", ['ui.router','ui.bootstrap','ui.grid','ui.grid.selection'])

// controller homeJinHeaderController
.controller("homeJinHeaderController", ["$rootScope","$scope","$modal","$location", function($rootScope,$scope,$modal,$location) {
	
	// 登出
	$scope.loginOutAction = contentPath + "/resources/j_spring_security_logout";

	$scope.toAccountMgr = function(){
		$location.path("/accountMgr");
	};
	
}])

// controller homeJinController
.controller("homeJinController", ["$rootScope","$scope", "HmsHttp", "$timeout","HmsModalService","$location", "$log",
	function($rootScope,$scope, HmsHttp, $timeout,HmsModalService,$location,$log) {
	/**
	 * Enum
	 */
	var Enum = {
			// 酒店pms状态
			HmsEHotelPmsStatusEnum: {
				Init: 0,
				Match: 1,
				Synchronous: 2
			},
			// 酒店状态
			HmsEHotelStatusEnum: {
				UNInitial: 0,
				Initial: 1,
				ManagerEdite: 2,
				Submit: 3,
				ManagerEditing: 4,
				Editing: 5
			}
		};
	//标题头
	$scope.columns = [{   field: "id",
                displayName: "主键",
                class:'text-center',
                width:'10%'
            }, {
                field: "hotelName",
                displayName: "酒店名",
                class:'text-left',
                width:'20%'
            }, {
                field: "detailAddr",
                displayName: "地址",
                class:'text-left',
                width:'30%'
            }, {
                field: "hotelContactName",
                displayName: "联系人",
                class:'text-center',
                width:'10%'
            }, {
                field: "saleUser",
                displayName: "销售联系方式",
                class:'text-center',
                width:'10%'
            }, {
                field: "online",
                displayName: "在线状态",
                class:'text-center',
                width:'10%'
            }];
	// 默认选中页签
	$scope.active = 0;
	$scope.isMySelfFlag = !isCheckUser;
	//外来人员权限  true-是   false-否    如果是则隐藏【我的酒店】选项
	$scope.isForeignPerson = isForeignPerson;
    $scope.navItems = [{
    	'id':'all',
    	'displayName':'全部酒店',
    	'action':'findHotelList'
    },{
    	'id':'pms',
    	'displayName':'待安装PMS',
    	'action':'findHotelListByPmsStatus'
    },{
    	'id':'edit',
    	'displayName':'待完善酒店信息',
    	'action':'findHotelListByStatus'
    },{
    	'id':'check',
    	'displayName':'待审核酒店',
    	'action':'findHotelListByCheck'
    },{
    	'id':'online',
    	'displayName':'已上线酒店',
    	'action':'findHotelListOnLine'
    }];
	
	if(isCheckUser){
		$scope.navItems.push({
    		'id':'offline',
	    	'displayName':'已下线酒店',
	    	'action':'findHotelListOffLine'
	    });
	}
	if($location.hash() == "adjust"){
		$scope.navItems.push({
    		'id':'all',
	    	'displayName':'位置校准',
	    	'action':'findHotelList',
	    	'funCode': 'locationAdjust'
	    });
	}
	$scope.queryContent = '';//搜索内容
	$scope.totalItems = 0; //数据总条数
	$scope.currentPage = 1; //当前页
	$scope.pageCount = 5; //每页显示数量
	$scope.numPages = 1; //总页数
	$scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
    //搜索框回车键功能
    $scope.enter = function(event){
    	if(event.keyCode == 13){
    		$scope.doSearch();
    	}
    };
    $scope.doSearch = function(){
		loadData();
    };
	//当前页发生变化时操作
	$scope.pageChanged = function(){
		loadData();
	};
    //我的变化时
    $scope.mySelfFlagChanged = function(){
        loadData();
    };
	//设置切换便签
	$scope.setActive = function(index){
		$scope.active = index;
		$scope.queryContent = '';//切换便签时清空搜索关键字
		$scope.currentPage = 1;
		if ($scope.navItems[index] && $scope.navItems[index].funCode && $scope.navItems[index].funCode == "locationAdjust") {
			$scope.pageCount = 100;
			$scope.isMySelfFlag = false;
		} else {
			$scope.pageCount = 5;
		}
        loadData();
	};
    //安装pms
   	$scope.installPMS = function(hotelInfo) {
	  	$scope.hotelInfo = hotelInfo;
		HmsModalService.show({
			scope: $scope,
			contentTmpl: contentPath + '/main/home/template/installpms-modal.html'
		});
  	};
	// 审核酒店信息
	$scope.checkEHotel = function(hotelInfo) {
		$rootScope.ctrlMode = 'check';
		loadHotelInfo( hotelInfo );		
	};
	$scope.editHotel = function(hotelInfo){
		$rootScope.ctrlMode = 'edit';
		loadHotelInfo( hotelInfo );
	};
	$scope.onlineHotel = function(hotelInfo){
		if( confirm('确定上线['+hotelInfo.hotelName+']这家酒店吗？') ){
			$scope.showLoading = true;
			HmsHttp.post(contentPath + "/hotelmessage/onLineHotel", {
				hotelid: hotelInfo.id
			})
			.success(function(data) {
				$scope.showLoading = false;
				if (!data.success) {
					Tip.message(data.errorMsg, "error");
				} else {
					loadData();
					Tip.message(hotelInfo.hotelName+"已上线");
				}
			}).error(function(msg) {
				$scope.showLoading = false;
				Tip.message(hotelInfo.hotelName+"上线操作失败", "error");
			});
		}
	};
	$scope.offlineHotel = function(hotelInfo){
		if( confirm('确定下线['+hotelInfo.hotelName+']这家酒店吗？') ){
			$scope.showLoading = true;
			HmsHttp.post(contentPath + "/hotelmessage/offlineHotel", {
				hotelid: hotelInfo.id
			})
			.success(function(data) {
				$scope.showLoading = false;
				if (!data.success) {
					Tip.message(data.errorMsg, "error");
				} else {
					loadData();
					Tip.message(hotelInfo.hotelName+"已下线");
				}
			}).error(function(msg) {
				$scope.showLoading = false;
				Tip.message(hotelInfo.hotelName+"下线操作失败", "error");
			});			
		}
	};
	$scope.readHotel = function(hotelInfo){
		$rootScope.ctrlMode = 'read';
		loadHotelInfo( hotelInfo );
	};

	//安装pms
   	$scope.showInstallPMS = function(d) {
		return d.pmsStatus <= Enum.HmsEHotelPmsStatusEnum.Init && isMeOrSuper(d);
  	};
  	//正在安装pms
   	$scope.showInstallPMSing = function(d) {
		return d.pmsStatus == Enum.HmsEHotelPmsStatusEnum.Match && isMeOrSuper(d);
  	};
	//审核酒店信息
	$scope.showCheckEHotel = function(d) {
		return isCheckUser && (d.state == Enum.HmsEHotelStatusEnum.Submit || d.state == Enum.HmsEHotelStatusEnum.Editing) && !isCurType('online') && !isCurType('offline') && d.visible != 'F';
	};
	//编辑酒店信息
	$scope.showEditHotel = function(d){
		return !$scope.showCheckEHotel(d) && !$scope.showReadHotel(d);
    };
	$scope.showOnlineHotel = function(d){
		return isCheckUser && d.visible == 'T' && isOnlined(d) && !isCurType('check');
	};
	$scope.showOfflineHotel = function(d){
		return isCheckUser && d.visible == 'F' && isOnlined(d);
	};	
	//查看酒店信息
	$scope.showReadHotel = function(d){
		return !isMeOrSuper(d) || $scope.showInstallPMS(d) || $scope.showInstallPMSing(d) || (isInChecking(d) && isMe(d)) || $scope.showOfflineHotel(d);
	};	
	function isCurType(type){
		return type == $scope.navItems[$scope.active].id;
	};
	function isMeOrSuper(d){
		return d.pmsUser == user.loginname || isCheckUser;
	};
    function isMe(d){
        return d.pmsUser == user.loginname;
    };
    function isInChecking(d){
        return d.state == Enum.HmsEHotelStatusEnum.Submit || d.state == Enum.HmsEHotelStatusEnum.Editing;
    };
	function isOnlined(d){
		return d.state == Enum.HmsEHotelStatusEnum.ManagerEditing || d.state == Enum.HmsEHotelStatusEnum.Editing;
	}
	//跳转到编辑页面
	function loadHotelInfo(hotelInfo){
        $rootScope.hmsLoading.show();
		//想session中注册hotelId
		HmsHttp.post(contentPath+"/hotel/pmsHotel",{hotelId:hotelInfo.id})
		.success(function(data){
            if(data && data.attribute){
                //设置rulecode
                $rootScope.RULE_CODE = data.attribute.ruleCode=='1001' ? 'A' : 'B'; //上线的酒店规则
                //跳转
                data.attribute.eHotel.openTime = data.attribute.eHotel.fmtOpenTime;
                data.attribute.eHotel.repairTime = data.attribute.eHotel.fmtRepairTime;
                $rootScope.thisHotel = data.attribute.eHotel;
                // $rootScope.RULE_CODE = ruleCode; //上线的酒店规则 
                $location.path("/message/hotelInfo");                
            }else{
                Tip.message('加载酒店数据失败','error');
            }
            $rootScope.hmsLoading.hide();
		})
		.error(function(error){
            $rootScope.hmsLoading.hide();
            Tip.message('加载酒店数据失败','error');
		});
	}
	$scope.rowsData = [];
    //加载数据
    function loadData(){
    	//如果是外来人员的权限,直接查询我的酒店
    	if($scope.isForeignPerson){
    		$scope.isMySelfFlag = true;
    	}
    	$rootScope.hmsLoading.show();
		var url = contentPath+"/hotelmessage/"+$scope.navItems[$scope.active]['action'];
		var params = { 
			pageNum:$scope.currentPage,
			pageSize: $scope.pageCount,
			isMySelfFlag:$scope.isMySelfFlag,
			queryContent:$scope.queryContent
		};
		HmsHttp.post(url, params)
		.success(function(data) {
    		$rootScope.hmsLoading.hide();
			$scope.rowsData = data.rows;
			$scope.totalItems = parseInt(data.total);
			formatDatas($scope.rowsData);
            if($scope.rowsData && $scope.rowsData.length>0)
                loadSaleUsers();
		}).error(function(error){			
    		$rootScope.hmsLoading.hide();
		});
    };
    //
    function formatDatas(dataList){
    	for(var i=0;i<dataList.length;i++) {
    		var data = dataList[i];

    		if ($scope.navItems[$scope.active] 
    			&& $scope.navItems[$scope.active].funCode 
    			&& $scope.navItems[$scope.active].funCode == "locationAdjust") {
				checkLoaction(data);
			}

    		if (data.online) {
    			if (data.online == "T") {
    				data.online = "在线";
    				continue;
    			} else if (data.online == "F") {
    				data.online = "离线";
    				continue;
    			}
    		}
    		data.online = "---";
    	}
    }
    function checkLoaction(data){
    	var city = data.cityid;
    	var detailAddr = data.detailAddr;
    	var srclng = data.longitude;
    	var srclat = data.latitude;
    	if (!city || !detailAddr || !srclng || !srclat) {
    		data.okLoaction = 3;
    		return;
    	}

    	var MGeocoder;
	    //加载地理编码插件
	    AMap.service(["AMap.Geocoder"], function() { 
	        MGeocoder = new AMap.Geocoder({ 
	            city:city, //城市，默认：“全国”
	            radius:3000 //范围，默认：500
	        });
	        //返回地理编码结果  
	        //地理编码
            MGeocoder.getLocation(detailAddr, function(status, result){
                if (status === 'complete' && result.info === 'OK'){
                    var geoArr = result.geocodes;
                    if (geoArr && geoArr.length>0) {
                    	data.newLatitude = geoArr[0].location.lat;
                    	data.newLongitude = geoArr[0].location.lng;
                    	if (Math.round(data.newLatitude*100000) != Math.round(srclat*100000) 
                    		|| Math.round(data.newLongitude*100000) != Math.round(srclng*100000)) {
                    		data.okLoaction = 2;
                    	} else {
                    		data.okLoaction = 1;
                    	}
                    } else {
                    	 data.okLoaction = 3;
                    }
                } else {
                	data.okLoaction = 3;
                }
                $scope.$digest();
            });
	    });
    }
    $scope.locationAdjustAll = function(){
    	if (!confirm("是否确定调整位置？")) {
    		return;
    	}
    	var addjust = [];
    	for (var i=0;i<$scope.rowsData.length;i++) {
    		var data = $scope.rowsData[i];
    		if (data.okLoaction == 2) {
    			addjust.push({
    				"hotelId": data.id,
    				"lat": data.newLatitude,
    				"lng": data.newLongitude
    			});
    		}
    	}
    	if (addjust) {
    		addjustHotelLocation(addjust);
    	} else {
    		Tip.message('没有数据需要调整');
    	}
    };
    $scope.locationAdjust = function(data){
    	if (!confirm("是否确定调整位置？")) {
    		return;
    	}
    	var addjust = [];
    	if (data.okLoaction == 2) {
			addjust.push({
				"hotelId": data.id,
				"lat": data.newLatitude,
				"lng": data.newLongitude
			});
		}
		if (addjust) {
			addjustHotelLocation(addjust);
    	} else {
    		Tip.message('没有数据需要调整');
    	}
    };
    function addjustHotelLocation(addjust){
    	var addjustUrl = contentPath + "/hotelmessage/addjustHotelLocation";
    	var params = {
    		"adjustList": angular.toJson(addjust)
    	};
    	$rootScope.hmsLoading.show();
    	HmsHttp.post(addjustUrl, params).success(
    		function(res){
    			if (res.count && res.count >= 0) {
    				Tip.message(res.count + "条数据调整成功");
    				loadData();
    			} else {
    				Tip.message("数据调整失败", "error");
    			}
    			$rootScope.hmsLoading.hide();
    		}).error(
    		function(err){
    			$rootScope.hmsLoading.hide();
    			Tip.message("数据调整失败:" + err, "error");
    		}
		);
    }
    //加载销售人员信息数据
    function loadSaleUsers(){
        var hotelIdList = [];
        angular.forEach($scope.rowsData,function(item,i){
            hotelIdList.push(item.id);
        });
        if(hotelIdList.length>0){
            var url = contentPath+"/hotelmessage/findSaleUserList";
            var params = {
                hotelIds:hotelIdList.join(',')
            };
            HmsHttp.post(url, params)
            .success(function(data) {
                if(data){
                    addSaleUser(data);
                }

            }).error(function(error){
                console.log('获取销售人员信息失败');
            });
        }
    };
    //把销售人员信息添加到列表上
    function addSaleUser(saleUserList){
        angular.forEach($scope.rowsData,function(item){
            var saleUser = _.findWhere(saleUserList,{hotelId:item.id});
            if(saleUser){
                item['saleUser'] = saleUser.userName +'\n' + saleUser.userMobile;
            }else{
                item['saleUser'] = '---';
            }
        });
    }
    
    $scope.cancel = function(){
    	HmsModalService.hide();
    };    
    $scope.ok = function(){
	    loadData();
    	$scope.cancel();
    };
	//监听刷新数据广播
	$scope.$on('PMS_RELOAD',function(msg){
		loadData();
	});

    loadData();
}])
/**
 * [安装PMS功能]
 * @param  {[type]} $scope         [description]
 * @param  {[type]} $modalInstance [description]
 * @param  {[type]} hotelId)       {             $scope.hotelId [description]
 * @return {[type]}                [description]
 */
.controller('installPMSController', ['$scope','HmsHttp', '$timeout',
	function ($scope,HmsHttp, $timeout) {
  // $scope.hotelInfo = $scope.$parent.hotelInfo;
  // 安装PMS hotel id
  $scope.pms = {
	    hotelId: $scope.hotelInfo.id,
		pmsCode: $scope.hotelInfo.pms,
		pmsType: 'NEW_PMS',
		psmTypeList: [{
			id:'NEW_PMS',
			name:'新PMS'
		},{
			id:'OLD_PMS',
			name:'旧PMS'
		}],
		// 是否正在安装
		isInstalling: false,
		// 初始化安装数据
		install: function() {
			var _self = this;
			if(!_self.pmsCode){
				Tip.message('请输入PMS编码','error');
				return;
			}
			if(_self.pmsType.length<=0){
				Tip.message('请选择PMS类型','error');
				return;
			}
			_self.isInstalling = true;
            var params = {
                hotelId:_self.hotelId,
                pmsCode: _self.pmsCode,
                pmsType:_self.pmsType
            };
            HmsHttp.post(contentPath + "/hotelmessage/installPms",params)
			.success(function(data) {
				if (!data.success) {
					Tip.message(data.errorMsg, "error");
				} else {
					Tip.message("PMS安装成功！");
                    _self.installOk();
				}
			}).error(function(msg) {
				Tip.message("初始化PMS数据失败", "error");
			}).finally(function(){
				_self.isInstalling = false;
			});
		},
		// 安装完成
		installOk: function() {
			$scope.ok();
		}
	};
}])
.controller("homeJinAccountMgrController", ["$rootScope","$scope", "HmsHttp", "$timeout","HmsModalService","$location", "$log", '$aside',
	function($rootScope,$scope, HmsHttp, $timeout,HmsModalService,$location,$log,$aside) {

	//标题头
	$scope.columns = [
			{   field: "id",
                displayName: "账期",
                class:'text-center',
                width:'10%'
            }, {
                field: "hotelName",
                displayName: "酒店名",
                class:'text-left',
                width:'30%'
            }, {
                field: "billcost",
                displayName: "金额",
                class:'text-left',
                width:'10%'
            }, {
                field: "confirmstatus",
                displayName: "确认状态",
                class:'text-center',
                width:'10%'
            }, {
                field: "checkstatus",
                displayName: "审核状态",
                class:'text-center',
                width:'10%'
            }];
	// 默认选中页签
	$scope.active = 0;

    $scope.navItems = [{
    	'id':'all',
    	'displayName':'全部',
    	'action':""
    },{
    	'id':'beforeCheck',
    	'displayName':'未审核',
    	'action':"1"
    },{
    	'id':'waitCheck',
    	'displayName':'待审核',
    	'action':"2"
    },{
    	'id':'successCheck',
    	'displayName':'已审核',
    	'action':"4"
    },{
    	'id':'failedCheck',
    	'displayName':'未通过',
    	'action':"3"
    }];
	
	$scope.queryContent = '';//搜索内容
	$scope.totalItems = 0; //数据总条数
	$scope.currentPage = 1; //当前页
	$scope.pageCount = 5; //每页显示数量
	$scope.numPages = 1; //总页数
	$scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
	$scope.currYearAndMonth = new Date().format("yyyyMM");
    //搜索框回车键功能
    $scope.enter = function(event){
    	if(event.keyCode == 13){
    		$scope.doSearch();
    	}
    };
    $scope.changeMonth = function(){
    	$scope.queryContent = '';//切换便签时清空搜索关键字
		$scope.currentPage = 1
		loadData();
    };
    $scope.prev = function(){
    	var formatDateStr = $scope.currYearAndMonth.substring(0,4) + "-" + $scope.currYearAndMonth.substring(4,6);
    	var date = new Date(formatDateStr).DateAdd('m', -1);
		$scope.currYearAndMonth = date.format("yyyyMM");
		$scope.queryContent = '';//切换便签时清空搜索关键字
		$scope.currentPage = 1
		loadData();
    };
    $scope.next = function(){
    	var formatDateStr = $scope.currYearAndMonth.substring(0,4) + "-" + $scope.currYearAndMonth.substring(4,6);
    	var date = new Date(formatDateStr).DateAdd('m', 1);
		$scope.currYearAndMonth = date.format("yyyyMM");
		$scope.queryContent = '';//切换便签时清空搜索关键字
		$scope.currentPage = 1
		loadData();
    };
    $scope.doSearch = function(){
		loadData();
    };
	//当前页发生变化时操作
	$scope.pageChanged = function(){
		loadData();
	};
	//设置切换便签
	$scope.setActive = function(index){
		$scope.active = index;
		$scope.queryContent = '';//切换便签时清空搜索关键字
		$scope.currentPage = 1
	};
	$scope.rowsData = [];
    //加载数据
    function loadData(){
    	$rootScope.hmsLoading.show();
		var url = contentPath+"/bill/queryBill";
		var params = { 
			pageNum:$scope.currentPage,
			pageSize: $scope.pageCount,
			hotelName:$scope.queryContent,
			yearAndMonth: $scope.currYearAndMonth,
			checkStatus: $scope.navItems[$scope.active]['action']
		};
		HmsHttp.post(url, params)
		.success(function(data) {
    		$rootScope.hmsLoading.hide();
			$scope.rowsData = data.rows;
			$scope.totalItems = parseInt(data.total);
		}).error(function(error){			
    		$rootScope.hmsLoading.hide();
		});
    };
	$scope.initBill = function (){
		$scope.bill = {
			currMemo:"",
			currAmount:null,
			canSendCheck:false,
			canReadCheck:false
		};
	};
	$scope.initBill();
	$scope.isMgr = true;
    $scope.checkDetails = [];
    $scope.canCheckFun = function(row){
    	if (row.confirmstatus == 1 && new Date().getDate() <= 30) {
			if (row.checkstatus == 2) {
				return true;
			}
		}
		return false;
    };
    $scope.canReadCheckFun = function(row){
		if (row.checkstatus != 1 && !$scope.canCheckFun(row)) {
			return true;
		}
		return false;
    };
    $scope.showReadCheck = function(row){
		$scope.showSheck(row);
	};
	$scope.showSheck = function(row){
		$scope.bill = angular.extend($scope.bill, row);
		$scope.bill.canSendCheck = $scope.canCheckFun(row);
		$scope.bill.canReadCheck = $scope.canReadCheckFun(row);
		$scope.checkDetails = [];
		var checkAside = $aside({
			title: '审核信息', 
			template: 'aside-sendCheck.tpl.html', 
			animation:"am-slide-right", 
			placement: 'right', 
			show: true,
			scope: $scope
		});

		$scope.getCheckDetails = function(){
			HmsHttp.get(contentPath + "/bill/checkInfo&billId=" + $scope.bill.id + "&pageNum=1&pageSize=10").then(
				function(res){
					if (res.data) {
						$scope.checkDetails = res.data.rows;
					}
				}
			);
		};

		$timeout(function(){
			$scope.getCheckDetails();
		},500);

		$scope.asideConfirm = function(){

			$scope.cusContent = "是否确定完成审核？";
			$scope.modalConfirm = function(){
				$scope.replay();
				HmsModalService.hide();
				checkAside.hide();
				$scope.checkDetails = [];
			};

			$scope.modalConfirmClose = function(){
				HmsModalService.hide();
			};

			if (!$scope.checkDetails.length) {
				Tip.message("无审核明细信息", "error");
				return;
			}

			if (!$scope.bill.currAmount) {
				Tip.message("请填写调整后金额", "error");
				return;
			}

			var rate = ($scope.bill.currAmount / $scope.bill.billcost) * 100;
			if (rate < 90 || rate > 110) {
				Tip.message("金额浮动请勿超过10%", "error");
				return;
			}

			HmsModalService.show({
				contentTmpl:"modal-confirm-notice.tmpl.html",
				scope:$scope,
			});
		};

		$scope.asideConfirmCancel = function(){
			$scope.bill.currMemo = "";
			checkAside.hide();
			$scope.checkDetails = [];
		};

		$scope.asideConfirmReject = function(){
			$scope.cusContent = "是否确定驳回该审核？";
			$scope.modalConfirm = function(){
				$scope.reject();
				HmsModalService.hide();
				checkAside.hide();
				$scope.checkDetails = [];
			};

			$scope.modalConfirmClose = function(){
				HmsModalService.hide();
			};

			if (!$scope.checkDetails.length) {
				Tip.message("无审核明细信息", "error");
				return;
			}

			HmsModalService.show({
				contentTmpl:"modal-confirm-notice.tmpl.html",
				scope:$scope,
			});
		};
	};

	$scope.replay = function(){
		if (!$scope.bill.id) {
			Tip.message("无法找到账单信息", "error");
			return;
		}
		$rootScope.hmsLoading.show();
		HmsHttp.post(contentPath + "/bill/pmsAmount", {billId: $scope.bill.id, confirmAmount:$scope.bill.currAmount, checkInfoId: $scope.checkDetails[0], backText:$scope.bill.currMemo})
		.success(function(data) {
            loadData();
		})
		.error(function(msg) {
            $rootScope.hmsLoading.hide();
			Tip.message("确认审核异常", "error");
		});
	};

	$scope.reject = function(){
		if (!$scope.bill.id) {
			Tip.message("无法找到账单信息", "error");
			return;
		}
		$rootScope.hmsLoading.show();
		HmsHttp.post(contentPath + "/bill/pmsCheckAmount", {billId: $scope.bill.id, checkInfoId: $scope.checkDetails[0], backText:$scope.bill.currMemo})
		.success(function(data) {
            loadData();
		})
		.error(function(msg) {
            $rootScope.hmsLoading.hide();
			Tip.message("退回审核异常", "error");
		});
	};

	$scope.showOrderDetail = function(row){
		$scope.billIdForList = row.id;
		HmsModalService.show({
			title: "账期明细列表",
			bodyTmpl:"account-list-ctrl.tmpl.html",
			scope:$scope,
		});

		$scope.closeModal = function(){
			HmsModalService.hide();
		};
	};
    
	//初始化调用（监听当前选择项目变化）
	$scope.$watch('active',function(newVal){
		loadData();
	});

}])
.controller("homeJinAccountDetailListController", ["$rootScope","$scope", "HmsHttp", "$timeout","HmsModalService","$location", "$log", '$aside',
	function($rootScope,$scope, HmsHttp, $timeout,HmsModalService,$location,$log,$aside) {
		//标题头	
    $scope.columns = [
	{
        field: "roomTypeName",
        displayName: "订单号",
        width: '10%'
    }, {
        field: "mName",
        displayName: "天数",
        width: '10%'
    }, {
        field: "liveTime",
        displayName: "房间号",
        width: '10%'
    }, {
        field: "roomNo",
        displayName: "房型",
        width: '10%'
    },  {
        field: "creattime",
        displayName: "入住时间",
        width: '15%'
    }, {
        field: "orderStatusName",
        displayName: "离店时间",
        width: '15%'
    }, {
        field: "orderStatusName",
        displayName: "原房价",
        width: '10%'
    }, {
        field: "orderStatusName",
        displayName: "优惠金额",
        width: '10%'
    }];
	$scope.totalItems = 0; //数据总条数
	$scope.currentPage = 1; //当前页
	$scope.pageCount = 10; //每页显示数量
	$scope.numPages = 1; //总页数
	$scope.maxSize = 5; //当总页数大于多少的时候，分页开始显示...
	//当前页发生变化时操作
	$scope.pageChanged = function(){
		loadData();
	};
    //加载数据
    function loadData(){
    	$rootScope.hmsLoading.show();
		var url = contentPath+"/bill/billInfo";
		HmsHttp.post(url, {billId: $scope.$parent.billIdForList, pageNum:$scope.currentPage, pageSize: $scope.pageCount})
		.success(function(data) {
            $rootScope.hmsLoading.hide();
			//formatData(data);
			// $scope.gridOptions.data = data.rows;
            $scope.rowsData = data.rows; 
			$scope.totalItems = parseInt(data.total);
		})
		.error(function(msg) {
            $rootScope.hmsLoading.hide();
			Tip.message("数据加载异常", "error");
		});
    };
	//格式化数据
	function formatData(data) {
		var rows = data.rows;
		_.map(rows, function(row) {
			var fmBegintime = DateUtils.formatDate4TimeVal(row.begintime, DateUtils.MM_DD),
                fmEndtime = DateUtils.formatDate4TimeVal(row.endtime, DateUtils.MM_DD);
            row.liveTime = fmBegintime +'/'+fmEndtime;
            row.mName = (row.contacts || '无') + '/' + row.contactsPhone;
            row.creattime = DateUtils.formatDate4TimeVal(row.creattime, DateUtils.YYYY_MM_DD_HH_MM_SS);
		});
	};
	//初始化调用（监听当前选择项目变化）
	$scope.$watch('active',function(newVal){
		loadData();
	});
}])
.directive('hmsHtml' , function(){
  return function(scope , el , attr){
    if(attr.hmsHtml){
      scope.$watch(attr.hmsHtml , function(html){
        el.html(html || '');//更新html内容
      });
    }
  };
})
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







