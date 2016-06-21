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
