angular.module('app').controller('menuCtrl', function($scope,$http,$uibModal,$log,commonService) {

	$scope.grid = {

			id:$.uuid(),

			//table展示的数据
			dataList:{}, 

			//查询
			search:function(){
				$scope.$broadcast(this.id);  
			}	
	}


	$scope.treeConfig = {
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
					$scope.$broadcast($scope.grid.id);  
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


	//按钮工具栏
	$scope.toolbar = {
			id: "toolbar"+$.uuid(),

			buttonList:[{name:"新增",target:"edit"}],

			//查询列
			inputList:[{label:"编号",type:"",value:""},
			           {label:"名称",type:"",value:""}],
			
			//工具栏点击触发事件
			trigger : function(target){
			       switch(target){
			        case "edit":
			        	$scope.edit();
			        break;
			       	default :
			       		$log.info(target);
			        	break;
			     	}
			}
	};
});