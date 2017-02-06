package com.sophia.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @author zkning
 */
@Component
public class ScheduledTasks {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	/**
	秒（0~59）
	分钟（0~59）
	小时（0~23）
	天（月）（0~31，但是你需要考虑你月的天数）
	月（0~11）
	天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
	年份（1970－2099）
	
	"0 15 10 * * ? 2005" 2005年的每天上午10:15触发 
	**/

	@Scheduled(cron="0 15 10 ? * ?")
	public void reportCurrentTime() {
		System.out.println("The time is now " + dateFormat.format(new Date()));
	}
}
