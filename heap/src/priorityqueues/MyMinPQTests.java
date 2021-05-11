package priorityqueues;

import priorityqueues.ArrayHeapMinPQ;

public class MyMinPQTests {

    public static void main(String[] args) {
        ArrayHeapMinPQ<Integer> testQ = new ArrayHeapMinPQ<>();
        testQ.add(1,1);
        testQ.add(3,3);
        testQ.add(2,2);
        testQ.removeMin();
        testQ.add(1,1);
        testQ.add(4,4);
        testQ.removeMin();
        testQ.removeMin();


        testQ.printHeap();

    }

}
