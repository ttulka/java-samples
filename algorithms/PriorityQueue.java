import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class PriorityQueue<T extends Comparable> {
	
	private final List<T> heap = new ArrayList<>();
	
	public void add(T value) {
		heap.add(value);
		swim(heap, heap.size() - 1);
	}
	
	public T remove() {
		return heap.remove(heap.size() - 1);
	}

	private void swim(List<T> heap, int index) {
		int parentIndex = parentIndex(index);
		if (heap.get(index).compareTo(heap.get(parentIndex)) > 0) {
			swap(heap, index, parentIndex); 
			swim(heap, parentIndex);
		}
	}
	
	private void swap(List<T> list, int index1, int index2) {
		T value = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, value);
	}
	
	private int parentIndex(int index) {
		return index / 2;
	}
	
	@Override
	public String toString() {
		return heap.toString();
	}
	
	public static void main(String[] args) {
		PriorityQueue<Integer> queue = new PriorityQueue<>();
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