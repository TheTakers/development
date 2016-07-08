angular.module('app').controller('sqlGroupCtrl', function($scope,$http,$uibModal) {
	
	$scope.select = {text:"divdfa",value:"xxxx"};
	
	$scope.pagination = {};
	$scope.queryparams = {};
 	$scope.sqlgroup = {};
	$scope.search = function(){
		
		//广播分页条查询
		$scope.$broadcast("sqlgroupgrid");  
	}
	
	$scope.groupTreeCallback = function(ztree){
		ztree.expandAll(true);
	};
	
	$scope.sett = {
			data:{
				simpleData:{
				enable: true, //不需要用户再把数据库中取出的 List 强行转换为复杂的 JSON 嵌套格式
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0}
			},
			callback: {
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
     
    $scope.cancel = function() {
    	 $scope.$close();
  	};
  	
  	$scope.save = function() {
  		$http.post('/search/sqlgroup/save',$scope.sqlgroup).success(function(data){
  			$scope.$broadcast("sqlgroupgrid");  
  			$scope.$close();
  		});	
 	};
  	
 	$scope.znodes = [{"id":1, "pId":0, "name":"SQL组"}, 
 	                 {"id":11, "pId":1, "name":"test11"}, 
 	                 {"id":12, "pId":1, "name":"test12"}, 
 	                 {"id":111, "pId":11, "name":"test111"}];
	
});
