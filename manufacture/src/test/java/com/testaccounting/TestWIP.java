package com.testaccounting;

import com.mirzaakhena.batchsystem.model.WIPCalculator;
import com.mirzaakhena.batchsystem.model.WIPItem;

public class TestWIP {

	public static void main(String[] args) {
		
		WIPCalculator w= new WIPCalculator();
		
		w.start(100, new WIPItem(530000, 20000, 30000));
		System.out.println(w);
		
		w.finish(90, 80, 80);
		System.out.println(w);
		
	}
	
}
