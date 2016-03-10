package com.mk.hms.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 
 * 序号生成器（按时间次序生成）
 * @author:章辉
 * @version:1.0.0 
 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述] 
 */
public class OrderGenerator {
	private static final char[] orderChars = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private static Lock lock = new ReentrantLock();
	private static long oldOrder;
	
	
	/**
	 * 生成新的序号
	 * @return 序员字符串
	 * @author:章辉
	 */
	public static String newOrder(){
		long order = newOrderId();
		StringBuilder sb = new StringBuilder(10);
		
		while(order > 0 ){
			int remainder = (int) (order % 36);
			char nChar = orderChars[remainder];
			sb.insert(0, nChar);
			order = order / 36;
		}
		
		return sb.toString();
	}
	
	/**
	 * 生成新的序号
	 * @return
	 * @author:章辉
	 */
	public static long newOrderId(){
		long order = System.currentTimeMillis();
		
		//加锁
		lock.lock();
		
		if(order <= oldOrder){
			order = oldOrder + 1;
		}
		oldOrder = order;
		
		//释放锁
		lock.unlock();
		
		return order;
	}
	
	public static void main(String[] args) {
		System.out.println(OrderGenerator.newOrderId());
	}
}
