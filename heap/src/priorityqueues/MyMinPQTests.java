package priorityqueues;

import priorityqueues.ArrayHeapMinPQ;

public class MyMinPQTests {

    public static void main(String[] args) {
        ArrayHeapMinPQ<Integer> testQ = new ArrayHeapMinPQ<>();
        testQ.add(2,2);
        System.out.println(testQ.size());
        testQ.add(3,3);
        System.out.println(testQ.size());
        testQ.removeMin();
        System.out.println(testQ.size());
    }

}
