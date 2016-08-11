angular.module('app').controller('menuCtrl', function($scope,$http,$uibModal,$log,commonService) {
	
	//table展示的数据
	$scope.dataList = {};
	
	//查询
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
	
	//编辑
	$scope.edit = function (id) {
		edit(commonService,'/basic/menu/findById','/basic/menu/edit','editCtrl',{id:id},$scope.search);
	};
	
	//删除
	$scope.remove = function (id) {
		remove(commonService,'/basic/menu/delete',{id:id},$scope.search);
	};
	
	//查看
	$scope.view = function(id){
		
	}
	
	
	$scope.todo = function(target){
		 switch(target){
         case "edit":
        	   $scope.edit();
               break;
         default :
           $log.info(target);
           break;
       }
	}
	
	
	//按钮工具栏
	$scope.toolbar = {
		id: "toolbar"+$.uuid(),
		
		buttonList:[{name:"新增",target:"edit"}],
		
		//查询列
		inputList:[{label:"编号",type:"",value:""},
		           {label:"名称",type:"",value:""}] 
	};
});