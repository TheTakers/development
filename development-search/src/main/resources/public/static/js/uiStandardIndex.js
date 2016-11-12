/**
 * SQLVIEW统一入口
 */
app.directive('uiStandardIndex', function($http,$log,$ocLazyLoad,commonService,$uibModal) {
	
	var sqlViewIndexEdit = function(commonService,dataUrl,templateUrl,ctrl,param,callback,size) {
		
		/*根据选中ID获取最新数据*/
		commonService.post(dataUrl,param.row,function(data){

			if(data.code == STATUS_CODE.SUCCESS){

				//获取最新数据到编辑页
				_.extend(param, data.result);
				commonService.show({templateUrl:templateUrl,controller:ctrl,param,callback:callback,size:(size||40)});
			}else{
				$.error(data.message);
			}
		});
	};
	
	//子窗口 
	var editModalDialog = function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
		$scope.sqlView = param.sqlView;
		
		//页面数据
		if(param.row){
			$scope.data = param.row;
		}else{
			
			//把data当成一个数组处理,它支持使用类似于下标形式的方法来把属性和属性值赋给对象
			$scope.data = [];
			var columnList = $scope.sqlView.columnList;
			
			//初始化数据
			for(var idx in columnList){
				 
				if(columnList[idx].isInsert == CHECK_WHETHER_YES.value)
				$scope.data[columnList[idx].field] = null;
			}
		}
		
		//保存操作
		$scope.save = function() {
			var url = param.btn.url;
			if(_.isEmpty(url)){
				if(_.isEmpty(param.row)){
					url = 'search/sqlview/persistent/';
				}else{
					url = 'search/sqlview/modfity/';
				}
				url += $scope.sqlView.code;
			}
			saveOfClose($http,url,$scope.data,$uibModalInstance);
		};
		$scope.cancel = function(){
			$uibModalInstance.dismiss('cancel');
		};
		
		//是否类型
		$scope.isType = function(type,ctype){
			return _.isEqual(type, ctype);
		}
		
		//显示需要编辑字段
		$scope.isEdit = function(field){
			if(_.isEmpty(param.row)){
				return   _.isEqual(field.isInsert, CHECK_WHETHER_YES.value);
			}else{
				return  _.isEqual(field.isUpdate, CHECK_WHETHER_YES.value);
			}
		}
		
		//是否查看
		$scope.isSearch = function(field,ctype){
			return _.isEqual(field.isSearch, CHECK_WHETHER_YES.value);
		}
		
		//字段列表
		$scope.fieldList = param.sqlView.columnList;
	};
	
	//设置树参数
	var initTree = function(scope,treeConfig){
		if(_.isUndefined(treeConfig)){
			return;
		}
		return {
			isShow:treeConfig.isShow,
			setting:{
				async:{
					url:"search/sqldefine/listAll/" + treeConfig.sqlId,
					type:"post",
					contentType: "application/json",
					enable:true
				},
				data:{
					simpleData:{
						enable: true, //不需要用户再把数据库中取出的 List 强行转换为复杂的 JSON 嵌套格式
						idKey: treeConfig.idKey,
						pIdKey: treeConfig.pIdKey,
						rootPId: treeConfig.rootPId
					},
					key:{
						name:treeConfig.name
					}
				},
				callback: {
					onClick: function(event,treeId,node,idx){
						scope.parameter.treeNode=node;
						scope.$broadcast(scope.grid.id);  
					}
				}
			}
		};
	};
	return {
		restrict:'E',
		templateUrl:"templates/basic/directive/uiStandardIndexTpl.html",
		replace : false,			
		transclude : false,
		scope:{
			url:"@",
			param:"=",
			returndata:"="
		},
		compile: function compile(tElement, tAttrs, transclude) {
			return {
				pre: function preLink(scope, iElement, iAttrs, controller) {
					
					//工具栏
					scope.toolbar = {id:$.uuid()};
					
					//请求参数
					scope.parameter = $.extend({id:$.uuid()},scope.param);
					commonService.ajax({url:scope.url,success:function success(data){
						scope.sqlView = data.result;
						if(data.code == STATUS_CODE.SUCCESS){
							scope.grid = {
									id:$.uuid(),
									
									//table展示的数据
									dataList:{}, 
									
									//条件查询
									search:function(){
										scope.$broadcast(scope.grid.id);  
									},
									url:'search/sqlview/findAll/'+scope.sqlView.code
							};
							scope.sqlView.fieldData = scope.sqlView.columnList;
							scope.treeconfig = initTree(scope,JSON.parse(scope.sqlView.treeData));
							scope.sqlView.filterData = eval(scope.sqlView.conditions);
							scope.sqlView.buttonData = JSON.parse(scope.sqlView.buttons);
							//查询参数
							scope.parameter = {
									condition:scope.sqlView.filterData
							};
						}
					},type:"post",async:false});
					
					//选中列表
					var checkedData= [];
					scope.rowClick = function(item,option){
						scope.returndata.option = option;
						
						//单选
						if(_.isEqual(GRID_OPTIONS.SINGLE, option)){
							checkedData[0] = item; 
						}else{
							updateCheckBox(checkedData,item);
						}
						scope.returndata.data = checkedData;
					}
					
					//是否显示
					scope.isdisplay = function(field){
						return _.isEqual(field.isDisplay, CHECK_WHETHER_YES.value);
					}
					
					scope.crud = function(item,btn){
						switch(btn.id){
						case CRUD_CODE.INSERT: //增
							sqlViewIndexEdit(commonService,'search/sqlview/getSqlViewByCode/'+scope.sqlView.code,'templates/basic/directive/uiStandardEditTpl.html',editModalDialog,{row:item,btn},scope.grid.search,btn.winSize);
							break;
						case CRUD_CODE.UPDATE://修
							sqlViewIndexEdit(commonService,'search/sqlview/getSqlViewAndSqlDefineRowDataByCode/'+scope.sqlView.code,'templates/basic/directive/uiStandardEditTpl.html',editModalDialog,{row:item,btn},scope.grid.search,btn.winSize);
							break;
						case CRUD_CODE.DELETE://删
							remove(commonService,'search/sqlview/delete/'+scope.sqlView.code ,item,scope.grid.search);
							break;
						case CRUD_CODE.VIEW://查
							sqlViewIndexEdit(commonService,'search/sqlview/getSqlViewAndSqlDefineRowDataByCode/'+scope.sqlView.code,'templates/basic/directive/uiView.html',editModalDialog,{row:item,btn},scope.grid.search,btn.winSize);
							break;
						default :
							
							//自定义弹窗页面
							if(btn.showWin == CHECK_WHETHER_YES.value){
								var options = {templateUrl:btn.url,controller:btn.ctrl,param:item,callback:scope.grid.search,size:btn.winSize,loadjs:btn.loadjs};
								commonService.show(options);
							}else{
								action(commonService,btn.url,{row:item},scope.grid.search);
							}
						}
					}
					
					//判断是否显示树
					scope.showtree = function(){
						return _.isEqual(scope.treeconfig.isShow,CHECK_WHETHER_YES.value);
					}
					
					//功能树宽度
					var treeData = JSON.parse(scope.sqlView.treeData);
					if(_.isEqual(treeData.isShow, CHECK_WHETHER_YES.value)){
						scope.treeWidth = treeData.width;
						scope.gridWidth = 12 - scope.treeWidth;
					}else{
						scope.gridWidth = 12;
					}
				} 
			}
		}
	};
});