angular.module('app').controller('sqlGroupCtrl', function($scope,$http,$uibModal,$log) {
	
	$scope.dataList = {};
	$scope.queryparams = {};
	
	$scope.search = function(){
		
		//广播分页条查询
		$scope.$broadcast("sqlgroupgrid");  
	}
	
	$scope.groupTreeCallback = function(ztree){
		ztree.expandAll(true);
	};
	
	$scope.setting = {
			async:{
				url:"/search/sqlgroup/treeData",
				type:"post",
				contentType: "application/json",
				enable:true
			},
			data:{
				simpleData:{
					enable: true, //不需要用户再把数据库中取出的 List 强行转换为复杂的 JSON 嵌套格式
					idKey: "id",
					pIdKey: "parentId",
					rootPId: 0
				}
			},
			callback: {
				onClick: function(event,treeId,node,idx){
					$scope.$broadcast("sqlgroupgrid");  
				}
			}};
	 
	 
	 //传递给子页
	 $scope.items = ['item1', 'item2', 'item3'];
	
	 $scope.openTemplate = function () {
        
		 var modalInstance = $uibModal.open({
			 templateUrl: '/search/sqlgroup/edit',
			 controller: 'sqlGroupCtrl', 
			 resolve: {
				 items: function () {
					 return $scope.items;
				 },
				 deps:function($ocLazyLoad,$stateParams,$log){
					 return $ocLazyLoad.load("templates/search/sqlgroup/edit.js");
				 }
			 }
		 });
         
         modalInstance.result.then(function (selectedItem) {
             $scope.selected = selectedItem;
             
             alert(selectedItem)
             $scope.$broadcast("sqlgroupgrid");  
             
           }, function () {
        	   
             $log.info('Modal dismissed at: ' + new Date());
           });
     };
});
