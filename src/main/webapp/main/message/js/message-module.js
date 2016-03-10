/**message module*/

"use strict";

// module
angular.module("messageApp", ['mgcrea.ngStrap.select','hmsAngularLib'])

// controller
.controller("messageController", ["$rootScope", "$scope", "HmsHttp", "$timeout", "$location","HmsModalService","HotelService",
	function($rootScope, $scope, HmsHttp, $timeout,$location,HmsModalService,HotelService) {

	$scope.ctrlMode = $rootScope.ctrlMode ? $rootScope.ctrlMode : 'default'; //控制模式
	$scope.readOnly = $scope.ctrlMode=='edit' || $scope.ctrlMode=='check' ? false : $rootScope.readOnly ? $rootScope.readOnly : true; //编辑/只读
	//控制老板登录,可以编辑按钮
	$scope.editDisabled = false;
	var group = $rootScope.group;
	var user = $rootScope.user;
	if (group && group.regphone != user.loginname) {
		//如果是前台,则不可以编辑
		$scope.editDisabled = true;
	}
	$scope.pmsUserEdit = false;//默认pmsUser 不可以编辑
	if(typeof(isCheckUser) != 'undefined' && isCheckUser){
		//如果是管理员登录,说明可以修改
		$scope.pmsUserEdit = true;
	}
	$scope.hotelNav = {
		'hotelInfo':{
			displayName:'酒店资料',
			item:[{
				'displayName':'酒店描述',
				'view':'/message/hotelInfo'
			},{
				'displayName':'酒店设施',
				'view':'/message/hotelFac'
			},{
				'displayName':'交通',
				'view':'/message/hotelTraffic'
			},{
				'displayName':'照片',
				'view':'/message/hotelPic'
			},{
				'displayName':'周边(选填)',
				'view':'/message/hotelPeripheral'
			},{
				'displayName':'营业执照及身份证',
				'view':'/message/hotelRegPhoto'
			}]
		},
		'roomType':{
			displayName:'房型管理',
			item:[]
		},
		'roomPrice':{
			displayName:'房价管理',
			item:[]
		},
		'accountSetting':{
			displayName:'账期设置',
			item:[{
				'displayName':'账期设置',
				'view':'/message/accountSetting'
			}]
		},
		'roomSetting':{
			displayName:'房间管理',
			item:[{
				'displayName':'房间管理',
				'view':'/message/roomSetting'
			}]
		}
	};
	var roomTypes = []; //房型基础数据
	$scope.getRoomType = function(roomTypeId){
		return _.findWhere(roomTypes,{id:parseInt(roomTypeId)});
	};
	// 当前选中列表项
	$scope.showItem = function(item) {
		showPage(item);
	};
	$scope.selected = function( item ){
		return $location.path() == item.view;
	};
	$scope.showfinish = function(){
		$scope.readOnly = false;
		$scope.ctrlMode = 'edit';
	};
	$scope.ruleCodeDicts = [];
	$scope.showI2DimCode = $rootScope.RULE_CODE=='B';
	$scope.shwoBRuleI2DimCode = function(){
		return contentPath + "/images/showqrcode.png"
	};
	$scope.showRuleCode = !((typeof(group)!='undefined' && typeof(user)!='undefined' && group.id) && (group.id == user.groupid));
	//显示酒店规则设置页面
	$scope.showSetHotelRule = function(){
        if($scope.ruleCodeDicts.length>0){
        	show();
        }else{
        	$rootScope.hmsLoading.show();
	        HotelService.findAreaRules()
	        .success(function(result){
	            $rootScope.hmsLoading.hide();            
				if(result && result.length>0){
					$scope.ruleCodeDicts = result;
					show();
				}else{
					Tip.message('获取酒店规则失败','error');
				}
	        })
	        .error(function(result){
	            $rootScope.hmsLoading.hide();
	            console.log(result);
	        });
	    };
        function show(){        	
            HmsModalService.show({
                contentTmpl: contentPath + "/main/message/template/hotel-rule.html",
                scope:$scope,
            });
        }
	};
	//审核操作
	$scope.checkEHotel = function() {
		$rootScope.hmsLoading.show();
		HmsHttp.post(contentPath + "/hotelmessage/checkEHotel", {
			hotelId: $rootScope.thisHotel.id
		})
		.success(function(data) {
			$rootScope.hmsLoading.hide();
			if (!data.success) {
				Tip.message(data.errorMsg, "error");
			} else {
				Tip.message("审核酒店信息成功");
				$location.path('/');
				$rootScope.$broadcast('PMS_RELOAD',true);
			}
		}).error(function(msg) {
			Tip.message("审核酒店信息异常", "error");
			$rootScope.hmsLoading.hide();
		});
	};
	//完成操作
	$scope.finish = function(){
    	$rootScope.hmsLoading.show();
    	HmsHttp.get(contentPath + "/hotelmessage/submitHotlInfo")
		.success(function(data){
			$rootScope.hmsLoading.hide();
			if (!data.success) {
				Tip.message(data.errorMsg, "error");
			}else{
				Tip.message("提交酒店信息成功");
				//如果是从pmsUser管理页面过来，怎更新列表并关闭
				if($rootScope.ctrlMode=='edit'){
					$location.path('/');
					$rootScope.$broadcast('PMS_RELOAD',true);
				}
			}
		})
		.error(function(error){
			Tip.message("提交酒店信息异常", "error");
			$rootScope.hmsLoading.hide();
		});
	};
	//重新获取酒店信息
	$scope.isReloadHotelInfo = function(){
		HmsHttp.get(contentPath + "/hotel/thisHotel")
		.success(function(data){
			// 格式化当前酒店字段
			data.openTime = data.fmtOpenTime;
			data.repairTime = data.fmtRepairTime;
			$rootScope.thisHotel = data;
		})
		.error(function(error){
			console.log("重新获取酒店信息异常");
		});
	};
	// 获取当前酒店分组信息
	function getThisHotelGroup() {
		HmsHttp.post(contentPath + "/hotelmessage/getThisHotelGroup", {
			hotelId: $rootScope.thisHotel.id
		})
			.success(function(data) {
				$scope.group = data;
			}).error(function(msg) {
				Tip.message("获取当前酒店分组信息异常", "error");
			});
	}
	//去后台获取房型信息
	function loadRoomType(){
		HmsHttp.post(contentPath + "/roomtype/findsByHotelId", {
				hotelId: $rootScope.thisHotel.id,
			})
			.success(function(data) {
				roomTypes = data;
				$scope.hotelNav.roomType.item = getRoomType(data);
				$scope.hotelNav.roomPrice.item = getRoomPrice(data);
			})
			.error(function(msg) {
				Tip.message("获取房型数据失败", "error");
			});
	};
	//获取房型数据
	function getRoomType(dataList){
		var item = [];
		_.each(dataList,function(data){
			item.push({
				'displayName':data.name,
				'view':'/message/roomInfo/'+data.id
			});
		});
		return item;
	};
	//获取房价数据
	function getRoomPrice(dataList){
		var item = [];
		_.each(dataList,function(data){
			item.push({
				'displayName':data.name,
				'view':'/message/roomPrice/'+data.id
			});
		});
		return item;
	};
	//加载子页面
	function showPage(item){
		$location.path(item.view);
	};
	getThisHotelGroup();
	loadRoomType(); //加载房型数据
	showPage($scope.hotelNav.hotelInfo.item[0]); //加载默认页面


	/*打印二维码*/
	// 展示打印二维码
	$scope.printI2DimCodes = function() {
		HmsHttp.post(contentPath + "/i2dimcodes/printI2DimCodes")
		.success(function(data) {
			$rootScope.printI2dimCodes = data || [];
			// 此处暂时操作Dom；按ngularJs建议，是不能在此操作Dom
			jQuery("#hmsI2DimCodesModal").modal("show");
		})
		.error(function(msg) {
			Tip.message("获取酒店二维码数据失败", "error");
		});
	};

	// 获取生成二维码链接
	$scope.getPrintI2dimCodeUrl = function(content, tag) {
		return Print.getPrintI2dimCodeUrl(content, tag);
	};

	// 进入打印页面
	$scope.goToPrintPage = function(content, tag) {
		Print.goToPrintPage(content, tag, $rootScope.thisHotel.hotelName, $scope.printI2dimCodes);
	};
	/*打印二维码*/
}])
/**
 * [酒店配套设施控制类 messageController子类]
 */
.controller('hotelFacController', ['$rootScope','$scope','HmsHttp','$location','HmsModalService',
	function($rootScope,$scope,HmsHttp,$location,HmsModalService){
	// 保存
	$scope.save = function(isAuto) {
		var auto = isAuto ? "自动" : "";
		var select1 = getSelectFacilities($scope.tFacilityListType1);
		var select2 = getSelectFacilities($scope.tFacilityListType2);
		var myTFacilityIdList = select1.concat(select2);
		return HmsHttp.post(contentPath + "/hotel/saveTFacilies", {tFaciliesString: myTFacilityIdList.join(",")})
			.success(function(data) {
				if (data.success) {
					Tip.message(auto + "保存完成！");
					$scope.hotelFacForm && $scope.hotelFacForm.$setPristine();
				} else {
					Tip.message(auto + "保存数据失败", "error");
				}
			})
			.error(function(msg) {
				Tip.message(auto + "保存酒店设施数据失败", "error");
			});
	};
	function init() {
		HmsHttp.get(contentPath + "/hotel/facilies")
			.success(function(data) {
				$scope.tFacilityList = data.tFacilities || [];
				$scope.myTFacilityList = data.myTFacilities || [];
				$scope.oldTFacilityList = data.oldTFacilities || [];
				$scope.tFacilityListType1 = [];
				$scope.tFacilityListType2 = [];
				// 高亮选中数据
				_.map($scope.tFacilityList, function(thisFac) {
					_.map($scope.myTFacilityList, function(mymyTFacility) {
						if (thisFac.id === mymyTFacility.facId) {
							thisFac.checked = true;
						}
					});
				});
				// 分类处理
				_.map($scope.tFacilityList, function(thisTFacility) {
					if (thisTFacility.facType === 1) {
						$scope.tFacilityListType1.push(thisTFacility);
					} else if (thisTFacility.facType === 2) {
						$scope.tFacilityListType2.push(thisTFacility);
					}
				});
			})
			.error(function(msg) {
				Tip.message("获取酒店设施数据失败", "error");
			});
	};
	//获取选中的设备设施
	function getSelectFacilities(facilities){
		var selectFacList = [];
		angular.forEach(facilities,function(facData,j){
			if(facData['checked']){
				selectFacList.push(facData['id']);
			}
		});
		return selectFacList;
	};
	//初始化数据
	init();
	$scope.$on("$stateChangeStart",function(event){
		if ($scope.hotelFacForm.$dirty) {
			$scope.save(true);
		}
	});

	/*打印二维码*/
	// 展示打印二维码
	$scope.printI2DimCodes = function() {
		HmsHttp.post(contentPath + "/i2dimcodes/printI2DimCodes")
		.success(function(data) {
			$rootScope.printI2dimCodes = data || [];
			// 此处暂时操作Dom；按ngularJs建议，是不能在此操作Dom
			jQuery("#hmsI2DimCodesModal").modal("show");
		})
		.error(function(msg) {
			Tip.message("获取酒店二维码数据失败", "error");
		});
	};

	// 获取生成二维码链接
	$scope.getPrintI2dimCodeUrl = function(content, tag) {
		return Print.getPrintI2dimCodeUrl(content, tag);
	};

	// 进入打印页面
	$scope.goToPrintPage = function(content, tag) {
		Print.goToPrintPage(content, tag, $rootScope.thisHotel.hotelName, $scope.printI2dimCodes);
	};
	/*打印二维码*/
}])
/**
 * [酒店信息控制类 messageController子类]
 */
.controller('hotelInfoController', ['$rootScope','$scope', 'HmsHttp', 'LocaltionService', 'InterfaceService',
	function($rootScope,$scope,HmsHttp,LocaltionService,InterfaceService){
	var thisHotel = $rootScope.thisHotel;
	$scope.yearList = DateUtils.getYearList(); //获取从1970年当前年份数组
	$scope.timeList = DateUtils.getTimeList(); //获取一天24小时数组，step未一小时
	//需要保存的数据对象
	$scope.saveBussinesszonesData = {
		openTime: thisHotel.openTime,
		repairTime: thisHotel.repairTime,
		begintime: DateUtils.getTime4HM(thisHotel.retentionTime || "000000"),
		endtime: DateUtils.getTime4HM(thisHotel.defaultLeaveTime || "000000"),
		introduction: thisHotel.introduction,
		subwayLineArray: [],
		hotelphone: $rootScope.thisHotel.hotelphone,
		hotelName: $rootScope.thisHotel.hotelName,
		hoteltype: $rootScope.thisHotel.hoteltype,
		qtphone: $rootScope.thisHotel.qtphone,
		pmsUser: $rootScope.thisHotel.pmsUser,
		//
		// provcode: $rootScope.thisHotel.provcode + "",
		// citycode: $rootScope.thisHotel.citycode + "",
		// discode: $rootScope.thisHotel.discode + "",
		// disId: $rootScope.thisHotel.disId + "",
		// areacode: $rootScope.thisHotel.areacode + "",
		// areaname: $rootScope.thisHotel.areaname + "",
		detailAddr: $rootScope.thisHotel.detailAddr,
		longitude: $rootScope.thisHotel.longitude,
		latitude: $rootScope.thisHotel.latitude,
		_proObj: {
			code: $rootScope.thisHotel.provcode + "",
		},
		_cityObj: {
			code: $rootScope.thisHotel.citycode + "",
		},
		_disObj: {
			code: $rootScope.thisHotel.discode + "",
			id: $rootScope.thisHotel.disId,
		},
		_areaObj:{
			areacode: ($rootScope.thisHotel.areacode || 0) + "",
			name: $rootScope.thisHotel.areaname || "",
		},
		"form":"hotelInfoForm"
	};	
    $scope.hotelTypeDicts = [
	    {
	        'id':1,
	        'name':"旅馆"
	    },
	    {
	        'id':2,
	        'name':"主题酒店"
	    },
	    {
	        'id':3,
	        'name':"精品酒店"
	    },
	    {
	        'id':4,
	        'name':"公寓"
	    },
	    {
	        'id':5,
	        'name':"招待所"  
	    },
	    {
	        'id':6,
	        'name':"客栈"
	    }
    ];

	// 获取商圈信息
	function hotelBussinesszones() {
		HmsHttp.get(contentPath + "/hotel/bussinesszones")
		.success(function(data) {
			// ---------------商圈--------------
			$scope.circleList = data.circleList || []; // 周边商圈信息
			$scope.myCircleList = data.myCircleList || []; // 已确定商圈信息
			fomartCircleData(); // 显示商圈信息
			// ----------------地铁-----------------
			$scope.subwayList = data.subwayList || []; // 地铁信息
			$scope.subwayLineList = []; // 地铁路线列表
			$scope.mySubwayList = data.mySubwayList || []; // 已确定地铁站点信息
			fomartSubwayData(); // 获取地铁线路列表
			//---------显示地铁线路站点------------------
			/*$scope.mySubwayList.push({
				businessZoneType: 12,
				cityid: 463,
				dis: 0,
				fatherid: 63,
				id: 64,
				name: "富锦路"
			});*/
			// -----------------周边大学----------------
			$scope.universityList = data.universityList || []; // 周边大学信息
			$scope.myUniversityList = data.myUniversityList || []; // 已确定周边大学信息
			fomartUniversityData(); //显示当前已选中周边大学节点

		})
		.error(function(msg) {
			Tip.message("获取酒店描述数据失败", "error");
		});
	};
	function fomartCircleData(){
		_.map($scope.circleList, function(circle) {
			_.map($scope.myCircleList, function(myCircle) {
				if (circle.id === myCircle.id) {
					circle.isExit = true;
				}
			});
		});
	};
	function fomartSubwayData(){
		_.map($scope.subwayList, function(subway) {
			if (!subway.fatherid) {
				$scope.subwayLineList.push(subway);
			} else {
				// 找出已选中地铁站点
				_.map($scope.mySubwayList, function(mySubway) {
					if (subway.id === mySubway.id) {
						subway.isExit = true;
					}
				});
			}
		});
		_.map($scope.subwayLineList, function(subwayLine) {
			_.map($scope.mySubwayList, function(mySubway) {
				if (subwayLine.id === mySubway.fatherid) {
					$scope.saveBussinesszonesData.subwayLineArray.push(subwayLine.id);
				}
			});
		});
	};
	function fomartUniversityData(){
		_.map($scope.universityList, function(university) {
			_.map($scope.myUniversityList, function(myUniversity) {
				if (university.id === myUniversity.id) {
					university.isExit = true;
				}
			});
		});
	}
	// 点击节点
	$scope.clickCircleItem = function(circleId) {
		_.map($scope.circleList, function(circle) {
			if (circle.id === circleId) {
				circle.isExit = circle.isExit ? false : true;
			}
		});
	};
	// 点击列表项，展示对应线路站点
	function changeSubwayLineList() {
		_.map($scope.subwayLineList, function(subwayLine) {
			var isExitSubwayLine = false;
			_.map($scope.saveBussinesszonesData.subwayLineArray, function(subwayId) {
				if (subwayId == subwayLine.id) {
					subwayLine.isShow = true;
					isExitSubwayLine = true;
				}
			});
			if (!isExitSubwayLine) {
				subwayLine.isShow = false;
			}
		});
	};	

	function init(){
		hotelBussinesszones();
	};
	//  点击站点选择
	$scope.clickSubwayItem = function(id, fatherid) {
		_.map($scope.subwayList, function(subway) {
			if (subway.fatherid === fatherid) {
				if (subway.id === id) {
					subway.isExit = subway.isExit ? false : true;
				} else {
					subway.isExit = false;
				}
			}
		});
	};
	// 大学周边
	$scope.clickUniversityItem = function(universityId) {
		_.map($scope.universityList, function(university) {
			// 修改点击状态
			if (universityId === university.id) {
				university.isExit = university.isExit ? false : true;
			}
		})
	};
	// 保存描述信息数据
	$scope.saveBussinesszones = function(isAuto) {
		
		var auto = isAuto ? "自动" : "";
		if(!$scope.saveBussinesszonesData.hotelName){
			Tip.message('请输入酒店名称','error');
			return;
		}else if(!$scope.saveBussinesszonesData.qtphone){
			Tip.message('请输入前台联系电话','error');
			return;
		}else if(!$scope.saveBussinesszonesData.hotelphone){
			Tip.message('请输入联系电话','error');
			return;
		}else if(!$scope.saveBussinesszonesData._proObj.code){
			Tip.message('请选择所在省','error');
			return;
		}else if(!$scope.saveBussinesszonesData._cityObj.code){
			Tip.message('请选择所在市','error');
			return;
		}else if(!$scope.saveBussinesszonesData._disObj.code){
			Tip.message('请选择所在区县','error');
			return;
		}else if(!$scope.saveBussinesszonesData._areaObj.areacode || $scope.saveBussinesszonesData._areaObj.areacode == "0"){
			Tip.message('请选择所在商圈','error');
			return;
		}else if(!$scope.saveBussinesszonesData.detailAddr){
			Tip.message('请输入酒店地址','error');
			return;
		}else if($scope.saveBussinesszonesData.detailAddr.length > 100){
			Tip.message('酒店地址至多只能输入100个字','error');
			return;
		}else if(!$scope.saveBussinesszonesData.longitude || !$scope.saveBussinesszonesData.latitude){
			Tip.message('请输入正确的酒店地址','error');
			return;
		}else if($scope.saveBussinesszonesData.repairTime < $scope.saveBussinesszonesData.openTime){
			Tip.message('最近装修时间不能早用开业时间','error');
			return;
		}else if($scope.saveBussinesszonesData.introduction && $scope.saveBussinesszonesData.introduction.length>500){
			Tip.message('酒店描述至多只能输入500个字','error');
			return;
		}else if(!$scope.saveBussinesszonesData.openTime){
			Tip.message('请选择开业时间','error');
			return;
		}else if(!$scope.saveBussinesszonesData.repairTime){
			Tip.message('请选择装修时间','error');
			return;
		}else if(!$scope.saveBussinesszonesData.hoteltype){
			Tip.message('请选择酒店类型','error');
			return;
		}

		$scope.saveBussinesszonesData.disId =  $scope.saveBussinesszonesData._disObj.id;
		$scope.saveBussinesszonesData.provcode = $scope.saveBussinesszonesData._proObj.code;
		$scope.saveBussinesszonesData.citycode = $scope.saveBussinesszonesData._cityObj.code;
		$scope.saveBussinesszonesData.discode = $scope.saveBussinesszonesData._disObj.code;
		$scope.saveBussinesszonesData.areacode = $scope.saveBussinesszonesData._areaObj.areacode;
		$scope.saveBussinesszonesData.areaname = $scope.saveBussinesszonesData._areaObj.name;
		// 格式化数据
		var isExitCircleList = [];
		_.map($scope.circleList, function(circle) {
			if (circle.isExit) {
				isExitCircleList.push(circle);
			}
		});
		$scope.saveBussinesszonesData.circleList = angular.toJson(isExitCircleList);
		var isExitSubwayList = [];
		_.map($scope.subwayList, function(subway) {
			if (subway.isExit) {
				isExitSubwayList.push(subway);
			}
		});
		$scope.saveBussinesszonesData.subwayList = angular.toJson(isExitSubwayList);
		var isExitUniversityList = [];
		_.map($scope.universityList, function(university) {
			if (university.isExit) {
				isExitUniversityList.push(university);
			}
		});
		$scope.saveBussinesszonesData.universityList = angular.toJson(isExitUniversityList);
		$scope.saveBussinesszonesData.subwayLineArray = null;
		// 请求
		HmsHttp.post(contentPath + "/hotel/saveBussinesszones", $scope.saveBussinesszonesData)
			.success(function(data) {
				if (!data.success) {
					Tip.message(data.errorMsg, "error");
				} else {
					Tip.message(auto + "保存成功");
					$scope.hotelInfoForm && $scope.hotelInfoForm.$setPristine();
					$scope.$parent.isReloadHotelInfo();
					$rootScope.thisHotel.detailAddr = $scope.saveBussinesszonesData.detailAddr;
					$rootScope.thisHotel.longitude = $scope.saveBussinesszonesData.longitude;
					$rootScope.thisHotel.latitude = $scope.saveBussinesszonesData.latitude;
					$rootScope.thisHotel.disId = $scope.saveBussinesszonesData.disId;
					$rootScope.thisHotel.hoteltype = $scope.saveBussinesszonesData.hoteltype;
					$rootScope.thisHotel.qtphone = $scope.saveBussinesszonesData.qtphone;
					$rootScope.thisHotel.provcode = $scope.saveBussinesszonesData.provcode;
					$rootScope.thisHotel.citycode = $scope.saveBussinesszonesData.citycode;
					$rootScope.thisHotel.discode = $scope.saveBussinesszonesData.discode;
					$rootScope.thisHotel.areacode = $scope.saveBussinesszonesData.areacode;
					$rootScope.thisHotel.areaname = $scope.saveBussinesszonesData.areaname;
					$rootScope.thisHotel.openTime = $scope.saveBussinesszonesData.openTime;
					$rootScope.thisHotel.repairTime = $scope.saveBussinesszonesData.repairTime;
					$rootScope.thisHotel.pmsUser = $scope.saveBussinesszonesData.pmsUser;
				}
			}).error(function(msg) {
				Tip.message(auto + "保存酒店描述数据失败", "error");
			});
	};
	//初始化数
	init();
	//监听被选中地铁线路变化
	$scope.$watch('saveBussinesszonesData.subwayLineArray',function(newVal){
		if(newVal){
			changeSubwayLineList();
		}
	},true);
	$scope.$on("$stateChangeStart",function(event){
		if ($scope.hotelInfoForm.$dirty) {
			$scope.saveBussinesszones(true);
		}
	});
	//手动调更新房态接口
	$scope.refreshRoomOnlineState = function(){
		InterfaceService.refreshRoomOnlineState($scope, $rootScope.thisHotel.id);
	};
}])
/**
 * [交通信息控制类 messageController子类]
 */
.controller('hotelTrafficController', ['$rootScope','$scope','HmsHttp',
 function($rootScope,$scope,HmsHttp){
 	//交通模型数据对象
	$scope.trafficObj = JSON.parse($rootScope.thisHotel.traffic || "{}");
	$scope.oldtrafficObj = JSON.parse($rootScope.thisHotel.traffic || "{}");
	$scope.trafficMapUrl = contentPath + "/main/plug-in/gaode/gaodeMapTools.html" + hmsVersionPath;

 	jQuery("#traffic-map").load(function(){
		this.contentWindow && this.contentWindow.parentH && (this.contentWindow.parentH = $scope);
		$scope.setTrafficInfos(this);
	});

 	$scope.setTrafficInfos = function(ifr){
 		ifr.contentWindow.placeSearch([$rootScope.thisHotel.longitude, $rootScope.thisHotel.latitude], 2000, "地铁站", $scope.metroCallback);
 		ifr.contentWindow.placeSearch([$rootScope.thisHotel.longitude, $rootScope.thisHotel.latitude], 2000, "公交站", $scope.busCallback);
 	};

 	$scope.metroCallback = function(points){
 		if (points && points.length && !$scope.trafficObj.metro) {
 			var resultStr = "";
 			var len = Math.min(points.length, 5);
 			for(var i=0;i<len;i++){
 				var poi = points[i];
 				resultStr +=  poi.address + "-" + poi.name + "\r\n";
 			}
 			$scope.trafficObj.metro = resultStr;
 			$scope.$digest();
 		}
 	};

 	$scope.busCallback = function(points){
 		if (points && points.length && !$scope.trafficObj.bus) {
 			var resultStr = "";
 			var len = Math.min(points.length, 5);
 			for(var i=0;i<len;i++){
 				var poi = points[i];
 				resultStr += poi.address + "-" + poi.name + "\r\n";
 			}
 			$scope.trafficObj.bus = resultStr;
 			$scope.$digest();
 		}
 	};

	//保存交通数据
	$scope.saveTraffic = function(isAuto) {
		var auto = isAuto ? "自动" : "";
		var oldtrafficValArray = _.values($scope.oldtrafficObj);
		var trafficValArray = _.values($scope.trafficObj);
		var isChangeFlag = false;
		isChangeFlag = _.difference(oldtrafficValArray, trafficValArray).length > 0 ? true
				:  _.difference(trafficValArray, oldtrafficValArray).length > 0 ? true
						: false;
		if (!isChangeFlag) {
			Tip.message("数据没有改变，无需保存", "error");
			return;
		} else {
			HmsHttp.post(contentPath + "/hotel/saveTraffic", {
					metro: $scope.trafficObj.metro,
					airport: $scope.trafficObj.airport,
					bus: $scope.trafficObj.bus,
					taxi: $scope.trafficObj.taxi
				})
				.success(function(data) {
					if (data.success) {
						Tip.message(auto + "保存成功");
						$scope.hotelTrafficForm && $scope.hotelTrafficForm.$setPristine();
						// 将保存之后数据clone给就数据对象
						$scope.oldtrafficObj = _.clone($scope.trafficObj);
						$rootScope.thisHotel.traffic = angular.toJson($scope.trafficObj);
					}
				})
				.error(function(msg) {
					Tip.message(auto + "保存数据失败", "error");
				});
		}
	};
	$scope.$on("$stateChangeStart",function(event){
		if ($scope.hotelTrafficForm.$dirty) {
			$scope.saveTraffic(true);
		}
	});
}])
/**
 * [酒店图片信息控制类 messageController子类]
 */
.controller('hotelPicController', ['$rootScope','$scope','HmsHttp','HmsUploadService',
	function($rootScope,$scope,HmsHttp,HmsUploadService){
	//删除图片
	$scope.delHotelInfoPic = function(img, key, url){
		if (!confirm("确认删除照片？")) {
			return;
		}
		for (var i = 0; i < img.keys.length; i++) {
			if (key == img.keys[i]) {
				img.keys.baoremove(i);
				img.previews.baoremove(i);
			}
		};
	};
	//保存图片
	$scope.save = function(showTip){
		var pics = getSelectPics();
		if(pics[0]['pic'].length<=0){
			if(showTip)
				Tip.message('请上传门头及招牌','error');
			return;
		}
		var params = {
			'pics':angular.toJson(pics)
		};
		HmsHttp.post(contentPath + "/hotelmessage/saveHotelInfoPic", params)
		.success(function(data){
			$rootScope.thisHotel.hotelpic = params['pics'];
			if(showTip)
				Tip.message('保存成功');

		}).error(function(error){
			if(showTip)
				Tip.message('上传失败','error');
		});
	};
	$scope.defImg = {
		"btnName": "选择照片",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 1,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|",
		"customV1": "def"//用户自定义变量：后台json对象的name名
	};
	$scope.lobbyImg = {
		"btnName": "选择照片",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 3,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|",
		"customV1": "lobby"//用户自定义变量：后台json对象的name名
	};
	$scope.mainHousingImg = {
		"btnName": "选择照片",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 3,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|",
		"customV1": "mainHousing"//用户自定义变量：后台json对象的name名
	};

	//加载酒店图片数据
	function initHotelPics(){
		var hotelpic = JSON.parse($rootScope.thisHotel.hotelpic || "[]");
		if (hotelpic.length) {
			for(var i=0;i<hotelpic.length;i++){
				var img = hotelpic[i];
				switch(img.name){
					case 'def':
						formart(img,$scope.defImg);
						break;
					case 'lobby':
						formart(img,$scope.lobbyImg);
						break;
					case 'mainHousing':
						formart(img,$scope.mainHousingImg);
						break;
				}
			}
		}
		$scope.recordImgDataForCompare();
	};

	function formart(from,to){
		var picUrls = from.pic || [];
		for (var i=0;i<picUrls.length;i++) {
			var url = picUrls[i].url;
			to.previews.push(url);
			to.keys.push(url.replace(HmsUploadService.publicDownloadUrl+ "/", ""));
		}
	};

	//获取上传的图片数据
	function getSelectPics(){
		var defs = [];
		for (var i=0;i<$scope.defImg.previews.length;i++) {
			defs.push({"url":$scope.defImg.previews[i]});
		}
		var lobbys= [];
		for (var i=0;i<$scope.lobbyImg.previews.length;i++) {
			lobbys.push({"url":$scope.lobbyImg.previews[i]});
		}
		var mainHousings = [];
		for (var i=0;i<$scope.mainHousingImg.previews.length;i++) {
			mainHousings.push({"url":$scope.mainHousingImg.previews[i]});
		}

		return [{
			'name':'def',
			'pic':defs
		},{
			'name':'lobby',
			'pic':lobbys
		},{
			'name':'mainHousing',
			'pic':mainHousings
		}];
	};

	$scope.$on("$stateChangeStart",function(event){
		if (!$scope.compareImgData()) {
			$scope.save(false);
		}
	});

	$scope.recordImgDataForCompare = function(){
		$scope.srcDefPreviews = $scope.defImg.previews.concat([]);
		$scope.srcLobbyPreviews = $scope.lobbyImg.previews.concat([]);
		$scope.srcMainHousingPreviews = $scope.mainHousingImg.previews.concat([]);
	};

	$scope.compareImgData = function(){
		if (_.difference($scope.srcDefPreviews, $scope.defImg.previews).length
			|| _.difference($scope.defImg.previews, $scope.srcDefPreviews).length) {
			return false;
		}
		if (_.difference($scope.srcLobbyPreviews, $scope.lobbyImg.previews).length
			|| _.difference($scope.lobbyImg.previews, $scope.srcLobbyPreviews).length) {
			return false;
		}
		if (_.difference($scope.srcMainHousingPreviews, $scope.mainHousingImg.previews).length
			|| _.difference($scope.mainHousingImg.previews, $scope.srcMainHousingPreviews).length) {
			return false;
		}
		return true;
	};

	//加载
	initHotelPics();
}])
/**
 * [酒店周边信息控制类 messageController子类]
 */
.controller('hotelPeripheralController', ['$rootScope','$scope','HmsHttp',
	function($rootScope,$scope,HmsHttp){
	// 周边环境数据对象
	$scope.peripheralObj = JSON.parse($rootScope.thisHotel.peripheral || "{}");
	$scope.oldPeripheralObj = JSON.parse($rootScope.thisHotel.peripheral || "{}");
	$scope.peripheralMapUrl = contentPath + "/main/plug-in/gaode/gaodeMapTools.html" + hmsVersionPath;

	jQuery("#peripheral-map").load(function(){
		this.contentWindow && this.contentWindow.parentH && (this.contentWindow.parentH = $scope);
		$scope.setTrafficInfos(this);
	});

 	$scope.setTrafficInfos = function(ifr){
 		ifr.contentWindow.placeSearch([$rootScope.thisHotel.longitude, $rootScope.thisHotel.latitude], 2000, "餐饮", $scope.restaurantCallback);
 		ifr.contentWindow.placeSearch([$rootScope.thisHotel.longitude, $rootScope.thisHotel.latitude], 2000, "景点", $scope.scenicSpotCallback);
 	};

 	$scope.restaurantCallback = function(points){
 		if (points && points.length && !$scope.peripheralObj.restaurant) {
 			var resultStr = "";
 			var len = Math.min(points.length, 5);
 			for(var i=0;i<len;i++){
 				var poi = points[i];
 				resultStr += poi.name + "-" + poi.address + "\r\n";
 			}
 			$scope.peripheralObj.restaurant = resultStr;
 			$scope.$digest();
 		}
 	};

 	$scope.scenicSpotCallback = function(points){
 		if (points && points.length && !$scope.peripheralObj.scenicSpot) {
 			var resultStr = "";
 			var len = Math.min(points.length, 5);
 			for(var i=0;i<len;i++){
 				var poi = points[i];
 				resultStr += poi.name + "-" + poi.address + "\r\n";
 			}
 			$scope.peripheralObj.scenicSpot = resultStr;
 			$scope.$digest();
 		}
 	};

	// 保存周边数据
	$scope.savePeriphera = function(isAuto) {
		var auto = isAuto ? "自动" : "";
		var oldperipheralValArray = _.values($scope.oldPeripheralObj);
		var peripheralValArray = _.values($scope.peripheralObj);
		var isChangeFlag = false;
		isChangeFlag = _.difference(oldperipheralValArray, peripheralValArray).length > 0 ? true
				:  _.difference(peripheralValArray, oldperipheralValArray).length > 0 ? true
						: false;
		if (!isChangeFlag) {
			// Tip.message("数据没有改变，无需保存", "error");
			return;
		} else {
			HmsHttp.post(contentPath + "/hotel/savePeripheral", {
					restaurant: $scope.peripheralObj.restaurant,
					scenicSpot: $scope.peripheralObj.scenicSpot,
					others: $scope.peripheralObj.others,
				})
				.success(function(data) {
					if (data.success) {
						Tip.message(auto + "保存成功");
						$scope.hotelPeripheraForm && $scope.hotelPeripheraForm.$setPristine();
						// 将保存之后数据clone给就数据对象
						$scope.oldPeripheralObj = _.clone($scope.peripheralObj);
						$rootScope.thisHotel.peripheral = angular.toJson($scope.peripheralObj);
					}
				})
				.error(function(msg) {
					Tip.message(auto + "保存数据失败", "error");
				});
		}
	};
	$scope.$on("$stateChangeStart",function(event){
		if ($scope.hotelPeripheraForm.$dirty) {
			$scope.savePeriphera(true);
		}
	});
}])
/**
 * [房型管理控制类，属于messageController的子类]
 */
.controller('roomInfoController', ['$rootScope','$scope','HmsHttp', '$timeout', 'HmsUploadService',
	function($rootScope,$scope,HmsHttp,$timeout,HmsUploadService){

	var roomTypeId = $rootScope.$stateParams.id;

	$scope.roomType = $scope.getRoomType(roomTypeId); //选中的房型

	var facIsLoaded = false,
		roomTypeInfoLoaded = false,
		isReload = false;

	//床型
	$scope.bedTypes = [
		{id:1,name:"单床房",bedCount:1},
		{id:2,name:"双床房",bedCount:2},
		{id:3,name:"其它",bedCount:3}
	];
	//床型尺寸
	$scope.sizeTypes = [
		{id:'',name:"----"},
		{id:'2.20',name:"2.20m"},
		{id:'2.00',name:"2.00m"},
		{id:'1.80',name:"1.80m"},
		{id:'1.50',name:"1.50m"},
		{id:'1.35',name:"1.35m"},
		{id:'1.20',name:"1.20m"},
		{id:'1.00',name:"1.00m"}
	];
	$scope.bedSize = [{id:''}];
	$scope.defImg = {
		"btnName": "选择照片",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 1,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|",
		"customV1": "def"//用户自定义变量：后台json对象的name名
	};
	$scope.bedImg = {
		"btnName": "选择照片",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 3,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|",
		"customV1": "def"//用户自定义变量：后台json对象的name名
	};
	$scope.toiletImg = {
		"btnName": "选择照片",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 3,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|",
		"customV1": "def"//用户自定义变量：后台json对象的name名
	};
	$scope.delHotelInfoPic = function(img, key) {
		if (!confirm("确认删除照片？")) {
			return;
		}
		for (var i = 0; i < img.keys.length; i++) {
			if (key == img.keys[i]) {
				img.keys.baoremove(i);
				img.previews.baoremove(i);
			}
		};
	};
	// 选择卫浴事件
	$scope.selectedBathroomFacilities = function(id) {
		var isCheck = false;
		_.map($scope.roomTypeBathroomFacilities, function(item) {
			if (!(item.id == id && item.isExit)) {
				item.isExit = false;
			} else {
				isCheck = true;
			}
		});
		if (!isCheck) {
			_.map($scope.roomTypeBathroomFacilities, function(item) {
				if (item.id == id) {
					item.isExit = true;
				}
			});
		}
	};
	//保存操作
	$scope.save = function(isAuto){
		var auto = isAuto ? "自动" : "";
		if(!$scope.roomTypeInfo.bedType){
			Tip.message("请选择床型",'error');
			return;
		}else{
			var bedSize = [];
			_.forEach($scope.bedSize,function(size){
				if(size.id)
					bedSize.push(size.id);
			});
			if(bedSize.length<=0){
				Tip.message('未设置床的尺寸','error');
				return;
			}
			if (!$scope.defImg.previews || $scope.defImg.previews.length == 0) {
				Tip.message('未上传主展图','error');
				return;
			}
			if($scope.roomTypeInfo.maxArea){
				var maxArea = parseFloat($scope.roomTypeInfo.maxArea);
				if(_.isNaN(maxArea) || maxArea<0){
					$scope.roomTypeInfo.maxArea = '';
					Tip.message('房间面积只能输入有效数字','error');
					return;
				}else{
					$scope.roomTypeInfo.maxArea = maxArea;
				}
			} else {
				Tip.message('房间面积不可为空', 'error');
				return;
			}
			$scope.roomTypeInfo['bedSize'] = bedSize.join(",");
			var normalFac = getSelectFacilities($scope.roomTypeNormalFacilities),
				otherFac = getSelectFacilities($scope.roomTypeOtherFacilities),
				bathroomFac = getSelectFacilities($scope.roomTypeBathroomFacilities),
				selectFac = normalFac.concat(otherFac, bathroomFac),
				exitFac = getFacList(),
				addFacs = _.difference(selectFac,exitFac),
				minusFacs = _.difference(exitFac,selectFac);
			var saveData = {
				'roomTypeId':roomTypeId,
				'maxArea':$scope.roomTypeInfo['maxArea'],
				'bedType':$scope.roomTypeInfo['bedType'],
				'bedSize':$scope.roomTypeInfo['bedSize'],
				'pics':angular.toJson(getSelectPics()),
				'addFacs':addFacs.join(','),
				'minusFacs':minusFacs.join(',')
			};
			if($scope.roomTypeInfo['id']){
				saveData['id'] = $scope.roomTypeInfo['id'];
			}
			HmsHttp.post(contentPath + "/roomtype/save", saveData)
				.success(function(data) {
					Tip.message(auto + "保存成功");
					$scope.roomInfoForm && $scope.roomInfoForm.$setPristine();
					//刷新数据
					$scope.defImg.previews = [];
					$scope.defImg.keys = [];
					$scope.bedImg.previews = [];
					$scope.bedImg.keys = [];
					$scope.toiletImg.previews = [];
					$scope.toiletImg.keys = [];
					loadRoomTypes();
				})
				.error(function(msg) {
					Tip.message(auto + "保存失败", "error");
				});

			return;
		}
	};
	function loadFacilities(){
		//获取房型设备设施
		HmsHttp.post(contentPath + "/roomtype/findFacilities", {
			})
			.success(function(data) {
				$scope.roomTypeNormalFacilities = data['normal'];
				$scope.roomTypeOtherFacilities = data['other'];
				$scope.roomTypeBathroomFacilities = data['bathroom'];
				if(roomTypeInfoLoaded){
					fomartFacility($scope.roomTypeNormalFacilities);
					fomartFacility($scope.roomTypeOtherFacilities);
					fomartFacility($scope.roomTypeBathroomFacilities, true);
				}
				facIsLoaded = true;
			})
			.error(function(msg) {
				Tip.message("获取房型信息失败", "error");
			});
	};
	//获取上传的图片数据
	function getSelectPics(){
		// var pics = $scope.roomTypeInfo['pics'] ? $scope.roomTypeInfo['pics'] :  [];

		var defs = [];
		for (var i=0;i<$scope.defImg.previews.length;i++) {
			defs.push({"url":$scope.defImg.previews[i]});
		}
		var beds = [];
		for (var i=0;i<$scope.bedImg.previews.length;i++) {
			beds.push({"url":$scope.bedImg.previews[i]});
		}
		var toilets = [];
		for (var i=0;i<$scope.toiletImg.previews.length;i++) {
			toilets.push({"url":$scope.toiletImg.previews[i]});
		}

		return [{
			'name':'def',
			'pic':defs
		},{
			'name':'bed',
			'pic':beds
		},{
			'name':'toilet',
			'pic':toilets
		}];
	};
	//获取选中的设备设施
	function getSelectFacilities(facilities){
		var selectFacList = [];
		angular.forEach(facilities,function(facData,j){
			if(facData['isExit']){
				selectFacList.push(facData['id']);
			}
		});
		return selectFacList;
	};
	//格式化设备设施数据
	function fomartFacility(facilities, isDefault){
		angular.forEach(facilities,function(facData,j){
			facData['isExit'] = isExit(facData['id']);
		});
		if (isDefault) {
			var isExitFlag = false;
			_.map(facilities, function(item) {
				if (item.isExit) {
					isExitFlag = true;
				}
			});
			if (!isExitFlag && facilities.length > 0) {
				facilities[0].isExit = true;
			}
		}
	};
	//格式化图片数据
	function fomartPics(pics){
		var rntPicObj = {},
			picsList = JSON.parse($scope.roomTypeInfo['pics'] ? $scope.roomTypeInfo['pics'] : "[]");
		for(var i=0;i<picsList.length;i++){
			var img = picsList[i];
			switch(img.name){
				case 'def':
					formart(img,$scope.defImg);
					break;
				case 'bed':
					formart(img,$scope.bedImg);
					break;
				case 'toilet':
					formart(img,$scope.toiletImg);
					break;
			}
		}
	};

	function formart(from,to){
		var picUrls = from.pic || [];
		for (var i=0;i<picUrls.length;i++) {
			var url = picUrls[i].url;
			to.previews.push(url);
			to.keys.push(url.replace(HmsUploadService.publicDownloadUrl+ "/", ""));
		}
	};
	//获取设备设施列表
	function getFacList(id){
		var facList = [];
		angular.forEach($scope.roomTypeInfo['facilities'],function(data,i){
			facList.push(data['facId']);
		});
		return facList;
	};
	//判断是否存在
	function isExit(id){
		var flag = false;
		angular.forEach($scope.roomTypeInfo['facilities'],function(data,i){
			if(data['facId'] == id){
				flag = true;
				return;
			}
		});
		return flag;
	};
	function setPics(urls,type){
		var defUrls = [];
		_.each(urls,function(url){
			defUrls.push({
				url:url
			});
		});
		$scope.roomTypeInfo.pics[type] = defUrls;
	};
	function fomartBedSizeData(bedSize){
		var datas = bedSize ? bedSize.split(",") : [];
		$scope.bedSize = [];
		_.forEach(datas,function(key,i){
			var isExit = _.find($scope.sizeTypes,function(sizeObj){
				return sizeObj.id == key;
			})
			if(isExit){
				$scope.bedSize.push({id:key});
			}
		});
		isReload = true;
	};
	//加载房型数据
	function loadRoomTypes(){
		$rootScope.hmsLoading.show();
		//获取房型信息
		HmsHttp.post(contentPath + "/roomtype/findByRoomtypeId", {
				roomTypeId: roomTypeId,
			})
			.success(function(data) {
				//房型信息
				$scope.roomTypeInfo = data;
				fomartBedSizeData($scope.roomTypeInfo['bedSize']);
				fomartPics();
				$scope.recordImgDataForCompare();
				if(facIsLoaded){
					fomartFacility($scope.roomTypeNormalFacilities);
					fomartFacility($scope.roomTypeOtherFacilities);
					fomartFacility($scope.roomTypeBathroomFacilities, true);
				}
				roomTypeInfoLoaded = true;
				$rootScope.hmsLoading.hide();
			})
			.error(function(msg) {
				$rootScope.hmsLoading.hide();
				Tip.message("获取房型信息失败", "error");
			});
	};
	function init(){
		loadRoomTypes();
		//加载设备信息
		loadFacilities();
	};
	//监听床型变化
	$scope.$watch('roomTypeInfo.bedType',function(newVal){
		if(newVal){
			var bedTypeObj = _.findWhere($scope.bedTypes,{id:newVal}),
				bedCount = bedTypeObj ? bedTypeObj.bedCount : 1;
			if($scope.bedSize.length == bedCount){
				return;
			}
			if(isReload){
				isReload = false;
			}else{
				$scope.bedSize = [];
			}
			var bedSize = [];
			for(var i=0;i<bedCount;i++){
				var id = $scope.bedSize[i] ? $scope.bedSize[i].id : '';
				bedSize.push({id:id});
			}
			$scope.bedSize = bedSize;
		}
	});
	init();
	$scope.$on("$stateChangeStart",function(event){
		if ($scope.roomInfoForm.$dirty || !$scope.compareImgData()) {
			$scope.save(true);
		}
	});
	$scope.recordImgDataForCompare = function(){
		$scope.srcDefPreviews = $scope.defImg.previews.concat([]);
		$scope.srcBedPreviews = $scope.bedImg.previews.concat([]);
		$scope.srcToiletPreviews = $scope.toiletImg.previews.concat([]);
	};
	$scope.compareImgData = function(){
		if (_.difference($scope.srcDefPreviews, $scope.defImg.previews).length
			|| _.difference($scope.defImg.previews, $scope.srcDefPreviews).length) {
			return false;
		}
		if (_.difference($scope.srcBedPreviews, $scope.bedImg.previews).length
			|| _.difference($scope.bedImg.previews, $scope.srcBedPreviews).length) {
			return false;
		}
		if (_.difference($scope.srcToiletPreviews, $scope.toiletImg.previews).length
			|| _.difference($scope.toiletImg.previews, $scope.srcToiletPreviews).length) {
			return false;
		}
		return true;
	};
}])
/**
 * [房型管理控制类，属于messageController的子类]
 */
.controller('roomPriceController', ['$rootScope','$scope','HmsHttp',
	function($rootScope,$scope,HmsHttp){
	var id;
	var roomTypeId = $rootScope.$stateParams.id;
	$scope.roomType = $scope.getRoomType(roomTypeId); //选中的房型

	$scope.subperPrice=0;
	$scope.priceType = 'price'; //keys:[price,subper,subprice]
	$scope.price = {
		'price':0,
		'subper':0,
		'subprice':0
	}
	//重置数据
	function reset(){
		id = 0;
		$scope.subperPrice = $scope.roomType.cost;
		$scope.priceType = 'price';
		$scope.price = {
			'price':0,
			'subper':0,
			'subprice':0
		}
	};
	//格式化数据
	function fomartPrice(priceData){
		id = priceData['id'];
		$scope.subperPrice = $scope.roomType.cost;
		_.each(['price','subper','subprice'],function(key){
			var value = priceData[key];
			if(value>0){
				$scope.priceType = key;
				//TODO下浮比例后台数据库存小数
				if(key=='subper'){
					value *= 100;
				}
			}
			$scope.price[key] = value;
		});
	}
	//保存操作
	$scope.save = function(isAuto){
		var auto = isAuto ? "自动" : "";
		if(!_.isNumber($scope.price[$scope.priceType])){
			$scope.price[$scope.priceType] = 0;
			Tip.message('请输入数字','error');
			return;
		}
		var saveData = {
			'roomTypeId':$scope.roomType.id,
			'type':$scope.priceType,
			'value':$scope.price[$scope.priceType]
		};
		if(saveData.value<=0){
			$scope.price[$scope.priceType] = 0;
			Tip.message('请输入大于0的数字','error');
			return;
		}else if(saveData.type=='subper' && saveData.value >= 100 ){
			$scope.price.subper = 0;
			Tip.message('下浮比例不能大于100','error');
			return;
		}else if(saveData.type=='subprice' && saveData.value >= $scope.roomType.cost ){
			$scope.price.subprice = 0;
			Tip.message('下浮金额不能大于门市价','error');
			return;
		}else if(saveData.type=='price' && saveData.value > $scope.roomType.cost ){
			$scope.price.price = $scope.roomType.cost;
			Tip.message('固定金额不能大于门市价','error');
			return;
		}
		saveData.value = Math.ceil(saveData.value);
		$scope.price[saveData.type] = saveData.value;
		if(saveData.type=='subper'){
			saveData.value /= 100;
		}
		HmsHttp.post(contentPath + "/roomtype/saveBasePrice", saveData)
			.success(function(data) {
				Tip.message(auto + "保存成功");
				$scope.roomPriceForm && $scope.roomPriceForm.$setPristine();
			})
			.error(function(msg) {
				Tip.message(auto + "保存失败", "error");
			});
		return;
	};
	function init(){
		$rootScope.hmsLoading.show();
		HmsHttp.post(contentPath + "/roomtype/findBasePrice", {
				roomTypeId: roomTypeId,
			})
			.success(function(data) {
				if(_.isEmpty(data)){
					reset();
				}else{
					fomartPrice(data);
				}
				$rootScope.hmsLoading.hide();
			})
			.error(function(msg) {
				$rootScope.hmsLoading.hide();
				Tip.message("获取房型信息失败", "error");
			});
	};
	function setSubper(price){
		price = _.isNumber(price) ? Math.ceil(price) : 0;
		$scope.subperPrice = Math.ceil( ((100-price)*$scope.roomType.cost)/100 );
	};
	function setSubprice(price){
		price = _.isNumber(price) ? Math.ceil(price) : 0;
		$scope.subperPrice = Math.ceil( $scope.roomType.cost-price );
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
	init();
	$scope.$on("$stateChangeStart",function(event){
		if ($scope.roomPriceForm.$dirty) {
			$scope.save(true);
		}
	});
}])
/**
 * [酒店规则设置控制类]
 */
.controller('HotelRuleController', ['$rootScope','$scope','HmsModalService', 'HotelService',
   function($rootScope,$scope,HmsModalService,HotelService){
   	
	if($rootScope.thisHotel.rulecode){
		$scope.ruleCode = $rootScope.thisHotel.rulecode;
	}else{
		$scope.ruleCode = 1002;
	}
	
   	$scope.modalConfirm = function(){
        save();
    };
    $scope.modalConfirmClose = function(){
        HmsModalService.hide();
    };
    function setHotel(){
    	$rootScope.thisHotel.rulecode = $scope.ruleCode;
    }
    //保存酒店规则
    function save(){
        var ruleCode = $scope.ruleCode;
        if(!ruleCode || ruleCode.length<=0){
            Tip.message('请选择酒店规则','error');
            return;
        }else if(ruleCode === $rootScope.thisHotel.rulecode){
            Tip.message('酒店规则没有修改,不需要保存','error');   
            return;     	
        }
        $rootScope.hmsLoading.show();
        HotelService.saveHotelRule(ruleCode)
        .success(function(result){
            $rootScope.hmsLoading.hide();
            if(result.success){
                HmsModalService.hide();
            	setHotel();
            	Tip.message('设置成功');                
            }else{
                Tip.message(result.errorMsg,'error');
            }
        })
        .error(function(result){
            $rootScope.hmsLoading.hide();
            Tip.message('设置失败！');
        });
    };
}])
/**
 * [账期设置控制类]
 */
.controller('AccountSettingController', ['$rootScope','$scope', 'HotelService',
   function($rootScope,$scope,HotelService){   	
	$scope.isThreshold = 'N';
	$scope.isHold = false;

   	$scope.setThreshold = function(){
   		$scope.isThreshold = $scope.isThreshold=='Y' ? 'N' : 'Y';
   		save();
   	};

   	function reset(){
   		$scope.isThreshold = $scope.isThreshold=='Y' ? 'N' : 'Y';
   	};
   	function fomart(data){
   		if(data){
   			$scope.isThreshold = data.isThreshold;
   			$scope.isHold = data.ishold;
   		}
   	};
   	function load(){
        $rootScope.hmsLoading.show();
        HotelService.getIsholdAndStatus()
        .success(function(result){
            $rootScope.hmsLoading.hide();
            fomart(result);
        })
        .error(function(result){
            fomart();
            $rootScope.hmsLoading.hide();
        });
   	};
    //保存酒店规则
    function save(){
        $rootScope.hmsLoading.show();
        HotelService.setHotelThreshold($scope.isThreshold)
        .success(function(result){
            $rootScope.hmsLoading.hide();
            if(result.success){
            	setHotel();
            	Tip.message('设置成功');                
            }else{
            	reset();
                Tip.message(result.errorMsg,'error');
            }
        })
        .error(function(result){
        	reset();
            $rootScope.hmsLoading.hide();
            Tip.message('设置失败！');
        });
    };

    load();
}])
/**
 * [调用bootstrap的滚动插件 Affix指令]
 */
.directive('hmsAffix' , function(){
  return function(scope , el , attr){
  	el.css({
  		width:el.width(),
  		top:'0px',
  		marginTop:'0px',
  		paddingTop:'10px',
  		zIndex:999,
  		background:'#fff',
  		opacity:0.9
  	});
  	//添加bootstrap的滚动插件 Affix
	$(el).affix({
		offset: {
	      	top: el.offset().top ? el.offset().top : 200
	  	}
	})
	.on('affix.bs.affix',function(event){
		$(this).css({
			borderBottom:'1px solid #f0f0f0'
		});
		$(this).parent().css({
			paddingTop:$(this).outerHeight()
		});
	})
	.on('affix-top.bs.affix',function(event){
		$(this).css({
			borderBottom:'none'
		});
		$(this).parent().css({
			paddingTop:0
		});
	});
  };
})
/**
 * [loading提示框]
 */
.directive('hmsLoading',function(){
	// Runs during compile
	return {
		restrict: 'AE', // E = Element, A = Attribute, C = Class, M = Comment
		replace:true,
		template: '<div style="width:100%;height:100%;position:absolute;top:0;left:0;z-index:9999;">'
				+	'<div class="text-center" style="width:100%;height:50px;position:fixed;margin:auto;top:50%;margin-top:-25px;" class="text-center">'
				+    	'<i class="fa fa-spinner fa-pulse fa-3x fa-fw text-primary"></i>'
				+    '</div>'
				+ '</div>',
		link: function($scope, iElm, iAttrs, controller) {
		}
	};
})
/**
 * [酒店服务类]
 */
.service('HotelService', ['HmsHttp', function(HmsHttp){
	var serv = contentPath +'/hotelmessage/';
	return{
		findAreaRules:function(){
			var act = 'findAreaRule';
			return HmsHttp.post(serv+act);
		},
		saveHotelRule:function(ruleCode){
			var act = 'saveHotelRule',
				params = {
					'ruleCode':ruleCode
				};
			return HmsHttp.post(serv+act,params);
		},
		getIsholdAndStatus:function(){
			var act = 'getIsholdAndStatus';
			return HmsHttp.get(serv+act);

		},
		setHotelThreshold:function(isThreshold){
			var act = 'setHotelThreshold',
				params = {
					'isThreshold':isThreshold
				};
			return HmsHttp.post(serv+act,params);
		}
	};	
}])
/**
 * [酒店房间设置 roomSetingController子类]
 */
.controller('roomSetingController', ['$rootScope','$scope','$http', function($rootScope,$scope,$http){
	//房型设置对象
	$scope.page = {
			totalItems  : 0, //数据总条数
			currentPage : 1, //当前页
			pageCount   : 10, //每页显示数量
			numPages    : 1, //总页数
			maxSize     : 5, //当总页数大于多少的时候，分页开始显示...
			currentGoPage:""
	}
	
	// 默认查询
	$scope.isActive = 1;//默认显示table列表html  2为设置房间html
	$scope.isDisabled = true;//控制查询和设置界面中的房型和房号 不可以编辑
	$scope.isSelect = false;//控制只读和设置界面 false-设置   true-查询 
	$scope.isSettingMoreRoomtype = false;//是否设置多个房型  默认不是
	$scope.readOnly = true;//控制是否显示保存按钮  false-显示保存按钮  true-不显示保存按钮  
	
	//房型下拉框
	$scope.roomTypes = [
	        { 
	        	id: "",
	    		name: "全部"
	        }
	];
	//房间设置保存对象
	$scope.roomSetting = {
			roomtypeid   : "",
			roomtypename : "",
			roomno       : "",
			bedtypename  : "",
			roomdirection: "",
			roomfloor    : "",
			iswindow     : "",
			isstair      : "",
			iselevator   : "",
			isstreet     : "",
			valid        : "1"
		}
	//table列表标题
	var columns = [
     {
         field: "roomTypeName",
         displayName: "房型",
         width:'20%'
     }, {
         field: "roomNo",
         displayName: "房间号",
         width:'15%'
     }, {
         field: "bedTypeName",
         displayName: "床型",
         width:'20%'
     }, {
         field: "roomNo",
         displayName: "是否设置房间信息",
         width:'20%'
     }];
	
	//查询对象默认查询全部
	$scope.roomSettingSelectObj = {
			roomtypeid  : "",
			bedtypename : "",
			pageNum     : $scope.page.currentPage,
			pageSize    : $scope.page.pageCount
	}
	
	//table数据模型假数据
	$scope.roomTypeList = [];
	
	$scope.columns = columns;
	
	//初始化 -放在最后自动调用
	$scope.init = function(){
		//查询房型下拉款
		$scope.selectRoomTypeOption();
		
		$scope.setSelectCondition();
		
		selectRoomTypeList();
	};
	
	//当前页发生变化时操作
	$scope.pageChanged = function(){
		
		$scope.setSelectCondition();
		
		selectRoomTypeList();
	};
	
	$scope.pageGoQiantai = function(){
        var type = /^[0-9]*[1-9][0-9]*$/;
        var reg = new RegExp(type);
        if ($scope.page.currentGoPage.match(reg) == null 
            || $scope.page.currentGoPage > (Math.ceil($scope.page.totalItems/$scope.page.pageCount))) {
            $scope.page.currentGoPage = "";
            return;
        }
        if(!$scope.page.currentGoPage){
        	return;
        }
        $scope.page.currentPage = $scope.page.currentGoPage;
        $scope.page.currentGoPage = '';
        
        $scope.pageChanged();
    };
    
	/**
	 * 查询房型下拉框
	 */
	$scope.selectRoomTypeOption = function() {
		var hotelId = "";
		if(typeof(thisHotel) == "undefined"){
			hotelId = $rootScope.thisHotel.id;
		}else{
			hotelId = thisHotel.id;
		}
		var param = {
				hotelId : hotelId
			}
		$http.post(contentPath + "/roomtype/findsByHotelId",param)
		.success(function(res) {
			 if(res){
				 $scope.roomTypes = _.union($scope.roomTypes,res)
			 }
		})
		.error(function(msg) {
			$rootScope.hmsLoading.hide();
			Tip.message("获取房型信息失败", "error");
		});
	};
	
	/**
	 * 查询 or 设置 RoomTypeSetting
	 */
	$scope.selectOrSettingRoomSetting = function(item) {
		$scope.roomSetting = {
				roomtypeid   : "",
				roomtypename : "",
				roomno       : "",
				bedtypename  : "",
				roomdirection: "",
				roomfloor    : "",
				iswindow     : "",
				isstair      : "",
				iselevator   : "",
				isstreet     : "",
				valid        : "1"
			}
		$rootScope.hmsLoading.show();
		
		//构造请求的参数roomTypeList   start  
		var roomTypeList = [];
		if(!item){
			//如果item是空,曾说明是用过【房间设置】进来了,则数组中存放checkbox选中的数据
			var list = $scope.roomTypeList;
			for (var i = 0; i < list.length; i++) {
				var roomType = list[i];
				if(roomType.tick){
					roomTypeList.push(roomType);
				}
			}
		}else{
			//如果item是非空,曾说明是用过【查看详情】进来了,则数组中存放当前查看的那条数据
			roomTypeList.push(item);
		}
		//构造请求的参数roomTypeList   end
		if(roomTypeList.length > 1){
			$scope.isSettingMoreRoomtype = true;
		}else{
			$scope.isSettingMoreRoomtype = false;
		}
		var param = {
			"roomTypeList":JSON.stringify(roomTypeList)
		}
		
		$http.post(contentPath + "/roomsetting/sets", param)
		.success(function(res) {
			 if(res && res.success){
				 $scope.roomSetting = res.attribute;
			 }
			$rootScope.hmsLoading.hide();
		})
		.error(function(msg) {
			$rootScope.hmsLoading.hide();
			Tip.message("查看房间信息失败", "error");
		});
	};
	
	/**
	 * 点击查询按钮条用的方法
	 */
	$scope.selectRoomTypeListBefore = function() {
		$scope.setSelectCondition();
		selectRoomTypeList();
	}
	
	/**
	 * 查询Table列表
	 */
	function selectRoomTypeList() {
		$rootScope.hmsLoading.show();
		
		var param = $scope.getSelectCondition();
		
		if(!param){
			Tip.message("获取房间信息失败", "error");
			return;
		}
		
		$http.post(contentPath + "/roomsetting/finds", param)
		.success(function(res) {
			
			if (res && res.data) {
    			$scope.roomTypeList = res.data;
				$scope.page.totalItems = res.total;
				$scope.checkBoxAll.allTick = false;//查询后取消全选
    		}
			$rootScope.hmsLoading.hide();
		})
		.error(function(msg) {
			$rootScope.hmsLoading.hide();
			Tip.message("获取房型列表失败", "error");
		});
	};
	
	//保存
	$scope.saveRoomSettingList = function() {
		if(!this.checkRoomType()){
			Tip.message("【是否有窗】选项必须填写！", "error");
			return;
		}
		
		if(!this.checkRoomRoomfloor()){
			Tip.message("楼层不能超过20位字符！", "error");
			return;
		}
		
		$rootScope.hmsLoading.show();
		
		var param = {
				"setting":JSON.stringify($scope.roomSetting)
			}
		$http.post(contentPath + "/roomsetting/saveRoomSetting", param)
		.success(function(res) {
			 if(res && res.success){
				 Tip.message(res.context);
			 }else if(res && !res.success){
				 Tip.message(res.errorMsg);
			 }
			$rootScope.hmsLoading.hide();
		})
		.error(function(msg) {
			$rootScope.hmsLoading.hide();
			var errorMsg = "保存失败"
			if(msg.errorMsg){
				errorMsg = msg.errorMsg
			}
			Tip.message(errorMsg, "error");
		});
	};
	
	//校验必须录入项
	$scope.checkRoomType = function() {
		//是否有窗
		if (!$scope.roomSetting.iswindow || $scope.roomSetting.iswindow == "") {
			return false;
		}
		return true;
	};
	
	//校验楼层不能超过3位数
	$scope.checkRoomRoomfloor = function() {
		//是否有窗
		if ($scope.roomSetting.roomfloor && $scope.roomSetting.roomfloor.length > 20) {
			return false;
		}
		return true;
	};
	
	$scope.checkBoxAll = {
			allTick : false
	};

	//checkbox 全选
	$scope.afterAllTick = function(){
		for(var i=0; i< $scope.roomTypeList.length; i++){
			var data = $scope.roomTypeList[i];
			if ($scope.checkBoxAll.allTick) {
				data.tick = true;
			} else {
				data.tick = false;
			}
		}
	};

	//checkbox 单选
	$scope.afterTick = function(){
		for(var i=0; i< $scope.roomTypeList.length; i++){
			var data = $scope.roomTypeList[i];
			if (!data.tick) {
				$scope.checkBoxAll.allTick = false;
				return;
			}
		}
		$scope.checkBoxAll.allTick = true;
	};
	
	// 查询返回
	$scope.settingBack = function() {
		$scope.isActive = 1;
		$scope.readOnly = true;
	};
	
	// 设置取消 （该功能需要重新查询）
	$scope.setSettingBack = function() {
		$scope.isActive = 1;
		$scope.readOnly = true;
		
		selectRoomTypeList();
	};
	
	//房间设置
	$scope.settingRoom = function() {
		var roomTypeIdIsAllEqual = true;//所有选中的数据,房型是不是一样的  默认是一样的
		//校验checkbox是否选中
		if(!isCheckCheckbox()){
			Tip.message("请选中需要设置房型数据", "error");
			return;
		}
		
		//如果选中的房型不一样,给用户一个提示
		if(!roomTypeIdIsAllEqual){
			//$scope.isSettingMoreRoomtype = true;
			if(!confirm("确认设置多个房型数据?")){
				return;
			}
		}else{
			//$scope.isSettingMoreRoomtype = false;
		}
		
		$scope.isActive = 2;
		$scope.isSelect = false;
		$scope.readOnly = false;
		
		//设置房间信息
		$scope.selectOrSettingRoomSetting();
		
		//设置校验
		function isCheckCheckbox(){
			var isCheck = false;
			var roomTypeId = "";//所有选中的数据,房型是不是一样的
			var list = $scope.roomTypeList;
			var index = 0;
			for (var i = 0; i < list.length; i++) {
				var roomType = list[i];
				if(roomType.tick && index == 0){
					index++;
					roomTypeId = roomType.roomtypeid;
				}
				if(roomType.tick){
					if(roomTypeId != roomType.roomtypeid){
						roomTypeIdIsAllEqual = false;
					}
					isCheck = true;
				}
			}
			
			//没有选中checkbox
			if(!isCheck){
				return false;
			}
			//已经选中
			return true;
		}
	};
	
	//保存查询条件上线文
	$scope.setSelectCondition = function(){
		$scope.roomSettingSelectObjContext = {
						roomtypeid  : $scope.roomSettingSelectObj.roomtypeid,
						bedtypename : $scope.roomSettingSelectObj.bedtypename,
						pageNum     : $scope.page.currentPage,
						pageSize    : $scope.page.pageCount
		}
	}
	
	//获取查询条件上线文
	$scope.getSelectCondition = function(){
		return $scope.roomSettingSelectObjContext;
	}
	
	//房型查询
	$scope.selectRoom = function(item) {
		$scope.isActive = 2;
		$scope.readOnly = true;
		$scope.isSelect = true;
		
		//设置房间信息
		$scope.selectOrSettingRoomSetting(item);
	};
	
	$scope.init();
}])
.controller('hotelRegPhotoController', ['$rootScope','$scope','HmsHttp','HmsUploadService',
	function($rootScope,$scope,HmsHttp,HmsUploadService){
	var hotel = $rootScope.thisHotel;
	$scope.licenseImg = {
		"btnName": "选择照片",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 1,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|"
	};
	$scope.idCardFrontImg = {
		"btnName": "选择照片",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 1,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|"
	};
	$scope.idCardBackImg = {
		"btnName": "选择照片",
		"keys": [],
		"previews": [],
		"MaxUploadNum": 1,
		"maxItemSize": 10*1024*1024,
		"fileTypes": "|jpg|jpeg|png|"
	};
	//删除图片
	$scope.delHotelRegPic = function(img, key, url){
		if (!confirm("确认删除照片？")) {
			return;
		}
		for (var i = 0; i < img.keys.length; i++) {
			if (key == img.keys[i]) {
				img.keys.baoremove(i);
				img.previews.baoremove(i);
			}
		};
	};
	//保存图片
	$scope.save = function(showTip){
		if ($scope.licenseImg.previews[0] 
			|| ($scope.idCardFrontImg.previews[0] 
			&& $scope.idCardBackImg.previews[0])) {
			HmsHttp.post(contentPath + "/qiniu/updateHotelCredentialsPic", {
				businessLicenseFront: $scope.licenseImg.previews[0] || "",
				idCardFront: $scope.idCardFrontImg.previews[0] || "",
				idCardBack: $scope.idCardBackImg.previews[0] || ""
			}).then(
				function(res){
					var msg = res.data;
					if (!msg.success) {
						if(showTip) {
							Tip.message(msg.errorMsg, "error");
						}
					} else {
						if(showTip) {
							Tip.message("酒店证照添加成功");
						}
						$rootScope.thisHotel.businessLicenseFront = $scope.licenseImg.previews[0];
						$rootScope.thisHotel.idCardFront = $scope.idCardFrontImg.previews[0];
						$rootScope.thisHotel.idCardBack = $scope.idCardBackImg.previews[0];
					}
				}, 
				function(msg){
					if(showTip) {
						Tip.message("酒店证照添加异常", "error");
					}
				}
			);
		} else {
			if(showTip) {
				Tip.message('请上传营业执照或身份证照片','error');
			}
		}
	};
	//加载酒店图片数据
	function initHotelPics(){
		if (hotel.businessLicenseFront) {
			$scope.licenseImg.keys.push(hotel.businessLicenseFront.replace(HmsUploadService.publicDownloadUrl+ "/", ""));
			$scope.licenseImg.previews.push(hotel.businessLicenseFront);
		}
		if (hotel.idCardFront) {
			$scope.idCardFrontImg.keys.push(hotel.idCardFront.replace(HmsUploadService.publicDownloadUrl+ "/", ""));
			$scope.idCardFrontImg.previews.push(hotel.idCardFront);
		}
		if (hotel.idCardBack) {
			$scope.idCardBackImg.keys.push(hotel.idCardBack.replace(HmsUploadService.publicDownloadUrl+ "/", ""));
			$scope.idCardBackImg.previews.push(hotel.idCardBack);
		}
		$scope.recordImgDataForCompare();
	};

	$scope.$on("$stateChangeStart",function(event){
		if (!$scope.compareImgData()) {
			$scope.save(false);
		}
	});

	$scope.recordImgDataForCompare = function(){
		$scope.srcLicensePreviews = $scope.licenseImg.previews.concat([]);
		$scope.srcIdCardFrontPreviews = $scope.idCardFrontImg.previews.concat([]);
		$scope.srcIdCardBackPreviews = $scope.idCardBackImg.previews.concat([]);
	};

	$scope.compareImgData = function(){
		if (_.difference($scope.srcLicensePreviews, $scope.licenseImg.previews).length
			|| _.difference($scope.licenseImg.previews, $scope.srcLicensePreviews).length) {
			return false;
		}
		if (_.difference($scope.srcIdCardFrontPreviews, $scope.idCardFrontImg.previews).length
			|| _.difference($scope.idCardFrontImg.previews, $scope.srcIdCardFrontPreviews).length) {
			return false;
		}
		if (_.difference($scope.srcIdCardBackPreviews, $scope.idCardBackImg.previews).length
			|| _.difference($scope.idCardBackImg.previews, $scope.srcIdCardBackPreviews).length) {
			return false;
		}
		return true;
	};

	//加载
	initHotelPics();
}]);

