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

app.controller('indexCtrl', function($scope,$compile,$http,$ocLazyLoad) {
	
	//选中tabs
	$scope.selectId;
	
	//tab数据
	$scope.tabs = [];
	
    /**logout**/
    $scope.logout = function(){
      $('#logout').submit();
    }
});

app.controller('menuCtrl', function($scope,$http,$ocLazyLoad,$log) {
	
	$scope.menu;
	$scope.name;
	
	$scope.result = function(data){
		$scope.menu = data.result;
		
		/*初始化选中首页
    	if(!_.isEmpty(data.result)){
    		
    		var item;
    		if(_.isEmpty($scope.name)){
    			for(var idx in data.result){
        			item = data.result[idx];
        			if(item.ico == 'ti-home'){
        				$scope.$parent.selectId = item.id;
        				$scope.$parent.tabs.push(item);	
        				break;
        			}
        		}
        	}else{
        		item = data.result[0];
        	}
    		//TODO loading js ..
    	}*/
	}
	$scope.search = function(){

		$.ajax({  
	         type : "post",  
	         url : "/basic/menu/menuTreeData",  
	         contentType:'application/json',
	         dataType:'json',
	         async:false,
	         data:JSON.stringify({name:$scope.name}),
	         success : function(data){  
	       	  if(data.code = '0'){
	       		$scope.menu = data.result;
	       	  }else{
	       		 $.error(data.message);
	       	  }
	         }  
	    });
	}
	
	$scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
		sidemenuInit()
	});
	
	$scope.init=function(){
		$scope.search();
	}
	
	$scope.forward = function(item){
		
		function flag(){
			for(var idx in $scope.$parent.tabs){
				
				if(_.isEqual($scope.$parent.tabs[idx].id, item.id))
					return true;
			}
			return false;
		}
		
		if(!_.isEmpty(item.link)){
			var promise =  $ocLazyLoad.load("templates"+item.link+".js");
			promise.then(function(res){//success 
 			},function(error){//error
			},function(info){//notify
			}).catch(function(ex){
			}).finally(function(res){
				
				if(!flag()){
					$scope.$parent.tabs.push(item);	
				}
				//绑定选中tab
				$scope.$parent.selectId = item.id;
			})
		}
	}

});
