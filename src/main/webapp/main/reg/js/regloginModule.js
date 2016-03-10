
/**
 * web application module
 * app module
 * @type {*}
 */
//var regModule = angular.module('appModule', ['ngRoute', 'regServices', 'regControllers']);
//regModule.config(['$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {

var hmsApp = angular.module('hmsApp', ['ui.router','ngSanitize','ngAnimate','mgcrea.ngStrap','regLoginControllers','hmsAngularLib', 'hmsService']);

/**
 * angularJs启动的时候只会执行一次，这里将state、stateParams放入rootScope中，目的是为了在
 * 后面可以方便的引用和注入
 */
hmsApp.run(["$rootScope", "$state", "$stateParams", function($rootScope, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
}])

/**
 * 路由配置
 * 注意：这里使用的是ui-router，而不是angularJs原生的
 * 原生的不支持路由嵌套，而ui-router支持路由嵌套
 *
  */
hmsApp.config(["$stateProvider", "$urlRouterProvider", function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise("/login");
    $stateProvider
    .state('login', {
    	url:'/login',
    	templateUrl:contentPath + "/main/reg/template/reg-login.html",
    	controller: 'controllers.regLoginCtrl'
    })
    .state("hotelMsg", {
    	url: '/hotelMsg',
        templateUrl: contentPath + "/main/reg/template/reg-hotelMsg.html",
        controller: 'controllers.regHotelMsgCtrl'
    })
    .state('photoMsg', {
        url: '/photoMsg',
        templateUrl: contentPath + "/main/reg/template/reg-photoMsg.html",
        controller:'controllers.regPhotoMsgCtrl'
    })
    .state("successMsg", {
    	url: '/successMsg',
        templateUrl: contentPath + "/main/reg/template/reg-successMsg.html",
        controller: 'controllers.regSuccessMsgCtrl'
    });
}]);