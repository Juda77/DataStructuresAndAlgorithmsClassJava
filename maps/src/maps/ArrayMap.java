package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;

    // You may add extra fields or helper methods though!
    private int size;
    private int nextInsertIndex;

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
        size = 0;
        nextInsertIndex = 0;
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {

        size = 0;
        nextInsertIndex = 0;
        //if the programmer gives us an invalid initialCapacity,
        //just use the default initial capacity specified by the class
        if (initialCapacity <= 0) {
            initialCapacity = DEFAULT_INITIAL_CAPACITY;
        }

        this.entries = this.createArrayOfEntries(initialCapacity);

    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {

        for (int i = 0; i < size; i++) {
            if (entries[i] == null) {
                continue;
            }
            K curr = entries[i].getKey();
            if (Objects.equals(curr, key)) {
                return entries[i].getValue();
            }
        }
        return null;

    }

    @Override
    public V put(K key, V value) {
        if (size == entries.length) {
            doubleSize();
        }

        for (int i = 0; i < size; i++) {
            if (entries[i] == null) {
                continue;
            } else if (Objects.equals(entries[i].getKey(), key)) {
                V oldValue = entries[i].getValue();
                entries[i] = new SimpleEntry<K, V>(key, value);
                return oldValue;
            }
        }

        SimpleEntry<K, V> newEntry = new SimpleEntry<>(key, value);
        entries[nextInsertIndex] = newEntry;
        size++;
        nextInsertIndex++;
        return null;

    }

    //double the size of the internal array
    @SuppressWarnings("unchecked")
    private void doubleSize() {
        SimpleEntry<K, V>[] resizedArray = createArrayOfEntries(2 * entries.length);
        for (int i = 0; i < entries.length; i++) {
            resizedArray[i] = entries[i];
        }
        entries = resizedArray;
    }

    @Override
    public V remove(Object key) {

        V val = null;
        for (int i = 0; i < size; i++) {
            if (entries[i] == null) {
                continue;
            } else if (Objects.equals(entries[i].getKey(), key)) {
                val = entries[i].getValue();
                entries[i] = null;
                if (i < size - 1) {
                    entries[i] = entries[size - 1];
                    entries[size - 1] = null;
                }
                size--;
                break;
            }
        }

        return val;

    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            entries[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < size; i++) {
            if (entries[i] == null) {
                continue;
            }
            if (Objects.equals(entries[i].getKey(), key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    //O(1)
    public int size() {
        return size;
    }

    @Override
    //O(1)
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries);
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        private int currIndex;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
            currIndex = 0;
        }

        @Override
        public boolean hasNext() {

            if (currIndex == entries.length || entries[currIndex] == null) {
                return false;
            }
            return true;

        }

        @Override
        public Map.Entry<K, V> next() {

            if (currIndex == entries.length || entries[currIndex] == null) {
                throw new NoSuchElementException();
            }
            Map.Entry<K, V> nextElement = entries[currIndex];
            currIndex++;
            return nextElement;

        }
    }
}
