app.service('commonService', function($log,$uibModal,$http){
	
	/**
	 * url 目标链接
	 * ctrl 实例controller
	 * param 传递给子页参数
	 * callback 回调参数
	 * loadjs 加载弹出窗对应js脚本
	 */
	this.show = function(options){
		var modalInstance = $uibModal.open(_.extend({ 
			resolve: {
				param: function () {
					return options.param;
				},
				deps:function($ocLazyLoad,$stateParams,$log){
					if(!_.isUndefined(options.loadjs)){
//						return $ocLazyLoad.load("templates/"+options.templateUrl+".js");
						return $ocLazyLoad.load(options.loadjs);
					}
				}
			}
		},options));
		
		modalInstance.result.then(function (selectedItem) { //获取子页返回值
			if(options.callback){
				options.callback(selectedItem);
			}
		}, function () { //子页关闭监听
		//	$log.info('Modal dismissed at: ' + new Date());
		});
		return modalInstance;
	};
	
	//wrap post
	this.post = function post(url,param,success){
		var $shade = $.shadetab();
		$http.post(url,param).success(function(data){
			$.trash($shade);
			success(data);
		});
	};
	
	/*传JSON格式数据需要先转成JSON字符串JSON.stringify*/
	this.ajax = function ajax(options){
		var $shade;
		var param = {type:"post",
				contentType:'application/json',
				dataType:'json',
				async:true,
				data:{},
				url:""};
		$.extend(param,options);
		$.ajax({  
			type : param.type,  
			url : param.url,  
			contentType:param.contentType,
			dataType:param.dataType,
			async:param.async,
			data:param.data,
			beforeSend: function(){
				$shade = $.shadetab();
			},
			complete: function () {
				if(!_.isEmpty($shade))
					$.trash($shade);
			},
			success : function(data){  
				if(param.success)
					param.success(data);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
			   $log.info(errorThrown);
		       if(!_.isEmpty($shade))
		    	    $.trash($shade);
		    }
		});
	};
	
	//根据SQLID获取字段集合
	this.getFieldListBySqlId = function(newValue,sucess){
		if(newValue){
			var options = {url:'/search/sqlview/createField',data:JSON.stringify({sqlId:newValue}),success:sucess};
			this.ajax(options);
		}
	}
});