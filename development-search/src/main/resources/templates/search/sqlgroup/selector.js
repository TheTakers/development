angular.module('app').controller('selectorCtrl',function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
	
 	
 	$scope.dataList = {};
 	$scope.queryparams = {};
 	
 	$scope.ok = function() {
 		var item ={}
 		if($scope.selected.length>0){
 			item.value = $scope.selected[0].id;
 			item.text = $scope.selected[0].name;
 			
 			 //传值给父页
 			$uibModalInstance.close(item);
 		}else{
 			$.warning("请选择记录!");
 		}
 	};
 	
 	$scope.cancel = function() {
 		 $uibModalInstance.dismiss('cancel');
 	};
 	
 	//选中列表
 	$scope.selected= [];
 	$scope.rowClick = function(item){
 		uniqueOf($scope.selected,item);
 	}
});