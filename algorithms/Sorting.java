import java.util.Arrays;

class Sorting {
	
	static class MergeSort {
		
		public static int[] sort(int[] arr) {
			if (arr.length < 2) {
				return arr;
			}
			
			final int pivotIdx = arr.length / 2;
			final int[] left = new int[pivotIdx];
			final int[] right = new int[arr.length - pivotIdx];
			
			System.arraycopy(arr, 0, left, 0, pivotIdx);
			System.arraycopy(arr, pivotIdx, right, 0, arr.length - pivotIdx);
						
			return merge(sort(left), sort(right));
		}
		
		private static int[] merge(int[] left, int[] right) {
			int[] arr = new int[left.length + right.length];			
			int i = 0;
			int li = 0;
			int ri = 0;			
			while (li < left.length && ri < right.length) {
				if (left[li] < right[ri]) {
					arr[i++] = left[li++];
				} else {
					arr[i++] = right[ri++];
				}
			}
			while (li < left.length) {
				arr[i++] = left[li++];
			}
			while (ri < right.length) {
				arr[i++] = right[ri++];
			}
			return arr;
		}		
	}
	
	public static void main(String[] args) {
		assertEquals(new int[]{}, Sorting.MergeSort.sort(new int[]{}));
		assertEquals(new int[]{1}, Sorting.MergeSort.sort(new int[]{1}));
		assertEquals(new int[]{1,2}, Sorting.MergeSort.sort(new int[]{1,2}));
		assertEquals(new int[]{1,2}, Sorting.MergeSort.sort(new int[]{2,1}));
		assertEquals(new int[]{1,2,3,4,5}, Sorting.MergeSort.sort(new int[]{1,2,3,4,5}));
		assertEquals(new int[]{1,2,3,4,5}, Sorting.MergeSort.sort(new int[]{5,4,3,2,1}));
		assertEquals(new int[]{1,2,3,4,5}, Sorting.MergeSort.sort(new int[]{4,1,5,2,3}));
	}
	
	static void assertEquals(int[] expected, int[] actual) {
		if (!Arrays.toString(expected).equals(Arrays.toString(actual))) {
			throw new RuntimeException("Expected " + Arrays.toString(expected) + ", got " + Arrays.toString(actual));
		}
	}
}