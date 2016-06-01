var app = angular.module('app', ['ngRoute', 'ngResource']);

app.config(['$routeProvider', '$httpProvider', function ($route) {
    $route
        .when('/', {templateUrl: '/home'})
        .when('/system/user', {templateUrl: '/system/user'})
        .when('/console/context', {templateUrl: '/console/context', controller: 'ConsoleContextCtrl'})
        .when('/console/memory', {templateUrl: '/console/memory', controller: 'MemoryContextCtrl'});
}]);

//menu data repository
app.factory('menuRepository',function(){
	
	return  {
	 nav:[ 
	              {id:'',name:'首页',icon:'home icon',url:'#/',pid:''},
	              {id:'',name:'控制台',icon:'database icon',url:'',pid:''},
	              {id:'',name:'系统设置',icon:'settings icon',url:'',pid:''}
	              ],
		   menu:[
	                {id:'',name:'系统菜单',icon:'',url:'',pid:'',child:[
	                                                                {id:'',name:'图标管理',icon:'flag icon',url:'',pid:''},
	                                                                {id:'',name:'菜单配置',icon:'block layout icon',url:'',pid:''}
	                                                                ]},
	                {id:'',name:'查询配置',icon:'',url:'',pid:'',child:[
	                                                                {id:'',name:'SQL',icon:'unhide icon',url:'',pid:''},
	                                                                {id:'',name:'SQL组',icon:'unordered list icon',url:'',pid:''},
	                                                                {id:'',name:'数据源',icon:'database icon',url:'',pid:''}
	                                                                ]},
	                {id:'',name:'用户管理',icon:'users icon',url:'',pid:'',child:[]},
	                {id:'',name:'控制台',icon:'',url:'',pid:'',child:[
	                                                               {id:'',name:'系统上下文',icon:'align justify icon',url:'#/console/context',pid:''},
	                                                                {id:'',name:'内存监控',icon:'align center icon',url:'#/console/memory',pid:''}
	                                                               ]},
	                {id:'',name:'日志管理',icon:'',url:'',pid:'',child:[
	                                                                {id:'',name:'系统日志',icon:'align justify icon',url:'',pid:''},
	                                                                {id:'',name:'接口日志',icon:'align center icon',url:'',pid:''}
	                                                                ]}
	                ]
	};
});

/**
app.controller('NavCtrl', ['$scope', function ($scope) {
    $scope.$root.$on('nav:head', function (event, header) {
        $scope.header = header;
    });
    $scope.$root.$on('nav:crumb', function (event, breadcrumb) {
        $scope.breadcrumb = breadcrumb;
    });
    $scope.is = function (header) {
        return $scope.header === header;
    };
}]);**/

app.controller('NavCtrl', function($scope,menuRepository) {
	$scope.nav = menuRepository.nav;
});

app.controller('MenuCtrl', function($scope,menuRepository) {
	$scope.menu = menuRepository.menu;
	$scope.flag = true;
});
