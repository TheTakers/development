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