import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> { 

	private Node first;
	private Node last;
	private int count = 0;
	
	
	private class Node { 
		Item item;
		Node next;
		Node previous;
	}
	
	public Deque() {
	}
	public boolean isEmpty() {
	   return first == null;
	}
	public int size() {
	   return count;
	}
	public void addFirst(Item item) {
	    if (item == null) {
	           throw new IllegalArgumentException("item is null"); 
	    }
		Node newNode = new Node();
		newNode.item = item;
		newNode.next = first;
		newNode.previous = null;
		if (isEmpty()) {
			first = newNode;
			last = newNode;
		}
		else {
			first.previous = newNode;
			first = newNode;
		}
		count++;
	}
	public void addLast(Item item) {
	    if (item == null) {
	           throw new IllegalArgumentException("item is null"); 
	    }
		Node newNode = new Node();
		newNode.item = item;
		newNode.next = null;
		newNode.previous = last;
		if (isEmpty()) {
			first = newNode;
			last = newNode;
		}
		else {
			last.next = newNode;
			last = newNode;
		}
		count++;
	}
	public Item removeFirst() {
	    if (isEmpty()) {
	           throw new NoSuchElementException("queue is empty");
	    }
		Node oldFirst = first;
		first = first.next;	
		if (first != null)
		    first.previous = null;
		else last = null;
		count--;
		return oldFirst.item;
	}
	public Item removeLast() {
	    if (isEmpty()) {
	           throw new NoSuchElementException("queue is empty");
	    }
		Node oldLast = last;
		last = last.previous;
		if (last != null)
		    last.next = null;
		else first = null;
		count--;
		return oldLast.item;
	}
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() {  return current != null;  } 
        public void remove() {
            throw new UnsupportedOperationException("unsupported operation");
        }
        public Item next()
        {
            if (current == null) {
                throw new NoSuchElementException("no more items to return");
            }
            Item item = current.item;
            current   = current.next; 
            return item;
        }
    }

	
	public static void main(String[] args) {
	       
	       Deque<String> deq;
	       
	       StdOut.println("0 - test isEmpty()");
	       StdOut.println("1 - test size()");
	       StdOut.println("2 - test addFirst()");
	       StdOut.println("3 - test addLast()");
	       StdOut.println("4 - test removeFirst()");
	       StdOut.println("5 - test removeLast()");
	       StdOut.println("6 - test iterator()");
	       StdOut.println("7 - test all");
	       StdOut.println("8 - exit");
	       int choice = StdIn.readInt();
	       Iterator<String> it;
	       switch(choice) {
	       case(0):
	           // testing isEmpty()
	           
	           deq = new Deque<String>();
    	       deq.addFirst("LNlnl");
               deq.addFirst("vsd");
               deq.addFirst("dfg");
	           
	           it = deq.iterator();
               while (it.hasNext()) {
                   String s = it.next();
                   StdOut.println(s);
               }
	           
	           for (int i = 0; i < 3; i++) {
	               StdOut.println(deq.removeFirst());
	               StdOut.println("Is queue empty: " + deq.isEmpty());
	           }
	       
	           break;
	           
	       case(1):
	           // testing size()
	           
	           deq = new Deque<String>();
	           for (int i = 0; i < 3; i++) {
	               String s = StdIn.readString();
	               deq.addFirst(s);
	               StdOut.println("Size: " + deq.size());
	           }
	           for (int i = 0; i < 3; i++) {
	               StdOut.println(deq.removeFirst());
	               StdOut.println("Size: " + deq.size());
	           }
	           
	           break;
	           
	       case(2):
	           // testing addFirst()
	           
	           deq = new Deque<String>();
               deq.addFirst("LNlnl");
               deq.addFirst("vsd");
               deq.addFirst("dfg");
               it = deq.iterator();
               while (it.hasNext()) {
                   String s = it.next();
                   StdOut.println(s);
               }
               deq.addFirst(null);
	           
	           break;
	           
           case(3):
               // testing addLast()
               
               deq = new Deque<String>();
               deq.addLast("LNlnl");
               deq.addLast("vsd");
               deq.addLast("dfg");
               it = deq.iterator();
               while (it.hasNext()) {
                   String s = it.next();
                   StdOut.println(s);
               }
               deq.addLast(null);
               
               break;
               
           case(4):
               // testing removeFirst()
               
               deq = new Deque<String>();
               deq.addFirst("LNlnl");
               deq.addLast("vsd");
               StdOut.println(deq.removeFirst());
               deq.addLast("dfg");
               StdOut.println(deq.removeFirst());
               it = deq.iterator();
               while (it.hasNext()) {
                   String s = it.next();
                   StdOut.println(s);
               }
               StdOut.println(deq.removeFirst());
               StdOut.println(deq.removeFirst());
               
               break;

	       case(5):
	           // testing removeLast()
	           
               deq = new Deque<String>();
               deq.addFirst("LNlnl");
               deq.addLast("vsd");
               StdOut.println(deq.removeLast());
               deq.addLast("dfg");
               StdOut.println(deq.removeLast());
               it = deq.iterator();
               while (it.hasNext()) {
                   String s = it.next();
                   StdOut.println(s);
               }
               StdOut.println(deq.removeLast());
               StdOut.println(deq.removeLast());
               
	           break;
	           
	       case(6):
	           // testing iterator()
	           
	           deq = new Deque<String>();
               deq.addFirst("LNlnl");
               deq.addFirst("vsd");
               deq.addFirst("dfg");
               deq.addFirst("dgdf");
               deq.addFirst("aefwef");
               deq.addFirst("qweq");
               it = deq.iterator();
               while (it.hasNext()) {
                   String s = it.next();
                   StdOut.println(s);
               }
	           
	           break;
	           
	       case(7):
	           // testing all()
	        
	           deq = new Deque<String>();
    	       deq.addFirst("LNlnl");
               deq.addFirst("vsd");
               deq.addLast("dfg");
               deq.addFirst("dgdf");
               deq.addLast("aefwef");
               deq.addFirst("qweq");
               it = deq.iterator();
               while (it.hasNext()) {
                   String s = it.next();
                   StdOut.println(s);
               }
               StdOut.println("removing: ");
               StdOut.println(deq.removeLast());
               StdOut.println(deq.removeFirst());
               StdOut.println(deq.removeFirst());
               StdOut.println(deq.removeLast());
               StdOut.println("checking left: ");
               it = deq.iterator();
               while (it.hasNext()) {
                   String s = it.next();
                   StdOut.println(s);
               }
               
               
	           break;
	           
	       case(8):
	           // end of testing
	           
	           break;
	           
	       default:  StdOut.println("end");
	           break;
	       }
	}
}

