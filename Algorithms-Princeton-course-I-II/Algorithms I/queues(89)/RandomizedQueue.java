import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] items;
	private int count = 0;

   public RandomizedQueue() {
       items = (Item[]) new Object[1];
   }
   
   public boolean isEmpty() {
       return count == 0;
   }
   
   public int size() {
	 return count;
   }
   
   public void enqueue(Item item) {
       if (item == null) {
           throw new IllegalArgumentException("item is null"); 
       }
       if (count == items.length) {
           resize(2*count);
       }
       items[count] = item;
       count++;
   }
   
   public Item dequeue() {
       if (isEmpty()) {
           throw new NoSuchElementException("queue is empty");
       }
       int random = StdRandom.uniform(count);
       Item item = items[random];
       
       // deleting by copying all elements except
       // one with random number
       if (item != null) {          
           items[random] = items[count-1];
           items[count-1] = null;
           count--;
       }
       
       
//       if (item != null) {
//           Item[] copy = (Item[]) new Object[items.length];
//           int i = 0;
//           for (i = 0; i < random; i++) {
//              copy[i] = items[i];
//           }
//           for (int j = random+1; j < items.length; j++) {
//               copy[i] = items[j];
//               i++;
//           }
//           items = copy;
//           count--;
//       }
       if (count > 0 && count == items.length/4) 
           resize(items.length/2);
       return item;
   }
   
   public Item sample() {
       if (isEmpty()) {
           throw new NoSuchElementException("queue is empty");
       }
       int random = StdRandom.uniform(count);
       Item item = items[random];
       return item;
   }
   
   private void resize(int capacity) {
       Item[] copy = (Item[]) new Object[capacity];
       for (int i = 0; i < count; i++)
          copy[i] = items[i];
       items = copy;
   }
   
   public Iterator<Item> iterator() {
	   return new RandomizedQueueIterator();
   }
   
   private class RandomizedQueueIterator implements Iterator<Item>
   {
       private int currentIndex = 0;
       public boolean hasNext() {  return items[currentIndex] != null;  }  
       public void remove() {
           throw new UnsupportedOperationException("unsupported operation");
       } 
       public Item next()
       {
           int random = StdRandom.uniform(currentIndex+1);
           if (random >= count) {
               throw new NoSuchElementException("no more items to return");
           }
           Item item = items[random];
           currentIndex++; 
           return item;
       }
   }
   
   public static void main(String[] args) {
       
       RandomizedQueue<String> rnq;
       
       StdOut.println("0 - test isEmpty()");
       StdOut.println("1 - test size()");
       StdOut.println("2 - test enqueue()");
       StdOut.println("3 - test dequeue()");
       StdOut.println("4 - test sample()");
       StdOut.println("5 - test iterator()");
       StdOut.println("6 - test all");
       StdOut.println("7 - one of tests");
       StdOut.println("8 - exit");
       int choice = StdIn.readInt();
       switch(choice){
       case(0):
           // testing isEmpty()
           
           rnq = new RandomizedQueue<String>();
           for (int i = 0; i < 3; i++) {
               String s = StdIn.readString();
               rnq.enqueue(s);
           }
           for (int i = 0; i < 3; i++) {
               StdOut.println(rnq.dequeue());
               StdOut.println("Is queue empty: " + rnq.isEmpty());
           }
       
           break;
           
       case(1):
           // testing size()
           
           rnq = new RandomizedQueue<String>();
           for (int i = 0; i < 3; i++) {
               String s = StdIn.readString();
               rnq.enqueue(s);
               StdOut.println("Size: " + rnq.size());
           }
           for (int i = 0; i < 3; i++) {
               StdOut.println(rnq.dequeue());
               StdOut.println("Size: " + rnq.size());
           }
           
           break;
           
       case(2):
           // testing enqueue()
           
           rnq = new RandomizedQueue<String>();
           rnq.enqueue("LNlnl");
           rnq.enqueue("vsd");
           rnq.enqueue(null);
           
           break;

       case(3):
           // testing dequeue()
           
           rnq = new RandomizedQueue<String>();
           rnq.enqueue("LNlnl");
           rnq.enqueue("vsd");
           StdOut.println(rnq.dequeue());
           StdOut.println(rnq.dequeue());
           rnq.enqueue("dfg");
           rnq.enqueue("vsoyikd");
           StdOut.println(rnq.dequeue());
           rnq.enqueue("hrr");
           rnq.enqueue("luil");
           StdOut.println(rnq.dequeue());
           StdOut.println(rnq.dequeue());
           StdOut.println(rnq.dequeue());
           
           break;

       case(4):
           // testing sample()
           
           rnq = new RandomizedQueue<String>();
           rnq.enqueue("LNlnl");
           rnq.enqueue("vsd");
           rnq.enqueue("dfg");
           rnq.enqueue("vsoyikd");
           rnq.enqueue("hrr");
           rnq.enqueue("luil");
           StdOut.println(rnq.sample());
           StdOut.println(rnq.sample());
           StdOut.println(rnq.sample());
           
           break;
           
       case(5):
           // testing iterator()
           
           rnq = new RandomizedQueue<String>();
           rnq.enqueue("LNlnl");
           rnq.enqueue("vsd");
           rnq.enqueue("dfg");
           rnq.enqueue("vsoyikd");
           rnq.enqueue("hrr");
           rnq.enqueue("luil");
           Iterator<String> it = rnq.iterator();
           while (it.hasNext()) {
               String s = it.next();
               StdOut.println(s);
           }    
           
           break;
           
       case(6):
           // testing all()
        
           int num = StdIn.readInt();
           rnq = new RandomizedQueue<String>();
           for (int i = 0; i < 9; i++) {
               String s = StdIn.readString();
               rnq.enqueue(s);
           }
           
           for (int i = 0; i < num; i++) {
               StdOut.println(rnq.dequeue());
           }
           
           break;
           
       case(7):
           // test

           rnq = new RandomizedQueue<String>();
           rnq.enqueue("IFKXBQPXCK");
           rnq.enqueue("BCEISEFNVE");
           rnq.enqueue("EGFEVQVWLA");
           StdOut.println(rnq.dequeue());     // ==> "BCEISEFNVE"
           rnq.enqueue("XSHPRFAZCH");
           StdOut.println(rnq.dequeue());     // ==> "null"
           
           break;
       case(8):
           // end
           break;
           
       default:  StdOut.println("end");
           break;
       }
       
   }
}