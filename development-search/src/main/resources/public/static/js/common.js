/**
 * Created by zkning on 2016/6/5.
 */

// trap  TODO not 
$.shadeId = "";

//shade tab content
$.shadetab = function shadetab(){
	
	if(_.isEmpty($.shadeId))
		return null;
	
	var $shade = $("#shade"+$.shadeId);
	var $parent = $($shade.parent());
	$shade.css({"width":$parent.width(),"height":$parent.height()});
	$shade.css({'left':$parent.offset().left,'top':$parent.offset().top});

	//show
	$shade.show();
	return $shade;
}

$.trash = function trash($shade){
	
	if(!_.isEmpty($shade))
		$shade.hide();
}

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

/**
 * new Date().format("yyyyMMddhhmmss")
 * 时间对象的格式化;
 */
Date.prototype.format = function(format) {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	}

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}