package com.jc.anagram.service;

import java.util.Set;

public interface AnagramService {
	/**
	 * Method to get the list of anagrams for a given word. The method implementation should replace special characters and blank spaces
	 * @param word Word for search the anagrams
	 * @return Set of anagrams of the given word
	 */
	Set<String> getAllAnagramsPhrases(String word);
	
	/**
	 * Method to get the list of anagrams for a certain word. It looks only for the direct anagrams of a word. So it doesn't replace any
	 * special character of blank space
	 * @param word Word to search the anagrams
	 * @return Set of anagrams of the given word
	 */
	Set<String> getAllAnagramsWords(String word);
}
