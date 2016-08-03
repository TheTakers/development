var app = angular.module('app', ['ngRoute', 'ngResource','ui.router','oc.lazyLoad','ui.bootstrap']);


/**
app.config(['$routeProvider', '$httpProvider', function ($route) {
    $route
        .when('/', {templateUrl: '/home'})
        .when('/system/user', {templateUrl: '/system/user'})
        .when('/console/context', {templateUrl: '/console/context', controller: 'ConsoleContextCtrl'})
        .when('/console/memory', {templateUrl: '/console/memory', controller: 'MemoryContextCtrl'})
        .when('/search/sqlDefine', {templateUrl: '/search/sqlDefine', controller: 'sqlDefineContextCtrl'})
        ;
}]);


app.config(['$stateProvider', '$httpProvider', function ($stateProvider) {
	
	$stateProvider
	.state('blank',{url:"",templateUrl:"/home"})
	.state('index',{url:"/",templateUrl:"/home"})
    .state('authorize', {
        // 这里设置了url参数
        url: "/{module}/{controller}/{mapping}",
        templateUrl: function(param){
        	 return "/"+param.module+"/"+param.controller+"/"+param.mapping;
        },
        resolve:{
        	deps:function($ocLazyLoad,$stateParams,$log){
        		
        		var promise =  $ocLazyLoad.load("templates/"+$stateParams.module+"/"+$stateParams.controller+"/"+$stateParams.mapping+".js");
        		
        		promise.then(function(res){//success 
        			
        		},function(error){//error
        			
        			$.error(error)
        		},function(info){//notify
        			 
        		}).catch(function(ex){
        			
                }).finally(function(res){
                	
                })
        		return promise;
        	}
        }
    })
}]);
**/

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
      
      $.error(err.statusText);
      return $q.reject(err);
    }
  };
}

// 添加对应的 Interceptors
app.config(['$httpProvider', function($httpProvider){
  $httpProvider.interceptors.push(httpInterceptor);
}]);
