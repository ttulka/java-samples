import java.util.Arrays;

class PriorityQueue {
	
	private static final int DEFAULT_CAPACITY = 2;
	
	private int[] heap;
	private int length;
	
	public PriorityQueue(int initCapacity) {
		this.heap = new int[initCapacity];
	}
	
	public PriorityQueue() {
		this(DEFAULT_CAPACITY);
	}
	
	public void add(int value) {
		if (length >= heap.length) {
			blowUp();
		}
		heap[length++] = value;
		swim(heap, length - 1);
	}
	
	public int remove() {
		return heap[--length];
	}

	private void swim(int[] heap, int index) {
		int parentIndex = parentIndex(index);
		if (heap[index] > heap[parentIndex]) {
			swap(heap, index, parentIndex); 
			swim(heap, parentIndex);
		}
	}
	
	private void swap(int[] arr, int index1, int index2) {
		int value = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = value;
	}
	
	private int parentIndex(int index) {
		return index / 2;
	}
	
	private void blowUp() {
		int[] buffer = new int[length + DEFAULT_CAPACITY];
		System.arraycopy(heap, 0, buffer, 0, length);
		heap = buffer;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(Arrays.copyOf(heap, length));
	}
	
	public static void main(String[] args) {
		PriorityQueue queue = new PriorityQueue();
		queue.add(3);
		queue.add(1);
		queue.add(5);
		queue.add(2);
		queue.add(4);

		System.out.println(queue);

		assertEquals(1, queue.remove());
		assertEquals(2, queue.remove());
		assertEquals(3, queue.remove());
		assertEquals(4, queue.remove());
		assertEquals(5, queue.remove());
	}
	
	static void assertEquals(Object expected, Object actual) {
		if (!expected.equals(actual)) {
			throw new RuntimeException("Expected " + expected + ", got " + actual);
		}
	}
}