package com.cc4j.test;

import java.util.Random;

public class testRandom {

	public static void main(String[] args) {
		Random rand = new Random();
		int randValue  = rand.nextInt(100);
		System.out.println(randValue);
	}

}
