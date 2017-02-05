/**
 * UI指令 ningzuokun
 */
/**========================================================================event directive======================================================================================================**/
app.directive('uiValidator', [function () {
	function regxResult(rule,value,length){
		if(length){
			if(value.length < length)
				return false;
		}
		switch (rule) {
		case "10001":
			if(_.isString(value)){
				return !_.isEmpty(value);
			}else{
				return value != null;
			}
		default:
			return eval(rule).test(value);
		}
	};

	return {
		require: "ngModel",
		link: function (scope, element, attr, ngModel) {
			if (ngModel) {

				//元素绑定提示信息
				var customValidator = function (value) {

					var verifyRule = $(attr).attr("uiValidator");
					var ruleList = eval(verifyRule);

					if(_.isEmpty(ruleList)){
						$(element).removeClass("ng-required");
						$(element).popover('destroy');
						return value;
					}

					for(var idx in ruleList){
						var regx = REGULAR_EXPRESSION[ruleList[idx]];
						var validity = regxResult(regx.rule,value,$(attr).attr("maxlength"));
						ngModel.$setValidity(regx.rule, validity);

						if(validity){
							$(element).removeClass("ng-required");
							$(element).popover('destroy');
						}else{
							/**
							$(element).attr("data-content",regx.tip);
							$(element).popover('show');
							
							$(element).popover({trigger:"hover|focus ",placement:"right",content:regx.tip})
							$(element).popover('show');
							$(element).addClass("ng-required"); **/
							break;
						}
//						return validity ? value : undefined;
					}
					return value;
				};
				ngModel.$formatters.push(customValidator);
				ngModel.$parsers.push(customValidator);
			}
		}
	};
}]);

//上下移动
app.directive('uiFloat', [function () {

	return {
		scope:{
			list:"="
		},
		link: function (scope, element, attr) {

			var course = $(attr).attr("course");
			var idxkey = $(attr).attr("idxkey");
			//上移
			var up  = function() { 
				var $tr = $(this).parents("tr"); 
				var trIdx = $tr.index();
				if (trIdx != 0) { 
					$tr.fadeOut().fadeIn(); 
					$tr.prev().before($tr); 
					var cidx = findIdx(idxkey,trIdx);

					//前一行实际索引
					var pidx = findIdx(idxkey,trIdx - 1);

					//取上一行
					scope.list[pidx][idxkey] = trIdx;

					//设置当前列对象索引 - 1
					scope.list[cidx][idxkey] = trIdx - 1;
				} 
			}

			//下移
			var down = function() { 
				var $tr = $(this).parents("tr");
				var length = $(this).parent().parent().parent().find("tr").length;
				var trIdx = $tr.index();
				if ($tr.index() != length - 1) { 
					$tr.fadeOut().fadeIn(); 
					$tr.next().after($tr); 

					//当前对象索引
					var cidx = findIdx(idxkey,trIdx);

					//下一行对象索引
					var nidx = findIdx(idxkey,trIdx + 1);

					// + 1
					scope.list[cidx][idxkey] = trIdx + 1;

					//原下一行索引更新
					scope.list[nidx][idxkey] = trIdx;
				} 
			}

			//找出指定对象
			var findIdx = function(idkey,trIdx){
				for(var idx in scope.list){
					if(scope.list[idx][idkey] == trIdx)
						return idx;
				}
				return -1;
			}

			if("up" == course){
				$(element).click(up);
			}else{
				$(element).click(down);
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
			clazz:"@"
		},
		replace : true,			
		transclude : false,
		template:function(element,atts){
			return  '<label class="{{clazz}}  control-label" ui-popover="" data-content="{{content}}" data-placement="top" data-trigger="hover">{{fixedValue}}'+
			'<span class="text-muted"></span></label>';
		},
		link:function(scope,element,attr){
			if(scope.content.length > 6){
				scope.fixedValue = scope.content.substring(0,4) + "..";
			}else{
				scope.fixedValue = scope.content;
			}
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
			valuekey:'@',
			textkey:'@'
		},
		template:function(element,atts){
			return  '<select ng-model="selected" class="btn dropdown-toggle btn-white select2" ng-options="item[vk] as item[tk] for item in data"> '+
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
					scope.tk = "text";
					scope.vk = "value";
					if(!_.isUndefined(scope.valuekey)){
						scope.vk = scope.valuekey;
					}
					if(!_.isUndefined(scope.textkey)){
						scope.tk = scope.textkey;
					}

					if(_.isEmpty(scope.url)) return;
					commonService.ajax({url:scope.url,data:scope.param,async:false,success:function(data){
						if(data.code = STATUS_CODE.SUCCESS){
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
			commonService.post(scope.url,_.extend({pageSize:scope.limit.pageSize,pageNo:scope.limit.pageNo},scope.params),function(response){
				if(STATUS_CODE.FAILURE == response.code){
					$.error(response.message);
					return;
				}
				scope.limit.pageCount = response.result.totalElements;
				scope.data = response.result.content;
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

app.directive('uiRangSliders', function($http,$log) {
	return {
		restrict:'E',
		scope:{
			options:'='
		},
		template:function(element,atts){
			return '<input id="'+$.uuid()+'"></input>';
		},
		replace : true,			
		transclude : false,
		compile: function compile(tElement, tAttrs, transclude) {
			return {
				pre: function preLink(scope, element, iAttrs, controller) {
					var from = $(element).attr("from");
					var fromValue = 0;
					if(from){
						fromValue = scope.options[from];
					}
					$(element).ionRangeSlider({
//						type: "double",
						grid: true,
						min: $(element).attr("min") || 0,
						max: $(element).attr("max"),
						from: fromValue,
						onFinish:function (data) {
							var from = $(element).attr("from");
							if(from){
								scope.options[from] = data.from;
							}
							var to = $(element).attr("to");
							if(to){
								scope.options[to] = data.to;
							}
						}
//					onChange,
//					to: 800,
//					prefix: "%"
					});
				}
			}
		}
	};
}); 

app.directive('uiDatetimepicker', function($http,$log) {
	return {
		restrict:'E',
		scope:{
			data:'=',
			format:'@',//格式化
			validator:'='
		},
		template:function(element,atts){
			return  '<div class="input-group"><input type="text" class="form-control input-sm" placeholder="{{format}}" ng-model="data" ui-validator="{{validator}}"></input>'+
			'<span class="input-group-addon bg-custom b-0 text-white"><i class="icon-calender"></i></span></div>';
		},
		replace : false,			
		link:function(scope,element,attr,ngModel){
			var foramt = 'Y-m-d H:i:s';
			if(scope.format){

				//去掉%
				foramt = scope.format.replace(/%/g,'');
			}

			//中文支持
			$.datetimepicker.setLocale('zh');
			var timepickerFlag = _.isEqual(foramt, 'H:i');
			var datetimepicker = element.find("input");
			$(datetimepicker).datetimepicker({
				timepicker:timepickerFlag,    //不显示时间选项
				format:foramt  //scope.sqlviewfield.options //Y-m-d H:i:s
			});

			//监控值改变
			$(datetimepicker).change(function(){
				scope.data = $(datetimepicker).val();
			})
		}
	};
}); 

//用户自定义编辑器
app.directive('uiUdEditor', function($http,$log,$uibModal) {
	return {
		restrict:'EA',
		scope:{
			data:'=',//需要被修改的数据
			param:'=', //传到子页面参数
			tplurl:'@', //模板url
			ctrl:'=',//弹窗控制器
			size:'=',//窗体大小
			loadjs:'=',//指令js
			options:'=',//{dataKey:'pid',dataValue:'pText',returnKey:'id',returnValue:'name'} 返回值,显示值
			validator:'='
		},
		template:function(element,atts){
			return'<div class="app-search-sm">'
			+'<input type="text" class="form-control input-sm" ng-model="data[opts.dataValue]" ui-validator="{{validator}}" maxlength="{{maxlength}}" readonly="true"></input>'
			+'<a ng-click="open()" ><i class="fa fa-search selector-hover"></i></a></div>';
		},
		replace : true,			
		transclude : false,
		link:function(scope,element,attr){
			scope.opts = eval( scope.options );
			scope.maxlength = $(attr)[0].maxlength;
			scope.open=function(){
				var modalInstance = $uibModal.open({
					templateUrl: scope.tplurl,

					//接收子页传值
					controller: scope.ctrl,
					size:scope.size,
					resolve: {
						context: function () {
							return scope;
						},
						deps:function($ocLazyLoad,$stateParams,$log){
							if(!_.isUndefined(scope.loadjs)){
								return $ocLazyLoad.load(scope.loadjs);
							}
						}
					}
				});
			}
		} 
	};
}); 


//选择器
app.directive('uiSelector', function($http,$log,$uibModal) {
	return {
		restrict:'E',
		scope:{
			url:'@',
			options:'@', //{dataKey:'pid',dataValue:'pText',returnKey:'id',returnValue:'name'} 返回值,显示值
			size:'@',
			data:"=",
			param:'=', //传给子页参数
			validator:'='
		},
		template:function(element,atts){
			return  '<div class="app-search-sm">'
			+'<input type="text"  class="form-control input-sm" ng-model="data[optsJson.dataValue]" ui-validator="{{validator}}" maxlength="{{maxlength}}" readonly="true"></input>'
			+'<a ng-click="open()" ><i class="fa fa-search selector-hover"></i></a></div>';
		},
		replace : true,			
		transclude : false,
		link:function(scope,element,attr){
			scope.optsJson = eval('(' + scope.options + ')');
			scope.maxlength = $(attr)[0].maxlength;
			scope.open=function(){
				var modalInstance = $uibModal.open({
					templateUrl: '/templates/basic/directive/selector.html',

					//接收子页传值
					controller: function($scope,$http,$uibModal,$log,$uibModalInstance,param) { 
						$scope.url = param.url;
						$scope.returndata = {};

						//选择器ok按钮
						$scope.ok = function() {
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
					size:scope.size,
					resolve: {
						param: function () {
							return scope;
						} 
					}
				});

				//获取子页返回值
				modalInstance.result.then(function (checked) { 
					var options = scope.optsJson;
					var selectedItem = checked.data;

					//单选
					if(_.isEqual(GRID_OPTIONS.SINGLE, checked.option)){
						scope.data[options.dataKey] =  selectedItem[0][options.returnKey];
						scope.data[options.dataValue] = selectedItem[0][options.returnValue];
					}else{

						//多选
						var value = "";
						var id = "";
						for(var idx in selectedItem){
							id +=  selectedItem[idx][options.returnKey] + ',';
							value += selectedItem[idx][options.returnValue] + ',';
						}
						id = id.substring(0,_.lastIndexOf(id,","));
						value = value.substring(0,_.lastIndexOf(value,","));
						scope.data[options.dataKey] = id;
						scope.data[options.dataValue] = value;
					}

					//子页关闭监听
				}, function () { 
					$log.info('Modal dismissed at: ' + new Date());
				});
			}
		} 
	};
}); 
//自动生成编码
app.directive('uiGenerateCode', function($http,$log,commonService) {
	var scopeParam = {
			url:'=',
			param:'=',
			data:"=",
			validator:"="
	};
	var templateFunc = function(element,atts){
		return  '<div class="uiGenerateCode"><input class="form-control input-sm"  ng-model="data"  ui-validator="{{validator}}" maxlength="{{maxlength}}" readonly="true"></input>'+
		'<button type="button" class="btn btn-info waves-effect waves-light input-sm" ng-click="createCode()">生成</button></div>';
	};
	var linkFunc = function(scope,element,attr){
		scope.maxlength = $(attr)[0].maxlength;
		scope.createCode = function(){
			var remoteUrl = _.isEmpty(scope.url) ?  "/basic/func/code" : scope.url;
			commonService.ajax({url:remoteUrl,data:scope.param,async:false,success:function(data){
				if(data.code == STATUS_CODE.SUCCESS){
					scope.data = data.result;
				}else{
					$.error(data.message);
				}

			}});
		}
	};
	return {
		restrict:'E',
		scope:scopeParam,
		template:templateFunc,
		replace : false,			
		transclude : false,
		link:linkFunc
	};
});

//根据SqlView编号生成的选择器
app.directive('uiViewSelector', function($http,$log,$uibModal) {
	var scopeParam = {
			data:"=",
			kv:'=', //{dataKey:'pid',dataValue:'pText',returnKey:'id',returnValue:'name'} 返回值,显示值
			param:'=', //传给子页参数
			size:'=',
			validator:'='
	};
	var templateFunc = function(element,atts){
		return  '<div class="app-search-sm">'
		+'<input type="text"  class="form-control input-sm" ng-model="data[firstMapp.valueKey]" ui-validator="{{validator}}" maxlength="{{maxlength}}" readonly="true"></input>'
		+'<a ng-click="open()" ><i class="fa fa-search selector-hover"></i></a></div>';
	};
	var linkFunc = function(scope,element,attr){
		scope.options = eval('(' + scope.kv + ')'); 

		//返回键值
		scope.firstMapp = scope.options.mappingList[0];
		scope.maxlength = $(attr)[0].maxlength;
		scope.open=function(){
			var modalInstance = $uibModal.open({
				templateUrl: '/templates/basic/directive/uiCodeSelectorTpl.html',
				controller: function($scope,$http,$uibModal,$log,$uibModalInstance,param) {//接收子页传值

					//获取视图编号
					$scope.code = param.options.code;
					$scope.returndata = {};
					$scope.ok = function() {
						if(!_.isEmpty($scope.returndata.data)){

							//传值给父页
							$uibModalInstance.close($scope.returndata);
						}else{
							$.warning("请选择记录!");
						}
					};
					$scope.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
				},
				size:scope.size,
				resolve: {
					param: function () {
						return scope;
					}
				}
			});

			//获取子页返回值
			modalInstance.result.then(function (checked) { 
				var mappingList = scope.options.mappingList;
				var selectedItem = checked.data;

				//单选
				if(selectedItem.length == 1){
					var sitem = selectedItem[0];
					for(var midx in mappingList){
						var mapping = mappingList[midx];
						scope.data[mapping.valueKey] = sitem[mapping.textValue];
					}
				}else{
					var textValue = "";
					for(var midx in mappingList){
						var mapping = mappingList[midx];
						for(var idx in selectedItem){
							textValue += selectedItem[idx][mapping.textValue] + ',';
						}
						textValue = textValue.substring(0,_.lastIndexOf(textValue,","));
						scope.data[mapping.valueKey] = textValue;
					}
				}
			}, function () { //子页关闭监听
				$log.info('Modal dismissed at: ' + new Date());
			});
		}
	}
	return {
		restrict:'E',
		scope:scopeParam,
		template:templateFunc,
		replace : true,			
		transclude : false,
		link:linkFunc 
	};
}); 


app.directive('uiIcon', function($http,$log) {
	var templateFunc = function(element,atts){
		return '<a href="javascript:void(0);" class="table-action-btn" ng-click="checkedIcon()"><i class="{{icon}}"></i></a>';
	}
	var linkFunc = function(scope,element,attr){
		scope.icon = "ion-eye";
		if(scope.prop){
			scope.icon = scope.prop;
		}
		var checkedIcon = function($scope,$http,$uibModal,$log,$uibModalInstance,param){
			$scope.ok=function($event){
				param.prop = $($event.target).attr("class");
				$uibModalInstance.close();
			}
		}
		$scope.checkedIcon = function(){
			var options = {
					templateUrl:"/templates/search/sqlview/iconSelector.html",
					controller:checkedIcon,
					param:$scope,
					size:80
			};
			commonService.show(options);
		}
	}
	return {
		restrict:'E',
		scope:{prop:'='},
		template:templateFunc,
		link:linkFunc
	};
});
