package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    List<PriorityNode<T>> items;

    private int insertIndex = 0; //index where the next item will be inserted
    private int size = 0;

    //to optimize contains(), use a hash set to track what items are in the heap
    Set<T> itemSet = new HashSet<>();

    //to optimize changePriority() we need a hash map to map
    //items with their indices in the heap
    Map<T, Integer> itemIndexMap = new HashMap<>();

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> temp = items.get(a);
        items.set(a, items.get(b));
        items.set(b, temp);
    }

    @Override
    public void add(T item, double priority) {

        /*if we have no space left, we have to resize, but ArrayLists resize for us,
         *so we don't have to make our own resize method
         */

        //start by checking if the heap already contains the item
        if (this.contains(item)) {
            throw new IllegalArgumentException();
        }

        //add the item to the last slot in the array as well as the hash set
        PriorityNode<T> newItem = new PriorityNode<>(item, priority);
        items.add(newItem);
        itemSet.add(item);

        //percolate/heapify up as needed
        heapifyUp(newItem, insertIndex);

        insertIndex++;
        size++;

    }

    public void heapifyUp(PriorityNode<T> newItem, int currIndex) {
        int parentIndex = (currIndex - 1) / 2;

        boolean parentIsBiggerThanChild = items.get(parentIndex).getPriority() > newItem.getPriority();
        while (parentIsBiggerThanChild && currIndex > 0) {
            swap(parentIndex, currIndex);
            currIndex = parentIndex;
            parentIndex = (currIndex - 1) / 2;
            parentIsBiggerThanChild = items.get(parentIndex).getPriority() > newItem.getPriority();
        }

        //add the item to the hash map
        itemIndexMap.put(newItem.getItem(), currIndex);
    }

    @Override
    public boolean contains(T item) {
        return itemSet.contains(item);
    }

    @Override
    public T peekMin() {

        //if there are no items, just return null
        if (size == 0) {
            throw new NoSuchElementException();
        }

        //return the very first item
        return items.get(0).getItem();
    }

    @Override
    public T removeMin() {

        //if there are no items in the heap, just return null
        if (size == 0) {
            throw new NoSuchElementException();
        }

        //get the min by taking the top
        PriorityNode<T> min = items.get(0);

        //remove the min from the set and hashmap
        itemSet.remove(min.getItem());
        itemIndexMap.remove(min.getItem());
        size--;

        //put the last element at the top
        PriorityNode<T> lastElement = items.get(insertIndex - 1);
        items.set(insertIndex - 1, null); //remove the last element
        insertIndex--;
        items.set(0, lastElement);

        //heapify the last element down to the correct spot
        heapifyDown(0);

        return min.getItem();
    }

    public void heapifyDown(int currentIndex) {

        PriorityNode<T> lastElement = items.get(currentIndex);
        //percolate/heapify down
        boolean childIsSmallerThanCurrent;
        double leftPriority = Integer.MAX_VALUE;
        double rightPriority = Integer.MAX_VALUE;
        int leftIndex = 1;
        int rightIndex = 2;
        if (leftIndex < this.size()) {
            leftPriority = items.get(leftIndex).getPriority();
        }
        if (rightIndex < this.size()) {
            rightPriority = items.get(rightIndex).getPriority();
        }
        childIsSmallerThanCurrent = lastElement.getPriority() > leftPriority
            || lastElement.getPriority() > rightPriority;

        while (childIsSmallerThanCurrent) {
            //determine which child is smaller
            if (leftPriority <= rightPriority) {
                swap(currentIndex, leftIndex);
                currentIndex = leftIndex;
            } else if (rightPriority < leftPriority) {
                swap(currentIndex, rightIndex);
                currentIndex = rightIndex;
            }

            leftIndex = 2 * currentIndex + 1;
            rightIndex = 2 * currentIndex + 2;
            leftPriority = Integer.MAX_VALUE;
            rightPriority = Integer.MAX_VALUE;
            if (leftIndex < this.size()) {
                leftPriority = items.get(leftIndex).getPriority();
            }
            if (rightIndex < this.size()) {
                rightPriority = items.get(rightIndex).getPriority();
            }
            childIsSmallerThanCurrent = lastElement.getPriority() > leftPriority
                || lastElement.getPriority() > rightPriority;

        }

        itemIndexMap.put(lastElement.getItem(), currentIndex);
    }

    @Override
    public void changePriority(T item, double priority) {

        //if the heap doesn't contain the item we want, just return
        if (!itemSet.contains(item)) {
            throw new NoSuchElementException();
        }

        //access the item's index that we want using the hash map
        //then percolate/heapify up or down as needed
        int currIndex = itemIndexMap.get(item);

        //remove the previous item-index mapping
        itemIndexMap.remove(item);

        int parentIndex = (currIndex - 1) / 2;
        int leftChildIndex = currIndex * 2 + 1;
        int rightChildIndex = currIndex * 2 + 2;

        PriorityNode<T> newItem = new PriorityNode<>(item, priority);

        //determine if we need to heapify upwards
        if (items.get(parentIndex).getPriority() > items.get(currIndex).getPriority()) {
            heapifyUp(newItem, currIndex);
        } else if (items.get(parentIndex).getPriority() < items.get(currIndex).getPriority()) {
            heapifyDown(currIndex);
        }

    }

    @Override
    public int size() {
        return size;
    }
}
