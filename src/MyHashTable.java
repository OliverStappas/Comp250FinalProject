
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS

        this.buckets = new ArrayList<>(initialCapacity);
        this.numBuckets = Math.max(1,initialCapacity);
        this.numEntries = 0;

        for (int i = 0; i < numBuckets; i++) {
            // Initialising to null
            this.buckets.add(new LinkedList<>());
        }

        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        if (numBuckets == 0) {
        }
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
        HashPair<K,V> hashPair = new HashPair<>(key, value);

        int index = hashFunction(key);

        if (!this.buckets.get(index).isEmpty()) {
            for (HashPair<K,V> hPair : this.buckets.get(index)) {
                if (hPair.getKey().equals(key)) {
                    V oldValue = hPair.getValue();
                    hPair.setValue(value);
                    return  oldValue;
                }
            }
        }
        if ( (double) (this.numEntries + 1) / this.numBuckets > MAX_LOAD_FACTOR) {
            this.rehash();
        }
        this.numEntries += 1;
        this.buckets.get(hashFunction(key)).add(hashPair);
        return null;

        //  ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE

        for (HashPair<K,V> hashPair: this.buckets.get(hashFunction(key))) {
            if (hashPair.getKey().equals(key)) {
                return hashPair.getValue();
            }

        }

        return null;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        //A remove() method that takes a key as input and removes from the table the entry (i.e.
        //the HashPair) associated to this key. The method should return the value associated to
        //the key. If the key is not found, then the method returns null. This method should run
        //in O(1) on average.
        //ADD YOUR CODE BELOW HERE

        int index = hashFunction(key);


        for (HashPair<K,V> hashPair : buckets.get(index)) {
            if (hashPair.getKey().equals(key)) {
                V value = hashPair.getValue();
                buckets.get(index).remove(hashPair);
                numEntries -= 1;
                return value;
            }
        }

        return null;

        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
        //ADD YOUR CODE BELOW HERE 2In the slides we mention that these methods run in O(n + m) where n is the number of entries, and m is the
        //number of buckets. Note that if you have a good hash function and a max load factor of 0.75, then this is equivalent
        //to say that the method runs in O(m).

        ArrayList<LinkedList<HashPair<K,V>>> oldBuckets = buckets;
        buckets = new ArrayList<>(2 * this.numBuckets);

        for (int i = 0; i < 2 * this.numBuckets; i++) {
            buckets.add(new LinkedList<>());
        }

        this.numBuckets *= 2;
        this.numEntries = 0;

        for (LinkedList<HashPair<K, V>> oldBucket : oldBuckets) {
            for (HashPair<K, V> hashPair : oldBucket) {
                put(hashPair.getKey(), hashPair.getValue());
            }
        }

        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE

        ArrayList<K> keysList = new ArrayList<>(this.numEntries);

        for (HashPair<K,V> hashPair: this) {
            keysList.add(hashPair.getKey());
        }
    	return keysList;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE

        MyHashTable<V,K> valueTable = new MyHashTable<>(this.numEntries);

        for (HashPair<K,V> hashPair: this) {
            valueTable.put(hashPair.getValue(),hashPair.getKey());
        }

        ArrayList<V> valuesList = new ArrayList<>(valueTable.numEntries);

        for (HashPair<V,K> hashPair: valueTable) {
            valuesList.add(hashPair.getKey());
        }

        return valuesList;

        //ADD CODE ABOVE HERE
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that y
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        //The method returns an ArrayList containing all the keys in the
        //table, sorted in descending order based on the values they map to.

        //ADD CODE BELOW HERE
        ArrayList<HashPair<K,V>> hashPairs =  new ArrayList<>(results.numEntries);
        for (HashPair<K,V> hashPair: results) {
            hashPairs.add(hashPair);
        }

        sortValuesDescending(hashPairs, 0, results.numEntries -1);

        ArrayList<K> descendingKeys = new ArrayList<>(results.numEntries);
        for (HashPair<K,V> hashPair: hashPairs) {
            descendingKeys.add(hashPair.getKey());
        }
        return descendingKeys;
        //ADD CODE ABOVE HERE
    }

    private static <K, V extends Comparable<V>> void sortValuesDescending(ArrayList<HashPair<K,V>> hashPairs, int leftIndex, int rightIndex) {
        int pivotIndex;
        if (!(leftIndex >= rightIndex)) {
            pivotIndex = placeAndDivide(hashPairs, leftIndex, rightIndex);
            sortValuesDescending(hashPairs, leftIndex, pivotIndex - 1);
            sortValuesDescending(hashPairs, pivotIndex + 1, rightIndex);
        }
    }

    private static <K, V extends Comparable<V>> int placeAndDivide(ArrayList<HashPair<K,V>> hashPairs, int leftIndex, int rightIndex) {
        HashPair<K,V> pivot = hashPairs.get(rightIndex);
        int wall = leftIndex - 1;

        for (int i = leftIndex; i < rightIndex; i++) {
            if (hashPairs.get(i).getValue().compareTo(pivot.getValue()) > 0) {
                wall ++;
                swap(hashPairs, i, wall);

            }
        }
        swap(hashPairs, rightIndex, wall + 1);
        return wall + 1;
    }

    private static <K, V extends Comparable<V>> void swap(ArrayList<HashPair<K,V>> hashPairs, int indexOne, int indexTwo) {
        HashPair<K,V> temp = hashPairs.get(indexOne);
        hashPairs.set(indexOne, hashPairs.get(indexTwo));
        hashPairs.set(indexTwo, temp);
    }





    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
        Iterator<HashPair<K,V>> hashPairIterator;
    	
        //ADD YOUR CODE ABOVE HERENoSuchElementException
    	
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE

            ArrayList<HashPair<K,V>> hashPairs = new ArrayList<>();
            for (LinkedList<HashPair<K,V>> linkedList: buckets) {
                hashPairs.addAll(linkedList);
            }
            this.hashPairIterator = hashPairs.iterator();

        	
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
            return this.hashPairIterator.hasNext();
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE

        	return this.hashPairIterator.next();
            //ADD YOUR CODE ABOVE HERE
        }
        
    }
}
