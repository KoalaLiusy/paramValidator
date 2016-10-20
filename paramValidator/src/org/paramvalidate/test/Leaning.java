package org.paramvalidate.test;

import java.text.MessageFormat;
import java.util.Date;

public class Leaning {
	public static void main(String[] args) {
		int planet = 7;
		String event = "a disturbance in the Force";
		String s = MessageFormat.format("参数时间是{1,time} on 日期是{1,date}，字符串{2}，有什么问题{0}",
				planet,new Date(),event);
		System.out.println(s);
	}
}
