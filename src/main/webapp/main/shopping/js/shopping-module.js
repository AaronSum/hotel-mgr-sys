/**account module*/

"use strict";
angular.module("hmsApp")
.controller("goodsMainController", ['$rootScope', '$scope', '$http', '$timeout', '$state', function($rootScope,$scope,$http,$timeout,$state) {
	$scope.goodsImgs = {
			'slider': contentPath+'/images/goods_slider.png',
			'recommend': contentPath+'/images/goods_recommend.png'
	};
	$scope.myCartCount = 0;
	$scope.myCartCountStr = "";

	$scope.goBackMainPage = function(){
		$state.go('shopping');
	};
	$scope.goMyOrdersPage = function(){
		$state.go('shopping.orders');
	};
	$scope.goMyCartPage = function(){
		$state.go('shopping.carts');
	};
	$scope.refreshMyCart = function(){
		var refreshMyCartUrl = contentPath + "/shopping/findShoppingCartWareCount";
		$http.post(refreshMyCartUrl).then(
 			function(res){
 				if (res.data >= 0) {
 					$scope.myCartCount = res.data;
 					if ($scope.myCartCount > 99) {
 						$scope.myCartCountStr = "99+";
 					} else {
 						$scope.myCartCountStr = "" + $scope.myCartCount;
 					}
 				}
			},
 			function(err){

 			}
		);
	};
	$scope.refreshMyCart();
}])
.controller("goodsController", ['$rootScope', '$scope', '$http', '$timeout', '$state', function($rootScope,$scope,$http,$timeout,$state) {
	$scope.initFlag = false;
	$scope.totalItems = 0; //数据总条数
	$scope.currentPage = 1; //当前页
	$scope.pageCount = 12; //每页显示数量
	$scope.numPages = 1; //总页数
	$scope.maxSize = 2; //当总页数大于多少的时候，分页开始显示...
	
	$scope.pageChanged = function(){
		$scope.goods.refreshList();
	};
	
	$scope.goods = {
		category: [],
		list: [],
		init: function(){
			var self = this;
			self.refreshCategory();
			// self.refreshList();
		},
		refreshCategory: function(){
			var self = this;
			var getCategoryUrl = contentPath + "/shopping/category";
			return $http.get(getCategoryUrl).then(
				function(res){
					if (res.data) {
						$scope.active = 0;
						self.category = res.data;
						self.category = [{'categoryname':'全部商品','id':-1}].concat(self.category);
					}
				},
				function(err){

				}
			);
		},
		refreshList: function(){
			var self = this;
			$rootScope.hmsLoading.show();
			var getGoodsUrl = contentPath + "/shopping/wares";
			var params = { 
				pageNum:$scope.currentPage,
				pageSize: $scope.pageCount,
				categoryId: 0
			};
			if ($scope.active >= 0) {
				params["categoryId"] = $scope.goods.category[$scope.active]['id'];
			}
	        if ($scope.totalItems) {
	            params.total = $scope.totalItems;
	        }
			return $http.post(getGoodsUrl, params).then(
				function(res) {
		    		$rootScope.hmsLoading.hide();
		    		if (res.data) {
		    			$scope.goods.list = res.data.rows;
		    			$scope.formatData($scope.goods.list);
						$scope.totalItems = res.data.total;
		    		}
		    		if (!$scope.initFlag) {
		    			$scope.initFlag = true;
		    		}
				},
				function(error){			
		    		$rootScope.hmsLoading.hide();
				}
			);
		},
		enterDetail: function(ware){
			$state.go('shopping.detail', {"id": ware.id});
		}
	};

	$scope.active = -1;

	//设置切换便签
	$scope.setActive = function(index){
		$scope.active = index;
		$scope.currentPage = 1;
	};

	$scope.formatData = function(list){
		for(var i=0; i<list.length; i++){
			var data = list[i];
			data.priceStr = "￥" + data.price;
		}
	};

	//初始化调用（监听当前选择项目变化）
	$scope.$watch('active',function(newVal){
		if (newVal >= 0) {
			$scope.goods.refreshList();
		}
	});

	$scope.goods.init();
}])
.controller('goodsDetailController', ['$rootScope', '$scope', '$http', '$state', '$animate', function($rootScope,$scope,$http,$state,$animate){
	$scope.detail = {};
	$scope.amount = 1;

	$scope.slider = {
 		ele:jQuery(".slider_con"),
 		active:false,
 		len:5,
 		step:1,
 		roller:[],
 		doneList:[],
 		type:"",
 		addData:null,
 		ignore:null,
 		stop:function(){
 			$scope.slider.doneList.splice(0);
 			$scope.slider.active = false;
 		},
 		decre:function(){
 			var self = $scope.slider;
 			if(!self.before(false)){
 				return;
 			}
 			if(self.active) {
 				if(self.type != "decre"){
 					self.doneList.splice(0);
 					return;
 				}
 				self.doneList.push("decre");
 				self.type = "decre";
 				return;
 			}
 			self.type = "decre";
 			self.doneList.push(self.type);
 			self.active = true;
 			$animate.addClass(self.ele, self.type).then(self.after);
 		},
 		incre:function(){
 			var self = $scope.slider;
 			if(!self.before(true)){
 				return;
 			}
 			if(self.active) {
 				if(self.type != "incre"){
 					self.doneList.splice(0);
 					return;
 				}
 				self.doneList.push("incre");
 				self.type = "incre";
 				return;
 			}
 			self.type = "incre";
 			self.doneList.push(self.type);
 			self.active = true;
 			$animate.addClass(self.ele, self.type).then(self.after);
 		},
 		before:function(isIncre){
 			if (isIncre) {
				if ($scope.statrIndex >=($scope.statrIndex + 5)) {
	 				return false;
	 			}
	 			$scope.statrIndex < ($scope.statrIndex + 5) && $scope.statrIndex++;
 			} else if (!isIncre) {
 				if ($scope.statrIndex <= 0) {
	 				return false;
	 			}
	 			$scope.statrIndex > 0 && $scope.statrIndex--;
 			}
 			return true;
 		},
 		after:function(){
 			var self = $scope.slider;
 			var thisTask = self.doneList[0];
 			if (thisTask == "decre") {
 				var _cut = self.roller.splice(0, self.roller.length - self.step);
 				self.roller = self.roller.concat(_cut);
 				var tempRoller = angular.copy(self.roller);
 				for (var i=0;i<self.len;i++) {
 					var newIndex = (i + self.step) % self.len;
 					self.roller[i].pos = tempRoller[newIndex].pos;
 					if (i < self.step && self.addData) {
 						self.addData(self.roller[i]);
 					}
 				}
 			} else if (thisTask == "incre") {
 				var _cut = self.roller.splice(0, self.step);
 				self.roller = self.roller.concat(_cut);
 				var tempRoller = angular.copy(self.roller);
 				for (var i=self.len-1;i>=0;i--) {
 					var newIndex = (i + self.len - self.step) % self.len;
 					self.roller[i].pos = tempRoller[newIndex].pos;
 					if (i >= (self.len - self.step) && self.addData) {
 						self.addData(self.roller[i]);
 					}
 				}
 			}
 			self.ele.removeClass(self.type);
 			self.doneList.shift();
 			$scope.$apply(function(){
 				if (self.doneList.length && (!self.ignore || !self.ignore())) {
 					$animate.addClass(self.ele, self.doneList[0]).then(self.after);
 					return;
 				}
 				self.doneList.splice(0);
 				self.active = false;
 				if (self.callBack) {
 					self.callBack();
 				}
 			});
 		}
 	};

 	$scope.statrIndex = 0;
 	(function initSlider(){
		var wareId;
 		$scope.wareId = wareId = $state.params.id;

 		//查询详细信息
 		var getGoodsDetailUrl = contentPath + "/shopping/findWareDetail?wareId=" + wareId;
 		$http.get(getGoodsDetailUrl).then(
 			function(res){
 				if (res.data) {
 					$scope.detail = res.data;
 					if ($scope.detail.price >= 0) {
 						$scope.detail.priceStr = "￥" + $scope.detail.srouceprice;
 						$scope.detail.currPriceStr = "￥" + $scope.detail.price;
 						$scope.detail.feeStr = "￥" + $scope.detail.fee + "元";
 						$scope.detail.adlanguage = $scope.detail.adlanguage;
 						$scope.detail.isYH = ($scope.detail.issg == 1 && $scope.detail.stocknum > 0)?" 有货":" 无货";
 						$scope.detail.isDisabled = ($scope.detail.issg == 1 && $scope.detail.stocknum > 0)?"N":"Y";
 					}

 					jQuery(".detail-image-big img").load(function(){
 						var bigImg = jQuery(".detail-image-big");
						bigImg[0].addEventListener("mousemove", function(event){
							jQuery(".detail-image-zoomer").show();
							jQuery(".detail-image-zoom").show();
							var transX = 0;
							var transY = 0;
							var mX = event.offsetX;
							var mY = event.offsetY;
							if (mX < 80) {
								transX = 0;
							} else if (340- mX < 80) {
								transX = 182;
							} else {
								transX = mX - 80;
							}
							if (mY < 80) {
								transY = 0;
							} else if (340- mY < 80) {
								transY = 182;
							} else {
								transY = mY - 80;
							}

							var transStr = "translate(" + transX + "px," + transY + "px)";
							var bigTransStr = "translate(" + (-transX*2.5) + "px," + (-transY*2.5) + "px) scale(2.5)";

							jQuery(".detail-image-zoomer").css("transform", transStr);
							jQuery(".detail-image-zoom-img").css("transform", bigTransStr);
						}, false);
						bigImg[0].addEventListener("mouseout", function(event){
							jQuery(".detail-image-zoomer").hide();
							jQuery(".detail-image-zoom").hide();
						}, false);
						var bigImg = jQuery(".detail-image-big");
						var imgSlider = jQuery(".detail-image-slider");
						imgSlider[0].addEventListener("mouseover", function(event){
							var target = event.target;
							if (target.nodeName == "IMG") {
								var src = jQuery(target).attr("lsrc");
								jQuery(bigImg.children()).attr("src", src);
								jQuery(".detail-image-zoom-img").attr("src", src);
								imgSlider.find(".active-img").removeClass("active-img");
								jQuery(target).addClass("active-img");
							}
						}, false);
 					});
 				}
			},
 			function(err){

 			}
		);
		//查询商品促销语
		var getWarePromotionUrl = contentPath + "/shopping/findWarePromotion?wareId=" + wareId;
		$http.get(getWarePromotionUrl).then(
			function(res){
				if (res.data) {
					$scope.promotion = res.data;
					//format data
					if ($scope.promotion.attribute) {
						for (var i=0; i< $scope.promotion.attribute.length; i++) {
							var info = $scope.promotion.attribute[i];
							info.promotiondescList = info.promotiondesc.split("||");
						}
					}
				}
			},
			function(err){

			}
		);

		$scope.wareMoreImages = [];

 		//查询图片信息
 		var getGoodsDetailSmallImagesUrl = contentPath + "/shopping/findWareImages?wareId=" + wareId;
 		$http.get(getGoodsDetailSmallImagesUrl).then(
 			function(res){
 				if (res.data) {
 					var index = 0;
					for (var i = 0; i < res.data.length ; i++) {
						var img = res.data[i];
						if (img.imagetype == "0" || img.imagetype == "2") {
							$scope.wareMoreImages.push(img);
						}
						
						if (img.imagetype == "0" || img.imagetype == "1") {
							$scope.slider.roller.push({"data":img,"pos":"pos" + (index++)});
						}
					}
					$scope.slider.len = $scope.slider.roller.length;
 				}
 			},
 			function(err){

 			}
		);

 		$scope.slider.addData = function(data){
 			if ($scope.slider.type == "decre") {
 				// $scope.statrIndex > 0 && $scope.statrIndex--;
 				// data.date = data.date.DateAdd('m', -$scope.slider.len);
 				// data.dateStr = data.date.format("yyyy年MM月");
 			} else if ($scope.slider.type == "incre") {
 				// $scope.statrIndex < ($scope.statrIndex + 5) && $scope.statrIndex++;
 				// data.date = data.date.DateAdd('m', $scope.slider.len);
 				// data.dateStr = data.date.format("yyyy年MM月");
 			}
 		};
 	})();

 	$scope.addAmount = function(){
 		$scope.amount++;
 	};

 	$scope.reduceAmount = function(){
 		if ($scope.amount == 1) {
 			return;
 		}
 		$scope.amount--;
 	};
 	$scope.amountIsNull = function(){
 		if ($scope.amount == "" ||$scope.amount == "0" ) {
 			$scope.amount = 1;
 			return;
 		}
 	};
 	$scope.ckeckAmount = function(obj){
		$scope.amount = $scope.amount.replace(/\D|^0/g,'');
 	};
 	$scope.addToCart = function(){
 		if ($scope.adding) {
 			return;
 		}
 		$scope.adding = true;
 		$rootScope.hmsLoading.show();
 		var addToCartUrl = contentPath + "/shopping/saveShoppingCart";
		$http.post(addToCartUrl, 
			{
				"wareId": $scope.wareId,
				"num": $scope.amount
			}
		).then(
 			function(res){
 				if (res.data && res.data.success) {
 					$state.go("shopping.addSuccess");
 				} else {
 					Tip.message("加入购物车失败", "error");
 				}
 				$rootScope.hmsLoading.hide();
 				$scope.adding = false;
 				//刷新购物车数量
 				$scope.refreshMyCart();
			},
 			function(err){
 				Tip.message("加入购物车失败，请稍后重试", "error");
 				$rootScope.hmsLoading.hide();
 				$scope.adding = false;
 			}
		);
 	};
 	
	//查询商品属性
 	var wareId = $state.params.id;
	var getGoodsDetailAttr = contentPath + "/shopping/findWareAttr?wareId=" + wareId;
	$http.get(getGoodsDetailAttr).then(
		function(res){
			if (res.data && res.data.attribute) {
				$scope.wareAttrList = res.data.attribute;
			}
		},
		function(err){
			Tip.message("获取商品属性失败，请稍后重试", "error");
		}
	);
}])
.controller("cartsController", ['$rootScope', '$scope', '$http', '$timeout', '$state', function($rootScope,$scope,$http,$timeout,$state) {
	$scope.initFlag = false;
	$scope.columns = [
		{   
			field: "ware",
			displayName: "商品信息",
			class:'text-center',
			width:'30%'
		}, 
		{   
			field: "discount",
			displayName: "单价(元)",
			class:'text-center',
			width:'10%'
		},
		{   
			field: "num",
			displayName: "数量",
			class:'text-center',
			width:'15%'
		}, 
		{   
			field: "amount",
			displayName: "金额(元)",
			class:'text-center',
			width:'15%'
		},
		{
			field: "discount",
			displayName: "优惠金额(元)",
			class:'text-center',
			width:'10%'
		},
		{   
			field: "fee",
			displayName: "运费",
			class:'text-center',
			width:'15%'
		}
	];
	
	$scope.cart = {};
	$scope.carts = {
		submitLock: false,
		init: function(){
			var self = this;
			self.refreshList();
		},
		refreshList: function(){
			var self = this;
			$rootScope.hmsLoading.show();
			var getCartsUrl = contentPath + "/shopping/findShoppingCart";
			return $http.post(getCartsUrl).then(
				function(res) {	
		    		if (res.data && res.data.success) {
		    			$scope.cart = res.data.attribute;
		    			$scope.cart.cartlist = $scope.cart.cartDetails;

						for (var i = 0; i < $scope.cart.cartlist.length; i++) {
							var cartdetail = $scope.cart.cartlist[i];
							cartdetail.wareShopping.promotionDesc = null;
							cartdetail.wareShopping.promotionPrice = null;
						}
		    			$scope.cart.allTick = true;
		    			$scope.formatData($scope.cart);
						self.promotion();
		    		}
		    		$rootScope.hmsLoading.hide();
		    		if (!$scope.initFlag) {
		    			$scope.initFlag = true;
		    		}
				},
				function(error){			
		    		$rootScope.hmsLoading.hide();
				}
			);
		},
		promotion: function(){ //查询商品促销信息

			var getWareListPromotionUrl = contentPath + "/shopping/getWarePromotionData";
			var wareDetails = new Array();
			for (var i = 0; i < $scope.cart.cartlist.length; i++) {
				var cartdetail = $scope.cart.cartlist[i];
				if (!cartdetail.tick) {
					continue;
				}
				var wareDetail = {
					wareId: cartdetail.wareid,
					sourceData:(cartdetail.num * (cartdetail.wareShopping.price))
				}
				wareDetails.push(wareDetail);
			}
			$http.post(getWareListPromotionUrl,
				{wareList:JSON.stringify(wareDetails)}
			).then(
				function(res){
					if (res.data && res.data.success) {
						$scope.promotions = res.data.attribute;
						for (var i = 0; i < $scope.cart.cartlist.length; i++) {
							var cartdetail = $scope.cart.cartlist[i];
							for (var j = 0; j < $scope.promotions.length; j++){
								var prom = $scope.promotions[j];
								if(prom.wareId ==cartdetail.wareid ){
									cartdetail.wareShopping.promotionDesc=prom.promotionDesc;
									cartdetail.wareShopping.discount=prom.promotionData;
									if(prom.actualData == prom.sourceData){
										cartdetail.wareShopping.promotionPrice = null;
										cartdetail.wareShopping.isPromotionActive = false;
									} else{
										cartdetail.wareShopping.promotionPrice = prom.actualData;
										cartdetail.wareShopping.isPromotionActive = true;
									}
								}
							}
						}
					}
				},
				function(err){

				}
			);
		},
		submitOrder : function(){
			var self = this;
			if (self.submitLock) {
				return;
			}
			self.submitLock = true;
			var date =  new Date().format(DateUtils.YYYY_MM_DD).toString();
			var time =  new Date().format(DateUtils.YYYY_MM_DD_HH_MM_SS).toString();
			//订单明细
			var OrderDetails = new Array();
			for (var i = 0; i < $scope.cart.cartlist.length; i++) {
				var cartdetail = $scope.cart.cartlist[i];
				if (!cartdetail.tick) {
					continue;
				}
				var orderDetail = {
					wareid: cartdetail.wareid,
					warename: cartdetail.wareShopping.name,
					price: Number(cartdetail.wareShopping.price),
					num: cartdetail.num,
					discount: Number(cartdetail.wareShopping.discount),
					fee: Number(cartdetail.wareShopping.fee),
					imageurlsmall: cartdetail.wareShopping.imageurlsmall,
					cretedate: time,
					updatedate: time
				}
				OrderDetails.push(orderDetail);
			}
			if (OrderDetails.length == 0) {
				self.submitLock = false;
				Tip.message("请选择需要结算的商品", "error");
				return;
			}
			//订单主表
			var params = {
				userid     : $scope.cart.userid,
				loginuser  : $scope.cart.loginuser,
				hotelid    : $scope.cart.hotelid,
				orderamount: Number($scope.orderamount),
				orderdate  : time,
				cretedate  : time,
				updatedate : time,
				paydate    : time,
				payType    : "10",
				istype     : "10",
				OrderDetails : OrderDetails
			};
			$rootScope.hmsLoading.show();
			var getCartsUrl = contentPath + "/shopping/saveOrder";
			$http.post(getCartsUrl,{jsonOrder:JSON.stringify(params)}).then(
				function(res) {
					console.log("保存订单成功");
					//刷新购物车数量
	 				$scope.refreshMyCart();
	 				self.submitLock = false;
					$state.go('shopping.orders');
					$rootScope.hmsLoading.hide();
				},
				function(error){
		    		console.log("保存订单失败");
		    		$rootScope.hmsLoading.hide();
		    		self.submitLock = false;
				}
			);
		},
		updateCountCallback: function(ware){
			var self = this;
			if(parseInt(ware.num) == 0){
				return;
			}
			var params = {
				wareId: ware.wareid,
			   	num: ware.num
			}
			var url = contentPath + "/shopping/updateShoppingCartWareIdNum";
			$http.post(url,params).then(
				function(res) {
					console.log(self);
					$rootScope.carts.promotion();
					console.log("修改数量成功");
				},
				function(error){
		    		console.log("修改数量失败");
				}
			);
		},
		deleteShoppingCart : function(row,cartList){			
			if(!confirm("确认要删除？")) {
				return;
	        }
			$rootScope.hmsLoading.show();
			var params = {
				wareId: row.wareid
			}
			var url = contentPath + "/shopping/deleteShoppingCartForWareId";
			$http.post(url,params).then(
				function(res) {
					$rootScope.hmsLoading.hide();
					console.log("删除购物车成功");
					if (res.data && res.data.success) {
						for (var i = 0; i < cartList.length; i++) {
							if(cartList[i].wareid == row.wareid){
								cartList.splice(i,1);
								$scope.refreshMyCart();
								break;
							}
						}
						//更新购物车小图标
						$scope.refreshMyCart();
					}
				},
				function(error){
		    		console.log("删除购物车失败");
		    		$rootScope.hmsLoading.hide();
				}
			);
		},
		enterDetail: function(ware){
			$state.go('shopping.carts');
		}
	};

	$rootScope.carts = $scope.carts;

	$scope.cart.allTick = true;

	$scope.afterAllTick = function(){
		for(var i=0; i< $scope.cart.cartlist.length; i++){
			var data = $scope.cart.cartlist[i];
			if ($scope.cart.allTick) {
				data.tick = true;
			} else {
				data.tick = false;
			}
		}
	};

	$scope.afterTick = function(){
		for(var i=0; i< $scope.cart.cartlist.length; i++){
			var data = $scope.cart.cartlist[i];
			if (!data.tick) {
				$scope.cart.allTick = false;
				return;
			}
		}
		$scope.cart.allTick = true;
	};

	$scope.formatData = function(cart){
		var payAmount = 0.0;
		for(var i=0; i<cart.cartlist.length; i++){
			var data = cart.cartlist[i];
			if (data.wareid) {
				data.tick = true;
			}
		}
	};
	$scope.carts.init();

	$scope.getTotalAmount = function(){
		var total = 0.0;
		for(var i=0; i< $scope.cart.cartlist.length; i++){
			var data = $scope.cart.cartlist[i];
			if (data.wareid && data.wareShopping && data.tick) {
				total += (parseFloat(data.num * data.wareShopping.price ) - parseFloat(data.wareShopping.discount));
			}
		}
		$scope.orderamount = total.toFixed(2);
		return "￥" + total.toFixed(2);
	};
}])
.controller("ordersController", ['$rootScope', '$scope', '$http', '$timeout', '$state', function($rootScope,$scope,$http,$timeout,$state) {
	$scope.columns = [
		/*{
			field: "orderId",
			displayName: "订单编号",
			class:'text-center',
			width:'20%'
		},*/
		{   
			field: "orderInfo",
			displayName: "订单信息",
			class:'text-center',
			width:'35%'
		},
		{   
			field: "orderAmount",
			displayName: "订单金额",
			class:'text-center',
			width:'20%'
		}, 
		{   
			field: "payDate",
			displayName: "购买日期",
			class:'text-center',
			width:'15%'
		}, 
		{   
			field: "status",
			displayName: "订单状态",
			class:'text-center',
			width:'15%'
		}
	];
	$scope.totalItems = 0; //数据总条数
	$scope.currentPage = 1; //当前页
	$scope.pageCount = 10; //每页显示数量
	$scope.numPages = 1; //总页数
	$scope.maxSize = 2; //当总页数大于多少的时候，分页开始显示...
	$scope.pageChanged = function(){
		$scope.orders.init();
	};
	$scope.orders = {
		init: function(){
			var self = this;
			self.refreshList();
		},
		
		refreshList: function(){
			var self = this;
			$rootScope.hmsLoading.show();
			var params = { 
					pageNum:$scope.currentPage,
					pageSize: $scope.pageCount
				};
			var getOrdersUrl = contentPath + "/shopping/findOrder";
			return $http.post(getOrdersUrl,params).then(
					function(res) {
			    		if (res.data) {
			    			$scope.orderslist = res.data.rows;
			    			//$scope.orders.detailList = res.data.orderDetails;
							$scope.totalItems = res.data.total;
			    		}
			    		$rootScope.hmsLoading.hide();
					},
					function(error){			
			    		$rootScope.hmsLoading.hide();
					}
				);
		},
		showEnterDetail: function(order){
			$state.go('shopping.mygoods',{orderId:order.orderid + "-" + order.istype});
		},
		deleteEnterDetail: function(order){
			 if(!confirm("确认要取消？")) {
				 return;
	         }
			var getOrdersUrl = contentPath + "/shopping/deleteOrderByOrderId";
			var params = {orderId:order.orderid};
			$http.post(getOrdersUrl,params).then(
				function(res) {
		    		 console.log("取消订单成功");
		    		 order.istype = "30";
				},
				function(error){
					console.log("取消订单失败");
				}
			);
		},
		mouseenter:function(){
			$("a[name='alertStatus']").popover();
		}
	};
	$scope.getWarnStr = function(order){
		return '我们将尽快为您进行配送，当状态进入“已开始配送”时，我们预计24小时内到货';
	};
	$scope.orders.init();
}])
.controller("mygoodsController", ['$rootScope', '$scope', '$http', '$timeout', '$state', function($rootScope,$scope,$http,$timeout,$state) {
	$scope.columns = [
		{   field: "ware",
		  displayName: "商品",
		  class:'text-center',
		  width:'30%'
		},
		{   field: "price",
		  displayName: "单价(元)",
		  class:'text-center',
		  width:'15%'
		}, 
		{   field: "num",
		  displayName: "数量",
		  class:'text-center',
		  width:'10%'
		},
		{   field: "totalprice",
		  displayName: "商品总价(元)",
		  class:'text-center',
		  width:'15%'
		},
		{   
			field: "discount",
			displayName: "优惠金额",
			class:'text-center',
			width:'15%'
		}, 
		{   field: "fee",
		  displayName: "运费(元)",
		  class:'text-center',
		  width:'10%'
		},
	];
	$scope.currentCartArea = thisHotel.detailAddr;
	$scope.totalItems = 0; //数据总条数
	$scope.currentPage = 1; //当前页
	$scope.pageCount = 10; //每页显示数量
	$scope.numPages = 1; //总页数
	$scope.maxSize = 2; //当总页数大于多少的时候，分页开始显示...
	$scope.mygoods = {
		init: function(){
			var self = this;
			self.refreshList();
		},
		refreshList: function(){
			var self = this;
			$rootScope.hmsLoading.show();
			var array = $state.params.orderId.split("-");
			var orderId = array[0] ;
			var orderStatus = array[1] ;
			$scope.orderId = orderId;
			if(orderStatus == "10"){
				$scope.orderStatus = "已确认";
			}else if(orderStatus == "20"){
				$scope.orderStatus = "已开始配送";
			}else if(orderStatus == "30"){
				$scope.orderStatus = "已收到";
			}else if(orderStatus == "40"){
				$scope.orderStatus = "款项已结清";
			}else{
				$scope.orderStatus = "未知";
			}
			var params = { 
					//pageNum:$scope.currentPage,
					//pageSize: $scope.pageCount,
					orderId:orderId
				};
			var getOrdersUrl = contentPath + "/shopping/findOrderDetailByOrderId";
			return $http.post(getOrdersUrl,params).then(
					function(res) {
			    		$rootScope.hmsLoading.hide();
			    		if (res.data) {
			    			//分页逻辑先注释掉
			    			//$scope.mygoodslist = res.data.rows;
			    			//$scope.totalItems = res.data.total;
			    			$scope.mygoodslist = res.data;
							$scope.formatData($scope.mygoodslist);
			    		}
					},
					function(error){			
			    		$rootScope.hmsLoading.hide();
					}
				);
		},
		enterDetail: function(ware){
			$state.go('shopping.mygoods');
		}
	};
	
	$scope.formatData = function(mygoodslist){
		var payAmount = 0.0;
		for(var i=0; i < mygoodslist.length; i++){
			var data = mygoodslist[i];
			payAmount += parseFloat(data.num * data.price - data.discount);
		}
		
		$scope.payAmount = payAmount.toFixed(2);
	};

	$scope.mygoods.init();

}])
.controller("detailMoreController", ['$rootScope', '$scope', '$http', '$timeout', '$state', function($rootScope,$scope,$http,$timeout,$state) {
	$scope.detailMoreUrl = contentPath + "/main/shopping/template/sample/detail_more.html";
	//设置选中页
	$scope.isActive = true;
	$scope.setActive2 = function(active){
		$scope.isActive = active;
	};
}]);
