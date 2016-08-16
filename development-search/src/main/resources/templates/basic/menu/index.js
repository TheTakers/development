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
			
			editCtrl:function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
				
				//页面数据
				$scope.data = param;
				
			 	$scope.save = function() {
			 		saveOfClose($http,'/basic/menu/save',$scope.data,$uibModalInstance);
			 	};
			 	
			 	$scope.cancel = function() {
			 		 $uibModalInstance.dismiss('cancel');
			 	};
			 	
			 	
			 	//字段列表
			 	$scope.fieldList = [
					            {title:"名称",field:"name",required:true,element:"text"},
					            {title:"url",field:"link",required:false,element:"text"},
					            {title:"图标",field:"ico",required:true,element:"text"},
					            {title:"所属菜单",field:"pid",required:false,element:"selector",url:"/basic/menu/selector",option:{text:"pText",value:"pid"}},
					            {title:"菜单路径",field:"path",required:false,element:"text"},
					            {title:"排序",field:"idx",required:false,element:"text"},
					            {title:"描述",field:"remark",required:false,element:"textarea"}
					            ]
			 	
			},
			
			columnList:[
			            {title:"名称",field:"name",display:true,exdata:"",query:true},
			            {title:"url",field:"link",display:true,exdata:"",query:true},
			            {title:"图标",field:"ico",display:true,exdata:"",query:true},
			            {title:"所属菜单",field:"pid",display:true,exdata:"",query:true},
			            {title:"菜单路径",field:"name",display:true,exdata:"",query:true},
			            {title:"排序",field:"path",display:true,exdata:"",query:true},
			            {title:"描述",field:"remark",display:true,exdata:"",query:true},
			            {title:"操作",field:""}
			            ],
		   funcList:[
		             {title:"查看",icon:"ion-eye",target:"view"},
		             {title:"编辑",icon:"ion-edit",target:"edit"},
		             {title:"删除",icon:"ion-trash-a",target:"remove"}
		             ]            
			
	}
	
	//tree config
	$scope.treeConfig ={
			setting:{
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
				}
			}
	};

	
	//按钮工具栏
	$scope.toolbar = {
			id: "toolbar"+$.uuid(),

			buttonList:[{name:"新增",target:"edit"}],

			//查询列
			inputList:[{label:"编号",field:"",element:"",value:"",expr:"",exdata:""},
			           {label:"名称",field:"",element:"",value:"",expr:"",exdata:""}]
	};
});