import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author 
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private Node<T> head, tail;
	private int size;
	private int modCount;
	
	
	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		Node<T> newNode = new Node<T>(element);
		if(size == 0) {
			head = newNode;
			tail = newNode;
		} else {
			newNode.setNext(head);
			head = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		Node<T> newNode = new Node<T>(element);
		if(size == 0) {
			head = newNode;
			tail = newNode;
		} else {
			tail.setNext(newNode);
		}
		tail = newNode;
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		// TODO 
		Node<T> newNode = new Node<T>(element);
		if(size == 0) {
			head = newNode;
		} else {
			tail.setNext(newNode);
		}
		tail = newNode;
		size++;
		modCount++;
	}

	@Override
	public void addAfter(T element, T target) {
		Node<T> targetNode = head;
		boolean foundIt = false;
		while(targetNode != null && !foundIt) {
			if(targetNode.getElement().equals(target)) {
				foundIt = true;
			} else {
				targetNode = targetNode.getNext();
			}
		}
		if(!foundIt) {
			throw new NoSuchElementException();
		}
		
		Node<T> newNode = new Node<T>(element);
		newNode.setNext(targetNode.getNext());
		targetNode.setNext(newNode);
		if(targetNode == tail) {
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> newNode = new Node<T>(element);
		if(isEmpty()) {
			head = tail = newNode;
		}
		if(index == 0) {
			newNode.setNext(head);
			head = newNode;
		}
		
		if(index == size) {
			tail.setNext(newNode);
			tail = newNode;
		}
		
		else {
			Node<T> current = head;
			for(int i = 0; i < index; i++) {
				current = current.getNext();
			}
			//current.setNext(current.getNext());
			Node<T> next = current.getNext();
			current.setNext(newNode);
			newNode.setNext(next);
		}
		
		size++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		Node<T> retVal = head;
		head = retVal.getNext();
		if(size == 1) {
			head = tail = null;
		}
		size--;
		modCount++;
		
		return retVal.getElement();
	}

	@Override
	public T removeLast() {
		// TODO 
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return remove(size - 1);
	}

	@Override
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		boolean found = false;
		Node<T> previous = null;
		Node<T> current = head;
		
		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}
		
		if (!found) {
			throw new NoSuchElementException();
		}
		
		if (size() == 1) { //only node
			head = tail = null;
		} else if (current == head) { //first node
			head = current.getNext();
		} else if (current == tail) { //last node
			tail = previous;
			tail.setNext(null);
		} else { //somewhere in the middle
			previous.setNext(current.getNext());
		}
		
		size--;
		modCount++;
		
		return current.getElement();
	}

	@Override
	public T remove(int index) {
	
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> previous = null;
		Node<T> current = head;
		for(int i = 0; i < index; i++) {
			previous = current;
			current = current.getNext();
		}
		if (size() == 1) { //only node
			head = tail = null;
		} else if (current == head) { //first node
			head = current.getNext();
		} else if (current == tail) { //last node
			tail = previous;
			tail.setNext(null);
		} else { //somewhere in the middle
			previous.setNext(current.getNext());
		}
		size--;
		modCount++;
		
		return current.getElement();
	}

	@Override
	public void set(int index, T element) {
		if(index < 0 || index >= size || isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> newNode = new Node<T>(element);
		if(index == 0) {
			head = tail = newNode;
		}
		
		Node<T> current = head;
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		current = newNode;
		
		modCount++;
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= size || isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> current = head;
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current.getElement();
	}

	@Override
	public int indexOf(T element) {
		int index = -1;
		
		Node<T> current = head;
		int currentIndex = 0;
		while(current != null && index < 0){
			if(current.getElement().equals(element)) {
				index = currentIndex;
			}
			currentIndex++;
			current = current.getNext();
		}
		
		return index;
	}

	@Override
	public T first() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}
	
	public String toString() {
		String list = "[" ;
		Node<T> current = head;
		while(current != null) {
			list += current.getElement();
			if(current.getNext() != null) {
				list += ", ";
			}
			current = current.getNext();
		}
		
		return list + "]";
	}

	@Override
	public boolean contains(T target) {
		boolean contains = false;
		Node<T> current = head;
		while(current != null) {
			if(current.getElement().equals(target)) {
				contains = true;
				return contains;
			}
			current = current.getNext();
		}
		
		return contains;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private Node<T> nextNode;
		private Node<T> previousNode = null;
		private Node<T> secPreviousNode = null;
		private int iterModCount;
		private boolean canRemove;
		
		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
			if(size == 0) {
				nextNode = null;
			}
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			// TODO 
			return nextNode != null;
		}

		@Override
		public T next() {
			// TODO
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			T next = nextNode.getElement();
			secPreviousNode = previousNode;
			previousNode = nextNode;
			nextNode = nextNode.getNext();
			canRemove = true;
			return next;
		}
		
		@Override
		public void remove() {
			// TODO
			if(!canRemove) {
				throw new IllegalStateException();
			} else if(previousNode == tail && head != tail) {
				tail = secPreviousNode;
				secPreviousNode.setNext(null);
			} else if(head == tail) {
				head = null;
				tail = null;
			} else if(secPreviousNode != null) {
				secPreviousNode.setNext(previousNode);
				previousNode.setNext(null);
			} else {
				head = nextNode;
				previousNode.setNext(null);
			}
			size--;
			canRemove = false;
		}
	}
}
