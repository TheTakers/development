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
		navId : 2,
		
		// 设置导航标识
		setNavId:function(navId){
			this.navId = navId;
		},
		//获取导航数据
		getMenuData:function(){
			if(this.navId != 1){
				return [
				          {id:'1',name:'首页',icon:'icon-home',url:'#/',pid:''},
			              {id:'2',name:'控制台',icon:'icon-pencil',url:'',pid:'',child:[
			   		                                                               {id:'',name:'系统上下文',icon:'icon-edit',url:'#/console/context',pid:''},
					                                                                {id:'',name:'内存监控',icon:'icon-remove',url:'#/console/memory',pid:''}
					                                                               ]},
			              {id:'3',name:'系统设置',icon:'icon-folder-open',url:'',pid:'',child:[
				        
		                {id:'',name:'系统菜单',icon:'icon-edit',url:'',pid:'',child:[
		                                                                {id:'',name:'图标管理',icon:'icon-user',url:'',pid:''},
		                                                                {id:'',name:'菜单配置',icon:'icon-external-link',url:'',pid:''}
		                                                                ]},
		                {id:'',name:'查询配置',icon:'icon-pencil',url:'',pid:'',child:[
		                                                                {id:'',name:'SQL',icon:'icon-bell',url:'#/search/sqlDefine',pid:''},
		                                                                {id:'',name:'SQL组',icon:'icon-globe',url:'',pid:''},
		                                                                {id:'',name:'数据源',icon:'icon-pencil',url:'',pid:''}
		                                                                ]},
		                {id:'',name:'用户管理',icon:'icon-edit',url:'',pid:'',child:[]},
		                {id:'',name:'日志管理',icon:'icon-globe',url:'',pid:'',child:[
		                                                                {id:'',name:'系统日志',icon:'icon-folder-open',url:'',pid:''},
		                                                                {id:'',name:'接口日志',icon:'icon-folder-open',url:'',pid:''}
		                                                                ]}
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
	
});

app.controller('menuCtrl', function($scope,$http,menuRepository) {
	$scope.menu = menuRepository.getMenuData();
	$scope.selected = function(el){
		
	};
});

