/**
 * Created by zkning on 2016/6/5.
 */

/**session失效**/
function sessionInvalid(){
	if (false){
		top.location.href = location.href; 
	}
} 


$.warning = function warning(info){
	$.Notification.notify('warning','bottom right','', info);
}

$.error = function error(info){
	$.Notification.notify('error','bottom right','', info);
}

$.info =  function(info){
	$.Notification.notify('info','bottom right','', info);
}

$.uuid = function() {
	  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		  var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
		  return v.toString(16);
	  });
}