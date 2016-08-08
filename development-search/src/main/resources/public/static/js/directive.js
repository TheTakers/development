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
app.directive('pagination', function($http,$log) {
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
        		 
        		 if(_.isUndefined(limit.pageCount)){
        			 $log.error('pageCount not define.');
        			 return;
        		 }
        		 
        		 if(_.isUndefined(limit.pageSize)){
        			 $log.error('pageSize not define.');
        			 return;
        		 }
        		 
        		 if(_.isUndefined(limit.pageNo)){
        			 $log.error('pageNo not define.');
        			 return;
        		 }
        		 
        		 if(limit.pageCount < 1){
        			 return;
        		 }
        		 
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
        		 
        		 $http.post(scope.url,_.extend({pageSize:scope.limit.pageSize,pageNo:scope.limit.pageNo},scope.params)).success(function(data){
        			
        			 if(_.isUndefined(data.result)){
        				 $log.error("结果集未包含result");
        				 return;
        			 }
        			 scope.limit.pageCount = data.result.totalElements;
        			 scope.data = data.result.content;
        			 createLimit(scope.limit);
        			 
      			});
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
        	 scope.options = [
        	                  {id:10,value:10},
        	                  {id:20,value:20},
        	                  {id:30,value:30},
        	                  {id:40,value:40},
        	                  {id:50,value:50}
        	                  ];
        	 
        	 scope.setPageSize = function(option){
        		 
        		scope.limit.pageSize = option;
        		scope.limit.pageNo = 1;
        		post();
        	 };
        	 
         }

     };
}); 

//ztree
app.directive('dtree', function($http,$log) {
	 return {
       restrict:'E',
       scope:{
    	   setting:'=',
    	   znodes:'=',
    	   callback:'&'
       },
       template:function(element,atts){
      	return  '<ul class="ztree"></ul>';
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

/*时间*/
app.directive('datetime', function($http,$log) {
	 return {
      restrict:'E',
      scope:{
   	   value:'='
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
app.directive('selector', function($http,$log,$uibModal) {
	 return {
      restrict:'E',
      scope:{
	   	   text:'=',
	   	   value:'=',
	   	   url:'@',
	   	   ctrl:'@',
	   	   loadScript:'@', //加载一个js文件
	   	   param:'=' //传给子页参数
      },
      template:function(element,atts){
     	return  '<div class="app-search-sm">'
			    +'<input type="text"  class="form-control input-sm" ng-model="text"></input><input type="hidden"  ng-model="value"></input>'
			 	+'<a ng-click="showDialog()" ><i class="fa fa-search selector-hover"></i></a></div>';
      },
      replace : true,			
      transclude : false,
      link:function(scope,element,attr){

    	  scope.showDialog=function(){
    	        
    			 var modalInstance = $uibModal.open({
    				 templateUrl: scope.url,
    	             controller: scope.ctrl,
    	             size:'lg',
    				 resolve: {
    					 param: function () {
    						 return scope.param;
    					 },
    					 deps:function($ocLazyLoad,$stateParams,$log){
    						 
    						 if(_.isUndefined(scope.loadScript) || scope.loadScript)
    						 return $ocLazyLoad.load("templates/"+scope.url+".js");
    					 }
    				 }
    			 });
    	         
    	         modalInstance.result.then(function (selectedItem) { //获取子页返回值
    	        	 
    	        	 if(selectedItem.text){
    	        		 scope.text = selectedItem.text;
    	        	 }
    	        	 
    	        	 if(selectedItem.value){
    	        		 scope.value = selectedItem.value;
    	        	 } 
    	             
    	           }, function () { //子页关闭监听
    	        	   
    	             $log.info('Modal dismissed at: ' + new Date());
    	           });
    	  }
      } 
  };
}); 

//面包屑
app.directive('breadcrumb', function($http,$log,$stateParams) {

	return {
		restrict:'A',
		template:function(element,atts){
			return  '<div class="row">'+
						'<div class="col-sm-12">'+
							'<ol class="breadcrumb">'+
								'<li ng-repeat="item in items">{{item}}</li>'+
							'</ol>'+
						'</div>'+
					'</div>';
		},
		replace : false,			
		transclude : false,
		link:function(scope,element,attr){
			scope.items = [];
			$.ajax({  
 				type : "post",  
 				url : "/basic/menu/breadcrumb",  
 				async : false,  
 				contentType:'application/json;charset=UTF-8',
 				dataType:'json',
 				data:JSON.stringify({action:"/"+$stateParams.module+"/"+$stateParams.controller+"/"+$stateParams.mapping}),
 				success : function(data){  
 					if(data.code = '0'){
 						scope.items = data.result;
 					}else{
 						$.error(data.message);
 					}
 				}  
 			});
		}
	};
})


//面包屑
app.directive('uitab', function($http,$log,$stateParams) {

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
			                '<div role="tabpanel" ng-class="{true: \'tab-pane active\', false: \'tab-pane\'}[isSelected(item.id)]"  ng-repeat="item in data" id="{{item.id}}" ><ng-include src="item.link"></ng-include></div>   '+                 
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
						
						if(_.isEqual(scope.selected, item.id))
						//直接激活最后一个tab
						$('#'+scope.id+' a[data-target="#'+_.last(scope.data).id+'"]').tab('show');
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
 
