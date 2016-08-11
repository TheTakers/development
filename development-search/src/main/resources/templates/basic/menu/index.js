angular.module('app').controller('menuCtrl', function($scope,$http,$uibModal,$log,commonService) {
	
	//grid
	$scope.grid = {
			id:$.uuid(),
			url:'/basic/menu/list',

			//table展示的数据
			dataList:{}, 

			//查询
			search:function(){
				$scope.$broadcast(this.id);  
			},
			
			columnList:[
			            {title:"名称",field:"name"},
			            {title:"url",field:"url"},
			            {title:"图标",field:"ico"},
			            {title:"所属菜单",field:"pid"},
			            {title:"菜单路径",field:"name"},
			            {title:"排序",field:"path"},
			            {title:"描述",field:"remark"},
			            {title:"操作",field:""}
			            ]
	}

	//tree config
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

	$scope.crud = function crud(id,target){
		switch(target){
		case "edit":
			$scope.edit(id);
			break;

		case "remove":
			$scope.remove(id);
			break;

		case "view":
			$scope.view(id);
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