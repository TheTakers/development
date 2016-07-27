app.service('commonService', function($log,$uibModal){
	
	/**
	 * url 目标链接
	 * ctrl 实例controller
	 * param 传递给子页参数
	 * callback 回调参数
	 * loadScript 加载弹出窗对应js脚本
	 */
	this.show = function(options){
		 
		var modalInstance = $uibModal.open(_.extend({ 
			resolve: {
				param: function () {
					return options.param;
				},
				deps:function($ocLazyLoad,$stateParams,$log){

					if(_.isUndefined(options.loadScript) || options.loadScript)
						return $ocLazyLoad.load("templates/"+options.templateUrl+".js");
				}
			}
		},options));

		modalInstance.result.then(function (selectedItem) { //获取子页返回值

			if(options.callback){
				options.callback(selectedItem);
			}

		}, function () { //子页关闭监听

			$log.info('Modal dismissed at: ' + new Date());
		});
		
		return modalInstance;
	}
});
