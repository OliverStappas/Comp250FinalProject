
import java.util.ArrayList;
import java.util.HashMap;
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

        this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(initialCapacity);
        this.numBuckets = initialCapacity;
        this.numEntries = (int) (initialCapacity * MAX_LOAD_FACTOR);

        for (int i = 0; i < numBuckets; i++) {
            // Initialising to null
            this.buckets.add(null);
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
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE


    	
    	return null;
        
        //  ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE

//        for (HashPair hp: this) {
//            if (hp.getKey().equals(key)) {
//                return hp.getValue();
//            }
//        }

        for (HashPair hashPair: this.buckets.get(hashFunction(key))) {
            if (hashPair.getKey().equals(key)) {
                return (V) hashPair.getValue();
            }

        }

        return null;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
        
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

        ArrayList<LinkedList<HashPair<K,V>>> temp = buckets;
        buckets = new ArrayList<LinkedList<HashPair<K,V>>>(2 * this.numBuckets);

        for (int i = 0; i < 2 * this.numBuckets; i++) {
            buckets.add(null);
        }

        // Now size is made zero
        // and we loop through all the nodes in the original bucket list(temp)
        // and insert it into the new list
        size = 0;
        numBuckets *= 2;

        for (int i = 0; i < temp.size(); i++) {

            // head of the chain at that index
            MapNode<K, V> head = temp.get(i);

            while (head != null) {
                K key = head.key;
                V val = head.value;

                // calling the insert function for each node in temp
                // as the new list is now the bucketArray
                insert(key, val);
                head = head.next;
            }
        }



        this.numBuckets *= 2;

//FROM INTERNET:v
//        System.out.println("\n***Rehashing Started***\n");
//
//        // The present bucket list is made temp
//        ArrayList<MapNode<K, V> > temp = buckets;
//
//        // New bucketList of double the old size is created
//        buckets = new ArrayList<MapNode<K, V> >(2 * numBuckets);
//
//        for (int i = 0; i < 2 * numBuckets; i++) {
//            // Initialised to null
//            buckets.add(null);
//        }
//        // Now size is made zero
//        // and we loop through all the nodes in the original bucket list(temp)
//        // and insert it into the new list
//        size = 0;
//        numBuckets *= 2;
//
//        for (int i = 0; i < temp.size(); i++) {
//
//            // head of the chain at that index
//            MapNode<K, V> head = temp.get(i);
//
//            while (head != null) {
//                K key = head.key;
//                V val = head.value;
//
//                // calling the insert function for each node in temp
//                // as the new list is now the bucketArray
//                insert(key, val);
//                head = head.next;
//            }
//        }
//
//        System.out.println("\n***Rehashing Ended***\n");

        //ADD YOUR CODE ABOVE HERE


    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
        ArrayList<K> keyList = new ArrayList<>(numBuckets);
        for (LinkedList<HashPair<K,V>> linkedList : this.buckets) {
            for (HashPair<K,V> hashPair: linkedList) {
                keyList.add(hashPair.getKey());
            }
        }
        
    	return keyList;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE

        ArrayList<K> valuesList = new ArrayList<>(numBuckets);
        for (LinkedList<HashPair<K,V>> linkedList : this.buckets) {
            for (HashPair<K,V> hashPair: linkedList) {
                valuesList.add(hashPair.getKey());
            }
        }

        return valuesList;
        //ADD CODE ABOVE HERE
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
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
    	
    	return null;
		
        //ADD CODE ABOVE HERE
    }

    
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
    	
        //ADD YOUR CODE ABOVE HERE
    	
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
        	
        	return false;
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	
        	return null;
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
    }
}
