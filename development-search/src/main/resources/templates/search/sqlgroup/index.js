angular.module('app').controller('sqlGroupCtrl', function($scope,$http,$uibModal) {
	
	$scope.select = {text:"divdfa",value:"xxxx"};
	
	$scope.pagination = {};
	$scope.queryparams = {};
	$scope.search = function(){
		
		//广播分页条查询
		$scope.$broadcast("sqlgroupgrid");  
	}
	
	$scope.groupTreeCallback = function(ztree){
		
		ztree.expandAll(true);
	};
	
	$scope.sett = {callback: {
		onClick: function(event,treeId,node,idx){
			$scope.$broadcast("sqlgroupgrid");  
		}
	}};
	
	 $scope.openTemplate = function () {
        
         var modalInstance = $uibModal.open({
             templateUrl: '/search/sqlgroup/edit',
             controller: 'sqlGroupCtrl',
             resolve: {
               items: function () {
                 return $scope.items;
               }
             }
         });
         
         modalInstance.result.then(function (selectedItem) {
             $scope.selected = selectedItem;
           }, function () {
             $log.info('Modal dismissed at: ' + new Date());
           });
     };
     $scope.cancel = function () {
    	 $scope.uibModalInstance.dismiss('cancel');
  	};
	$scope.znodes = [
	     			{ name:"父节点1 - 展开", open:true,
	    				children: [
	    					{ name:"父节点11 - 折叠",
	    						children: [
	    							{ name:"叶子节点111"},
	    							{ name:"叶子节点112"},
	    							{ name:"叶子节点113"},
	    							{ name:"叶子节点114"}
	    						]},
	    					{ name:"父节点12 - 折叠",
	    						children: [
	    							{ name:"叶子节点121"},
	    							{ name:"叶子节点122"},
	    							{ name:"叶子节点123"},
	    							{ name:"叶子节点124"}
	    						]},
	    					{ name:"父节点13", isParent:true}
	    				]},
	    			{ name:"父节点2 - 折叠",
	    				children: [
	    					{ name:"父节点21 - 展开", open:true,
	    						children: [
	    							{ name:"叶子节点211"},
	    							{ name:"叶子节点212"},
	    							{ name:"叶子节点213"},
	    							{ name:"叶子节点214"}
	    						]},
	    					{ name:"父节点23 - 折叠",
	    						children: [
	    							{ name:"叶子节点231"},
	    							{ name:"叶子节点232"},
	    							{ name:"叶子节点233"},
	    							{ name:"叶子节点234"}
	    						]}
	    				]},
	    			{ name:"父节点3", isParent:true}

	    		];
	
});