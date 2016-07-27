package edu.bit.java.sungjin;

class QuickSort {

	public static int[] getSort(int[] arr, int pivot, int len) {

		int temp;
		int right = len;
		int left = pivot;

		if (pivot == len)
			return arr;

		for (int i = pivot; i <= len; i++) { 
			if (arr[left] < arr[right]) { 
				right = right - 1;
				continue;
			} else {
				temp = arr[left]; 
				arr[left] = arr[right]; 
				arr[right] = temp; 
				if (left == right) {
					/*System.out.println("pivot!!" + left);
					System.out.println("len!!" + right);*/
					getSort(arr, pivot, (left - 1));
					getSort(arr, (left + 1), len);
					break;
				} else { 
					left = left + 1; 
				}
			}
		} 
		return arr;
	}
}

public class QuickSortTest {

	public static void main(String[] args) {
		
		int[] arr = new int[] { 41, 11, 5, 10, 160, 120, 150, 120, 110 };
		int[] arr2 = QuickSort.getSort(arr, 0, arr.length - 1);
	}
}