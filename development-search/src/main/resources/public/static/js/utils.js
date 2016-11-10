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
		if(data.code == STATUS_CODE.SUCCESS){
			$uibModalInstance.close(data.result);
		}else{
			$.error(data.message);
		}
	});	
};
 
/**
 * 直接调用远程接口
 * @param commonService
 * @param url  接口url
 * @param param {item:row}
 * @param callback
 */
function action(commonService,url,param,callback){
	$.confirm({
		confirm: function(){
			commonService.post(url,param,function(data){
				if(data.code == STATUS_CODE.SUCCESS){
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
/**
 * 列删除
 * @param commonService
 * @param url
 * @param param
 * @param callback
 */
function remove(commonService,url,param,callback) {

	$.confirm({
		confirm: function(){
			commonService.post(url,param,function(data){
				if(data.code == STATUS_CODE.SUCCESS){
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
