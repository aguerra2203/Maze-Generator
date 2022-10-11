import java.util.LinkedList;

public class DLLQueue<E> implements Queue<E> {
	private LinkedList<E> queue;

	public DLLQueue() {
		queue = new LinkedList<E>();
	}

	@Override
	public void enqueue(E v) {
		queue.addLast(v);

	}

	@Override
	public E dequeue() {
		return queue.removeFirst();
	}

	@Override
	public E first() {
		return queue.getFirst();
	}

	@Override
	public int size() {
		return queue.size();
	}
	
	public E top() {
		return queue.get(0);
	}

	@Override
	public boolean isEmpty() {
		return queue.size() == 0;
	}

}
