angular.module('app').controller('editCtrl',function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
	
	//页面数据
	$scope.data = param;
	
	//生成编码
 	$scope.createCode = function(){
 		$.ajax({  
 	         type : "post",  
 	         url : "/basic/func/code",  
 	         async : false,  
 	         success : function(data){  
 	       	  if(data.code = '0'){
 	       		$scope.data.sqlId = data.result;
 	       	  }else{
 	       		 $.error(data.message);
 	       	  }
 	         }  
 	    });
 	}
 	
 	$scope.save = function() {
 		saveOfClose($http,'/search/sqldefine/save',$scope.data,$uibModalInstance);
 	};
 	
 	$scope.cancel = function() {
 		 $uibModalInstance.dismiss('cancel');
 	};
 	
});