angular.module('app').controller('menuCtrl', function($scope,$http,$uibModal,$log,commonService) {

	$scope.dataList = {};
	$scope.queryparams = {};

	$scope.search = function(){

		//广播分页条查询
		$scope.$broadcast("grid");  
	}

	$scope.setting = {
			async:{
				url:"/basic/menu/treeData",
				type:"post",
				contentType: "application/json",
				enable:true
			},
			data:{
				simpleData:{
					enable: true, //不需要用户再把数据库中取出的 List 强行转换为复杂的 JSON 嵌套格式
					idKey: "id",
					pIdKey: "pid",
					rootPId: 0
				}
			},
			callback: {
				onClick: function(event,treeId,node,idx){
					$scope.$broadcast("grid");  
				}
			}};

	$scope.edit = function (id) {
		edit($http,commonService,'/basic/menu/findById','/basic/menu/edit','editCtrl',{id:id},$scope.search);
	};
	
	$scope.remove = function (id) {
		remove($http,'/basic/menu/delete',{id:id},$scope.search);
	}
});