package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.


    public LinkedDeque() {
        size = 0;
        front = (Node<T>) new Node<Integer>(2);
        back = (Node<T>) new Node<Integer>(1);
        front.next = back;
        back.prev = front;
    }

    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item);
        if (size == 0) {
            front.next = newNode;
            back.prev = newNode;
            newNode.prev = front;
            newNode.next = back;
            size++;
            return;
        }
        front.next.prev = newNode;
        newNode.next = front.next;
        front.next = newNode;
        newNode.prev = front;
        size++;

    }

    public void addLast(T item) {
        Node<T> newNode = new Node<>(item);
        if (size == 0) {
            front.next = newNode;
            back.prev = newNode;
            newNode.prev = front;
            newNode.next = back;
            size++;
            return;
        }
        back.prev.next = newNode;
        newNode.prev = back.prev;
        back.prev = newNode;
        newNode.next = back;
        size++;

    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        Node<T> first = front.next;
        if (size == 1) {
            front.next = back;
            back.prev = front;
        } else {
            front.next = front.next.next;
            front.next.prev = front;
        }

        size--;
        return first.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node<T> last = back.prev;
        if (size == 1) {
            front.next = back;
            back.prev = front;
        } else {
            back.prev = back.prev.prev;
            back.prev.next = back;
        }

        size--;
        return last.value;

    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> curr = front.next;
        int counter = 0;
        while (counter < index) {
            curr = curr.next;
            counter++;
        }
        return curr.value;
    }

    public int size() {
        return size;
    }
}
