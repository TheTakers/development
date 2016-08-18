angular.module('app').controller('sqlDefineCtrl', function($scope,$http,$uibModal,$log,commonService) {
	
	//grid
	$scope.grid = {
			id:$.uuid(),
			url:'/search/sqldefine/list',

			//table展示的数据
			dataList:{}, 

			//查询
			search:function(){
				$scope.$broadcast(this.id);  
			},
			
			action:"/search/sqlDefine",
			
			editCtrl:function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
				
				//页面数据
				$scope.data = param;
				
			 	$scope.save = function() {
			 		saveOfClose($http,'/search/sqlDefine/save',$scope.data,$uibModalInstance);
			 	};
			 	
			 	$scope.cancel = function() {
			 		 $uibModalInstance.dismiss('cancel');
			 	};
			 	
			 	
			 	//字段列表
			 	$scope.fieldList = [
					            {title:"SQLID",field:"sqlId",required:true,element:"uigeneratecode"},
					            {title:"别名",field:"sqlName",required:false,element:"text"},
					            {title:"数据源",field:"datasource",required:true,element:"text"},
					            {title:"缓存",field:"cache",required:false,element:"selector",url:"/basic/menu/selector",option:{text:"pText",value:"pid"}},
					            {title:"状态",field:"status",required:false,element:"text"},
					            {title:"描述",field:"remark",required:false,element:"text"},
					            {title:"SQL",field:"sql",required:false,element:"textarea"}
					            ]
			 	
			},
			
			columnList:[
			            {title:"SQLID",field:"sqlId",display:true,exdata:"",query:true},
			            {title:"别名",field:"sqlName",display:true,exdata:"",query:true},
			            {title:"SQL组",field:"pText",display:true,exdata:"",query:true},
			            {title:"数据源",field:"datasource",display:true,exdata:"",query:true},
			            {title:"缓存",field:"cache",display:true,exdata:"",query:true},
			            {title:"状态",field:"status",display:true,exdata:"",query:true},
			            {title:"描述",field:"remark",display:true,exdata:"",query:true},
			            {title:"SQL",field:"selectSql",display:true,exdata:"",query:true},
			            {title:"操作",field:""}
			            ]       
	}
	
	//0 行内按钮, 1 工具栏按钮
	$scope.buttonList = [
	             {title:"查看",icon:"ion-eye",target:"view",type:0},
	             {title:"编辑",icon:"ion-edit",target:"edit",type:0},
	             {title:"删除",icon:"ion-trash-a",target:"remove",type:0},
	             {title:"新增",icon:"",target:"edit",type:1}
	             ]  
	
	//tree config
	$scope.treeConfig ={
			setting:{
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
						$scope.parameter.treeNode=node;
						$scope.$broadcast($scope.grid.id);  
					}
				}
			}
	};

	
	//按钮工具栏
	$scope.toolbar = {
			id: "toolbar"+$.uuid(),

			//查询列
			inputList:[{label:"SQLID",field:"sqlid",element:"",value:"",expr:"",exdata:""},
			           {label:"别名",field:"sqlname",element:"",value:"",expr:"",exdata:""}]
	};
	
	$scope.parameter = {
			condition:$scope.toolbar.inputList
	};
});