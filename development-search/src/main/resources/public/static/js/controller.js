app.controller('ConsoleContextCtrl', ['$scope', function () {
    $('.menu.item').tab();
}]);
app.controller('MemoryContextCtrl', ['$scope', '$location', function ($scope, $location) {
    $('.message .close').on('click', function () {
        $(this).closest('.message').transition('fade');
    });
    $('.ui.submit').on('click', function () {
        $('#gc').submit();
    });
    $scope.visible = false;
    if ($location.search().success) {
        $scope.visible = true;
    }
}]);
app.controller('sqlDefineContextCtrl', ['$scope', function () {
    $('.menu.item').tab();
}]);


/*menu data repository
app.factory('menuRepository',function(){
	return {
		//获取导航数据
		getMenuData:function(){
				return [
				          {id:'1',name:'首页',icon:'ti-home',url:'/',pid:''},
			              {id:'2',name:'控制台',icon:'ti-spray',url:'',pid:'',child:[
			   		                                                               {id:'',name:'系统上下文',icon:'glyphicon glyphicon-bookmark',url:'/console/context',pid:''},
					                                                                {id:'',name:'内存监控',icon:'glyphicon glyphicon-print',url:'/console/memory',pid:''}
					                                                               ]},
			              {id:'3',name:'系统设置',icon:'ti-light-bulb',url:'',pid:'',child:[
				        
		                {id:'',name:'系统菜单',icon:'ti-spray',url:'',pid:'',child:[
		                                                                {id:'',name:'菜单配置',icon:'glyphicon glyphicon-tint',url:'/basic/menu/index',pid:''}
		                                                                ]},
		                {id:'',name:'查询配置',icon:'ti-pencil-alt',url:'',pid:'',child:[
		                                                                {id:'',name:'SQL组',icon:'glyphicon glyphicon-text-width',url:'/search/sqlgroup/index',pid:''},
		                                                                {id:'',name:'SQL',icon:'glyphicon glyphicon-chevron-left',url:'/search/sqldefine/index',pid:''},
		                                                                {id:'',name:'数据源',icon:'glyphicon glyphicon-tag',url:'',pid:''}
		                                                                ]},
		                {id:'',name:'用户管理',icon:'ti-menu-alt',url:'',pid:'',child:[]},
		                {id:'',name:'日志管理',icon:'ti-bar-chart',url:'',pid:'',child:[
		                                                                {id:'',name:'系统日志',icon:'glyphicon glyphicon-user',url:'',pid:''},
		                                                                {id:'',name:'接口日志',icon:'glyphicon glyphicon-music',url:'',pid:''}
		                                                                ]}
		                          ]}
		                ];
		}
	};
});*/

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

app.controller('indexCtrl', function($scope,$compile,$http,$ocLazyLoad) {
    
	$scope.breadcrumbData = [];
	
    /**logout**/
    $scope.logout = function(){
      $('#logout').submit();
    }
});

app.controller('menuCtrl', function($scope,$http) {
	
	$scope.menu;
	$scope.init=function(){
		$.ajax({  
	         type : "post",  
	         url : "/basic/menu/menuTreeData",  
	         contentType:'application/json',
	         dataType:'json',
	         async:false,
	         success : function(data){  
	       	  if(data.code = '0'){
	        	$scope.menu = data.result;
	       	  }else{
	       		 $.error(data.message);
	       	  }
	         }  
	    });
	}
	
	//获取菜单路径
 	$scope.getpaths = function(id,link){
 		
 		if(link && link.length > 0){
 			$.ajax({  
 				type : "post",  
 				url : "/basic/menu/breadcrumb",  
 				async : false,  
 				contentType:'application/json;charset=UTF-8',
 				dataType:'json',
 				data:JSON.stringify({id:id}),
 				success : function(data){  
 					if(data.code = '0'){
 						$scope.$parent.breadcrumbData = data.result;
 					}else{
 						$.error(data.message);
 					}
 				}  
 			});
 		}
 		
 	}
});
