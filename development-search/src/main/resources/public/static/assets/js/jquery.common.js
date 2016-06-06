/**
 * Created by zkning on 2016/6/5.
 */

/**session失效**/
function sessionInvalid(){
	if (window != top){
		top.location.href = location.href; 
	}
}


$.warning = function warning(info){
	$.Notification.notify('warning','bottom right','警告', info);
}

$.error = function error(info){
	$.Notification.notify('error','bottom right','系统异常', info);
}

$.info =  function(info){
	$.Notification.notify('info','bottom right','提示', info);
}
