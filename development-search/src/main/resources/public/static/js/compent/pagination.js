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
 * <pagination data='{{Json Array}}' pageSize="20" page></pagination>
 * 
 **/
appModule.directive('pagination', function($scope) {
	 return {
         restrict:'E',
         scope:true,
         template:function(element,atts){
        	 
        	 //url 获取data 
        	 $.scope.refresh = function(pageSize){
        		 return {};//返回刷新数据
        	 };
        	 
        	 var data = {};//加载的数据
        	 
        	 <div class='fixed-table-pagination' ng-if='data.length'>
	 			<div class='pull-left pagination-detail'></div>
	 				<div class='pull-right pagination'>
		 				<ul class='pagination'>
		 					<li ng-class="pagination.pageSize > 1 ? 'page-first disabled' : 'page-first'"><a href='javascript:void(0)'>«</a></li>
		 					<li ng-class="pagination.pageSize > 1 ? 'page-pre disabled' : 'page-pre'><a href='javascript:void(0)'>‹</a></li>
		 					<li class='page-number active'><a href='javascript:void(0)'>1</a></li>
		 					<li class='page-number'><a href='javascript:void(0)'>2</a></li>
		 					<li class='page-number'><a href='javascript:void(0)'>3</a></li>
		 					<li class='page-number'><a href='javascript:void(0)'>4</a></li>
		 					<li class='page-number'><a href='javascript:void(0)'>5</a></li>
		 					<li class='page-next'><a href='javascript:void(0)'>›</a></li>
		 					<li class='page-last'><a href='javascript:void(0)'>»</a></li>
		 				</ul>
		 			</div>
	 		</div>
	 		
         },
         
         transclude : false
     };
});