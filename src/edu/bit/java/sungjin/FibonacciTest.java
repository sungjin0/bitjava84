package edu.bit.java.sungjin;

class FibonacciSequence {

	public static int[] makeFibonacci(final int repeatnum) {

		int[] arr = new int[repeatnum];

		for (int i = 0; i < repeatnum; i++) {
			arr[i] = run(i + 1);
		}
		return arr;
	}

	private static int run(int pair) {

		if (pair == 1 || pair == 2) {
			return 1;
		} else {
			return run(pair - 2) + run(pair - 1);
		}
	}
}

public class FibonacciTest {

	public static void main(String[] args) {

		int[] fibonacci = FibonacciSequence.makeFibonacci(10);
	}
}