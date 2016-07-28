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


function saveOf(url,param,$uibModalInstance){
	$.ajax({  
		type : "post",  
		url : url,  
		data:param,
		async : true,  
		contentType:'application/json',
		dataType:'json',
		success : function(data){
			
			if(data.code = '0'){
				$scope.data.code = data.result;
			}else{
				$.error(data.message);
			}
		}  
	});
};