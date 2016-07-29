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
		
		if(!_.isEmpty(id)){
			
			/*根据选中ID获取最新数据*/
			$http.post('/basic/menu/findById',{id:id}).success(function(data){
				
				if(data.code == '0'){
					commonService.show({templateUrl:'/basic/menu/edit',controller:'editCtrl',param:data.result,callback:$scope.search});
				}else{
					$.error(data.message);
				}
			});
		}else{
			commonService.show({templateUrl:'/basic/menu/edit',controller:'editCtrl',param:{},callback:$scope.search});
		}
	};
	
	$scope.remove = function (id) {
		$http.post('/basic/menu/delete',{id:id}).success(function(data){
			if(data.code == '0'){
				$scope.search();
				$.info(data.message);
			}else{
				$.error(data.message);
			}
		});
	}
});