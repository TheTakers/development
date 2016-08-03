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
	
	$scope.tabs = [];
	
	//选中tabs
	$scope.selectId;
	$scope.breadcrumbData = [];
	
	
	$scope.isSelected = function(id){
		return _.isEqual($scope.selectId, id);
	}
	
	$scope.init = function(){}
	
    /**logout**/
    $scope.logout = function(){
      $('#logout').submit();
    }
});

app.controller('menuCtrl', function($scope,$http,$ocLazyLoad,$log) {
	
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
	        	
	        	/*初始化选中首页
	        	if(!_.isEmpty(data.result)){
	        		
	        		var item;
	        		for(var idx in data.result){
	        			item = data.result[idx];
	        			if(item.ico == 'ti-home'){
	        				$scope.$parent.selectId = item.id;
	        				$scope.$parent.tabs.push(item);	
	        				break;
	        			}
	        		}
        			
        			//TODO loading js ..
	        	}*/
	       	  }else{
	       		 $.error(data.message);
	       	  }
	         }  
	    });
	}
	
	$scope.forward = function(item){
		
		if(!_.isEmpty(item.link)){
			var promise =  $ocLazyLoad.load("templates"+item.link+".js");
			promise.then(function(res){//success 
 			},function(error){//error
			},function(info){//notify
			}).catch(function(ex){
			}).finally(function(res){
				
				if(_.findIndex($scope.$parent.tabs, item) < 0){
					$scope.$parent.tabs.push(item);	
				} 
				//绑定选中tab
				$scope.$parent.selectId = item.id;
			})
		}
	}

});
