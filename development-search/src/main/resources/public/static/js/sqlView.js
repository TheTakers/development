//视图配置功能
app.directive('uisqlview', function($http,$log,$ocLazyLoad,commonService,$uibModal) {
	
	/*新增初始化*/
	var init = function(data){
		if(data){
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
		$scope.data = init(param.row);
		//修改字段
		$scope.fieldList = param.sqlView.fieldSetting;
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
			saveOfClose($http,param.sqlView.controller + "/save",$scope.data,$uibModalInstance);
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
		
		//移除选项
		$scope.removeField=function(item){
			$scope.columnList.splice(_.findIndex($scope.columnList, item),1);
		}
		
		//是否显示文本编辑器
		$scope.turnon = function(item){
			return item.componentType == 'VIEWSELECTOR';
		}
		
		//参数设置弹出框
		$scope.sqlSelectParamCtrl = function($scope,$http,$uibModal,$log,$uibModalInstance,commonService,param){
			
			//视图所有列
			$scope.columnList =  param.param;
			if(param.data.expand){
				$scope.expand = JSON.parse(param.data.expand);
			}else{
				$scope.expand={
					code:"",
					mappingList:[{
						valueKey:param.data.field, //数据键值
						textValue:"",//对应返回文本值
						icon:"md-add"
					}]
				};
			}
		
		//增减参数
		$scope.adRecord = function(idx){
				
				//最后一条增加数据
				if($scope.expand.mappingList.length - 1 == idx){
					
					//往第一行插入数据
					$scope.expand.mappingList.splice(0,0,{
							valueKey:"",
							textValue:"",
							icon:"md-remove"});
				}else{
					
					//减去
					$scope.expand.mappingList.splice(idx,1);
				}
			}
			
			//选择器ok按钮
			$scope.ok = function() {
				param.data.expand = JSON.stringify($scope.expand);
				$uibModalInstance.close($scope.expand);
			};

			//取消
			$scope.cancel = function() {
				$uibModalInstance.dismiss('cancel');
			};
			
			//监控SQLID变化
			$scope.$watch('expand.code',function(newValue,oldValue, scope){
				commonService.getFieldListBySqlId(newValue,function(response){
					if(response.code == STATUS_CODE.SUCCESS){
						$scope.responseList = response.result;
						
						//消化
						$scope.$digest();
					}
				});
			});
		}
		
		/**=======================按钮设置=======================================================================================================================**/
		$scope.btype = OPTION_BUTTON;
		$scope.winSize=WIN_SIZE;
		
		//添加按钮 
		$scope.createButton = function(){
			var item = {};
			item.id = $.uuid();
			item.title="";
			item.icon="md-add";
			item.type= 0;
			item.lazyurl = "";
			item.url = "";
			item.ctrl = "";
			item.showWin= 0;
			item.winSize= 40;
			item.idx = $scope.buttonList.length + 1;
			$scope.buttonList.push(item);	
		}
		$scope.removeButton = function(item){
			$scope.buttonList.splice(item,1);
		}
		
		//增、删、改、查
		$scope.insert = {id:CRUD_CODE.INSERT,title:"增加",icon:"md-add",type:1,url:"",showWin:1,winSize:50};
		$scope.update = {id:CRUD_CODE.UPDATE,title:"修改",icon:"ion-edit",type:0,url:"",showWin:1,winSize:50};
		$scope.remove = {id:CRUD_CODE.DELETE,title:"删除",icon:"ion-trash-a",type:0,url:"",showWin:0,winSize:""};
		$scope.view   = {id:CRUD_CODE.VIEW,title:"查看",icon:"ion-eye",type:0,url:"",showWin:1,winSize:50};
		
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
		
		//图标选择控制器
		var checkedIcon = function($scope,$http,$uibModal,$log,$uibModalInstance,param){
			$scope.ok=function($event){
				param.icon = $($event.target).attr("class");
				
				//关闭图标
				$uibModalInstance.close();
			}
		}
		
		//选择图标
		$scope.checkedIcon = function(item){
			var options = {templateUrl:"/templates/search/sqlview/iconSelector.html",controller:checkedIcon,
					param:item,
					size:80};
			commonService.show(options);
		}
		/**=======================树设置====================================**/
		$scope.nodeOpts = TREE_OPTIONS;
		
		//功能树形
		$scope.treeData = {
			url:"", //默认树形统一接口
			idKey:"",
			name:"",
			pIdKey: "",
			rootPId: null, //TODO 跟节点默认为null是否有问题?
			isShow:0,
			nodeOpts:'ALL',
			width:2,
			sqlId:'',
			relationField:''
		};
		if($scope.data.treeData){
			$scope.treeData =  JSON.parse($scope.data.treeData);
		}
		
		//初始化
		$scope.verifyRule = $scope.treeData.isShow == 1 ? "[10001]" : "[]"; 
		
		//校验必填
		$scope.treeRequired = function(){
			$scope.verifyRule = $scope.treeData.isShow == 0 ? "[10001]" : "[]"; 
		}
		
		//监控SQLID变化
		$scope.$watch('treeData.sqlId',function(newValue,oldValue, scope){
			commonService.getFieldListBySqlId(newValue,function(response){
				if(response.code == STATUS_CODE.SUCCESS){
					$scope.treeFieldList = response.result;
					
					//消化
					$scope.$digest();
				}
			});
		});
		 
	}
	
	//列表记录弹窗
	function baseIndexEdit(commonService,dataUrl,templateUrl,ctrl,param,callback,size) {
		
		if(param.id){
			/*根据选中ID获取最新数据*/
			commonService.post(dataUrl,param,function(data){
				
				if(data.code == STATUS_CODE.SUCCESS){
					param.row = data.result;
					commonService.show({templateUrl:templateUrl,controller:ctrl,param:param,callback:callback,size:(size||40)});
				}else{
					$.error(data.message);
				}
			});
			
		}else{
			commonService.show({templateUrl:templateUrl,controller:ctrl,param:param,callback:callback,size:(size||40)});
		}
	};
	
	return {
		restrict:'E',
		templateUrl:"templates/basic/directive/index.html",
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
						scope.sqlView = data;
						scope.grid = {
								id:$.uuid(),
								dataList:{}, 
								search:function(){
									scope.$broadcast(scope.grid.id);  
								},
								url:data.controller + '/list',
								fieldData:data.fieldData
						};
						scope.treeconfig = initTree(data.treeData);

						//查询参数
						scope.parameter = {
								condition:scope.sqlView.filterData
						};
					}
					commonService.ajax({url:scope.url,success:success,type:"get",dataType:"text",async:false});

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
							baseIndexEdit(commonService,scope.sqlView.controller + '/findById',func.url,modalDialog,{id:item.id,sqlView:scope.sqlView},scope.grid.search,80);
							break;
						case CRUD_CODE.DELETE://删除
							remove(commonService,scope.sqlView.controller + '/delete',{id:item.id},scope.search);
							break;

						case CRUD_CODE.VIEW: //查
							break;
						}
					}
					//判断是否显示树
					scope.showtree = function(){
						return !_.isUndefined(scope.treeconfig);
					}
				} 
			}
		}
	};
});