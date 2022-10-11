import java.util.LinkedList;

public class DLLStack<T> implements Stack<T> {
	LinkedList<T> stack;
	
	public DLLStack() {
		stack = new LinkedList<T>();
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return stack.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return stack.isEmpty();
	}

	@Override
	public void push(T v) {
		stack.addFirst(v);

	}

	@Override
	public T pop() {
		return stack.removeFirst();		
	}

	@Override
	public T top() {
		return stack.get(0);
	}

}
