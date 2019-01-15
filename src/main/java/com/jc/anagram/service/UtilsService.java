package com.jc.anagram.service;

public interface UtilsService {
	/**
	 * Method to get a given word with all the character shorted (e.g. best -> bets)
	 * @param word Word to sort
	 * @return Word with all the characters sorted
	 */
	String getSortedChars(String word);
}
