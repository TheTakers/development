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
				          {id:'1',name:'首页',icon:'ti-home',url:'/',pid:''},
			              {id:'2',name:'控制台',icon:'ti-spray',url:'',pid:'',child:[
			   		                                                               {id:'',name:'系统上下文',icon:'glyphicon glyphicon-bookmark',url:'/console/context',pid:''},
					                                                                {id:'',name:'内存监控',icon:'glyphicon glyphicon-print',url:'/console/memory',pid:''}
					                                                               ]},
			              {id:'3',name:'系统设置',icon:'ti-light-bulb',url:'',pid:'',child:[
				        
		                {id:'',name:'系统菜单',icon:'ti-spray',url:'',pid:'',child:[
		                                                                {id:'',name:'图标管理',icon:'glyphicon glyphicon-adjust',url:'',pid:''},
		                                                                {id:'',name:'菜单配置',icon:'glyphicon glyphicon-tint',url:'',pid:''}
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

app.controller('indexCtrl', function($scope,$compile,$http,menuRepository,$ocLazyLoad) {
    
    /**logout**/
    $scope.logout = function(){
      $('#logout').submit();
    }
});

app.controller('menuCtrl', function($scope,$http,menuRepository) {
	$scope.menu = menuRepository.getMenuData();
	 
});
