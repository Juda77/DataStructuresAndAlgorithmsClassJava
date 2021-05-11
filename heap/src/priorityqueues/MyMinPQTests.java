package priorityqueues;

import priorityqueues.ArrayHeapMinPQ;

public class MyMinPQTests {

    public static void main(String[] args) {
        ArrayHeapMinPQ<Integer> testQ = new ArrayHeapMinPQ<>();
        testQ.add(1,1);
        testQ.add(0,0);
        testQ.add(100,100);
        testQ.add(50,50);
        testQ.removeMin();


        testQ.printHeap();

    }

}
