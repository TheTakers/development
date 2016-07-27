angular.module('app').controller('sqlGroupCtrl', function($scope,$http,$uibModal,$log,commonService) {

	$scope.dataList = {};
	$scope.queryparams = {};

	$scope.search = function(){

		//广播分页条查询
		$scope.$broadcast("grid");  
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
					$scope.$broadcast("grid");  
				}
			}};

	$scope.openTemplate = function () {
		
		//根据选中ID获取最新数据
		$http.post('/search/sqlgroup/treeData',{}).success(function(data){
			commonService.show({templateUrl:'/search/sqlgroup/edit',controller:'editCtrl',param:data});
		});
	};
});