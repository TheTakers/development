var app = angular.module('app', ['ngRoute', 'ngResource']);

app.config(['$routeProvider', '$httpProvider', function ($route) {
    $route
        .when('/', {templateUrl: '/home'})
        .when('/system/user', {templateUrl: '/system/user'})
        .when('/console/context', {templateUrl: '/console/context', controller: 'ConsoleContextCtrl'})
        .when('/console/memory', {templateUrl: '/console/memory', controller: 'MemoryContextCtrl'})
        .when('/search/sqlDefine', {templateUrl: '/search/sqlDefine', controller: 'sqlDefineContextCtrl'})
        ;
}]);

//menu data repository
app.factory('menuRepository',function(){
	
	return {
		
		//导航标识
		navId : 1,
		
		// 设置导航标识
		setNavId:function(navId){
			this.navId = navId;
		},
		
		//根据导航标识获取菜单
		getNavData:function(){
			
			return [ 
		              {id:'1',name:'首页',icon:'home icon',url:'#/',pid:''},
		              {id:'2',name:'控制台',icon:'database icon',url:'',pid:''},
		              {id:'3',name:'系统设置',icon:'settings icon',url:'',pid:''}
		              ];
		},
		
		//获取导航数据
		getMenuData:function(){
			if(this.navId != 1){
				return [
		                {id:'',name:'系统菜单',icon:'',url:'',pid:'',child:[
		                                                                {id:'',name:'图标管理',icon:'flag icon',url:'',pid:''},
		                                                                {id:'',name:'菜单配置',icon:'block layout icon',url:'',pid:''}
		                                                                ]},
		                {id:'',name:'查询配置',icon:'',url:'',pid:'',child:[
		                                                                {id:'',name:'SQL',icon:'unhide icon',url:'#/search/sqlDefine',pid:''},
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
		                ];
			}
		}
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

app.controller('indexCtrl', function($scope,$http,menuRepository) {
	$scope.nav = menuRepository.getNavData();
	$scope.menu = menuRepository.getMenuData();
	//nav click
	$scope.setNavId = function(navId){
		menuRepository.setNavId(navId);
		$scope.menu = menuRepository.getMenuData();
	};
});
