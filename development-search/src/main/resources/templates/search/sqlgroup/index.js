angular.module('app').controller('sqlGroupCtrl', function($scope,$http,$uibModal) {
	
	$scope.select = {text:new Date().format("yyyyMMddhhmmss"),value:"xxxx"};
	
	$scope.pagination = {};
	$scope.queryparams = {};
	
	//编辑单个对象
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
				pIdKey: "parentId",
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
 	
 	//生成编码
 	$scope.createCode = function(){
 		$.ajax({  
 	         type : "post",  
 	         url : "/common/func/code",  
 	         async : false,  
 	         success : function(data){  
 	       	  if(data.code = '0'){
 	       		$scope.sqlgroup.code = data.result;
 	       	  }else{
 	       		 $.error(data.message);
 	       	  }
 	         }  
 	    });
 	}
});
