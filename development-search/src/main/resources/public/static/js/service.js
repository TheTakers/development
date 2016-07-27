app.service('commonService', function($log,$uibModal){
	
	/**
	 * url 目标链接
	 * ctrl 实例controller
	 * param 传递给子页参数
	 * callback 回调参数
	 * loadScript 加载弹出窗对应js脚本
	 */
	this.show = function(options){
		 
		return $uibModal.open(_.extend({ 
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
	}
});
