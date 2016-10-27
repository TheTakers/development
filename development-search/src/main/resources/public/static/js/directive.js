/**分页组件**/

/*
appModule.directive('hello', function() {
	 return {

		    E(元素)：<directiveName></directiveName>
			A(属性)：<div directiveName='expression'></div>
			C(类)：   <div class='directiveName'></div>
			M(注释)：<--directive:directiveName expression-->

	         restrict : 'E',
	         priority:								 //设置模板中相对于其他标识符的执行顺序，如angularjs内置指令的ng-repeat的优先级为1000，ng-init的优先级为450；
	         template:								 //指定一个字符串内嵌模板（字符串或者函数）函数可接受两个参数tElement和tAttrs其中tElement是指使用此指令的元素，而tAttrs则实例的属性，它是一个由元素上所有的属性组成的集合（对象）
	         templateUrl : 'expanderTemp.html', 	 //指定URL加载模板,如果已经指定了内嵌模板字符串就不会加载此项
	         replace : true,						 //如果true，替换当前元素,默认false拼接到当前元素
	         transclude : true,						 //移动一个ng-transclude标识符的原始字节带到新的模板位置
	         scrope:{} 								 //创建一个新的作用域,而不是继承父级作用域
	         controller:							 //创建一个控制器通过标识公开通信API
	         require:         						 //当前标识需要另一个标识提供正确的函数功能
	         compile:                                //通过标识符拷贝编程修改DOM模板
	         link : function(scope,element,attris){} //通过代码修改目标DOM元素的实例,添加时间监听 ,建立数据绑定 element指引用该指令的所有元素
         }
     };
 */


/**
 * <pagination data="{{dataList}}" ></pagination> 列表数据
 * click event trigger query
 **/
app.directive('pagination', function($http,$log,commonService) {
	return {
		restrict:'E',
		template:function(element,atts){
			return  "<div class='fixed-table-pagination' ng-if='limit.code.length'>"+
			"<div class='pull-left pagination-stats'>"+
			"	<div class='pagecount'><span>共{{limit.pageCount}}条 / {{limit.totalPageNum}}页</span></div>"+
			"	<div class='pagesize'>"+
			"		 <select ng-change='setPageSize(selected)' ng-model='selected' ng-options='x.id as x.value for x in options' class='form-control input-sm' ></select>"+
			"	</div>"+
			"</div>"+
			"<div class='pull-left pagination-detail'></div>"+
			"<div class='pull-right pagination'>"+
			"<ul class='pagination'>"+
			"<li class='{{item.classs}}' ng-repeat='item in limit.code' ng-click='go(item.value)'><a href='javascript:void(0)'>{{item.label}}</a></li>"+
			"</ul>"+
			"</div>"+
			"</div>";
		},
		scope:{
			id:"@",
			data:"=",
			url:"@",
			params:"="
		},
		transclude : false,
		link : function(scope,element,attr){
			//create Pagination
			function createLimit(limit){
				//计算总页数
				var totalPageNum = Math.floor((limit.pageCount  +  limit.pageSize - 1) / limit.pageSize);  
				limit.totalPageNum = totalPageNum;
				//开始页码计算 
				var startPage = limit.pageNo - 5 < 1 ? 1 : limit.pageNo - 5;
				//结束页码
				var endPage = (limit.pageNo + 4 < 10 ? 10 : limit.pageNo + 4) > totalPageNum ? totalPageNum : (limit.pageNo + 4 < 10 ? 10 : limit.pageNo + 4);
				//number
				var code = [];
				//page first
				code.push({classs:'page-first' + (limit.pageNo == 1 ? ' disabled' :''),value:1,label:'«'});
				//page pre
				code.push({classs:'page-pre' + (limit.pageNo == 1 ? ' disabled' :'') ,value:limit.pageNo-1,label:'‹'});
				// class pageno 
				for(var idx = startPage ; idx <= endPage ; idx++){
					if(idx == limit.pageNo)
						code.push({classs:'page-number active',value:idx,label:idx});
					else
						code.push({classs:'page-number',value:idx,label:idx}); 
				}
				//page next
				code.push({classs:'page-next' + (limit.pageNo == totalPageNum ? ' disabled' :''),value:limit.pageNo+1,label:'›'});
				//page last
				code.push({classs:'page-last' + (limit.pageNo == totalPageNum ? ' disabled' :''),value:totalPageNum,label:'»'});
				scope.limit.code = code;
			}
			//default
			scope.selected = 10;
			//define pagination
			scope.limit = {pageSize:scope.selected,pageNo:1};
			//get data
			function post(){
				if(!_.isEmpty(scope.url)){
					commonService.post(scope.url,_.extend({pageSize:scope.limit.pageSize,pageNo:scope.limit.pageNo},scope.params),function(data){
						if(_.isUndefined(data.result)){
							$log.error("结果集不包含result");
							return;
						}
						scope.limit.pageCount = data.result.totalElements;
						scope.data = data.result.content;
						createLimit(scope.limit);
					});
				}
			}
			//initialize
			post();
			//catch parent broadcast goto
			scope.$on(scope.id, function(d,data) {  
				post();
			});  
			scope.go = function(v){
				//check data
				if(v < 1 || v > scope.limit.totalPageNum){
					return;
				}
				scope.limit.pageNo = v;
				post();
			};
			//num of page
			scope.options = [{id:10,value:10}, {id:20,value:20}, {id:30,value:30},
			                 {id:40,value:40}, {id:50,value:50} ];
			scope.setPageSize = function(option){
				scope.limit.pageSize = option;
				scope.limit.pageNo = 1;
				post();
			};
		}
	};
}); 

//ztree
app.directive('uitree', function($http,$log) {
	return {
		restrict:'E',
		scope:{
			setting:'=',
			znodes:'=',
			callback:'&'
		},
		template:function(element,atts){

			return  '<ul class="ztree" id="'+$.uuid()+'"></ul>';
		},
		replace : true,			
		transclude : false,
		link:function(scope,element,attr){

			var ztree = $.fn.zTree.init($(element), scope.setting, scope.znodes);

			if(scope.callback){
				scope.callback({ztree:ztree});
			}
		}
	};
}); 



//page
app.directive('uibasepage', function($http,$log,$ocLazyLoad,commonService,$uibModal) {
	
	//子窗口 
	var modalDialog = function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
		//页面数据
		$scope.data = param.formData;
		//保存操作
		$scope.save = function() {
			saveOfClose($http,param.modelView.controller + "/save",$scope.data,$uibModalInstance);
		};
		$scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');
		};
		$scope.isType = function(type,ctype){
			return _.isEqual(type, ctype);
		}
		//字段列表
		$scope.fieldList = param.modelView.fieldSetting;
	}
	return {
		restrict:'E',
		templateUrl:"/basic/directive/index",
		replace : false,			
		transclude : false,
		scope:{
			point:"@",
			param:"=",
			returndata:"="
		},
		compile: function compile(tElement, tAttrs, transclude) {
			return {
				pre: function preLink(scope, iElement, iAttrs, controller) {

					//设置树参数
					function initTree(treeConfig){

						if(_.isUndefined(treeConfig)){
							return;
						}

						return {
							setting:{
								async:{
									url:treeConfig.url,
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
					}

					//工具栏
					scope.toolbar = {id:$.uuid()};

					//请求参数
					scope.parameter = $.extend({id:$.uuid()},scope.param);
					function success(data){
						data = eval('(' + data + ')');
						scope.modelView = data;
						scope.grid = {
								id:$.uuid(),
								//table展示的数据
								dataList:{}, 
								//查询
								search:function(){
									scope.$broadcast(this.id);  
								},
								url:data.controller + '/list',
								fieldData:data.fieldData
						};
						scope.treeconfig = initTree(data.treeData);

						//查询参数
						scope.parameter = {
								condition:scope.modelView.filterData
						};
					}
					commonService.ajax({url:scope.point,success:success,type:"get",dataType:"text",async:false});

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

					scope.crud = function crud(item,func){
						switch(func.target){
						case "edit":
							item = item || {id:""};
							edit(commonService,scope.modelView.controller + '/findById','templates/basic/directive/edit.html',modalDialog,{id:item.id,modelView:scope.modelView},scope.grid.search,60);
							break;

						case "remove":
							remove(commonService,scope.modelView.controller + '/delete',{id:item.id},scope.search);
							break;

						case "view":

							break;

						default :
							item = item || {id:""};
							edit(commonService,scope.modelView.controller + '/findById',func.url,modalDialog,{id:item.id,modelView:scope.modelView},scope.grid.search);
							break;
						}
					}

					//判断是否显示树
					scope.findtreeconfig = function(){
						return _.isUndefined(scope.treeconfig);
					}
				} 
			}
		}
	};
});

/**
 * SQLVIEW统一入口
 */
app.directive('uiviewindex', function($http,$log,$ocLazyLoad,commonService,$uibModal) {
	
	//子窗口 
	var editModalDialog = function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
		//页面数据
		$scope.data = param.formData;
		
		//保存操作
		$scope.save = function() {
			saveOfClose($http,param.btn.url + "/save",$scope.data,$uibModalInstance);
		};
		$scope.cancel = function(){
			$uibModalInstance.dismiss('cancel');
		};
		
		//是否类型
		$scope.isType = function(type,ctype){
			return _.isEqual(type, ctype);
		}
		//显示需要编辑字段
		$scope.isShow = function(field){
			if(param.formData){
				return   _.isEqual(field.isInsert, CHECK_WHETHER_YES.value);
			}else{
				return  _.isEqual(field.isUpdate, CHECK_WHETHER_NO.value);
			}
		}
		
		//是否查看
		$scope.isSearch = function(field,ctype){
			if(param.formData){
				return _.isEqual(field.isInsert, CHECK_WHETHER_YES.value);
			}else{
				return _.isEqual(field.isUpdate, CHECK_WHETHER_NO.value);
			}
		}
		//字段列表
		$scope.fieldList = param.modelView.columnList;
	};
	
	//设置树参数
	var initTree = function(scope,treeConfig){
		if(_.isUndefined(treeConfig)){
			return;
		}
		return {
			setting:{
				async:{
					url:treeConfig.url,
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
		templateUrl:"/basic/directive/index",
		replace : false,			
		transclude : false,
		scope:{
			point:"@",
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
					commonService.ajax({url:scope.point,success:function success(data){
						scope.modelView = data.result;
						if(data.code == STATUS_CODE.SUCCESS){
							scope.grid = {
									id:$.uuid(),
									//table展示的数据
									dataList:{}, 
									//查询
									search:function(){
										scope.$broadcast(this.id);  
									},
									url:'search/sqldefine/list/'+scope.modelView.sqlId
							};
							scope.modelView.fieldData = scope.modelView.columnList;
							scope.treeconfig = initTree(scope,JSON.parse(scope.modelView.treeData));
							scope.modelView.filterData = eval(scope.modelView.conditions);
							scope.modelView.buttonData = JSON.parse(scope.modelView.buttons);
							//查询参数
							scope.parameter = {
									condition:scope.filterData
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
					
					scope.crud = function crud(item,btn){
						switch(btn.id){
						case CRUD_CODE.INSERT: //增
						case CRUD_CODE.UPDATE://修
							uiEdit(commonService,'search/sqlview/findBySqlId/'+scope.modelView.sqlId,'templates/basic/directive/uiEdit.html',editModalDialog,{item:item,modelView:scope.modelView,btn},scope.grid.search,btn.winSize);
							break;
						case CRUD_CODE.DELETE://删
							remove(commonService,'search/sqlview/delete/'+scope.modelView.sqlId+'/'+item.id ,{id:item.id},scope.search);
							break;
						case CRUD_CODE.VIEW://查
							uiEdit(commonService,'search/sqlview/findBySqlId/'+scope.modelView.sqlId,'templates/basic/directive/uiView.html',editModalDialog,{item:item,modelView:scope.modelView,btn},scope.grid.search,btn.winSize);
							break;
						default :
						}
					}
					
					//判断是否显示树
					scope.findtreeconfig = function(){
						return _.isEmpty(scope.treeconfig.isShow,OPTION_WHETHER[0].value) ;
					}
				} 
			}
		}
	};
});

app.directive('uisqlview', function($http,$log,$ocLazyLoad,commonService,$uibModal) {
	
	/*新增初始化*/
	var init = function(data){
		if(data.id){
			return data;
		}
		return {
			multiple :0,
			showRowNum:0,
			name:"",
			code:"",
			remark:"",
			sqlId:"",
			fieldList : [],
			columnList : [],
			conditions : "[]",
			buttons : "[]"
		};
	};
	
	//子窗口 
	var modalDialog = function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
		$scope.optionData = OPTION_WHETHER;
		
		//基本信息
		$scope.data = init(param.formData);
		//修改字段
		$scope.fieldList = param.modelView.fieldSetting;
		//TODO SQL字段
		$scope.columnList = $scope.data.columnList;
		//TODO 过滤条件
		$scope.filterList = eval($scope.data.conditions);
		//TODO 按钮设置
		$scope.buttonList = eval($scope.data.buttons);
		
		//保存操作
		$scope.save = function() {
			$scope.data.columnList = $scope.columnList;
			$scope.data.filterList = $scope.filterList;
			$scope.data.buttonList = $scope.buttonList;
			$scope.data.treeData = $scope.treeData;
			saveOfClose($http,param.modelView.controller + "/save",$scope.data,$uibModalInstance);
		};
		$scope.cancel = function() {
			$uibModalInstance.dismiss('cancel');
		};
		/**=======================过滤设置================================================**/
		$scope.ctype = DICT_COMPONENTTYPE;
		$scope.expr = DICT_EXPRESSION;
		$scope.isType = function(type,ctype){
			return _.isEqual(type, ctype);
		}
		//选中条件
		$scope.addFilterItem = function(item){
			var opt = {};
			opt.title=item.title
			opt.field = item.field;
			opt.dataType = item.dataType;
			opt.componentType=DICT_COMPONENTTYPE[0].value;
			opt.expr = DICT_EXPRESSION[0].value;
			opt.isSort = item.isSort;
			opt.expand = item.expand;
			$scope.filterList.push(opt);	
		}
		//移除选项
		$scope.removeItem=function(item){
			$scope.filterList.splice(_.findIndex($scope.filterList, item),1);
		}
		/**=======================生成列表================================================**/
		//验证规则
		$scope.checkRule = function(item){
			commonService.show({template:' <div class="modal-header"><h8 class="modal-title">验证规则</h6></div><div class="card-box" style="margin-top:10px;">'+
				'<div class="checkbox checkbox-primary" ng-repeat="regx in keys">'+
				'<input type="checkbox" id="{{regx}}"  ng-click="checked(regx)" ng-checked="isChecked(regx)" ></input>'+
				'<label for="{{regx}}">{{REGULAR_EXPRESSION[regx].tip}}</label></div></div>',
				/**'<div class="modal-footer"><button type="button" class="btn btn-default waves-effect" data-dismiss="modal"  ng-click="cancel()">取消</button>'+
				'<button type="button" class="btn btn-info waves-effect waves-light" ng-click="ok()">确定</button></div>'**/
				controller:function($scope,$http,$uibModal,$log,$uibModalInstance,param) {
					$scope.REGULAR_EXPRESSION = REGULAR_EXPRESSION;
					$scope.keys = _.keys(REGULAR_EXPRESSION);
					var rule = eval(item.rule);
					$scope.isChecked = function(regx){
						return rule.indexOf(regx) > -1;
					}
					$scope.checked = function(regx){
						updateCheckBox(rule,regx);
						item.rule = JSON.stringify(rule);
					}
				},
				param:{item:item},
				callback:function(){
					
				}});
			
		}
		
		//生成列表
		$scope.createFieldData = function(){
			$.confirm({
				confirm: function(){
					commonService.ajax({url:"/search/sqlview/createField",data:JSON.stringify({sqlId:$scope.data.sqlId}),success:function(data){
						if(STATUS_CODE.SUCCESS == data.code){
							$scope.columnList = data.result;
							
							//消化
							$scope.$digest();
						}else{
							$.error(data.message);
						}
					}});
				} 
			});
		}
		/**=======================按钮设置================================================**/
		$scope.btype = OPTION_BUTTON;
		$scope.winSize=WIN_SIZE;
		
		//添加按钮 
		$scope.createButton = function(){
			var item = {};
			item.id = $.uuid();
			item.title="";
			item.icon="";
			item.type= 0;
			item.url="";
			item.showWin= 0;
			item.winSize= "40";
			$scope.buttonList.push(item);	
		}
		$scope.removeButton = function(item){
			$scope.buttonList.splice(getArrayIdxById($scope.buttonList,item),1);
		}
		
		//增、删、改、查
		$scope.insert = {id:CRUD_CODE.INSERT,title:"增加",icon:"",type:0,url:"",showWin:1,winSize:"50"};
		$scope.update = {id:CRUD_CODE.UPDATE,title:"修改",icon:"ion-edit",type:1,url:"",showWin:1,winSize:"50"};
		$scope.remove = {id:CRUD_CODE.DELETE,title:"删除",icon:"ion-trash-a",type:1,url:"",showWin:0,winSize:""};
		$scope.view   = {id:CRUD_CODE.VIEW,title:"查看",icon:"ion-eye",type:1,url:"",showWin:1,winSize:"50"};
		
		$scope.crudCheck = function(item){
			var idx = getArrayIdxById($scope.buttonList,item);
			if(idx > -1){
				$scope.buttonList.splice(idx,1);
			}else{
				$scope.buttonList.push(item);	
			}
		}
		$scope.isChecked = function(item){
			return getArrayIdxById($scope.buttonList,item) > -1;
		}
		/**=======================树设置====================================**/
		//功能树形
		$scope.treeData = {
			url:"",
			idKey:"id",
			pIdKey: "parentId",
			rootPId: 0,
			isShow:0
		};
		if($scope.data.treeData){
			$scope.treeData =  JSON.parse($scope.data.treeData);
		}
		
		//树功能必填设置
		$scope.treeRequiredClass = "";
		$scope.treeRequired = function(){
			if($scope.treeData.isShow == 0){
				$scope.treeRequiredClass = "";
			}else{
				$scope.treeRequiredClass = "ng-required";
			}
			return !_.isEmpty($scope.treeRequiredClass);
		}
	}
	
	//列表记录弹窗
	function sqlViewEdit(commonService,dataUrl,templateUrl,ctrl,param,callback,size) {
		
		if(param.id){
			/*根据选中ID获取最新数据*/
			commonService.post(dataUrl,param,function(data){
				
				if(data.code == STATUS_CODE.SUCCESS){
					param.formData = data.result;
					commonService.show({templateUrl:templateUrl,controller:ctrl,param,callback:callback,size:(size||40)});
				}else{
					$.error(data.message);
				}
			});
			
		}else{
			commonService.show({templateUrl:templateUrl,controller:ctrl,param,callback:callback,size:(size||40)});
		}
	};
	
	return {
		restrict:'E',
		templateUrl:"/basic/directive/index",
		replace : false,			
		transclude : false,
		scope:{ 
			point:"@",
			param:"=",
			returndata:"="
		},
		compile: function compile(tElement, tAttrs, transclude) {
			return {
				pre: function preLink(scope, iElement, iAttrs, controller) {

					//设置树参数
					function initTree(treeConfig){

						if(_.isUndefined(treeConfig)){
							return;
						}

						return {
							setting:{
								async:{
									url:treeConfig.url,
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
					}

					//工具栏
					scope.toolbar = {id:$.uuid()};

					//请求参数
					scope.parameter = $.extend({id:$.uuid()},scope.param);
					function success(data){
						data = eval('(' + data + ')');
						scope.modelView = data;
						scope.grid = {
								id:$.uuid(),
								dataList:{}, 
								search:function(){
									scope.$broadcast(this.id);  
								},
								url:data.controller + '/list',
								fieldData:data.fieldData
						};
						scope.treeconfig = initTree(data.treeData);

						//查询参数
						scope.parameter = {
								condition:scope.modelView.filterData
						};
					}
					commonService.ajax({url:scope.point,success:success,type:"get",dataType:"text",async:false});

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

					scope.crud = function crud(item,func){
						switch(func.id){
						case CRUD_CODE.INSERT:
						case CRUD_CODE.UPDATE:
							item = item || {id:""};
							sqlViewEdit(commonService,scope.modelView.controller + '/findById',func.url,modalDialog,{id:item.id,modelView:scope.modelView},scope.grid.search,100);
							break;
						case CRUD_CODE.DELETE://删除
							remove(commonService,scope.modelView.controller + '/delete',{id:item.id},scope.search);
							break;

						case CRUD_CODE.VIEW: //查
							break;
						}
					}
					//判断是否显示树
					scope.findtreeconfig = function(){
						return _.isUndefined(scope.treeconfig);
					}
				} 
			}
		}
	};
});

//tabs
app.directive('uitab', function($http,$log) {

	return {
		restrict:'E',
		scope:{
			data:"=",//tab数据
			selected:"="//选中tab
		},
		template:function(element,atts){
			return  '<div  ng-if="data.length > 0">'+
			' <ul class="nav nav-tabs" id="{{id}}">'+

			' 	<li class="dropdown pull-right tabdrop">'+
			' 	<a class="dropdown-toggle" data-toggle="dropdown" href="#" >'+
			'    <i class="glyphicon glyphicon-align-justify"></i><b=class="caret"></b></a>'+
			'    <ul class="dropdown-menu">'+
			'    <li ><a href="javascript:void(0);" ng-repeat="item in data" ng-click="setSelected(item.id)"  >{{item.name}}</a></li></ul></li>'+

			'<li role="presentation" ng-class="{true: \'active\', false: \'\'}[isSelected(item.id)]" ng-repeat="item in data" ng-mouseover="mouseover(item.id)" ng-mouseleave="mouseleave(item.id)" ng-click="click(item.id)" >'+
			'<a  href="javascript:void(0);" data-target="#{{item.id}}"  data-toggle="tab">'+
			'<span class="hidden-xs">{{item.name}}</span>'+
			'</a>'+
			'<i class="close-tab glyphicon glyphicon-remove" ng-if="item.id == focusId" ng-click="closed(item)"></i>'+
			'</li>       '+             
			'</ul>'+
			'<div class="tab-content">'+
			'<div role="tabpanel" ng-class="{true: \'tab-pane active\', false: \'tab-pane\'}[isSelected(item.id)]"  ng-repeat="item in data" id="{{item.id}}" ><div class="shade" id="shade{{item.id}}"><div class="spinner"><div class="cube1"></div><div class="cube2"></div></div></div><ng-include src="item.link"></ng-include></div>   '+                 
			'</div>'+
			'</div>	';
		},
		replace : false,			
		transclude : false,
		link:function(scope,element,attr){

			scope.id = 'tab_'+$.uuid();

			//tabs焦点
			scope.focusId;

			scope.isSelected = function(id){
				return _.isEqual(scope.selected, id);
			}

			scope.mouseover = function(itemId){
				scope.focusId = itemId;
			}

			scope.mouseleave = function(itemId){
				scope.focusId = '';
			}

			scope.setSelected = function(id){

				//激活选中tab
				$('#'+scope.id+' a[data-target="#'+id+'"]').tab('show');
			}

			//点击事件更新selected
			scope.click = function(id){
				scope.selected = id;
			}

			scope.$watch('selected',function(newValue,oldeValue){

				//TODO 解耦
				$.shadeId = newValue;

				//监听选中tab变更,清除所有激活样式避免激活多个tab -> 这里会重新触发ng-class isSelected方法
				$('li[role = "presentation"].active').removeClass('active'); 
				$('div[role = "tabpanel"].active').removeClass('active');
			})

			scope.closed = function(item){
				scope.mouseleave();
				var idx = _.findIndex(scope.data, item);
				if(idx > -1){
					scope.data.splice(idx,1);
					if(!_.isEmpty(scope.data)){

						if(_.isEqual(scope.selected, item.id)){

							//直接激活最后一个tab
							$('#'+scope.id+' a[data-target="#'+_.last(scope.data).id+'"]').tab('show');
							scope.selected=_.last(scope.data).id;
						}
					}
				}
			}
		}
	};
})

app.directive('onFinishRenderFilters', function ($timeout) {
	return {
		restrict: 'A',
		link: function(scope, element, attr) {
			if (scope.$last === true) {
				$timeout(function() {
					scope.$emit('ngRepeatFinished');
				});
			}
		}
	};
});