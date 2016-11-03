/**
 * array crud item
 */
function updateCheckBox(array,item){

//	var idx = _.findIndex(array, item);
	var idx = array.indexOf(item);
	if(idx > -1){
		array.splice(idx,1);
	}else{
		array.push(item);	
	}
}
/**
 * 根据ID获取array的下标
 */
function getArrayIdxById(array,item){
	var index = -1;
	for(var idx in array){
		index ++;
		if( array[idx].id == item.id ){
			return index;
		}
	}
	return -1;
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
function sqlViewEdit(commonService,dataUrl,templateUrl,ctrl,param,callback,size) {
	
	/*根据选中ID获取最新数据*/
	commonService.post(dataUrl,param.row,function(data){

		if(data.code == STATUS_CODE.SUCCESS){

			//获取最新数据到编辑页
			_.extend(param, data.result);
			commonService.show({templateUrl:templateUrl,controller:ctrl,param,callback:callback,size:(size||40)});
		}else{
			$.error(data.message);
		}
	});
	
//	if(param.row){
//	}else{
//		commonService.show({templateUrl:templateUrl,controller:ctrl,param,callback:callback,size:(size||40)});
//	}
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
