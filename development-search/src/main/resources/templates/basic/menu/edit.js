angular.module('app').controller('editCtrl',function($scope,$http,$uibModal,$log,$uibModalInstance,param) { //接收子页传值
	
	//页面数据
	$scope.data = param;
	
 	$scope.save = function() {
 		saveOfClose($http,'/basic/menu/save',$scope.data,$uibModalInstance);
 	};
 	
 	$scope.cancel = function() {
 		 $uibModalInstance.dismiss('cancel');
 	};
 	
 	
 	//字段列表
 	$scope.fieldList = [
		            {title:"名称",field:"name",required:false,element:"text"},
		            {title:"url",field:"link",required:false,element:"text"},
		            {title:"图标",field:"ico",required:false,element:"text"},
		            {title:"所属菜单",field:"pid",required:false,element:"selector",url:"/basic/menu/selector",option:{text:"pText",value:"pid"}},
		            {title:"菜单路径",field:"name",required:false,element:"text"},
		            {title:"排序",field:"path",required:false,element:"text"},
		            {title:"描述",field:"remark",required:false,element:"textarea"}
		            ]
 	
});