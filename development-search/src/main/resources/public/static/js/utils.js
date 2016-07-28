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


function saveOf($http,url,param,$uibModalInstance){
	$http.post(url,param).success(function(data){
		 if(data.code = '0'){
	       		$uibModalInstance.close(data.result);
	       	  }else{
	       		 $.error(data.message);
	       	  }
		});	
};