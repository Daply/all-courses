import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        // int k = StdIn.readInt();
        RandomizedQueue<String> rnq;
        rnq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (str.equals("stop"))
                break;
            rnq.enqueue(str);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rnq.dequeue());
        }
    }
}
