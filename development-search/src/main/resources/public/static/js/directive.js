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
//选择器
app.directive('uibaseselector', function($http,$log,$uibModal) {
	return {
		restrict:'E',
		scope:{
			data:"=",
			url:'@',
			expand:'@', //扩展字段
			param:'=' //传给子页参数
		},
		template:function(element,atts){
			return  '<div class="app-search-sm">'
			+'<input type="text"  class="form-control input-sm" value="{{data[inputData.dataValue]}}"></input>'
			+'<a ng-click="showDialog()" ><i class="fa fa-search selector-hover"></i></a></div>';
		},
		replace : true,			
		transclude : false,
		link:function(scope,element,attr){

			if(_.isEmpty(scope.expand)){
				return;
			}
			scope.inputData = eval('(' + scope.expand + ')');

			scope.showDialog=function(){

				var modalInstance = $uibModal.open({
					templateUrl: '/basic/directive/selector',

					//接收子页传值
					controller: function($scope,$http,$uibModal,$log,$uibModalInstance,param) { 

						$scope.url = param.url;
						$scope.returndata = {};

						//选择器ok按钮
						$scope.ok = function() {
							var item ={}
							if(!_.isEmpty($scope.returndata.data)){

								//传值给父页
								$uibModalInstance.close($scope.returndata);
							}else{
								$.warning("请选择记录!");
							}
						};

						//取消
						$scope.cancel = function() {
							$uibModalInstance.dismiss('cancel');
						};

					},
					size:'l',
					resolve: {
						param: function () {
							return scope;
						},
						deps:function($ocLazyLoad,$stateParams,$log){

							//if(_.isUndefined(scope.loadScript) || scope.loadScript)
							//return $ocLazyLoad.load("templates/"+scope.url+".js");
						}
					}
				});

				modalInstance.result.then(function (checked) { //获取子页返回值

					var expand = scope.inputData;

					var selectedItem = checked.data;
					//单选
					if(_.isEqual(1, checked.option)){
						scope.data[expand.dataKey] =  selectedItem[0][expand.returnKey];
						scope.data[expand.dataValue] = selectedItem[0][expand.returnValue];
					}else{
						//多选
						var value = "";
						var id = "";
						for(var idx in selectedItem){

							id +=  selectedItem[idx][expand.returnKey] + ',';
							value += selectedItem[idx][expand.returnValue] + ',';
						}
						id = id.substring(0,_.lastIndexOf(id,","));
						value = value.substring(0,_.lastIndexOf(value,","));
						scope.data[expand.dataKey] = id;
						scope.data[expand.dataValue] = value;
					}
				}, function () { //子页关闭监听
					$log.info('Modal dismissed at: ' + new Date());
				});
			}
		} 
	};
}); 
//page
app.directive('uibasepage', function($http,$log,$ocLazyLoad,commonService,$uibModal) {
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

						//字段列表
						$scope.fieldList = param.modelView.fieldSetting;
					}

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
							edit(commonService,scope.modelView.controller + '/findById','/basic/directive/edit',modalDialog,{id:item.id,modelView:scope.modelView},scope.grid.search);
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

//page
app.directive('uisqlview', function($http,$log,$ocLazyLoad,commonService,$uibModal) {
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

					//子窗口 
					var modalDialog = function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
						$scope.optionData = [{value:"1",text:"是"},{value:"0",text:"否"}];
						$scope.formData = param.formData;
						//保存操作
						$scope.save = function() {
							saveOfClose($http,param.modelView.controller + "/save",$scope.formData,$uibModalInstance);
						};
						$scope.cancel = function() {
							$uibModalInstance.dismiss('cancel');
						};

						//字段列表
						$scope.fieldList = param.modelView.fieldSetting;
						$scope.fieldGrid = {id:$.uuid()};

						//生成列表
						$scope.createFieldData = function(){
							$.confirm({
								confirm: function(){
									commonService.ajax({url:"/search/sqlview/createField",data:{sql:"20160831114541"},async:false,success:function(data){
										$scope.fieldGrid.dataList = data.result;
									}});
								} 
							});
						}
					}

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
							edit(commonService,scope.modelView.controller + '/findById',func.url,modalDialog,{id:item.id,modelView:scope.modelView},scope.grid.search);
							break;

						case "remove":
							remove(commonService,scope.modelView.controller + '/delete',{id:item.id},scope.search);
							break;

						case "view":

							break;

						default :
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

//自动生成编码
app.directive('uigeneratecode', function($http,$log,commonService) {

	return {
		restrict:'E',
		scope:{
			data:"=",
			required:"@"
		},
		template:function(element,atts){
			return  '<input class="form-control input-sm" ng-model="data" required="{{required}}"></input>'+
			'<button type="button" class="btn btn-info waves-effect waves-light input-sm" ng-click="createCode()">生成</button>';
		},
		replace : false,			
		transclude : false,
		link:function(scope,element,attr){ 

			//生成编码
			scope.createCode = function(){

				commonService.ajax({url:"/basic/func/code",async:false,success:function(data){

					if(data.code = '0'){
						scope.data = data.result;
					}else{
						$.error(data.message);
					}

				}});
			}
		}
	};
})
