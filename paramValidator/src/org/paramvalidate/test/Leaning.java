package org.paramvalidate.test;

import java.text.MessageFormat;
import java.util.Date;

public class Leaning {
	public static void main(String[] args) {
		int planet = 7;
		String event = "a disturbance in the Force";
		String s = MessageFormat.format("����ʱ����{1,time} on ������{1,date}���ַ���{2}����ʲô����{0}",
				planet,new Date(),event);
		System.out.println(s);
	}
}
