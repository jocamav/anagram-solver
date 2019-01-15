package com.jc.anagram.service;

import java.util.HashSet;

public interface DictionaryService {

    public static final int MIN_LETTERS = 3;
    
	/**
	 * Method to get the list of anagrams of a given word
	 * @param word Word to get the list of anagrams
	 * @return HashSet of anagrams of the given word
	 */
	HashSet<String> getAnagrams(String word);
	
	/**
	 * Method to check if a given word can be a word. A word must have at least 3 characters
	 * @param word Word to check
	 * @return true if it has the min length to be a word; false otherwise
	 */
	boolean canBeAWord(String word);
}
