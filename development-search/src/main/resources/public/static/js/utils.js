/**
 * array crud item
 */
function uniqueOf(array,item){

	var idx = _.findIndex(array, item);
	if(idx > -1){
		array.splice(idx,1);
	}else{
		array.push(item);	
	}
}


/**
 * 弹窗保存并关闭
 * @param $http
 * @param url
 * @param param
 * @param $uibModalInstance
 */
function saveOfClose($http,url,param,$uibModalInstance){
	$http.post(url,param).success(function(data){
		 if(data.code == '0'){
	       		$uibModalInstance.close(data.result);
	       	  }else{
	       		 $.error(data.message);
	       	  }
		});	
};


/**
 * dataUrl :编辑页初始化数据页
 * templateUrl :编辑页url
 * ctrl :编辑页controller
 * callback :回调函数
 */
function edit(commonService,dataUrl,templateUrl,ctrl,param,callback) {
		
		if(!_.isEmpty(param.id)){
			
			/*根据选中ID获取最新数据*/
			
			commonService.post(dataUrl,param,function(data){
				
				if(data.code == '0'){
					commonService.show({templateUrl:templateUrl,controller:ctrl,param:{formData:data.result,modalData:param.modalData},callback:callback});
				}else{
					$.error(data.message);
				}
			});
			
		}else{
			commonService.show({templateUrl:templateUrl,controller:ctrl,param:param,callback:callback});
		}
	};
	
function remove(commonService,url,param,callback) {
		
		$.confirm({
		    confirm: function(){
		    	commonService.post(url,param,function(data){
					if(data.code == '0'){
						$.info(data.message);
						
						if(callback){
							callback(param);
						}
					}else{
						$.error(data.message);
					}
				});
		    } 
		});
	}
