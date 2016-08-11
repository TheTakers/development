angular.module('app').controller('sqlDefineCtrl', function($scope,$log,$http,$uibModal,$utils) {
	
	$scope.queryparams = {};
	$scope.dataList = {};
	$scope.search = function(){
		
		//广播分页条查询
		$scope.$broadcast("sqldefinegrid");  
	}
	
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
					$scope.$broadcast("grid");  
				}
			}};
	
	$scope.edit = function (id) {
		edit($utils,'/search/sqldefine/findById','/search/sqldefine/edit','editCtrl',{id:id},$scope.search);
	};
	
	$scope.remove = function (id) {
		remove($utils,'/basic/menu/delete',{id:id},$scope.search);
	}
     
    $scope.cancel = function() {
    	 $scope.$dismiss();
  	};
  	
  	$scope.save = function() {
  		$http.post('/search/sqldefine/save',$scope.sqlgroup).success(function(data){
  			$scope.$broadcast("sqldefinegrid");  
  			$scope.$close();
  		});	
 	};
 
});
