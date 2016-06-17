var app = angular.module('app', ['ngRoute', 'ngResource']);


/**
app.config(['$routeProvider', '$httpProvider', function ($route) {
    $route
        .when('/', {templateUrl: '/home'})
        .when('/system/user', {templateUrl: '/system/user'})
        .when('/console/context', {templateUrl: '/console/context', controller: 'ConsoleContextCtrl'})
        .when('/console/memory', {templateUrl: '/console/memory', controller: 'MemoryContextCtrl'})
        .when('/search/sqlDefine', {templateUrl: '/search/sqlDefine', controller: 'sqlDefineContextCtrl'})
        ;
}]);**/


//定义一个 Service ，稍等将会把它作为 Interceptors 的处理函数
app.factory('httpInterceptor', ['$q', httpInterceptor]);

function httpInterceptor($q,$log) {
  return {
    request: function(config){
      return config;
    },
    requestError: function(err){
      return $q.reject(err);
    },
    response: function(res){
    	
  	  if(res.status == 200){
  		  
  		  if(!_.isUndefined(res.data.code)){
  			  
  			  if(res.data.code == 1)
  				  $.error(res.data.message);
  		  }
  	  }
      return res;
    },
    responseError: function(err){
    	$.error(err);
      return $q.reject(err);
    }
  };
}

// 添加对应的 Interceptors
app.config(['$httpProvider', function($httpProvider){
  $httpProvider.interceptors.push(httpInterceptor);
}]);
