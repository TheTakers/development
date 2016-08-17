angular.module('app').controller('sqlGroupCtrl', function($scope,$http,$uibModal,$log,commonService) {
	
	//grid
	$scope.grid = {
			id:$.uuid(),
			url:'/search/sqlgroup/list',

			//table展示的数据
			dataList:{}, 

			//查询
			search:function(){
				$scope.$broadcast(this.id);  
			},
			
			action:"/search/sqlgroup",
			
			editCtrl:function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
				
				//页面数据
				$scope.data = param;
				
			 	$scope.save = function() {
			 		saveOfClose($http,this.action + '/save',$scope.data,$uibModalInstance);
			 	};
			 	
			 	$scope.cancel = function() {
			 		 $uibModalInstance.dismiss('cancel');
			 	};
			 	
			 	
			 	//字段列表
			 	$scope.fieldList = [
					            {title:"SQLID",field:"code",required:true,element:"uigeneratecode"},
					            {title:"别名",field:"name",required:false,element:"text"},
					            {title:"所属分组",field:"parent_id",required:false,element:"selector",url:"/search/sqlgroup/selector",option:{text:"pText",value:"parent_id"}},
					            {title:"状态",field:"status",required:false,element:"text"},
					            {title:"描述",field:"remark",required:false,element:"text"}
					            ]
			 	
			},
			
			columnList:[
			            {title:"编号",field:"code",display:true,exdata:"",query:true},
			            {title:"别名",field:"name",display:true,exdata:"",query:true},
			            {title:"所属分组",field:"pText",display:true,exdata:"",query:true},
			            {title:"路径",field:"path",display:true,exdata:"",query:true},
			            {title:"备注",field:"remark",display:true,exdata:"",query:true},
			            {title:"操作",field:""}
			            ]         
	}
	
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

	//0 行内按钮, 1 工具栏按钮
	$scope.buttonList = [
	             {title:"查看",icon:"ion-eye",target:"view",type:0},
	             {title:"编辑",icon:"ion-edit",target:"edit",type:0},
	             {title:"删除",icon:"ion-trash-a",target:"remove",type:0},
	             {title:"新增",icon:"",target:"edit",type:1}
	             ]  
	
	//按钮工具栏
	$scope.toolbar = {
			id: "toolbar"+$.uuid(),
			
			//查询列
			inputList:[{label:"编号",field:"code",element:"",value:"",expr:"like",exdata:""},
			           {label:"别名",field:"name",element:"",value:"",expr:"like",exdata:""}]
	};
	
	$scope.parameter = {
			condition:$scope.toolbar.inputList
	};
});