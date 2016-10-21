/**========================================================================event directive======================================================================================================**/
app.directive('formValidator', [function () {
	
	function regexp(rule,value){
		
		switch (rule.code) {
		
		    case 1: //email 
		    	 var emailsRegexp = /^([a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9-]+(\.[a-z0-9-]+)*[;；]?)+$/i;
		    	 return emailsRegexp.test(value);
		    case 2://最大长度
		        return value && value.length <= 4
		    case 3://数字
		    	
		    default:
		        // ...
		}
	};
    return {
    	scope:{
    		rule:'='
    	},
        require: "ngModel",
        link: function (scope, element, attr, ngModel) {
            if (ngModel) {
            	var customValidator = function (value) {
            		var validity = ngModel.$isEmpty(value) || regexp(scope.rule,value);
            		ngModel.$setValidity("formValidator", validity);
            		return validity ? value : undefined;
            	};
            	ngModel.$formatters.push(customValidator);
            	ngModel.$parsers.push(customValidator);
            }
        }
    };
}]);


/**========================================================================ui directive======================================================================================================**/
app.directive('uiLabel', function($http,$log) {
	return {
		restrict:'E',
		scope:{
			content:"@",
			clazz:"@",
			required:"="
		},
		replace : true,			
		transclude : false,
		template:function(element,atts){
			return  '<label class="{{clazz}}  control-label" ui-popover="" data-content="{{content}}"  data-trigger="hover">{{fixedValue}}'+
					'<span class="text-muted"  ng-if="required"> * </span></label>';
		},
		link:function(scope,element,attr){
			scope.fixedValue = scope.content.substring(0,4);
		}
	};
});

app.directive('uiPopover', function($http,$log) {
	return {
		restrict:'A',
		link:function(scope,element,attr){
			$(element).popover();
		}
	};
});

app.directive('uiButton', function($http,$log) {
	return {
		restrict:'E',
		scope:{
			icon:'@',
			title:'@',
			url:'=',
			param:'='
		},
		template:function(element,atts){
			return  '<ul class="ztree" id="'+$.uuid()+'"></ul>';
		},
		replace : true,			
		transclude : false,
		link:function(scope,element,attr){

		}
	};
}); 
app.directive('uiDropdown', function($http,$log) {
	return {
		restrict:'E',
		scope:{
			url:'=',
			param:'=',
			data:'=',	//数据
			selected:'=', //被选中值
			required:'='
		},
		template:function(element,atts){
			return  '<select ng-model="selected" class="btn dropdown-toggle btn-white" ng-options="item.value as item.text for item in data"> '+
				       //'<option value="">请选择</option>'+
					'</select>';
		},
		replace : true,			
		transclude : false,
		compile: function compile(tElement, tAttrs, transclude) {
			return {
				pre: function preLink(scope, iElement, iAttrs, controller) {
						if(_.isString(scope.data)){
							scope.data = eval(scope.data);
						}
						if(_.isEmpty(scope.url)) return;
						commonService.ajax({url:scope.url,data:scope.param,async:false,success:function(data){
							if(data.code = '0'){
								scope.data = data.result;
							}else{
								$.error(data.message);
							}
						}});
					}
				}
		},
		link:function(scope,element,attr){}
	};
}); 
app.directive('uiPagination', function($http,$log,commonService) {
	
	//创建分页
	function createLimit(scope){
		var limit = scope.limit;
		//计算总页数
		var totalPageNum = Math.floor((limit.pageCount  +  limit.pageSize - 1) / limit.pageSize);  
		limit.totalPageNum = totalPageNum;
		//开始页码计算 
		var startPage = limit.pageNo - 5 < 1 ? 1 : limit.pageNo - 5;
		//结束页码
		var endPage = (limit.pageNo + 4 < 10 ? 10 : limit.pageNo + 4) > totalPageNum ? totalPageNum : (limit.pageNo + 4 < 10 ? 10 : limit.pageNo + 4);
		//页码
		var code = [];
		//第一页
		code.push({classs:'page-first' + (limit.pageNo == 1 ? ' disabled' :''),value:1,label:'«'});
		//前一页
		code.push({classs:'page-pre' + (limit.pageNo == 1 ? ' disabled' :'') ,value:limit.pageNo-1,label:'‹'});
		//设置当前页样式
		for(var idx = startPage ; idx <= endPage ; idx++){
			if(idx == limit.pageNo)
				code.push({classs:'page-number active',value:idx,label:idx});
			else
				code.push({classs:'page-number',value:idx,label:idx}); 
		}
		//下一页
		code.push({classs:'page-next' + (limit.pageNo == totalPageNum ? ' disabled' :''),value:limit.pageNo+1,label:'›'});
		//尾页
		code.push({classs:'page-last' + (limit.pageNo == totalPageNum ? ' disabled' :''),value:totalPageNum,label:'»'});
		scope.limit.code = code;
	}
	//查询数据
	function remote(scope){
		if(!_.isEmpty(scope.url)){
			commonService.post(scope.url,_.extend({pageSize:scope.limit.pageSize,pageNo:scope.limit.pageNo},scope.params),function(data){
				if(_.isUndefined(data.result)){
					$log.error("分页响应数据异常");
					return;
				}
				scope.limit.pageCount = data.result.totalElements;
				scope.data = data.result.content;
				createLimit(scope);
			});
		}
	}

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
			//默认显示
			scope.selected = 10;
			//分页限制
			scope.limit = {pageSize:scope.selected,pageNo:1};
			//初始化数据
			remote(scope);
			//catch parent broadcast goto
			scope.$on(scope.id, function(d,data) {  
				remote(scope);
			});  
			scope.go = function(v){
				//check data
				if(v < 1 || v > scope.limit.totalPageNum){
					return;
				}
				scope.limit.pageNo = v;
				remote(scope);
			};
			//num of page
			scope.options = [{id:10,value:10}, {id:20,value:20}, {id:30,value:30},{id:40,value:40}, {id:50,value:50} ];
			scope.setPageSize = function(option){
				scope.limit.pageSize = option;
				scope.limit.pageNo = 1;
				remote(scope);
			};
		}
	};
}); 
app.directive('uiTree', function($http,$log) {
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
app.directive('uiDatepicker', function($http,$log) {
	return {
		restrict:'E',
		scope:{
			value:'=',
			required:'='
		},
		template:function(element,atts){
			return  '<div class="input-group"><input type="text" class="form-control input-sm" placeholder="yyyy-mm-dd" ng-model="value"></input>'+
			'<span class="input-group-addon bg-custom b-0 text-white"><i class="icon-calender"></i></span></div>';
		},
		replace : false,			
		transclude : false,
		link:function(scope,element,attr){
			$(element.find("input")).datepicker({
				format: 'yyyy-mm-dd',
				autoclose: true,
				language: 'zh-CN'
			});
		}
	};
}); 
//选择器
app.directive('uiSelector', function($http,$log,$uibModal) {
	return {
		restrict:'E',
		scope:{
			url:'@',
			data:"=",
			expand:'@', //扩展字段
			param:'=', //传给子页参数
			required:'='
		},
		template:function(element,atts){
			return  '<div class="app-search-sm">'
			+'<input type="text"  class="form-control input-sm" ng-class="{\'ng-required\': required}"  value="{{data[inputData.dataValue]}}" readonly="true"></input>'
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
					if(_.isEqual(GRID_OPTIONS.SINGLE, checked.option)){
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
//自动生成编码
app.directive('uiGenerateCode', function($http,$log,commonService) {
	return {
		restrict:'E',
		scope:{
			url:'=',
			param:'=',
			data:"=",
			required:"="
		},
		template:function(element,atts){
			return  '<input class="form-control input-sm" ng-class="{\'ng-required\': required}"  ng-model="data" required="{{required}}" readonly="true"></input>'+
			'<button type="button" class="btn btn-info waves-effect waves-light input-sm" ng-click="createCode()">生成</button>';
		},
		replace : false,			
		transclude : false,
		link:function(scope,element,attr){ 
			
			//生成编码
			scope.createCode = function(){
				var remoteUrl = _.isEmpty(scope.url) ?  "/basic/func/code" : scope.url;
				commonService.ajax({url:remoteUrl,data:scope.param,async:false,success:function(data){
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