angular.module('app').controller('selectorCtrl',function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
	
 	$scope.currentNode= {text:"SQL",value:"0000"};
 	
 	$scope.ok = function() {
 		 //传值给父页
		$uibModalInstance.close($scope.currentNode);
 	};
 	
 	
 	$scope.cancel = function() {
 		 $uibModalInstance.dismiss('cancel');
 	};
 	
});