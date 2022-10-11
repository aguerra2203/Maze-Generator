
public interface Queue<E> {
	public abstract void enqueue(E v);
	
	public abstract E dequeue();
	
	public abstract E first();
	
	public abstract int size();
	
	public abstract boolean isEmpty();
	
	public abstract String toString();

}
