/**router config*/
'use strict';

//module
angular.module('hmsApp', ['ngAnimate', 'ui.router', 'ui.bootstrap.pagination', 'ui.bootstrap.buttons', 'template/pagination/pagination.html', 'hmsInitApp', 'homeApp', 'orderApp','borderApp', 'ratesApp', 'accountApp', 'messageApp', 'userApp' , 'hmsAngularLib' ,'commentApp' , 'hmsService'])

/**
 * angularJs启动的时候只会执行一次，这里将state、stateParams放入rootScope中，目的是为了在
 * 后面可以方便的引用和注入
 */
.run(['$rootScope', '$state', '$stateParams', "$timeout", function($rootScope, $state, $stateParams, $timeout) {    
    
    $rootScope.contentPath = contentPath;   // 获取根路径
    $rootScope.thisHotel = thisHotel;   //当前酒店
    $rootScope.RULE_CODE = ruleCode=='1001' ? 'A' : 'B'; //上线的酒店规则 

    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;

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

    var socketMessageMapper = {
        /**
         * 空指令.
         */
        //DIRECTIVE_NULL
        "-1": "",

        /**
         * 请求连接指令.
         */
        //DIRECTIVE_CONNECT
        "0": "",

        /**
         * 请求身份指令.
         */
        //DIRECTIVE_REQUEST_IDENTIER
        "1": "",

        /**
         * 发送身份指令.
         */
        //DIRECTIVE_SEND_IDENTIFER
        "2": "",

        /**
         * 发送关键字指令.
         */
        //DIRECTIVE_SEND_KEYWORD
        "3": "",

        /**
         *  接受关键字指令.
         */
        //DIRECTIVE_RECEIVED_KEYWORD
        "4": "",

        /**
         * 预定酒店指令.
         */
        //DIRECTIVE_BOOK_HOTEL
        "5": "CHANGE_ORDER_PRICE",

        /**
         * 酒店注册指令.
         */
        //DIRECTIVE_REGISTER_HOTEL
        "6": "",

        /**
         * 执行失败指令.
         */
        //DIRECTIVE_EXECUTE_FAILED
        "7": "",

        /**
         * 收到订单指令.
         */
        //DIRECTIVE_RECEIVE_ORDER
        "9": "RECEIVE_ORDER"
    };

    //注册socket监听
    var socket;
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    // Javascript Websocket Client
    if (window.WebSocket) {
        socket = new WebSocket('ws://' + hostAddress + contentPath + '/websocket');
        socket.onmessage = function(event) {
            var data = JSON.parse(event.data);
            if (data.userId == user.id) {
                var directiveCode = data["directiveCode"];
                if (directiveCode && socketMessageMapper[directiveCode]) {
                    $rootScope.$broadcast(socketMessageMapper[directiveCode], data);
                }
            } else {
                console.log('-------wrong message--userId:' + data.userId + '-----' + new Date().format(DateUtils.YYYY_MM_DD_HH_MM_SS));
            }
        };
        socket.onopen = function(event) {
        	sendMessageOfWebSocket(JSON.stringify({'directive': 'connect', 'userId': user.id}));
            console.log('-------socket open-------' + new Date().format(DateUtils.YYYY_MM_DD_HH_MM_SS));
            
        	pingWebSocket(JSON.stringify({'directive': 'ping'}));
        	console.log('-------socket------' + new Date().format(DateUtils.YYYY_MM_DD_HH_MM_SS));
        };
        socket.onclose = function(event) {
            console.log('-------socket closed-------' + new Date().format(DateUtils.YYYY_MM_DD_HH_MM_SS));
        };
    } else {
        Tip.message('当前浏览器不支持socket','error');
    }
    // ping WebSocket
    function pingWebSocket(message) {
    	 if (!window.WebSocket) { return; }
         if (socket.readyState == WebSocket.OPEN) {
             socket.send(message);
         } else {
        	 console.log('-------socket--sendmsgerror----' + new Date().format(DateUtils.YYYY_MM_DD_HH_MM_SS));
         }
         $timeout(function() {
        	 pingWebSocket(JSON.stringify({'directive': 'ping'}));
        	 console.log('-------socket------' + new Date().format(DateUtils.YYYY_MM_DD_HH_MM_SS));
        }, 5000);
    };
    //Send Websocket data  
    function sendMessageOfWebSocket(message) {
        if (!window.WebSocket) { return; }
        if (socket.readyState == WebSocket.OPEN) {
            socket.send(message);
        } else {
            Tip.message('socket未开启','error');
        }
    };
    
}])

/**
 * 路由配置
 * 注意：这里使用的是ui-router，而不是angularJs原生的
 * 原生的不支持路由嵌套，而ui-router支持路由嵌套
 *
  */
.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', function($stateProvider, $urlRouterProvider, $httpProvider) {
    $urlRouterProvider.otherwise('/home');
    $stateProvider.state('home', {
        url: '/home',
        templateUrl: contentPath + '/main/home/template/home.html'
    })
    .state('orders', {
        url: '/orders',
        templateUrl: contentPath + '/main/order/template/order.html'
    })    
    .state(' borders', {
        url: '/borders',
        templateUrl: contentPath + '/main/order/template/b-order.html'
    })
    .state('shopping', {
        url: '/goods',
        templateUrl: contentPath + '/main/shopping/template/goods.html',
        label: "商城 > 首页"
    })
    .state('shopping.detail', {
        url: '/detail/:id',
        templateUrl: contentPath + '/main/shopping/template/detail.html',
        label: "商城 > 商品详情"
    })
    .state('shopping.addSuccess', {
        url: '/addSuccess',
        templateUrl: contentPath + '/main/shopping/template/addSuccess.html',
        label: "商城 > 商品详情"
    })
    .state('shopping.carts', {
        url: '/carts',
        templateUrl: contentPath + '/main/shopping/template/carts.html',
        label: "商城 > 我的购物车"
    })
    .state('shopping.orders', {
        url: '/orders',
        templateUrl: contentPath + '/main/shopping/template/orders.html',
        label: "商城 > 我的订单"
    })
    .state('shopping.mygoods', {
        url: '/mygoods/:orderId',
        templateUrl: contentPath + '/main/shopping/template/mygoods.html',
        label: "商城 > 我的商品"
    })
    .state('rates', {
        url: '/rates',
        // templateUrl: contentPath + '/main/rates/template/rates.html'
        views:{
            '':{
                templateUrl: contentPath + '/main/rates/template/rates.html'              
            },
            'modify@rates': {
                templateUrl: contentPath + '/main/rates/template/rates-modify.html' 
            }
        }
    })
    .state('account', {
        url: '/account',
        templateUrl: contentPath + '/main/account/template/finance.html'
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
        .state('message.accountSetting',{
            url: "/accountSetting",
            templateUrl: contentPath + "/main/message/template/account-setting.html"        
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
    .state('users', {
        url: '/users',
        views: {
        	'': {
        		templateUrl: contentPath + '/main/users/template/users.html'
        	},
        	'list@users': {
        		templateUrl: contentPath + '/main/users/template/list.html'
        	},
        	'add@users': {
        		templateUrl: contentPath + '/main/users/template/add.html'
        	}
        }
    })
    .state('comment',{
        url:'/comment',
        templateUrl: contentPath + "/main/comment/template/comment.html"        
    });
    
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
}])