package PriorityQueue.Classes;


import classes.DList;
import classes.DList.DNode;
import interfaces.IPriorityQueue;


public class PriorityQueue implements IPriorityQueue {
	
	DList pQueue = new DList();
	
	public class Pair {
		
		private Object item;
		private int key;
		
		public Pair(Object a, int k) {
			
			item = a;
			key = k;
		}
		
	}

	@Override
	public void insert(Object item, int key) {
		
		if (item == null) {
			throw new RuntimeException ("Item is null.");
		}
		
		Pair in = new Pair(item,key);
		
		if (pQueue.isEmpty() ) {
			
			pQueue.add(in);
		}
		
		else {
			
			DNode temp = pQueue.header.getNext();
			DList test = new DList();
			DNode node = test.new DNode(in,null,null);
			while (temp != null) {
				
				Pair current = (Pair)temp.getElement();
				
				if (temp == pQueue.trailer || current.key > in.key ) {
					
					DNode prev = temp.getPrev();
					prev.setNext(node);
					temp.setPrev(node);
					node.setPrev(prev);
					node.setNext(temp);
					break;
				}
				else {
					temp = temp.getNext();
				}
			}
		}
		
		
	}

	@Override
	public Object removeMin() {
		
		DNode min = pQueue.header.getNext();
		if(min == pQueue.trailer) {
			throw new NullPointerException("Priority Queue is empty.");
		}
		DNode next = min.getNext();
		pQueue.header.setNext(next);
		next.setPrev(pQueue.header);
		Pair result = (Pair)min.getElement();
		
		
		return (Object)result.item;
	}

	@Override
	public Object min() {
		
		DNode min = pQueue.header.getNext();
		if(min == pQueue.trailer) {
			throw new NullPointerException("Priority Queue is empty.");
		}
		
		Pair result = (Pair)min.getElement();
		
		return (Object)result.item;
	}

	@Override
	public boolean isEmpty() {

		
		return (pQueue.size() == 0);
	}

	@Override
	public int size() {
		
		return pQueue.size();
	}

}
