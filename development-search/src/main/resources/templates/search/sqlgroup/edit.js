angular.module('app').controller('editCtrl',function($scope,$http,$uibModal,$log,$uibModalInstance,items) { //接收子页传值
	
	//页面数据
	$scope.data = {};
	
	//生成编码
 	$scope.createCode = function(){
 		alert(items[0])
 		$.ajax({  
 	         type : "post",  
 	         url : "/common/func/code",  
 	         async : false,  
 	         success : function(data){  
 	       	  if(data.code = '0'){
 	       		$scope.data.code = data.result;
 	       	  }else{
 	       		 $.error(data.message);
 	       	  }
 	         }  
 	    });
 	}
 	
 	$scope.currentNode= 'selected';
 	
 	$scope.save = function() {
  		$http.post('/search/sqlgroup/save',$scope.data).success(function(data){
  			
  			 //传值给父页
  			 $uibModalInstance.close($scope.currentNode);
  		});	
 	};
 	
 	
 	$scope.cancel = function() {
 		 $uibModalInstance.dismiss('cancel');
 	};
 	
});