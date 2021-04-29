package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {

    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1.0;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 11;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 11;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!
    private double resizingLoadFactorThreshold;
    private int chainCount;
    private int chainCapacity;
    private int size;

    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;
        this.chainCount = initialChainCount;
        this.chainCapacity = chainInitialCapacity;
        this.size = 0;
        chains = createArrayOfChains(this.chainCount);
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    public int getIndex(Object key) {
        int hashCode = 0;

        //compute hash code
        if (key != null) {
            hashCode = key.hashCode();
        }

        if (hashCode < 0) {
            hashCode *= -1;
        }

        int index = hashCode % chains.length;
        return index;
    }

    @Override
    public V get(Object key) {
        //compute hash code
        int index = getIndex(key);
        if (chains[index] == null) {
            return null;
        }
        return chains[index].get(key);

    }

    @Override
    public V put(K key, V value) {

        V val = null;
        if (this.containsKey(key)) {
            val = this.get(key);
            this.remove(key);
            size--;
        }

        size++; //increment size for inserting the new element
        double loadFactor = size / chains.length;
        if (loadFactor >= resizingLoadFactorThreshold) {
            resize();
        }

        //determine the chain to insert at
        int index = getIndex(key);

        if (chains[index] == null) { //if the index currently has no chain, make one
            chains[index] = createChain(this.chainCapacity);

            chainCount++;
        }

        chains[index].put(key, value);
        return val;

    }

    private void resize() {

        AbstractIterableMap<K, V>[] biggerChains = createArrayOfChains(chains.length * 2);

        Iterator<Map.Entry<K, V>> iter = iterator();
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = iter.next();
            K key = entry.getKey();
            V value = entry.getValue();
            //determine the chain to insert at
            int index = getIndex(key);

            if (biggerChains[index] == null) { //if the index currently has no chain, make one
                biggerChains[index] = createChain(this.chainCapacity);
            }
            biggerChains[index].put(key, value);

        }

        chains = biggerChains;
    }

    @Override
    public V remove(Object key) {

        //determine chain that the key would be in
        int index = getIndex(key);
        if (chains[index] == null) {
            return null; //no chain at index
        } else {

            V entry = chains[index].remove(key);

            if (entry != null) {
                size--;
            }

            if (chains[index].size() == 0) {
                chains[index] = null;
                chainCount--;
            }
            return entry;
        }

    }

    @Override
    public void clear() {
        size = 0;
        this.chains = createArrayOfChains(this.chainCount);
    }

    @Override
    public boolean containsKey(Object key) {
        int index = getIndex(key);
        if (chains[index] == null) {
            return false;
        }

        return chains[index].containsKey(key);

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        // You may add more fields and constructor parameters
        int currChain = 0;
        Iterator<Map.Entry<K, V>> iteratorForChain;
        boolean chainsIsEmpty;

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {

            this.chains = chains;
            currChain--;
            getToNextChain();
            if (currChain != chains.length) {
                chainsIsEmpty = false;
                iteratorForChain = chains[currChain].iterator();
            } else {
                chainsIsEmpty = true;
            }


        }

        @Override
        public boolean hasNext() {

            if (chainsIsEmpty) {
                return false;
            } else if (iteratorForChain.hasNext()) {
                return true;
            } else {

                getToNextChain();
                if (currChain >= chains.length) {
                    return false;
                } else {
                    iteratorForChain = chains[currChain].iterator();
                    return iteratorForChain.hasNext();
                }
            }


        }

        @Override
        public Map.Entry<K, V> next() {

            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else if (iteratorForChain.hasNext()) {
                return iteratorForChain.next();
            } else {

                getToNextChain();
                if (currChain == chains.length) {
                    iteratorForChain = null;
                    return null;
                } else {
                    iteratorForChain = chains[currChain].iterator();
                    return iteratorForChain.next();
                }

            }

        }

        private void getToNextChain() {

            do {
                currChain++;
            } while (currChain < chains.length && chains[currChain] == null);
        }
    }
}
