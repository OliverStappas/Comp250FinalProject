
import java.util.ArrayList;

public class Twitter {
	//You will not be getting a stress tester for the Twitter class. However, you can make your own, since you have
	// estimated time for the hash table class for different running time; if you test your twitter class with the same
	// number of tweets on a method that is expected to have the same running time you should get a similar time.
	
	//ADD YOUR CODE BELOW HERE
	MyHashTable<String, ArrayList<Tweet>> dateToTweets;
	MyHashTable<String, Tweet> authorToLatestTweet;
	MyHashTable<String, Integer> wordsToOccurences;
	MyHashTable<String, String> uniqueStopWords;

	//ADD CODE ABOVE HERE 
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE

		this.dateToTweets = new MyHashTable<>(tweets.size());
		this.authorToLatestTweet = new MyHashTable<>(tweets.size());
		this.wordsToOccurences = new MyHashTable<>(tweets.size());

		uniqueStopWords = new MyHashTable<>(stopWords.size());
		for (String stopWord: stopWords) {
			uniqueStopWords.put(stopWord.toLowerCase(), "bbbbb");
		}

		for (Tweet tweet: tweets) {
			this.addTweet(tweet);
		}

		//ADD CODE ABOVE HERE 
	}
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
			//Depending on your implementation there might be different data structures within your attributes of Twitter or there might be multiple MyHashTables.
		// Your addTweet() should add this tweet into each one of your data structures accordingly. And you might use different keys/values in each of your MyHashTable,
		// so addTweet should take care of which info is key and which is value, etc.

		//As a confirmation, for my addTweet() method I am thinking of adding the tweet given to an arraylist(which is a field of my twitter where I add all the tweets
		// inside twitter). However, since addTweet() should be O(1) if this arraylist gets very long, this would cause a fault in the time complexity right?
		//RESPONSE: You can add to an array list in O(1). You cannot insert in constant time.
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE

		String tweetDate = t.getDateAndTime().split(" ")[0];
		String tweetAuthor = t.getAuthor();

		Tweet latestTweet = authorToLatestTweet.get(tweetAuthor);
		if (latestTweet == null || t.compareTo(latestTweet) > 0 ) {  // Comparing date
			authorToLatestTweet.put(tweetAuthor, t);
		}

		if (dateToTweets.get(tweetDate) == null) {
			dateToTweets.put(tweetDate, new ArrayList<>(5));
		}
		dateToTweets.get(tweetDate).add(t);

		ArrayList<String> words = getWords(t.getMessage());
		MyHashTable<String, String> uniqueWords = new MyHashTable<>(words.size());
		for (String word : words) {
			uniqueWords.put(word.toLowerCase(), "aaaa");
		}

		for (String word : uniqueWords.keys()) {
			if (uniqueStopWords.get(word) == null) {
				if (wordsToOccurences.get(word) == null) {
                    wordsToOccurences.put(word, 0);

                }
				wordsToOccurences.put(word, wordsToOccurences.get(word) + 1);
			}
		}

		//ADD CODE ABOVE HERE 
	}
	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
    	//Note that keys in a HashTable must be unique and that two different authors may post tweets at the exact same second.
		//
		//If s1 and s2 are two strings, then use s1.compareTo(s2) returns 0 if s1 = s2, a positive integer is s1 > s2 and a negative integer if s1 < s2.

        //ADD CODE BELOW HERE

		// get author: O(1)

		// author -> tweets map O(N)
		//

    	return authorToLatestTweet.get(author);
    	
        //ADD CODE ABOVE HERE 
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE
		
		// date -> twees
		
    	return dateToTweets.get(date);
    	
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once.  O(NlogN) 
	 */
    public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE

    	return MyHashTable.fastSort(wordsToOccurences);
    	
        //ADD CODE ABOVE HERE    	
    }
    
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

    

}
