package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        //I just generalized this method for any length
        //for the sake of practice
        ListNode curr = list.front;
        ListNode prev = null;
        while (curr.next != null) {
            ListNode next = curr.next; //save the next so it doesn't get lost when we redirect curr's pointer
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        curr.next = prev;
        list.front = curr;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {

        if (list.front == null || list.front.next == null) {
            return;
        }

        ListNode originalFirst = list.front;
        list.front = originalFirst.next;
        ListNode curr = list.front;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = originalFirst;
        originalFirst.next = null;
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {

        LinkedIntList newList = new LinkedIntList();
        ListNode currNewNode = null;
        ListNode currA = null;
        ListNode currB = null;
        if (a.front != null) {
            newList.front = new ListNode(a.front.data);
            currNewNode = newList.front;
            currA = a.front.next;
            while (currA != null) {
                currNewNode.next = new ListNode(currA.data);
                currNewNode = currNewNode.next;
                currA = currA.next;
            }
            currB = b.front;
        } else if (b.front != null) {
            newList.front = new ListNode(b.front.data);
            currNewNode = newList.front;
            currB = b.front.next;
        }

        while (currB != null) {
            currNewNode.next = new ListNode(currB.data);
            currNewNode = currNewNode.next;
            currB = currB.next;
        }

        return newList;


    }
}
