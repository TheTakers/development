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
 * 
 * <pagination data="{{pagination}}"></pagination>
 * click event trigger query
 **/
app.directive('pagination', function($parse) {
	 return {
         restrict:'E',
         template:function(element,atts){
        	 
        	return  "<div class='fixed-table-pagination'>"+
        		
	        	"<div class='pull-left pagination'>"+
				"共{{pagination.pageCount}}条/{{pagination.totalPageNum}}页  "+
				"	<div class='pagination'>"+
				"		 <select ng-change='setPageSize(selected)' ng-model='selected' ng-options='x.id as x.value for x in options' class='form-control input-sm' ></select>"+
				"	</div>"+
				"</div>"+
	 			"<div class='pull-left pagination-detail'></div>"+
	 				"<div class='pull-right pagination'>"+
		 				"<ul class='pagination'>"+
		 					"<li class='{{item.classs}}' ng-repeat='item in pagination.code' ng-click='go(item.value)'><a href='javascript:void(0)'>{{item.label}}</a></li>"+
		 				"</ul>"+
		 			"</div>"+
		 		"</div>";
	 		
         },
         scope:{
        	 data:"@",
        	 search:"&"
         },
         transclude : false,
         link : function(scope,element,attr){
        	 
        	 //生成分页条
        	 function createPagination(pagination){
        		 
        		 if(_.isUndefined(pagination.pageCount)){
        			 console.log('pageCount not define.');
        		 }
        		 
        		 if(_.isUndefined(pagination.pageSize)){
        			 console.log('pageSize not define.');
        		 }
        		 
        		 if(_.isUndefined(pagination.pageNo)){
        			 console.log('pageNo not define.');
        		 }
        		 
        		 
        		 //计算总页数
            	 var totalPageNum = Math.floor((pagination.pageCount  +  pagination.pageSize - 1) / pagination.pageSize);  
            	 pagination.totalPageNum = totalPageNum;
            	 
            	 
            	//计算总页数
            	 var totalPageNum = Math.floor((pagination.pageCount  +  pagination.pageSize - 1) / pagination.pageSize);  
            	 pagination.totalPageNum = totalPageNum;
            	 
            	 
            	 //开始页码计算 
            	 var startPage = Math.floor(pagination.pageNo * 5) / 5;
            	 
            	 //结束页码
            	 var endPage = startPage + 10 - 1;
            	 
            	 if(endPage >= totalPageNum){
            		 startPage = totalPageNum -10;
            		 endPage = totalPageNum;
            	 }
            	 
        		 
        		 //页码
        		 var code = [];
        		 
        		 //page first
        		 code.push({classs:'page-first' + (pagination.pageNo == 1 ? ' disabled' :''),value:1,label:'«'});
        		 //page pre
        		 code.push({classs:'page-pre' + (pagination.pageNo == 1 ? ' disabled' :'') ,value:pagination.pageNo-1,label:'‹'});
        		 
        		 // class pageno 
        		 for(var idx = startPage ; idx <= endPage ; idx++){
        			 
        			 if(idx == pagination.pageNo)
        				 code.push({classs:'page-number active',value:idx,label:idx});
        			 else
        				 code.push({classs:'page-number',value:idx,label:idx}); 
        		 }
        		 
        		 //page next
        		 code.push({classs:'page-next' + (pagination.pageNo == totalPageNum ? ' disabled' :''),value:pagination.pageNo+1,label:'›'});
        		 //page last
        		 code.push({classs:'page-last' + (pagination.pageNo == totalPageNum ? ' disabled' :''),value:totalPageNum,label:'»'});
        		 pagination.code = code;
        	 }
        	 
        	 
        	 scope.pagination = $.parseJSON(attr.data);
        	 createPagination(scope.pagination);
        	
        	 scope.go = function(v){
        		 
        		 //check data
        		 if(v < 1 || v > scope.pagination.totalPageNum){
        			 return;
        		 }
        		 
        		 //update local pagination
        		 scope.pagination = scope.search({pagination:{pageNo:v,pageSize:scope.pagination.pageSize}});
        		 
        		 //refresh parent data
        		 createPagination(scope.pagination);
        	 }
        	
        	 
        	 //每页显示数
        	 scope.options = [
        	                  {id:20,value:20},
        	                  {id:40,value:40},
        	                  {id:60,value:60},
        	                  {id:80,value:80},
        	                  {id:100,value:100}
        	                  ]
        	 scope.selected = 20;//默认20页
        	 scope.setPageSize = function(option){
        		 
        		 //update local pagination
        		 scope.pagination = scope.search({pagination:{pageNo:1,pageSize:option}});
        		 
        		//refresh parent data
        		 createPagination(scope.pagination);
        	 }
        	 
         }

     };
});