/**router config*/
'use strict';

//module
angular.module("hmsApp", ['ui.router','ui.bootstrap','ui.grid','ui.grid.selection','messageApp','homeJinApp','ngAnimate', 'hmsService'])

/**
 * angularJs启动的时候只会执行一次，这里将state、stateParams放入rootScope中，目的是为了在
 * 后面可以方便的引用和注入
 */
.run(["$rootScope", "$state", "$stateParams","$timeout","$location", function($rootScope, $state, $stateParams,$timeout,$location) {
        
    $rootScope.contentPath = contentPath;   // 获取根路径

    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;

    $rootScope.$on("$stateChangeStart",function(event){
        if(!$rootScope.thisHotel || !$rootScope.thisHotel.id){
            $location.path('/');
        }
    });
    
    //显示或者隐藏系统loading动画
    $rootScope.hmsLoading = {
        isShow: false,
        show:function(){
            this.isShow = true;
            this.timer.run();
        },
        hide:function(){
            this.isShow = false;
            this.timer.cancel();
        },
        timer:{
            run:function(){
                this.cancel();
                this.timeOut = $timeout(function(){
                                $rootScope.hmsLoading.hide();
                            },5000);
            },
            cancel:function(){     
                if(this.timeOut && this.timeOut.$$state.status==0){
                    $timeout.cancel(this.timeOut);
                }                
            }
        }
    };
}])

/**
 * 路由配置
 * 注意：这里使用的是ui-router，而不是angularJs原生的
 * 原生的不支持路由嵌套，而ui-router支持路由嵌套
 *
  */
.config(["$stateProvider", "$urlRouterProvider", function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");
    $stateProvider.state("homeJin", {
        url: "/homeJin",
        templateUrl: contentPath + "/main/home/template/home-jin.html"
    })
    // .state("accountMgr", {
    //     url:'/accountMgr',
    //     templateUrl: contentPath + "/main/account/template/accountMgr.html"
    // })
    .state('none',{
        url:'/',
        template:''
    })
    .state("message", {
        url: "/message",
        templateUrl: contentPath + "/main/message/template/message.html"
    })
    .state('message.hotelFac',{
        url: "/hotelFac",
        templateUrl: contentPath + "/main/message/template/hotel-fac.html"        
    })
    .state('message.hotelInfo',{
        url: "/hotelInfo",
        templateUrl: contentPath + "/main/message/template/hotel-info.html"        
    })
    .state('message.hotelTraffic',{
        url: "/hotelTraffic",
        templateUrl: contentPath + "/main/message/template/hotel-traffic.html"        
    })
    .state('message.hotelPic',{
        url: "/hotelPic",
        templateUrl: contentPath + "/main/message/template/hotel-pic.html"        
    })
    .state('message.hotelPeripheral',{
        url: "/hotelPeripheral",
        templateUrl: contentPath + "/main/message/template/hotel-peripheral.html"        
    })
    .state('message.hotelRegPhoto',{
        url: "/hotelRegPhoto",
        templateUrl: contentPath + "/main/message/template/hotel-regphoto.html"        
    })
    .state('message.roomInfo',{
        url: "/roomInfo/:id",
        templateUrl: contentPath + "/main/message/template/room-info.html"        
    })
    .state('message.roomPrice',{
        url: "/roomPrice/:id",
        templateUrl: contentPath + "/main/message/template/room-price.html"        
    }) 
    .state('message.roomSetting',{
    	url: "/roomSetting",
        views: {
        	'': {
        		templateUrl: contentPath + "/main/message/template/room-setting.html"        
        	},
        	'select@message.roomSetting': {
        		templateUrl: contentPath + "/main/message/template/room-setting-list.html"
        	},
        	'setting@message.roomSetting': {
        		templateUrl: contentPath + "/main/message/template/room-setting-detail.html"
        	}
        }
    })
}])
